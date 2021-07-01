

//import static media.AudioPlayer.addAudioByFilePath;

import java.awt.Point;
import java.io.File;

import ball.Ball;
import controller.Game;
import model.World;
import model.Direction;
import physic.CollisionBetweenBallAndPlayer;
import physic.Gravity;
import pikachu.Pikachu;
import pikachu.Smashing;
import pikachu.Walking;
import utils.Constant;
import views.GameView;
import media.AudioPlayer;

public class Main {
	public static void main(String[] args) {
		AudioPlayer.addAudioByFilePath(Game.BGM, new File("assets/sounds/bgm.wav"));
        AudioPlayer.addAudioByFilePath(Pikachu.AUDIO_SMASH, new File("assets/sounds/smash.wav"));
        AudioPlayer.addAudioByFilePath(Pikachu.AUDIO_JUMP, new File("assets/sounds/jump.wav"));
        AudioPlayer.addAudioByFilePath(Ball.AUDIO_SMASH_BALL, new File("assets/sounds/smash_ball.wav"));
        AudioPlayer.addAudioByFilePath(World.AUDIO_BALL_DROP, new File("assets/sounds/ball_drop.wav"));
        AudioPlayer.addAudioByFilePath(Game.AUDIO_WIN, new File("assets/sounds/win_sound.wav"));
        
        // initialization procedure
        Pikachu p1 = new Pikachu(new Point(0, 0), (Point) Constant.P1_INITIAL_POSITION.clone());
        p1.setFace(Direction.RIGHT);
        Pikachu p2 = new Pikachu(new Point(0, 0), (Point) Constant.P2_INITIAL_POSITION.clone());
        p2.setFace(Direction.LEFT);
        Ball ball = new Ball((Point) Constant.BALL_INITIAL_V.clone(), (Point) Constant.BALL_INITIAL_POSITION_P1.clone());
        World world = new World(new CollisionBetweenBallAndPlayer(), new Gravity(), p1, p2, ball);  // model
        Game game = new Game(world, p1, p2, 15);  // controller
        GameView view = new GameView(game);  // view
        
        
        
        game.start();  // run the game and the game loop
        view.launch(); // launch the GUI
        
    }
}
