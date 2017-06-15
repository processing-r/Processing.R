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

  public boolean getFocused() { return super.focused; }

  public double getPI() { return PI; }

}
