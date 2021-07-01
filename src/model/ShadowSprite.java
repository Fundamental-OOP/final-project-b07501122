package model;

import java.awt.*;

import utils.Constant;

import static utils.ImageStateUtils.imageFromStringPath;

public class ShadowSprite extends Sprite {
    private final double shadowRatio = 0.8;
    private final int shadowHeight = 20;
    private final Sprite owner;
    private final Image shadowImage;

    public ShadowSprite(Sprite owner, String shadowPath) {
        this.owner = owner;
        shadowImage = imageFromStringPath(shadowPath);
    }

    @Override
    public void render(Graphics g) {
        Rectangle range = getRange();
        g.drawImage(shadowImage, range.x, range.y, range.width, range.height, null);
    }

    @Override
    public Rectangle getRange() {
        Rectangle ownerRange = owner.getRange();
        int x = (int) (ownerRange.x + (1 - shadowRatio)/2 * ownerRange.width);
        return new Rectangle(x, Constant.GROUND_LINE, (int) (shadowRatio * ownerRange.width), shadowHeight);
    }

    @Override
    public void update() {
    }

    @Override
    public Dimension getBodyOffset() {
        return new Dimension();
    }

    @Override
    public Dimension getBodySize() {
        return new Dimension();
    }
}
