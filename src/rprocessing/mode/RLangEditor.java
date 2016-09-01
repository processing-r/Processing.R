package rprocessing.mode;

import javax.swing.JMenu;

import processing.app.Base;
import processing.app.Formatter;
import processing.app.Mode;
import processing.app.ui.Editor;
import processing.app.ui.EditorException;
import processing.app.ui.EditorState;
import processing.app.ui.EditorToolbar;

/**
 * 
 * @author github.com/gaocegege
 */
public class RLangEditor extends Editor {

    /**  */
    private static final long serialVersionUID = -5993950683909551427L;

    /**
     * @param base
     * @param path
     * @param state
     * @param mode
     * @throws EditorException
     */
    protected RLangEditor(Base base, String path, EditorState state, Mode mode)
                                                                               throws EditorException {
        super(base, path, state, mode);
    }

    /** 
     * @see processing.app.ui.Editor#buildFileMenu()
     */
    @Override
    public JMenu buildFileMenu() {
        return null;
    }

    /** 
     * @see processing.app.ui.Editor#buildHelpMenu()
     */
    @Override
    public JMenu buildHelpMenu() {
        return null;
    }

    /** 
     * @see processing.app.ui.Editor#buildSketchMenu()
     */
    @Override
    public JMenu buildSketchMenu() {
        return null;
    }

    /** 
     * @see processing.app.ui.Editor#createFormatter()
     */
    @Override
    public Formatter createFormatter() {
        return null;
    }

    /** 
     * @see processing.app.ui.Editor#createToolbar()
     */
    @Override
    public EditorToolbar createToolbar() {
        return null;
    }

    /** 
     * @see processing.app.ui.Editor#deactivateRun()
     */
    @Override
    public void deactivateRun() {
    }

    /** 
     * @see processing.app.ui.Editor#getCommentPrefix()
     */
    @Override
    public String getCommentPrefix() {
        return null;
    }

    /** 
     * @see processing.app.ui.Editor#handleImportLibrary(java.lang.String)
     */
    @Override
    public void handleImportLibrary(String arg0) {
    }

    /** 
     * @see processing.app.ui.Editor#internalCloseRunner()
     */
    @Override
    public void internalCloseRunner() {
    }

}
