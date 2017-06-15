package rprocessing.applet;

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
}
