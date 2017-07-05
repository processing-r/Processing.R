package rprocessing.applet;

import org.renjin.aether.AetherPackageLoader;
import org.renjin.eval.Session;
import org.renjin.eval.SessionBuilder;
import org.renjin.script.RenjinScriptEngine;
import org.renjin.script.RenjinScriptEngineFactory;
import org.renjin.sexp.StringVector;

import processing.core.PApplet;

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

  public RenjinScriptEngine getRenjinEngine() {
    return renjinEngine;
  }

  public BuiltinApplet() {
    AetherPackageLoader packageLoader = new AetherPackageLoader();
    Session session =
        new SessionBuilder().withDefaultPackages().setPackageLoader(packageLoader).build();
    this.renjinEngine = new RenjinScriptEngineFactory().getScriptEngine(session);
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
    this.renjinEngine.put("focused", super.focused);
  }

  /**
   *
   * @see processing.core.PApplet#focusLost()
   */
  @Override
  public void focusLost() {
    super.focusLost();
    this.renjinEngine.put("focused", super.focused);
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
    // this.renjinEngine.put("mouseButton", mouseButton);
  }
}
