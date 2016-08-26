package rprocessing;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import processing.core.PApplet;

/**
 * RlangPApplet
 * PApplet for R language, powered by Renjin.
 * 
 * @author github.com/gaocegege
 */
public class RLangPApplet extends PApplet {

    /**
     * Mode for Processing.
     * 
     * @author github.com/gaocegege
     */
    private enum Mode {
        STATIC, ACTIVE, MIXED
    }

    private final String       programText;

    /** Engine to interpret R code */
    // Useless
    private final ScriptEngine renjinEngine;

    public RLangPApplet(final ScriptEngine renjinEngine, final String programText) {
        this.renjinEngine = renjinEngine;
        this.programText = programText;
    }

    public void processSketch() throws ScriptException {
        renjinEngine.eval(programText);
    }
}
