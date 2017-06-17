package rprocessing.applet;

import org.renjin.sexp.StringVector;

import processing.core.PApplet;
import rprocessing.exception.NotFoundException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.renjin.parser.RParser;
import org.renjin.script.RenjinScriptEngine;
import org.renjin.sexp.Closure;
import org.renjin.sexp.ExpressionVector;
import org.renjin.sexp.FunctionCall;
import org.renjin.sexp.SEXP;
import org.renjin.sexp.Symbol;

/**
 * BuiltinApplet is the type to refactor the function calls.
 * 
 * @author github.com/gaocegege
 */
public class BuiltinApplet extends PApplet {

  /*
   * TODO: Check for the cast.
   */
  /** Engine to interpret R code */
  protected final RenjinScriptEngine renjinEngine;

  public BuiltinApplet() throws NotFoundException {
    // Create a script engine manager.
    ScriptEngineManager manager = new ScriptEngineManager();
    // Create a Renjin engine.
    ScriptEngine engine = manager.getEngineByName("Renjin");
    // Check if the engine has loaded correctly.
    if (engine == null) {
      throw new NotFoundException("Renjin Script Engine not found on the classpath.");
    }
    this.renjinEngine = (RenjinScriptEngine) engine;
  }

  public void size(double width, double height) {
    super.size((int) width, (int) height);
  }

  public void size(double width, double height, StringVector renderer) {
    super.size((int) width, (int) height, renderer.asString());
  }

  public double frameCount() {
    return super.frameCount;
  }

  public boolean getFocused() {
    return super.focused;
  }

  public double getPI() {
    return PI;
  }

  /**
   *
   * @see processing.core.PApplet#focusGained()
   */
  @Override
  public void focusGained() {
    super.focusGained();
    this.renjinEngine.put("focused",super.focused);
  }

  /**
   *
   * @see processing.core.PApplet#focusLost()
   */
  @Override
  public void focusLost() {
    super.focusLost();
    this.renjinEngine.put("focused",super.focused);
  }

  @Override
  public void mouseMoved() {
    wrapMouseVariables();
  }

  protected void wrapMouseVariables() {
    this.renjinEngine.put("mouseX", mouseX);
    this.renjinEngine.put("mouseY", mouseY);
    this.renjinEngine.put("pmouseX", pmouseX);
    this.renjinEngine.put("pmouseY", pmouseY);
    //this.renjinEngine.put("mouseButton", mouseButton);
  }
}
