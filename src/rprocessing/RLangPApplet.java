package rprocessing;

import javax.script.ScriptEngine;

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

    @Override
    public void setup() {
        //        try {
        this.arc(0, 12, 12, 24, 34, 59);
        //            this.renjinEngine.eval("typeof(a)");
        //            this.renjinEngine.eval(programText);
        //        } catch (ScriptException e) {
        //            System.out.println(e);
        //        }
    }
}
