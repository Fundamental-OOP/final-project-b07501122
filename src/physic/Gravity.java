package physic;

import java.awt.*;

import model.*;
import utils.Constant;

public class Gravity implements MotionHandler {
	private int remaining = 10;
    @Override
    public void handle(VelocitySprite target) {
        if(--remaining <= 0) {
        	target.setV(new Point(target.getVX(), target.getVY() + Constant.GRAVITY));
        	remaining = 10;
        }
    	
    }
}