package model;

import java.awt.*;

public abstract class VelocitySprite extends Sprite {
    protected Point velocity;

    public VelocitySprite(Point velocity) {
        this.velocity = velocity;
    }

    public int getVX() {
        return velocity.x;
    }

    public int getVY() {
        return velocity.y;
    }

    public void setV(Point velocity) {
        this.velocity = velocity;
    }
}