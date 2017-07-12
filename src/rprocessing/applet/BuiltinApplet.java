package rprocessing.applet;

import org.renjin.aether.AetherPackageLoader;
import org.renjin.eval.Session;
import org.renjin.eval.SessionBuilder;
import org.renjin.script.RenjinScriptEngine;
import org.renjin.script.RenjinScriptEngineFactory;
import org.renjin.sexp.StringVector;

import processing.core.PApplet;
import processing.core.PConstants;

/**
 * BuiltinApplet is the type to refactor the function calls.
 * 
 * @author github.com/gaocegege
 */
public class BuiltinApplet extends PApplet implements PConstants {

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

  public void bezierDetail(double detail) {
    super.bezierDetail((int) detail);
  }

  public void colorMode(double mode) {
    super.colorMode((int) mode);
  }

  public void colorMode(double mode, float max) {
    super.colorMode((int) mode, max);
  }

  public void colorMode(double mode, float max1, float max2, float max3) {
    super.colorMode((int) mode, max1, max2, max3);
  }

  public void colorMode(double mode, float max1, float max2, float max3, float maxA) {
    super.colorMode((int) mode, max1, max2, max3, maxA);
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

  @Override
  public void mouseClicked() {
    wrapMouseVariables();
  }

  @Override
  public void mouseMoved() {
    wrapMouseVariables();
  }

  @Override
  public void mousePressed() {
    wrapMouseVariables();
  }

  @Override
  public void mouseReleased() {
    wrapMouseVariables();
  }

  @Override
  public void mouseDragged() {
    wrapMouseVariables();
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

  protected void wrapMouseVariables() {
    this.renjinEngine.put("mouseX", mouseX);
    this.renjinEngine.put("mouseY", mouseY);
    this.renjinEngine.put("pmouseX", pmouseX);
    this.renjinEngine.put("pmouseY", pmouseY);
    // this.renjinEngine.put("mouseButton", mouseButton);
  }

  @Override
  public void keyPressed() {
    wrapKeyVariables();
  }

  @Override
  public void keyReleased() {
    wrapKeyVariables();
  }

  @Override
  public void keyTyped() {
    wrapKeyVariables();
  }

  private char lastKey = Character.MIN_VALUE;

  protected void wrapKeyVariables() {
    if (lastKey != key) {
      lastKey = key;
      /*
       * If key is "CODED", i.e., an arrow key or other non-printable, pass that value through
       * as-is. If it's printable, convert it to a unicode string, so that the user can compare key
       * == 'x' instead of key == ord('x').
       */
      final char pyKey = key == CODED ? parseChar(Integer.valueOf(key)) : parseChar(key);
      this.renjinEngine.put("key", pyKey);
    }
    this.renjinEngine.put("keyCode", keyCode);
  }
}
