package pikachu;

import fsm.FiniteStateMachine;
import fsm.ImageRenderer;
import fsm.State;
import fsm.WaitingPerFrame;
import media.AudioPlayer;
import model.Direction;
import model.ShadowSprite;
import model.VelocitySprite;
import model.SpriteShape;
import utils.Constant;

import java.awt.*;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import static fsm.FiniteStateMachine.Transition.from;
import static pikachu.Pikachu.Event.*;
import static utils.ImageStateUtils.imageStatesFromFolder;

public class Pikachu extends VelocitySprite {
    private final SpriteShape shape;
    private final FiniteStateMachine fsm;
    private final Set<Direction> directions = new CopyOnWriteArraySet<>();
    private boolean lflag, rflag; /* flag to track key pressing */
    private boolean isInSky;
    
    private final ShadowSprite shadow;

    public enum Event {
        WALK, STOP, JUMP, ATTACK
    }
    
	public static final String AUDIO_SMASH = "smash";
	public static final String AUDIO_JUMP = "jump";
	
    public Pikachu(Point velocity, Point location) {
        super(velocity);
        this.location = location;
        shape = new SpriteShape(new Dimension(Constant.PIKACHU_WIDTH, Constant.PIKACHU_HEIGHT),
                                new Dimension(Constant.PIKACHU_OFFSET_X, Constant.PIKACHU_OFFSET_Y), 
                                new Dimension(Constant.PIKACHU_BODY_WIDTH, Constant.PIKACHU_BODY_HEIGHT));
        fsm = new FiniteStateMachine();
        shadow = new ShadowSprite(this, Constant.SHADOW_IMG_PATH);

        ImageRenderer imageRenderer = new PikachuImageRenderer(this);
        State walking = new WaitingPerFrame(12,
                new Walking(imageStatesFromFolder("assets/pikachu/walking", imageRenderer)));
        State diving = new WaitingPerFrame(3,
                new Diving(this, fsm, imageStatesFromFolder("assets/pikachu/diving", imageRenderer)));
        State jumping = new WaitingPerFrame(12,
                new Jumping(imageStatesFromFolder("assets/pikachu/jumping", imageRenderer)));
        State smashing = new WaitingPerFrame(12,
                new Smashing(fsm, imageStatesFromFolder("assets/pikachu/smashing", imageRenderer)));

        fsm.setInitialState(walking);
        fsm.addReset(walking, walking);
        fsm.addReset(diving, walking);
        fsm.addReset(jumping, walking);
        fsm.addReset(smashing, jumping);

        fsm.addTransition(from(walking).when(ATTACK).to(diving));
        fsm.addTransition(from(walking).when(JUMP).to(jumping));
        // fsm.addTransition(from(diving).when(JUMP).to(jumping));
        fsm.addTransition(from(jumping).when(ATTACK).to(smashing));

        lflag = rflag = false;
        isInSky = false;
    }

    public void move(Direction direction) {
        face = direction;
        /* flag to track key pressing */
        if (direction == Direction.LEFT) {
            lflag = true;
            setV(new Point(Constant.WALKING_LEFT_SPEED_X, getVY()));
        } else {
            rflag = true;
            setV(new Point(Constant.WALKING_RIGHT_SPEED_X, getVY()));
        }
    }

    public void stop(Direction direction) {
        /* flag to track key pressing */
        if (direction == Direction.LEFT)
            lflag = false;
        else if (direction == Direction.RIGHT)
            rflag = false;
        if (rflag)
            move(Direction.RIGHT);
        else if (lflag)
            move(Direction.LEFT);
        else setV(new Point(0, getVY()));
    }

    public void jump() {
        if (!isInSky) {
            isInSky = true;
            setV(new Point(getVX(), Constant.JUMPING_SPEED_Y));
            AudioPlayer.playSounds(AUDIO_JUMP);
            fsm.trigger(JUMP);
        }
    }

    public void attack() {
        if (getVX() != 0 && !isInSky) {
            if (face == Direction.LEFT)
                setV(new Point(Constant.DIVING_LEFT_SPEED_X, getVY()));
            else /* face == Direction.RIGHT */
                setV(new Point(Constant.DIVING_RIGHT_SPEED_X, getVY()));
            AudioPlayer.playSounds(AUDIO_JUMP);
            fsm.trigger(ATTACK);
        } else if (isInSky) {
        	AudioPlayer.playSounds(AUDIO_SMASH);
            fsm.trigger(ATTACK);
        }       
    }

    public void update() {
        fsm.update();
        this.world.move(this, new Dimension(getVX(), getVY()));
        this.getWorld().getGravity().handle(this);
    }

    @Override
    public void render(Graphics g) {
        fsm.render(g);
        shadow.render(g);
    }

    public boolean getLflag() {
        return lflag;
    }

    public boolean getRflag() {
        return rflag;
    }

    public boolean getSmashing() {
        return fsm.currentState().toString().equals("Smashing");
    }

    public Set<Direction> getDirections() {
        return directions;
    }

    @Override
    public Point getLocation() {
        return location;
    }

    @Override
    public Rectangle getRange() {
        return new Rectangle(location, shape.size);
    }

    @Override
    public Dimension getBodyOffset() {
        return shape.bodyOffset;
    }

    @Override
    public Dimension getBodySize() {
        return shape.bodySize;
    }

    public void reset(Point velocity, Point location) {
        this.velocity = velocity;
        this.location = location;
        lflag = rflag = false;
        isInSky = false;
        fsm.init();
    }
    
    public boolean getIsInSky() {
		return isInSky;
	}
    
    public void resetState() {
        isInSky = false;
        fsm.init();
    }
}