package rprocessing;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import processing.core.PApplet;
import rprocessing.util.RScriptReader;
import rprocessing.util.StreamPrinter;

public class TryRenjin {
    private static final String CORE_TEXT = RScriptReader.readResourceAsText(TryRenjin.class,
                                              "./r/core.R");

    public static void main(String[] args) throws Exception {
        StreamPrinter a = new StreamPrinter(System.out);
        // Create a script engine manager.
        ScriptEngineManager manager = new ScriptEngineManager();
        // Create a Renjin engine.
        ScriptEngine engine = manager.getEngineByName("Renjin");
        // Check if the engine has loaded correctly.
        if (engine == null) {
            throw new RuntimeException("Renjin Script Engine not found on the classpath.");
        }
        // TODO: read the code.
        RLangPApplet rp = new RLangPApplet(engine,
            "posAX <- 11\nposAY <- 22\nposBX <- 33\nposBY <- 22\nprocessing$line(posAX, posAY, posBX, posBY)");
        // Put RLangPApplet instance to R scope, and eval core.R.
        engine.put("processing", rp);
        engine.eval(CORE_TEXT);
        // Run Sketch.
        PApplet.runSketch(args, rp);
    }
}