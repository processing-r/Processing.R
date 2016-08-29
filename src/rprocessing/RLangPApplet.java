package rprocessing;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.renjin.script.RenjinScriptEngine;
import org.renjin.sexp.Closure;

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

    private static final String      PROCESSING_VAR_NAME = "processing";

    private final String             programText;

    /** Engine to interpret R code */
    private final RenjinScriptEngine renjinEngine;

    public RLangPApplet(final ScriptEngine renjinEngine, final String programText) {
        this.renjinEngine = (RenjinScriptEngine) renjinEngine;
        this.programText = programText;
    }

    public void AddPAppletToRContext() {
        this.renjinEngine.put(PROCESSING_VAR_NAME, this);
    }

    @Override
    public void setup() {
        try {
            wrapProcessingVariables();
            this.renjinEngine.eval(this.programText);
        } catch (ScriptException e) {
            System.out.println(e);
        }
    }

    @Override
    public void draw() {
        Closure c = (Closure) this.renjinEngine.get("draw");
        c.doApply(this.renjinEngine.getTopLevelContext());
    }

    /*
     * Helper functions
     */

    protected void wrapProcessingVariables() {
        this.renjinEngine.put("width", width);
        this.renjinEngine.put("height", height);
        this.renjinEngine.put("displayWidth", displayWidth);
        this.renjinEngine.put("displayHeight", displayHeight);
        this.renjinEngine.put("focused", focused);
        this.renjinEngine.put("keyPressed", keyPressed);
        this.renjinEngine.put("frameCount", frameCount);
        this.renjinEngine.put("frameRate", frameRate);
    }

    /*
     * Wrapper functions
     */

    public void point(double x, double y) {
        super.point((float) x, (float) y);
    }

    public void line(double posAX, double posAY, double posBX, double posBY) {
        super.line((float) posAX, (float) posAY, (float) posBX, (float) posBY);
    }
}
