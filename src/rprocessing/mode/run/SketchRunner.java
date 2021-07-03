package rprocessing.mode.run;

import java.awt.Point;
import java.rmi.RemoteException;

import processing.app.SketchException;
import processing.core.PApplet;
import processing.core.PConstants;
import rprocessing.Runner;
import rprocessing.SketchPositionListener;
import rprocessing.exception.RMIRuntimeException;
import rprocessing.exception.RSketchError;
import rprocessing.mode.RLangMode;
import rprocessing.mode.run.RMIUtils.RMIProblem;
import rprocessing.util.Printer;

/**
 *
 * @author github.com/gaocegege
 */
public class SketchRunner implements SketchService {

  private static final boolean VERBOSE = Boolean.parseBoolean(System.getenv("VERBOSE_RLANG_MODE"));

  private final String id;
  private final ModeService modeService;
  private Thread runner = null;
  private volatile boolean shutdownWasRequested = false;

  private static void log(final String msg) {
    if (VERBOSE) {
      System.err.println(SketchRunner.class.getSimpleName() + ": " + msg);
    }
  }

  public SketchRunner(final String id, final ModeService modeService) {
    this.id = id;
    this.modeService = modeService;
    if (PApplet.platform == PConstants.MACOSX) {
      try {
        OSXAdapter.setQuitHandler(this, this.getClass().getMethod("preventUserQuit"));
      } catch (final Throwable e) {
        System.err.println(e.getMessage());
      }
    }
    // new Thread(new Runnable() {
    // @Override
    // public void run() {
    // Runner.warmup();
    // }
    // }, "SketchRunner Warmup Thread").start();
  }

  /**
   * On Mac, even though this app has no menu, there's still a built-in cmd-Q handler that, by
   * default, quits the app. Because starting up the {@link SketchRunner} is expensive, we'd prefer
   * to leave the app running.
   * <p>
   * This function responds to a user cmd-Q by stopping the currently running sketch, and rejecting
   * the attempt to quit.
   * <p>
   * But if we've received a shutdown request from the {@link SketchServiceProcess} on the PDE VM,
   * then we permit the quit to proceed.
   *
   * @return true iff the SketchRunner should quit.
   */
  public boolean preventUserQuit() {
    if (shutdownWasRequested) {
      log("Permitting quit.");
      return true;
    }
    log("Cancelling quit, but stopping sketch.");
    new Thread(new Runnable() {
      @Override
      public void run() {
        stopSketch();
      }
    }).start();
    return false;
  }

  @Override
  public void shutdown() {
    shutdownWasRequested = true;
    log("Calling system.exit(0)");
    System.exit(0);
  }

  @Override
  public void startSketch(final PdeSketch sketch) {
    runner = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          try {
            final Printer stdout = new Printer() {
              @Override
              public void print(final Object o) {
                try {
                  modeService.printStdOut(id, String.valueOf(o));
                } catch (final RemoteException exception) {
                  System.err.println(exception);
                }
              }

              @Override
              public void println(final Object o) {
                try {
                  modeService.printStdOut(id, String.valueOf(o) + "\n");
                } catch (final RemoteException exception) {
                  System.err.println(exception);
                }
              }
            };
            final Printer stderr = new Printer() {
              @Override
              public void print(final Object o) {
                try {
                  modeService.printStdErr(id, String.valueOf(o));
                } catch (final RemoteException exception) {
                  System.err.println(exception);
                }
              }

              @Override
              public void println(final Object o) {
                try {
                  modeService.printStdErr(id, String.valueOf(o) + "\n");
                } catch (final RemoteException exception) {
                  System.err.println(exception);
                }
              }
            };
            final SketchPositionListener sketchPositionListener = new SketchPositionListener() {
              @Override
              public void sketchMoved(final Point leftTop) {
                try {
                  modeService.handleSketchMoved(id, leftTop);
                } catch (final RemoteException exception) {
                  System.err.println(exception);
                }
              }
            };
            log("Run the sketch.");
            Runner.runSketchBlocking(sketch, stdout, stderr, sketchPositionListener);
          } catch (final RSketchError exception) {
            log("Sketch runner caught " + exception);
            modeService.handleSketchException(id, exception);
          } catch (final Exception exception) {
            log("Sketch runner caught Exception:" + exception);
            modeService.handleSketchException(id, convertREvalError(exception));
          } finally {
            log("Handling sketch stoppage...");
            modeService.handleSketchStopped(id);
          }
        } catch (final RemoteException exception) {
          log("Sketch runner caught RemoteException:" + exception);
          log(exception.toString());
        }
        // Exiting; no need to interrupt and join it later.
        runner = null;
      }
    }, "Processing.R mode runner");
    runner.start();
  }

  @Override
  public void stopSketch() {
    log("stopSketch()");
    if (runner != null) {
      log("Interrupting runner thread.");
      runner.interrupt();
      try {
        log("Joining runner thread.");
        runner.join();
        log("Runner thread terminated normally.");
      } catch (final InterruptedException exception) {
        log("Interrupted while joined to runner thread.");
      }
      runner = null;
    }
  }

  /**
   * SketchServiceRunner will run this function in a subprocess in its constructor function.
   */
  public static void main(final String[] args) {
    final String id = args[0];
    // If env var SKETCH_RUNNER_FIRST=true then SketchRunner will wait for a ping from the Mode
    // before registering itself as the sketch runner.
    if (RLangMode.SKETCH_RUNNER_FIRST) {
      try {
        waitForMode(id);
      } catch (RMIRuntimeException exception) {
        System.err.println(exception);
        System.exit(-1);
      }
    } else {
      try {
        startSketchRunner(id);
      } catch (RMIRuntimeException exception) {
        System.err.println(exception);
        System.exit(-1);
      }
    }
  }

  private static class ModeWaiterImpl implements ModeWaiter {
    private final String id;

    public ModeWaiterImpl(final String id) {
      this.id = id;
    }

    @Override
    public void modeReady(final ModeService modeService) throws RMIRuntimeException {
      try {
        launch(id, modeService);
      } catch (final Exception exception) {
        throw new RMIRuntimeException(exception);
      }
    }
  }

  private static void waitForMode(final String id) throws RMIRuntimeException {
    try {
      RMIUtils.bind(new ModeWaiterImpl(id), ModeWaiter.class);
    } catch (final RMIProblem exception) {
      throw new RMIRuntimeException(exception);
    }
  }

  private static void startSketchRunner(final String id) throws RMIRuntimeException {
    try {
      final ModeService modeService = RMIUtils.lookup(ModeService.class);
      launch(id, modeService);
    } catch (final Exception exception) {
      throw new RMIRuntimeException(exception);
    }
  }

  private static void launch(final String id, final ModeService modeService)
      throws RMIProblem, RemoteException {
    final SketchRunner sketchRunner = new SketchRunner(id, modeService);
    final SketchService stub = (SketchService) RMIUtils.export(sketchRunner);
    log("Calling mode's handleReady().");
    modeService.handleReady(id, stub);
    Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
      @Override
      public void run() {
        log("Exiting; telling modeService.");
        try {
          modeService.handleSketchStopped(id);
        } catch (final RemoteException exception) {
          // nothing we can do about it now.
        }
      }
    }));
  }

  private SketchException convertREvalError(final Exception exception) {
    return new SketchException(exception.getMessage());
  }
}
