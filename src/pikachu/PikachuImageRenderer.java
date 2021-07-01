package pikachu;

import fsm.ImageRenderer;
import model.Direction;

import java.awt.*;

public class PikachuImageRenderer implements ImageRenderer {
    protected Pikachu pikachu;

    public PikachuImageRenderer(Pikachu pikachu) {
        this.pikachu = pikachu;
    }

    @Override
    public void render(Image image, Graphics g) {
        Direction face = pikachu.getFace();
        Rectangle range = pikachu.getRange();
        if (face == Direction.LEFT) {
            g.drawImage(image, range.x + range.width, range.y, -range.width, range.height, null);
        } else {
            g.drawImage(image, range.x, range.y, range.width, range.height, null);
        }
    }
}