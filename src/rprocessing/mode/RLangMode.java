package rprocessing.mode;

import java.io.File;

import processing.app.Base;
import processing.app.Messages;
import processing.app.Mode;
import processing.app.ui.Editor;
import processing.app.ui.EditorException;
import processing.app.ui.EditorState;
import rprocessing.mode.run.SketchRunner;
import rprocessing.mode.run.SketchServiceManager;

/**
 * 
 * @author github.com/gaocegege
 */
public class RLangMode extends Mode {
    /**
     * TODO: Constructor.
     * @param base
     * @param folder
     */
    public RLangMode(final Base base, final File folder) {
        super(base, folder);

        sketchServiceManager = new SketchServiceManager(this);
    }

    public static final boolean        VERBOSE             = Boolean.parseBoolean(System
                                                               .getenv("VERBOSE_RLANG_MODE"));

    /**
     * If the environment variable SKETCH_RUNNER_FIRST is equal to the string "true", then
     * {@link RLangMode} expects that the {@link SketchRunner} is already running and waiting
     * to be communicated with (as when you're debugging it in Eclipse, for example).
     */
    public static final boolean        SKETCH_RUNNER_FIRST = Boolean.parseBoolean(System
                                                               .getenv("SKETCH_RUNNER_FIRST"));

    private final SketchServiceManager sketchServiceManager;

    public SketchServiceManager getSketchServiceManager() {
        return sketchServiceManager;
    }

    /** 
     * @see processing.app.Mode#createEditor(processing.app.Base, java.lang.String, processing.app.ui.EditorState)
     */
    @Override
    public Editor createEditor(Base base, final String path, final EditorState state)
                                                                                     throws EditorException {
        // Lazily start the sketch running service only when an editor is required.
        if (!sketchServiceManager.isStarted()) {
            sketchServiceManager.start();
        }

        try {
            return new RLangEditor(base, path, state, this);
        } catch (EditorException e) {
            Messages.showError("Editor Exception", "Issue Creating Editor", e);
            return null;
        }
    }

    /** 
     * @see processing.app.Mode#getDefaultExtension()
     */
    @Override
    public String getDefaultExtension() {
        return "rlangpde";
    }

    @Override
    public String getModuleExtension() {
        return "r";
    }

    /** 
     * @see processing.app.Mode#getExtensions()
     */
    @Override
    public String[] getExtensions() {
        return new String[] { getDefaultExtension(), getModuleExtension() };
    }

    /** 
     * @see processing.app.Mode#getIgnorable()
     */
    @Override
    public String[] getIgnorable() {
        return null;
    }

    /** 
     * @see processing.app.Mode#getTitle()
     */
    @Override
    public String getTitle() {
        return "R Language";
    }
}
