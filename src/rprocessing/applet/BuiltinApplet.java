package rprocessing.applet;

import org.renjin.aether.AetherPackageLoader;
import org.renjin.eval.Session;
import org.renjin.eval.SessionBuilder;
import org.renjin.script.RenjinScriptEngine;
import org.renjin.script.RenjinScriptEngineFactory;
import org.renjin.sexp.StringVector;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;

/**
 * BuiltinApplet is the type to refactor the function calls.
 * 
 * @author github.com/gaocegege
 */
public class BuiltinApplet extends PApplet implements PConstants {

  private static final boolean VERBOSE = Boolean.parseBoolean(System.getenv("VERBOSE_RLANG_MODE"));

  /*
   * TODO: Check for the cast.
   */
  /** Engine to interpret R code */
  protected final RenjinScriptEngine renjinEngine;

  public RenjinScriptEngine getRenjinEngine() {
    return renjinEngine;
  }

  private static void log(String msg) {
    if (!VERBOSE) {
      return;
    }
    System.err.println(BuiltinApplet.class.getSimpleName() + ": " + msg);
  }

  public BuiltinApplet() {
    AetherPackageLoader packageLoader = new AetherPackageLoader();
    Session session =
        new SessionBuilder().withDefaultPackages().setPackageLoader(packageLoader).build();
    this.renjinEngine = new RenjinScriptEngineFactory().getScriptEngine(session);
  }

  public void size(double width, double height) {
    this.logWarningsforCast();
    super.size((int) width, (int) height);
  }

  public void size(double width, double height, StringVector renderer) {
    this.logWarningsforCast();
    super.size((int) width, (int) height, renderer.asString());
  }

  public void bezierDetail(double detail) {
    this.logWarningsforCast();
    super.bezierDetail((int) detail);
  }

  public void curveDetail(double detail) {
    this.logWarningsforCast();
    super.curveDetail((int) detail);
  }

  public void sphereDetail(double res) {
    this.logWarningsforCast();
    super.sphereDetail((int) res);
  }

  public void sphereDetail(double ures, double vres) {
    this.logWarningsforCast();
    super.sphereDetail((int) ures, (int) vres);
  }

  public void noiseDetail(double lod) {
    this.logWarningsforCast();
    super.noiseDetail((int) lod);
  }

  public void noiseDetail(double lod, double falloff) {
    this.logWarningsforCast();
    super.noiseDetail((int) lod, (float) falloff);
  }

  public PImage createImage(double w, double h, int format) {
    this.logWarningsforCast();
    return super.createImage((int) w, (int) h, format);
  }

  public PGraphics createGraphics(double w, double h) {
    this.logWarningsforCast();
    return super.createGraphics((int) w, (int) h);
  }

  public PGraphics createGraphics(double w, double h, String renderer) {
    this.logWarningsforCast();
    return super.createGraphics((int) w, (int) h, renderer);
  }

  public PGraphics createGraphics(double w, double h, String renderer, String path) {
    this.logWarningsforCast();
    return super.createGraphics((int) w, (int) h, renderer, path);
  }

  public void pixelDensity(double density) {
    this.logWarningsforCast();
    super.pixelDensity((int) density);
  }

  private void logWarningsforCast() {
    log("WARNING: The function call casts double to int.");
  }
}
