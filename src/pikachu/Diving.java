package pikachu;

import fsm.Sequence;
import fsm.State;
import fsm.StateMachine;

import java.awt.*;
import java.util.List;

public class Diving extends Sequence {
    private final Pikachu pikachu;
    private final StateMachine stateMachine;

    public Diving(Pikachu pikachu, StateMachine stateMachine, List<? extends State> states) {
        super(states);
        this.pikachu = pikachu;
        this.stateMachine = stateMachine;
    }

    @Override
    protected void onSequenceEnd() {
        currentPosition = 0;
        if (pikachu.getLflag()) {
            pikachu.setV(new Point(-6, 0));
        } else if (pikachu.getRflag()) {
            pikachu.setV(new Point(6, 0));
        }
        stateMachine.reset();
    }

    @Override
    public String toString() {
        return "Diving";
    }
}