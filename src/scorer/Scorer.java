package scorer;

import java.awt.*;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import utils.Constant;
import static utils.ImageStateUtils.imageFromStringPath;

import model.Sprite;
import model.World;

public class Scorer extends Sprite {
    List<Image> AllDigitImages = new ArrayList<>();

    public Scorer(World world, String digitImagesFolder) {
        setWorld(world);
        this.AllDigitImages = getAllDigitImagesFromFolder(digitImagesFolder);
    }

    private Image getDigitImageFromFolder(String digitImagesFolder, int digit) {
        return imageFromStringPath(Paths.get(digitImagesFolder, 
                                String.format("number_%d.png", digit)).toString());
    }

    private List<Image> getAllDigitImagesFromFolder(String digitImagesFolder) {
        List<Image> allDigitImages = new ArrayList<>();
        for (int digit = 0; digit <= 9; ++digit)
            allDigitImages.add(getDigitImageFromFolder(digitImagesFolder, digit));
        return allDigitImages;
    }

    @Override
    public void render(Graphics g) {
        int[] scores = getWorld().getGame().getScores();
        renderScore(g, scores[0], Constant.P1_SCORE_POSITION);
        renderScore(g, scores[1], Constant.P2_SCORE_POSITION);
    }

    private void renderScore(Graphics g, int score, Point position) {
        List<Image> digitImages = getDigitImages(score);
        for (int i = 0; i < digitImages.size(); ++i) {
            g.drawImage(digitImages.get(i), 
                        position.x + i * Constant.SCORER_DIGIT_SEP, 
                        position.y, Constant.SCORER_DIGIT_WIDTH, 
                        Constant.SCORER_DIGIT_HEIGHT, null);
        }
    }

    private List<Image> getDigitImages(Integer score) {
        List<Image> digitImages = new ArrayList<>();
        String scoreString = score.toString();
        for (int i = 0; i < scoreString.length(); ++i) {
            int digit = scoreString.charAt(i) - '0';
            digitImages.add(AllDigitImages.get(digit));
        }
        return digitImages;
    }

    // ====== Not used ====== //

    @Override
    public Rectangle getRange() {
        return null;
    }

    @Override
    public void update() {
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
