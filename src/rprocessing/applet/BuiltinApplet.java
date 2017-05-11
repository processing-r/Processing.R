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
     * Wrapper functions
     * 
     * float is not implemented in R, so processing.r need to cast double to
     * float.
     */

    public void size(double width, double height) {
        super.size((int) width, (int) height);
    }

    public void size(double width, double height, StringVector renderer) {
        super.size((int) width, (int) height, renderer.asString());
    }

    /*
     * 2D Primitives
     */

    public void point(double x, double y) {
        super.point((float) x, (float) y);
    }

    public void line(double posAX, double posAY, double posBX, double posBY) {
        super.line((float) posAX, (float) posAY, (float) posBX, (float) posBY);
    }

    public void ellipse(double posX, double posY, double width, double height) {
        super.ellipse((float) posX, (float) posY, (float) width, (float) height);
    }

    public void arc(double posX, double posY, double width, double height, double start,
                    double stop) {
        super.arc((float) posX, (float) posY, (float) width, (float) height, (float) start,
            (float) stop);
    }

    public void quad(double posAX, double posAY, double posBX, double posBY, double posCX,
                     double posCY, double posDX, double posDY) {
        super.quad((float) posAX, (float) posAY, (float) posBX, (float) posBY, (float) posCX,
            (float) posCY, (float) posDX, (float) posDY);
    }

    public void rect(double posAX, double posAY, double posBX, double posBY) {
        super.rect((float) posAX, (float) posAY, (float) posBX, (float) posBY);
    }

    public void rect(double posAX, double posAY, double posBX, double posBY, double redii) {
        super.rect((float) posAX, (float) posAY, (float) posBX, (float) posBY, (float) redii);
    }

    public void rect(double posAX, double posAY, double posBX, double posBY, double tl, double tr,
                     double br, double bl) {
        super.rect((float) posAX, (float) posAY, (float) posBX, (float) posBY, (float) tl,
            (float) tr, (float) br, (float) bl);
    }

    public void triangle(double posAX, double posAY, double posBX, double posBY, double posCX,
                         double posCY) {
        super.triangle((float) posAX, (float) posAY, (float) posBX, (float) posBY, (float) posCX,
            (float) posCY);
    }

    /*
     * Curves
     */

    public void bezier(double posAX, double posAY, double posAZ, double posBX, double posBY,
                       double posBZ, double posCX, double posCY, double posCZ, double posDX,
                       double posDY, double posDZ) {
        super.bezier((float) posAX, (float) posAY, (float) posAZ, (float) posBX, (float) posBY,
            (float) posBZ, (float) posCX, (float) posCY, (float) posCZ, (float) posDX,
            (float) posDY, (float) posDZ);
    }

    public void bezier(double posAX, double posAY, double posBX, double posBY, double posCX,
                       double posCY, double posDX, double posDY) {
        super.bezier((float) posAX, (float) posAY, (float) posBX, (float) posBY, (float) posCX,
            (float) posCY, (float) posDX, (float) posDY);
    }

    public double bezierPoint(double posAX, double posAY, double posBX, double posBY, double t) {
        return super.bezierPoint((float) posAX, (float) posAY, (float) posBX, (float) posBY,
            (float) t);
    }

    public double bezierTangent(double posAX, double posAY, double posBX, double posBY, double t) {
        return super.bezierTangent((float) posAX, (float) posAY, (float) posBX, (float) posBY,
            (float) t);
    }

    public void curve(double posAX, double posAY, double posAZ, double posBX, double posBY,
                      double posBZ, double posCX, double posCY, double posCZ, double posDX,
                      double posDY, double posDZ) {
        super.curve((float) posAX, (float) posAY, (float) posAZ, (float) posBX, (float) posBY,
            (float) posBZ, (float) posCX, (float) posCY, (float) posCZ, (float) posDX,
            (float) posDY, (float) posDZ);
    }

    public void curve(double posAX, double posAY, double posBX, double posBY, double posCX,
                      double posCY, double posDX, double posDY) {
        super.curve((float) posAX, (float) posAY, (float) posBX, (float) posBY, (float) posCX,
            (float) posCY, (float) posDX, (float) posDY);
    }

    public double curvePoint(double posAX, double posAY, double posBX, double posBY, double t) {
        return super.curvePoint((float) posAX, (float) posAY, (float) posBX, (float) posBY,
            (float) t);
    }

    public double curveTangent(double posAX, double posAY, double posBX, double posBY, double t) {
        return super.curveTangent((float) posAX, (float) posAY, (float) posBX, (float) posBY,
            (float) t);
    }

    public void curveTightness(double tightness) {
        super.curveTightness((float) tightness);
    }

    /*
     * Colors
     */

    public void backgroud(int rgb, double alpha) {
        super.background(rgb, (float) alpha);
    }

    public void background(double gray) {
        super.background((float) gray);
    }

    public void background(double gray, double alpha) {
        super.background((float) gray, (float) alpha);
    }

    public void background(double v1, double v2, double v3) {
        super.background((float) v1, (float) v2, (float) v3);
    }

    public void background(double v1, double v2, double v3, double alpha) {
        super.background((float) v1, (float) v2, (float) v3, (float) alpha);
    }

    public void colorMode(int mode, double max) {
        super.colorMode(mode, (float) max);
    }

    public void colorMode(int mode, double max1, double max2, double max3) {
        super.colorMode(mode, (float) max1, (float) max2, (float) max3);
    }

    public void colorMode(int mode, double max1, double max2, double max3, double maxA) {
        super.colorMode(mode, (float) max1, (float) max2, (float) max3, (float) maxA);
    }

    public void fill(int rgb, double alpha) {
        super.fill(rgb, (float) alpha);
    }

    public void fill(double gray) {
        super.fill((float) gray);
    }

    public void fill(double gray, double alpha) {
        super.fill((float) gray, (float) alpha);
    }

    public void fill(double v1, double v2, double v3) {
        super.fill((float) v1, (float) v2, (float) v3);
    }

    public void fill(double v1, double v2, double v3, double alpha) {
        super.fill((float) v1, (float) v2, (float) v3, (float) alpha);
    }

    public void stroke(int rgb, double alpha) {
        super.stroke(rgb, (float) alpha);
    }

    public void stroke(double gray) {
        super.stroke((float) gray);
    }

    public void stroke(double gray, double alpha) {
        super.stroke((float) gray, (float) alpha);
    }

    public void stroke(double v1, double v2, double v3) {
        super.stroke((float) v1, (float) v2, (float) v3);
    }

    public void stroke(double v1, double v2, double v3, double alpha) {
        super.stroke((float) v1, (float) v2, (float) v3, (float) alpha);
    }

    /*
     * 3D
     */

    public void box(double size) {
        super.box((float) size);
    }

    public void box(double w, double h, double d) {
        super.box((float) w, (float) h, (float) d);
    }

    // TODO: Blocked until pre-processor.
    // See https://github.com/gaocegege/Processing.R/issues/9#issuecomment-299710866
    //    public double alpha(int rgb) {
    //        return 0;
    //    }

    public void perspective(double fovy, double aspect, double zNear, double zFar) {
        super.perspective((float) fovy, (float) aspect, (float) zNear, (float) zFar);
    }

    public void frameRate(double fps) {
        super.frameRate((float) fps);
    }

    public double frameCount() {
        return super.frameCount;
    }

    public void camera(double eyeX, double eyeY, double eyeZ, double centerX, double centerY,
                       double centerZ, double upX, double upY, double upZ) {
        super.camera((float) eyeX, (float) eyeY, (float) eyeZ, (float) centerX, (float) centerY,
            (float) centerZ, (float) upX, (float) upY, (float) upZ);
    }

    public void rotateX(double angle) {
        super.rotateX((float) angle);
    }

    public void rotateY(double angle) {
        super.rotateY((float) angle);
    }

    public void translate(double x, double y, double z) {
        super.translate((float) x, (float) y, (float) z);
    }

    public double radians(double degrees) {
        return super.radians((float) degrees);
    }

    public void text(String c, double posX, double posY) {
        super.text(c, (float) posX, (float) posY);
    }
}
