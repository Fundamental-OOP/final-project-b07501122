package utils;

import java.awt.Point;
import java.awt.Rectangle;

public class Constant {

	/** World **/
    public final static int GROUND_LINE = 620;
	public final static int WORLD_WIDTH = 1280;	
	public final static int WORLD_HEIGHT = 720;	
	public final static int WORLD_HALF_WIDTH = WORLD_WIDTH / 2;	

	/** Net **/
	public final static int NET_THICKNESS = 30;	
	public final static int NET_UPPER_X = WORLD_HALF_WIDTH - NET_THICKNESS/2;
	public final static int NET_UPPER_Y = 450;
	public final static int NET_TOP_UPPER_Y = 430;
	public final static int NET_TOP_THICKNESS = 20;
	public final static int NET_HEIGHT = 220;
	public final static int NET_PILLAR_HALF_WIDTH = 25;	
	public final static int NET_PILLAR_TOP_TOP_Y_COORD = 176;	
	public final static int NET_PILLAR_TOP_BOTTOM_Y_COORD = 192;		
	public final static Rectangle NET_SIDE = new Rectangle(NET_UPPER_X, NET_UPPER_Y, NET_THICKNESS, NET_HEIGHT);
	public final static Rectangle NET_TOP = new Rectangle(NET_UPPER_X, NET_TOP_UPPER_Y, NET_THICKNESS, NET_TOP_THICKNESS);

	/** Boundary */
	public final static int EDGE_THICKNESS = 30;
	public final static Rectangle LEFT_WALL = new Rectangle(-EDGE_THICKNESS, 0, EDGE_THICKNESS, WORLD_HEIGHT);
	public final static Rectangle RIGHT_WALL = new Rectangle(WORLD_WIDTH, 0, EDGE_THICKNESS, WORLD_HEIGHT);
	public final static Rectangle MIDDLE_WALL = new Rectangle(WORLD_HALF_WIDTH - NET_THICKNESS / 2, 0, NET_THICKNESS, WORLD_HEIGHT);
	public final static Rectangle SKY_WALL = new Rectangle(0, -EDGE_THICKNESS, WORLD_WIDTH, EDGE_THICKNESS);
	public final static Rectangle GROUND = new Rectangle(0, GROUND_LINE, WORLD_WIDTH, EDGE_THICKNESS);

	/** Game **/
	public static final long DELAY_TIME = 10;
	public final static int WALKING_LEFT_SPEED_X = -6;
	public final static int WALKING_RIGHT_SPEED_X = 6;
	public final static int JUMPING_SPEED_Y = -6;
	public final static int DIVING_LEFT_SPEED_X = -9;
	public final static int DIVING_RIGHT_SPEED_X = 9;

	/** Paths **/
	public final static String BALL_HPYER_IMG_PATH = "assets/ball/ball_hyper.png";
	public final static String BALL_TRAIL_IMG_PATH = "assets/ball/ball_trail.png";
	public final static String SHADOW_IMG_PATH = "assets/game/background/shadow.png";
	public final static String BACKGROUND_IMG_PATH = "assets/game/background/background.png";
	public final static String CLOUD_IMG_PATH = "assets/game/background/cloud.png";

	/** Pikachu constants **/
	public final static int PIKACHU_WIDTH = 128;
	public final static int PIKACHU_HEIGHT = 128;
	public final static int PIKACHU_OFFSET_X = 32;
	public final static int PIKACHU_OFFSET_Y = 32;
	public final static int PIKACHU_BODY_WIDTH = 64;
	public final static int PIKACHU_BODY_HEIGHT = 96;
	public final static Point P1_INITIAL_POSITION = new Point(WORLD_WIDTH / 6, GROUND_LINE - PIKACHU_HEIGHT);	
	public final static Point P2_INITIAL_POSITION = new Point(WORLD_WIDTH * 5 / 6,  GROUND_LINE - PIKACHU_HEIGHT);	

	/** Ball constants **/
	public final static int BALL_RADIUS = 80;	
	public final static int BALL_TOUCHING_GROUND_Y_COORD = 252;
	public final static Point BALL_INITIAL_V = new Point(0, 0);	
	public final static int X_HIT_SPEED = 3;		// X speed after hit
	public final static int X_SMASH_SPEED = 8;	// X speed after smash
	public final static int Y_HIT_SPEED = 6;  // Y speed after chit
	public final static int Y_SMASH_SPEED = 12;	// Y speed after smash
	public final static Point BALL_INITIAL_POSITION_P1 = new Point(WORLD_WIDTH / 6, WORLD_HEIGHT / 6);	
	public final static Point BALL_INITIAL_POSITION_P2 = new Point(WORLD_WIDTH * 5 / 6, WORLD_HEIGHT / 6);

	/** Gravity */
	public final static int GRAVITY = 1;
	
	/**  Cloud **/
	public final static int CLOUD_WIDTH = 96;
	public final static int CLOUD_HEIGHT = 48;
    public final static int CLOUD_ENLARGE_FREQUENCY = 10;
	public final static int CLOUD_NUMS = 10;
	public final static int CLOUD_MIN_SPEED = 1;
	public final static int CLOUD_MAX_SPEED = 4;
	public final static int CLOUD_MIN_LOCATION = 0;
	public final static int CLOUD_MAX_LOCATION = (int) (WORLD_HEIGHT * 1 / 2); 
	public final static double CLOUD_MAX_ENLARGE_RATIO = 1.3;

	/** Scorer **/
	public final static int SCORER_DIGIT_WIDTH = 80;
	public final static int SCORER_DIGIT_HEIGHT = 80;
	public final static int SCORER_DIGIT_SEP = 45;
	public final static Point P1_SCORE_POSITION = new Point(64, 64);
	public final static Point P2_SCORE_POSITION = new Point(WORLD_WIDTH - 200, 64);
}
