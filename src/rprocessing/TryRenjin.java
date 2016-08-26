package rprocessing;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import processing.core.PApplet;
import rprocessing.util.StreamPrinter;

public class TryRenjin {
    public static void main(String[] args) throws Exception {
        StreamPrinter a = new StreamPrinter(System.out);
        // create a script engine manager:
        ScriptEngineManager manager = new ScriptEngineManager();
        // create a Renjin engine:
        ScriptEngine engine = manager.getEngineByName("Renjin");
        // check if the engine has loaded correctly:
        if (engine == null) {
            throw new RuntimeException("Renjin Script Engine not found on the classpath.");
        }
        RLangPApplet rp = new RLangPApplet(engine, "p.arc(50, 55, 50, 50, 0, HALF_PI)");
        //        engine.put("p", rp);
        PApplet.runSketch(args, rp);
        rp.arc(50, 55, 50, 50, 0, 30);
        //        engine.eval("print(p.processSketch())");
    }
}