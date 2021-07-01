package ball;

import fsm.CyclicSequence;
import fsm.ImageState;

import java.util.List;

public class Super extends CyclicSequence {
    public Super(List<ImageState> states) {
        super(states);
    }

    @Override
    public String toString() {
        return "Super";
    }
}

