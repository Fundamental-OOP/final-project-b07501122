package pikachu;

import fsm.Sequence;
import fsm.State;
import fsm.StateMachine;
import media.AudioPlayer;

import java.util.List;

public class Smashing extends Sequence {

	private final StateMachine stateMachine;

    public Smashing(StateMachine stateMachine, List<? extends State> states) {
        super(states);
        this.stateMachine = stateMachine;
    }

    @Override
    protected void onSequenceEnd() {
        currentPosition = 0;
        stateMachine.reset();
    }

    @Override
    public String toString() {
        return "Smashing";
    }
}