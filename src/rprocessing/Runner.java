package rprocessing;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.renjin.eval.EvalException;
import org.renjin.parser.ParseException;

import processing.core.PApplet;
import rprocessing.exception.REvalException;
import rprocessing.lancher.StandaloneSketch;
import rprocessing.util.Printer;
import rprocessing.util.RScriptReader;
import rprocessing.util.StreamPrinter;

/**
 * R script runner
 * @author github.com/gaocegege
 */
public class Runner {

    public static RunnableSketch sketch;

    private static final String  CORE_TEXT = RScriptReader.readResourceAsText(Runner.class,
                                               "r/core.R");

    public static boolean        VERBOSE   = Boolean.getBoolean("verbose");

    private static void log(final Object... objs) {
        if (!VERBOSE) {
            return;
        }
        for (final Object o : objs) {
            System.err.print(Runner.class.getSimpleName() + ": " + String.valueOf(o));
        }
        System.err.println();
    }

    public static void main(final String[] args) throws Exception {
        if (args.length < 1) {
            throw new RuntimeException("I need the path of your R script as an argument.");
        }
        try {
            sketch = new StandaloneSketch(args);
            runSketchBlocking(sketch, new StreamPrinter(System.out), new StreamPrinter(System.err));
        } catch (final Throwable t) {
            System.err.println(t);
            System.exit(-1);
        }
    }

    public synchronized static void runSketchBlocking(final RunnableSketch sketch,
                                                      final Printer stdout, final Printer stderr)
                                                                                                 throws REvalException {
        runSketchBlocking(sketch, stdout, stderr, null);
    }

    public synchronized static void runSketchBlocking(final RunnableSketch sketch,
                                                      final Printer stdout,
                                                      final Printer stderr,
                                                      final SketchPositionListener sketchPositionListener)
                                                                                                          throws REvalException {
        final String[] args = sketch.getPAppletArguments();

        // Create a script engine manager.
        ScriptEngineManager manager = new ScriptEngineManager();
        // Create a Renjin engine.
        ScriptEngine engine = manager.getEngineByName("Renjin");
        // Check if the engine has loaded correctly.
        if (engine == null) {
            throw new RuntimeException("Renjin Script Engine not found on the classpath.");
        }
        log("Tring to initialize RLangPApplet.");
        RLangPApplet rp = new RLangPApplet(engine, sketch.getMainCode());
        log("Adding processing variable into R top context.");
        rp.AddPAppletToRContext();

        try {
            engine.eval(CORE_TEXT);
            // Run Sketch.
            PApplet.runSketch(args, rp);
        } catch (ScriptException se) {
            throw new REvalException(se.getMessage());
        } catch (ParseException pe) {
            throw new REvalException(pe.getMessage());
        } catch (EvalException ee) {
            throw new REvalException(ee.getMessage());
        }
    }
}
