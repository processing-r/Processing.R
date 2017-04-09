package rprocessing.mode.run;

import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import processing.app.Messages;
import rprocessing.mode.RLangEditor;
import rprocessing.mode.RLangMode;

/**
 * 
 * @author github.com/gaocegege
 */
public class SketchServiceManager implements ModeService {

    private static final String DEBUG_SKETCH_RUNNER_KEY = "$SKETCHRUNNER$";

    private static final boolean VERBOSE = Boolean.parseBoolean(System.getenv("VERBOSE_RLANG_MODE"));

    @SuppressWarnings("unused")
    private static void log(final String msg) {
        if (VERBOSE) {
            System.err.println(SketchServiceManager.class.getSimpleName() + ": " + msg);
        }
    }

    private final RLangMode                        mode;
    private final Map<String, SketchServiceRunner> sketchServices       = new HashMap<>();
    private final Set<String>                      killedSketchServices = new HashSet<>();
    private volatile boolean                       isStarted            = false;

    /**
     * This is used when {@link PythonMode#SKETCH_RUNNER_FIRST} is true. This lets
     * use run the SketchRunner in a debugger, for example.
     */
    private SketchService                          debugSketchRunner;

    public SketchServiceManager(final RLangMode mode) {
        this.mode = mode;
    }

    public SketchServiceRunner createSketchService(final RLangEditor editor) {
        final SketchServiceRunner p;
        if (RLangMode.SKETCH_RUNNER_FIRST) {
            p = new SketchServiceRunner(mode, editor, debugSketchRunner);
            sketchServices.put(DEBUG_SKETCH_RUNNER_KEY, p);
        } else {
            p = new SketchServiceRunner(mode, editor);
            sketchServices.put(editor.getId(), p);
        }
        return p;
    }

    public void destroySketchService(final RLangEditor editor) {
        final SketchServiceRunner process = sketchServices.remove(editor.getId());
        killedSketchServices.add(editor.getId());
        if (process != null) {
            process.shutdown();
        }
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void start() {
        isStarted = true;
        try {
            if (RLangMode.SKETCH_RUNNER_FIRST) {
                final ModeService stub = (ModeService) RMIUtils.export(this);
                final ModeWaiter modeWaiter = RMIUtils.lookup(ModeWaiter.class);
                modeWaiter.modeReady(stub);
            } else {
                RMIUtils.bind(this, ModeService.class);
            }
        } catch (final Exception e) {
            Messages.showError("RLangMode Error", "Cannot start rlang sketch service.", e);
            return;
        }
    }

    private SketchServiceRunner processFor(final String editorId) {
        if (RLangMode.SKETCH_RUNNER_FIRST) {
            return sketchServices.get(DEBUG_SKETCH_RUNNER_KEY);
        }

        final SketchServiceRunner p = sketchServices.get(editorId);
        if (p == null) {
            throw new RuntimeException("I somehow got a message from the sketch runner for "
                                       + editorId
                                       + " but don't have an active service process for it!");
        }
        return p;
    }

    @Override
    public void handleReady(final String editorId, final SketchService service) {
        if (RLangMode.SKETCH_RUNNER_FIRST) {
            log("Debug sketch runner is ready.");
            debugSketchRunner = service;
            return;
        }
        processFor(editorId).handleReady(service);
    }

    @Override
    public void handleSketchException(final String editorId, final Exception e) {
        processFor(editorId).handleSketchException(e);
    }

    @Override
    public void handleSketchStopped(final String editorId) {
        // The sketch runner might cause this to be fired during shtdown.
        if (killedSketchServices.remove(editorId)) {
            return;
        }
        processFor(editorId).handleSketchStopped();
    }

    @Override
    public void handleSketchMoved(final String editorId, final Point leftTop) {
        processFor(editorId).handleSketchMoved(leftTop);
    }

    @Override
    public void printStdErr(final String editorId, final String s) {
        processFor(editorId).printStdErr(s);
    }

    @Override
    public void printStdOut(final String editorId, final String s) {
        processFor(editorId).printStdOut(s);
    }
}