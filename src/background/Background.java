package background;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static utils.ImageStateUtils.imageFromStringPath;

import utils.Constant;

import model.Sprite;

public class Background extends Sprite {
    private final Image bgImage;
    private final String cloudImagePath;
    List<Sprite> clouds = new ArrayList<>();

    public Background(String bgPath, String cloudPath) {
        this.bgImage = imageFromStringPath(bgPath);
        this.cloudImagePath = cloudPath;
        addRandClouds();
    }

    private int getRandInRange(int lo, int up) {
        return (int) ((Math.random() * (up - lo)) + lo);
    }

    private double getRandInRange(double lo, double up) {
        return Math.random() * (up - lo) + lo;
    }

    private Point getRandCloudLocation() {
        return new Point(getRandInRange(0, Constant.WORLD_WIDTH),
                        getRandInRange(Constant.CLOUD_MIN_LOCATION, Constant.CLOUD_MAX_LOCATION));
    }

    private Point getRandCloudVolicity() {
        return new Point(getRandInRange(Constant.CLOUD_MIN_SPEED, Constant.CLOUD_MAX_SPEED), 0);
    }

    private Dimension getRandCloudShape() {
        double randEnlargeRatio = getRandInRange(1.0, Constant.CLOUD_MAX_ENLARGE_RATIO);
        return new Dimension((int) (randEnlargeRatio * Constant.CLOUD_WIDTH),
                            (int) (randEnlargeRatio * Constant.CLOUD_HEIGHT));
    }

    private Cloud getRandCloud() {
        return new Cloud(this, getRandCloudLocation(), 
                getRandCloudVolicity(), getRandCloudShape(), cloudImagePath);
    }

    private void addRandClouds() {
        for (int i = 0; i < Constant.CLOUD_NUMS; ++i)
            clouds.add(getRandCloud());
    }

    @Override
    public void update() {
        for (Sprite cloud : clouds)
            cloud.update();
    }

    @Override
    public void render(Graphics g) {
        Rectangle range = getRange();
        g.drawImage(bgImage, range.x, range.y, range.width, range.height, null);
        for (Sprite cloud : clouds)
            cloud.render(g);
    }

    @Override
    public Rectangle getRange() {
        return new Rectangle(Constant.WORLD_WIDTH, Constant.WORLD_HEIGHT);
    }

    // ========== Not Used ========== //

    @Override
    public Dimension getBodyOffset() {
        return null;
    }

    @Override
    public Dimension getBodySize() {
        return null;
    }
    
}
