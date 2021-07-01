package background;

import java.awt.*;

import model.Sprite;
import model.VelocitySprite;
import static utils.ImageStateUtils.imageFromStringPath;

public class Cloud extends VelocitySprite {
    private final Dimension shape;
    private final Image cloudImage;
    private Sprite owner;

    public Cloud(Sprite owner, Point location, Point velocity, Dimension shape, String cloudPath) {
        super(velocity);
        this.owner = owner;
        this.location = location;
        this.shape = shape;
        cloudImage = imageFromStringPath(cloudPath);
    }

    private Point getLocationFromLeftmost() {
        int leftMost = 0 - (int) shape.getWidth();
        return new Point(leftMost, location.y);
    }

    @Override
    public void update() {
        location.translate(getVX(), getVY());
        if (!getRange().intersects(owner.getRange()))
            location = getLocationFromLeftmost();
    }

    @Override
    public void render(Graphics g) {
        Rectangle range = getRange();
        g.drawImage(cloudImage, range.x, range.y, range.width, range.height, null);
    }

    @Override
    public Rectangle getRange() {
        return new Rectangle(location.x, location.y, (int) shape.getWidth(), (int) shape.getHeight());
    }

    @Override
    public Dimension getBodyOffset() {
        return null;
    }

    @Override
    public Dimension getBodySize() {
        return null;
    }
    
}
