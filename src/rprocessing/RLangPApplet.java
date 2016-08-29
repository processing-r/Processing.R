package rprocessing;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.renjin.parser.RParser;
import org.renjin.script.RenjinScriptEngine;
import org.renjin.sexp.Closure;
import org.renjin.sexp.ExpressionVector;
import org.renjin.sexp.FunctionCall;
import org.renjin.sexp.SEXP;

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

    // A static-mode sketch must be interpreted from within the setup() method.
    // All others are interpreted during construction in order to harvest method
    // definitions, which we then invoke during the run loop.
    private final Mode               mode;

    /** The name of processing's PApplet in R top context. */
    private static final String      PROCESSING_VAR_NAME = "processing";

    /** Program Code */
    private final String             programText;

    /** Engine to interpret R code */
    private final RenjinScriptEngine renjinEngine;

    public RLangPApplet(final ScriptEngine renjinEngine, final String programText) {
        this.renjinEngine = (RenjinScriptEngine) renjinEngine;
        this.programText = programText;
        this.mode = this.detectMode();
        this.prePassCode();
    }

    /**
     * Evaluate all the function calls.
     */
    public void prePassCode() {
        SEXP source = RParser.parseSource(this.programText + "\n", "inline-string");
        if (source.getClass().equals(ExpressionVector.class)) {
            ExpressionVector ev = (ExpressionVector) source;
            for (int i = 0; i < ev.length(); ++i) {
                if (ev.get(i).getClass().equals(FunctionCall.class)) {
                    this.renjinEngine.getTopLevelContext().evaluate(ev.get(i),
                        this.renjinEngine.getTopLevelContext().getEnvironment());
                }
            }
        }
    }

    /**
     * TODO: Detect the mode.
     */
    private Mode detectMode() {
        return Mode.STATIC;
    }

    /**
     * Add PApplet instance to R top context
     * Notice: DO NOT do it in constructor.
     */
    public void AddPAppletToRContext() {
        this.renjinEngine.put(PROCESSING_VAR_NAME, this);
    }

    /**
     * TODO: Evaluate settings before the main program.
     * @see processing.core.PApplet#settings()
     */
    @Override
    public void settings() {
        Object obj = this.renjinEngine.get("settings");
        if (obj.getClass() == Closure.class) {
            ((Closure) obj).doApply(this.renjinEngine.getTopLevelContext());
        } else if (mode == Mode.STATIC) {
            // TODO: Implement Static Mode.
        }
    }

    /**
     * Evaluate the program code.
     * @see processing.core.PApplet#setup()
     */
    @Override
    public void setup() {
        if (this.mode == Mode.STATIC) {
            try {
                wrapProcessingVariables();
                this.renjinEngine.eval(this.programText);
                //                System.out.println(this.renjinEngine.getTopLevelContext().getEnvironment()
                //                    .getVariable("settings"));
            } catch (ScriptException e) {
                System.out.println(e);
            }
        } else if (this.mode == Mode.ACTIVE) {
            Object obj = this.renjinEngine.get("setup");
            if (obj.getClass() == Closure.class) {
                ((Closure) obj).doApply(this.renjinEngine.getTopLevelContext());
            }
        } else {
            // TODO: implement MIX Mode.
        }
    }

    /**
     * Call the draw function in R script.
     * @see processing.core.PApplet#draw()
     */
    @Override
    public void draw() {
        Object obj = this.renjinEngine.get("draw");
        if (obj.getClass() == Closure.class) {
            ((Closure) obj).doApply(this.renjinEngine.getTopLevelContext());
        }
    }

    /*
     * Helper functions
     */

    /**
     * Set Environment variables in R top context.
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
     * 
     * float is not implemented in R, so processing.r need to cast double to
     * float.
     */

    public void size(double width, double height) {
        super.size((int) width, (int) height);
    }

    public void point(double x, double y) {
        super.point((float) x, (float) y);
    }

    public void line(double posAX, double posAY, double posBX, double posBY) {
        super.line((float) posAX, (float) posAY, (float) posBX, (float) posBY);
    }
}
