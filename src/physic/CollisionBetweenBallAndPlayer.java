package physic;

import java.awt.*;

import model.*;
import ball.Ball;
import media.AudioPlayer;
import pikachu.Pikachu;
import utils.Constant;

public class CollisionBetweenBallAndPlayer implements CollisionHandler {
    @Override
    public void handle(Point originalLocation, Sprite target1, Sprite target2) {

        Pikachu player = (Pikachu) target1;
        Ball ball = (Ball) target2;

        if (player instanceof Pikachu && ball instanceof Ball) {
            Rectangle body = player.getBody();

            int afterVx, afterVy;
            // handle collision based on hit or smash
            if (player.getSmashing()) {
            	afterVx = Constant.X_SMASH_SPEED;
                afterVy = (player.getY() > ball.getY()) ? -Constant.Y_SMASH_SPEED : Constant.Y_SMASH_SPEED  ;
                ball.getSmash();
            } else {
                afterVx = Constant.X_HIT_SPEED;
                afterVy = (player.getY() > ball.getY()) ? -Constant.Y_HIT_SPEED : Constant.Y_HIT_SPEED;
                ball.getHit();
            }

            int offsetLeft = ball.getX() - body.x;
            // if collision occur,
            // greater the x position difference between pika and ball,
            // greater the x velocity of the ball.
            if (offsetLeft < 0) {
                afterVx = -afterVx;
            }
            ball.setV(new Point(new Point(afterVx, afterVy)));
        }
    }
}
