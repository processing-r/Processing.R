package rprocessing;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

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
        engine.put("p", a);
        engine.eval("print(p)");
        // ... put your Java code here ...
    }
}