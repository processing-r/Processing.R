package rprocessing.mode;

import java.io.File;

import processing.app.Base;
import processing.app.Mode;
import processing.app.ui.Editor;
import processing.app.ui.EditorException;
import processing.app.ui.EditorState;

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
    public RLangMode(Base base, File folder) {
        super(base, folder);

    }

    public static final boolean VERBOSE = Boolean.parseBoolean(System.getenv("VERBOSE_RLANG_MODE"));

    /** 
     * @see processing.app.Mode#createEditor(processing.app.Base, java.lang.String, processing.app.ui.EditorState)
     */
    @Override
    public Editor createEditor(Base base, final String path, final EditorState state)
                                                                                     throws EditorException {
        return null;
    }

    /** 
     * @see processing.app.Mode#getDefaultExtension()
     */
    @Override
    public String getDefaultExtension() {
        return null;
    }

    /** 
     * @see processing.app.Mode#getExtensions()
     */
    @Override
    public String[] getExtensions() {
        return null;
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
