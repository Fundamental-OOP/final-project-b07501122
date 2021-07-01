package pikachu;

import fsm.CyclicSequence;
import fsm.ImageState;

import java.util.List;

public class Walking extends CyclicSequence {
    
    public Walking(List<ImageState> states) {
        super(states);
    }

    @Override
    public String toString() {
        return "Walking";
    }
}