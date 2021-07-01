package ball;

import fsm.CyclicSequence;
import fsm.ImageState;

import java.util.List;

public class Normal extends CyclicSequence {
    public Normal(List<ImageState> states) {
        super(states);
    }

    @Override
    public String toString() {
        return "Normal";
    }
}