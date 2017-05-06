package rprocessing.applet;

import processing.core.PApplet;

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

    public void point(double x, double y) {
        super.point((float) x, (float) y);
    }

    public void line(double posAX, double posAY, double posBX, double posBY) {
        super.line((float) posAX, (float) posAY, (float) posBX, (float) posBY);
    }
    
    public void ellipse(double posX, double posY, double width, double height) {
    	super.ellipse((float) posX, (float) posY, (float) width, (float) height);
    }
    
    public void arc(double posX, double posY, double width, double height, double start, double stop) {
    	super.arc((float) posX, (float) posY, (float) width, (float) height, (float) start, (float) stop);
    }
    
    public void quad(double posAX, double posAY, double posBX, double posBY, double posCX, double posCY, double posDX, double posDY) {
    	super.quad((float) posAX, (float) posAY, (float) posBX, (float) posBY, (float) posCX, (float) posCY, (float) posDX, (float) posDY);
    }
    
    public void rect(double posAX, double posAY, double posBX, double posBY) {
    	super.rect((float) posAX, (float) posAY, (float) posBX, (float) posBY);
    }
    
    public void rect(double posAX, double posAY, double posBX, double posBY, double redii) {
    	super.rect((float) posAX, (float) posAY, (float) posBX, (float) posBY, (float) redii);
    }
    
    public void rect(double posAX, double posAY, double posBX, double posBY, double tl, double tr, double br, double bl) {
    	super.rect((float) posAX, (float) posAY, (float) posBX, (float) posBY, (float) tl, (float) tr, (float) br, (float) bl);
    }
    
    public void triangle(double posAX, double posAY, double posBX, double posBY, double posCX, double posCY) {
    	super.triangle((float) posAX, (float) posAY, (float) posBX, (float) posBY, (float) posCX, (float) posCY);
    }
}
