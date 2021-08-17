package rprocessing;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.script.ScriptException;

import processing.core.PApplet;
import processing.core.PConstants;
import rprocessing.exception.NotFoundException;
import rprocessing.exception.RSketchError;
import rprocessing.lancher.StandaloneSketch;
import rprocessing.mode.library.LibraryImporter;
import rprocessing.util.Printer;
import rprocessing.util.StreamPrinter;

/**
 * R script runner
 *
 * @author github.com/gaocegege
 */
public class Runner {

  public static RunnableSketch sketch;

  private static final boolean VERBOSE = Boolean.parseBoolean(System.getenv("VERBOSE_RLANG_MODE"));

  private static final String ARCH;

  static {
    log("Getting the architecture.");
    final int archBits = Integer.parseInt(System.getProperty("sun.arch.data.model"));
    if (PApplet.platform == PConstants.MACOSX) {
      ARCH = "macosx" + archBits;
    } else if (PApplet.platform == PConstants.WINDOWS) {
      ARCH = "macosx" + archBits;
    } else if (PApplet.platform == PConstants.LINUX) {
      ARCH = "linux" + archBits;
    } else {
      ARCH = "unknown" + archBits;
    }
  }

  private static void log(final Object... objs) {
    if (!VERBOSE) {
      return;
    }
    for (final Object o : objs) {
      System.err.print(Runner.class.getSimpleName() + ": " + String.valueOf(o));
    }
    System.err.println();
  }

  public static void main(final String[] args) throws Exception {
    if (args.length < 1) {
      throw new NotFoundException("The path of your R script is needed as an argument.");
    }
    try {
      log("Run the runner in Main.");
      sketch = new StandaloneSketch(args);
      runSketchBlocking(sketch, new StreamPrinter(System.out), new StreamPrinter(System.err));

      // See https://github.com/processing-r/Processing.R/issues/89
      // It can't be reproduced, so comment the statement.
      // System.exit(0);
    } catch (final Throwable t) {
      System.err.println("Runner raises an exception: " + t);
      System.exit(-1);
    }
  }

  public static synchronized void runSketchBlocking(final RunnableSketch sketch,
      final Printer stdout, final Printer stderr) throws NotFoundException, RSketchError {
    runSketchBlocking(sketch, stdout, stderr, null);
  }

  public static synchronized void runSketchBlocking(final RunnableSketch sketch,
      final Printer stdout, final Printer stderr,
      final SketchPositionListener sketchPositionListener) throws NotFoundException, RSketchError {
    final String[] args = sketch.getPAppletArguments();

    log("Tring to initialize RLangPApplet.");
    RLangPApplet rp = new RLangPApplet(sketch.getMainCode(), stdout);
    log("Adding processing variable into R top context.");
    rp.addPAppletToRContext();
    rp.evaluateCoreCode();

    final List<File> libDirs = sketch.getLibraryDirectories();
    if (libDirs != null) {
      try {
        LibraryImporter libraryImporter = new LibraryImporter(libDirs, rp.getRenjinEngine());
        final Set<String> libs = new HashSet<>();
        for (final File dir : libDirs) {
          searchForExtraStuff(dir, libs);
        }
        libraryImporter.injectIntoScope();
      } catch (ScriptException se) {
        throw RSketchError.toSketchException(se);
      }
    }

    rp.runBlock(args);
  }

  /**
   * Recursively search the given directory for jar files and directories containing dynamic
   * libraries, adding them to the classpath and the library path respectively.
   */
  private static void searchForExtraStuff(final File dir, final Set<String> entries) {
    if (dir == null) {
      throw new IllegalArgumentException("null dir");
    }

    final String dirName = dir.getName();
    if (!dirName.equals(ARCH) && dirName.matches("^(macosx|windows|linux)(32|64)$")) {
      log("Ignoring wrong architecture " + dir);
      return;
    }

    // log("Searching: ", dir);

    final File[] dlls = dir.listFiles(new FilenameFilter() {
      @Override
      public boolean accept(final File dir, final String name) {
        return name.matches("^.+\\.(so|dll|jnilib|dylib)$");
      }
    });
    if (dlls != null && dlls.length > 0) {
      entries.add(dir.getAbsolutePath());
    // } else {
    //     log("No DLLs in ", dir);
    }

    final File[] jars = dir.listFiles(new FilenameFilter() {
      @Override
      public boolean accept(final File dir, final String name) {
        return name.matches("^.+\\.jar$");
      }
    });
    if (!(jars == null || jars.length == 0)) {
      for (final File jar : jars) {
        entries.add(jar.getAbsolutePath());
      }
    // } else {
    //     log("No JARs in ", dir);
    }

    final File[] dirs = dir.listFiles(new FileFilter() {
      @Override
      public boolean accept(final File f) {
        return f.isDirectory() && f.getName().charAt(0) != '.';
      }
    });
    if (!(dirs == null || dirs.length == 0)) {
      for (final File d : dirs) {
        searchForExtraStuff(d, entries);
      }
    // } else {
    //   log("No dirs in ", dir);
    }
  }
}
