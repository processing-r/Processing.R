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

    public void point(double x, double y) {
        super.point((float) x, (float) y);
    }

    public void line(double posAX, double posAY, double posBX, double posBY) {
        super.line((float) posAX, (float) posAY, (float) posBX, (float) posBY);
    }

    @Override
    public void setup() {
        try {
            this.renjinEngine.eval(programText);
        } catch (ScriptException e) {
            System.out.println(e);
        }
    }
}
