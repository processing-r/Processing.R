package rprocessing.mode;

import java.awt.event.InputEvent;

import processing.app.ui.Editor;
import processing.app.ui.EditorToolbar;

/**
 * 
 * @author github.com/gaocegege
 */
public class RLangToolbar extends EditorToolbar {
    /**  */
    private static final long serialVersionUID = -4227659009839101912L;

    public RLangToolbar(final Editor editor) {
        super(editor);
    }

    @Override
    public void handleRun(final int modifiers) {
        final RLangEditor peditor = (RLangEditor) editor;
        final boolean shift = (modifiers & InputEvent.SHIFT_MASK) != 0;
        if (shift) {
            peditor.handlePresent();
        } else {
            peditor.handleRun();
        }
    }

    @Override
    public void handleStop() {
        final RLangEditor peditor = (RLangEditor) editor;
        peditor.handleStop();
    }
}
