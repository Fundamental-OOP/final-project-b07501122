package model;

import java.awt.*;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import background.Background;
import ball.Ball;
import controller.Game;
import media.AudioPlayer;
import physic.Gravity;
import pikachu.Pikachu;
import scorer.Scorer;
import utils.Constant;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class World {
    private final List<Sprite> sprites = new CopyOnWriteArrayList<>();
    private final CollisionHandler collisionHandler;
    private final Gravity gravity;
    private Game game;
	private final Background background;
	private final Scorer scorer;
    public static final String AUDIO_BALL_DROP = "ball_drop";
    
    public World(CollisionHandler collisionHandler, Gravity gravity,  Sprite... sprites) {
        this.collisionHandler = collisionHandler;
		this.background = new Background("assets/game/background/background.png", 
									"assets/game/background/cloud.png");
		this.scorer = new Scorer(this, "assets/game/number");
        this.gravity = gravity;
        addSprites(sprites);
    }

    public void update() {
    	// TODO NEW vel_handler?
		background.update();
    	for (Sprite sprite : sprites) {
    		sprite.update();
    	}
    }
    
	public void reset(int scorer) {
		Pikachu p1 = (Pikachu) sprites.get(0);
		Pikachu p2 = (Pikachu) sprites.get(1);
		Ball ball = (Ball) sprites.get(2);
		p1.reset(new Point(0, 0), (Point) Constant.P1_INITIAL_POSITION.clone());
		p2.reset(new Point(0, 0), (Point) Constant.P2_INITIAL_POSITION.clone());
		if(scorer == 1)
			ball.reset((Point) Constant.BALL_INITIAL_V.clone(), (Point) Constant.BALL_INITIAL_POSITION_P1.clone());
		else
			ball.reset((Point) Constant.BALL_INITIAL_V.clone(), (Point) Constant.BALL_INITIAL_POSITION_P2.clone());
	}

    public void move(Sprite from, Dimension offset) {
        Point originalLocation = new Point(from.getLocation());
        from.getLocation().translate(offset.width, offset.height);

        Rectangle body = from.getBody();
        // collision with edges
        if(from instanceof Ball) {
        	int scorer = ballCollideGround((Ball) from);
        	if(scorer > 0) {       		
        		game.roundOver(scorer);
        		return;
        	}
        	ballCollideEdges(originalLocation, (Ball) from);
        }
        else {
        	pikachuCollideEdges(originalLocation, (Pikachu) from);
        }
        // collision with ball and pikachu
        for (Sprite to : sprites) {
            if (to != from && body.intersects(to.getBody())) {
            	if(from instanceof Pikachu)
            		collisionHandler.handle(originalLocation, from, to);
            	else
            		collisionHandler.handle(originalLocation, to, from);
            }
        }
    }	
    
    public void addSprites(Sprite... sprites) {
        stream(sprites).forEach(this::addSprite);
    }

    public void addSprite(Sprite sprite) {
        sprites.add(sprite);
        sprite.setWorld(this);
    }

    public void removeSprite(Sprite sprite) {
        sprites.remove(sprite);
        sprite.setWorld(null);
    }

	public Collection<Sprite> getSprites(Rectangle area) {
        return sprites.stream()
                .filter(s -> area.intersects(s.getBody()))
                .collect(toSet());
    }

    public List<Sprite> getSprites() {
        return sprites;
    }
    
    public MotionHandler getGravity() {
		return this.gravity;
	}
    
    public void setGame(Game game) {
		this.game = game;
	}

	public Game getGame() {
		return game;
	}
    
    // Actually, directly couple your model with the class "java.awt.Graphics" is not a good design
    // If you want to decouple them, create an interface that encapsulates the variation of the Graphics.
	public void renderBackground(Graphics g) {
		background.render(g);
	}
    public void render(Graphics g) {
        for (Sprite sprite : sprites) {
            sprite.render(g);
        }
		scorer.render(g);
    }
    
    // below private are edge detection
    private void pikachuCollideEdges(Point originalLocation, Pikachu pikachu) {
    	if (pikachu.getBody().intersects(Constant.GROUND)) {
    	  	// TODO state reset: resetState getIsJumping
    		if(pikachu.getIsInSky())
    			pikachu.resetState();
    		pikachu.setLocation(new Point(pikachu.getX(), Constant.GROUND_LINE - Constant.PIKACHU_HEIGHT));
    		pikachu.setV(new Point(pikachu.getVX(), 0));
    	} 
    	if(pikachu.getBody().intersects(Constant.RIGHT_WALL) || pikachu.getBody().intersects(Constant.LEFT_WALL)
    											   || pikachu.getBody().intersects(Constant.MIDDLE_WALL) ) {
    		pikachu.setLocation(originalLocation);
    		pikachu.setV(new Point(0, pikachu.getVY()));
    	}
	}

	private void ballCollideEdges(Point originalLocation, Ball ball) {
		if(ball.getBody().intersects(Constant.RIGHT_WALL)) {
			ball.setLocation(new Point(Constant.WORLD_WIDTH - Constant.BALL_RADIUS, (int) originalLocation.getY()));
			ball.setV(new Point(-(ball.getVX() / Math.abs(ball.getVX())) * 3, ball.getVY()));
		}
		else if (ball.getBody().intersects(Constant.LEFT_WALL)){
			ball.setLocation(new Point(0, (int) originalLocation.getY()));
			ball.setV(new Point(-(ball.getVX() / Math.abs(ball.getVX())) * 3, ball.getVY()));
		}		
		else if (ball.getBody().intersects(Constant.NET_SIDE)) {
			if(ball.getLocation().x < Constant.WORLD_HALF_WIDTH) 
				ball.setLocation(new Point(Constant.NET_UPPER_X - Constant.BALL_RADIUS, (int) originalLocation.getY()));			
			else
				ball.setLocation(new Point(Constant.NET_UPPER_X + Constant.NET_THICKNESS + Constant.BALL_RADIUS, (int) originalLocation.getY()));
			ball.setV(new Point(-(ball.getVX() / Math.abs(ball.getVX())) * 3, ball.getVY()));
		}
		else if(ball.getBody().intersects(Constant.NET_TOP)) {
			ball.setLocation(new Point((int) originalLocation.getX(), Constant.NET_TOP_UPPER_Y - Constant.BALL_RADIUS));
			ball.setV(new Point(ball.getVX(), -(ball.getVY() / Math.abs(ball.getVY())) * 3));
		}
		else if(ball.getBody().intersects(Constant.SKY_WALL)) {
			ball.setLocation(originalLocation);
			ball.setV(new Point(ball.getVX(), -(ball.getVY() / Math.abs(ball.getVY())) * 3));
		}
	}

	private int ballCollideGround(Ball ball) {		
    	if(ball.getBody().intersects(Constant.GROUND)) {
    		AudioPlayer.playSounds(AUDIO_BALL_DROP);
    		return ball.getLocation().x  < Constant.WORLD_HALF_WIDTH ? 2 : 1;
    	}
    	else {
    		return 0;
    	}
	}
}
