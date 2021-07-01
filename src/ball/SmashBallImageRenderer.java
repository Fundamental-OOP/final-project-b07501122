package ball;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;

import static utils.ImageStateUtils.imageFromStringPath;

public class SmashBallImageRenderer extends BallImageRenderer {
    List<Image> afterImages = new ArrayList<>();

    public SmashBallImageRenderer(Ball ball, List<String> afterImagePaths) {
        super(ball);
        for (String afterImagePath : afterImagePaths) {
            afterImages.add(imageFromStringPath(afterImagePath));
        }
    }

    @Override
    public void render(Image image, Graphics g) {
        super.render(image, g);
        List<Rectangle> prevRanges = ball.getPrevRanges();
        for (int i = 0; i < prevRanges.size(); ++i) {
            Rectangle range = prevRanges.get(i);
            g.drawImage(afterImages.get(i), range.x, range.y, range.width, range.height, null);
        }
    }
}
