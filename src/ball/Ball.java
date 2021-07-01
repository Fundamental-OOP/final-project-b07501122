package ball;
import model.Direction;
import model.ShadowSprite;
import model.SpriteShape;
import model.VelocitySprite;
import fsm.FiniteStateMachine;
import fsm.ImageRenderer;
import fsm.State;
import fsm.WaitingPerFrame;
import media.AudioPlayer;

import static fsm.FiniteStateMachine.Transition.from;
import static ball.Ball.Event.*;
import static utils.ImageStateUtils.imageStatesFromFolder;
import utils.Constant;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

public class Ball extends VelocitySprite {
    private final SpriteShape shape;
    private List<Rectangle> prevRanges = new ArrayList<>();
    private final FiniteStateMachine fsm;
    private final Set<Direction> directions = new CopyOnWriteArraySet<>();
    private final int recordPrevRangeSize = 2;
    private final int recordPrevRangeWaitFrame = 10;
    private int recordPrevRangeCountDown;
    private final ShadowSprite shadow;

    public static final String AUDIO_SMASH_BALL = "smash_ball";
    
    public enum Event {
        Collision, SMASH
    }

    public Ball(Point velocity, Point location) {
        super(velocity);
        this.location = location;
        // set ball's size.
        // TODO constant
        shape = new SpriteShape(new Dimension(Constant.BALL_RADIUS, Constant.BALL_RADIUS),
                new Dimension(0, 0), new Dimension(Constant.BALL_RADIUS, Constant.BALL_RADIUS));
        fsm = new FiniteStateMachine();
        shadow = new ShadowSprite(this, Constant.SHADOW_IMG_PATH);
        recordPrevRangeCountDown = recordPrevRangeWaitFrame;

        this.resetFsm();
    }

    public void resetFsm() {
        ImageRenderer imageRenderer = new BallImageRenderer(this);
        ImageRenderer smashImageRenderer = new SmashBallImageRenderer(this,
                Arrays.asList(Constant.BALL_HPYER_IMG_PATH, Constant.BALL_TRAIL_IMG_PATH));
        State normal_mode = new WaitingPerFrame(4,
                new Normal(imageStatesFromFolder("assets/ball", imageRenderer)));
        State super_mode = new WaitingPerFrame(4,
                new Super(imageStatesFromFolder("assets/ball", smashImageRenderer)));

        fsm.setInitialState(normal_mode);
        fsm.addTransition(from(normal_mode).when(SMASH).to(super_mode));
        fsm.addTransition(from(super_mode).when(Collision).to(normal_mode));
    }

    public List<Rectangle> getPrevRanges() {
        return prevRanges;
    }

    public void getSmash() {
    	AudioPlayer.playSounds(AUDIO_SMASH_BALL);
        fsm.trigger(SMASH);
    }

    public void getHit() {
        fsm.trigger(Collision);
    }

    public List<Rectangle> getPrevLocations() {
        return prevRanges;
    }

    public void reset(Point velocity, Point location) {
        this.setV(velocity);
        this.resetFsm();
        this.location = location;
        this.prevRanges.clear();
        this.recordPrevRangeCountDown = recordPrevRangeWaitFrame;
    }

    public void attack() {
    }

    public int getDamage() {
        return 0;
    }

    private void updatePrevRange() {
        --recordPrevRangeCountDown;
        if (recordPrevRangeCountDown == 0) {
            recordPrevRangeCountDown = recordPrevRangeWaitFrame;
            if (prevRanges.size() >= recordPrevRangeSize)
                prevRanges.remove(0);
            prevRanges.add(getRange());
        }
    }

    public void update() {
        updatePrevRange();
        fsm.update();
        this.getWorld().move(this, new Dimension(this.getVX(), this.getVY()));
        this.getWorld().getGravity().handle(this);
    }

    @Override
    public void render(Graphics g) {
        fsm.render(g);
        shadow.render(g);
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
}
