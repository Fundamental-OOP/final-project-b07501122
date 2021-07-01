package ball;

import fsm.ImageRenderer;
import model.Direction;

import java.awt.*;

public class BallImageRenderer implements ImageRenderer {
    protected Ball ball;

    public BallImageRenderer(Ball ball) {
        this.ball = ball;
    }

    @Override
    public void render(Image image, Graphics g) {
        Direction face = ball.getFace();
        Rectangle range = ball.getRange();
        if (face == Direction.LEFT) {
            g.drawImage(image, range.x + range.width, range.y, -range.width, range.height, null);
        } else {
            g.drawImage(image, range.x, range.y, range.width, range.height, null);
        }
    }
}
