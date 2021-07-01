package pikachu;

import fsm.CyclicSequence;
import fsm.ImageState;

import java.util.List;

public class Jumping extends CyclicSequence {
    public Jumping(List<ImageState> states) {
        super(states);
    }

    @Override
    public String toString() {
        return "Jumping";
    }
}