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
import rprocessing.util.Constant;

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

    /** Program Code */
    private final String             programText;

    /** Engine to interpret R code */
    private final RenjinScriptEngine renjinEngine;

    public RLangPApplet(final ScriptEngine renjinEngine, final String programText) {
        this.renjinEngine = (RenjinScriptEngine) renjinEngine;
        this.programText = programText;
        this.prePassCode();
        // Detect the mode after pre-pass program code.
        this.mode = this.detectMode();
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
     * Detect the mode.
     * After: prePassCode()
     */
    private Mode detectMode() {
        if (isActiveMode()) {
            if (isMixMode()) {
                return Mode.MIXED;
            }
            return Mode.ACTIVE;
        }
        return Mode.STATIC;
    }

    /**
     * Add PApplet instance to R top context
     * Notice: DO NOT do it in constructor.
     */
    public void AddPAppletToRContext() {
        this.renjinEngine.put(Constant.PROCESSING_VAR_NAME, this);
    }

    /**
     * @see processing.core.PApplet#settings()
     */
    @Override
    public void settings() {
        Object obj = this.renjinEngine.get(Constant.SETTINGS_NAME);
        if (obj.getClass().equals(Closure.class)) {
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
        wrapProcessingVariables();
        if (this.mode == Mode.STATIC) {
            try {
                this.renjinEngine.eval(this.programText);
            } catch (ScriptException e) {
                System.out.println(e);
            }
        } else if (this.mode == Mode.ACTIVE) {
            Object obj = this.renjinEngine.get(Constant.SETUP_NAME);
            if (obj.getClass().equals(Closure.class)) {
                ((Closure) obj).doApply(this.renjinEngine.getTopLevelContext());
            }
        } else {
            System.out.println("The program is in mix mode now.");
        }
    }

    /**
     * Call the draw function in R script.
     * @see processing.core.PApplet#draw()
     */
    @Override
    public void draw() {
        Object obj = this.renjinEngine.get(Constant.DRAW_NAME);
        if (obj.getClass().equals(Closure.class)) {
            ((Closure) obj).doApply(this.renjinEngine.getTopLevelContext());
        }
    }

    /*
     * Helper functions
     */

    /**
     * Detect whether the program is in active mode.
     * 
     * @return
     */
    @SuppressWarnings("rawtypes")
    private boolean isActiveMode() {
        Class closureClass = Closure.class;
        if (isSameClass(this.renjinEngine.get(Constant.SETTINGS_NAME), closureClass)
            || isSameClass(this.renjinEngine.get(Constant.SETUP_NAME), closureClass)
            || isSameClass(this.renjinEngine.get(Constant.DRAW_NAME), closureClass)) {
            return true;
        }
        return false;
    }

    /**
     * Detect whether the program is in mix mode.
     * After: isActiveMode()
     * 
     * @return
     */
    @SuppressWarnings("rawtypes")
    private boolean isMixMode() {
        Class closureClass = Closure.class;
        if (isSameClass(this.renjinEngine.get(Constant.SIZE_NAME), closureClass)) {
            return true;
        }

        return false;
    }

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

    /**
     * Return whether the object has same class with clazz.
     * 
     * @param obj
     * @param clazz
     * @return
     */
    @SuppressWarnings("rawtypes")
    private static boolean isSameClass(Object obj, Class clazz) {
        return obj.getClass().equals(clazz);
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
