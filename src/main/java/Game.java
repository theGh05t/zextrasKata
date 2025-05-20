import Exceptions.InvalidRollValueException;
import Frames.AFrame;
import Frames.Frame;
import Frames.LastFrame;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private final List<AFrame> frames = new ArrayList<>(10);

    public void roll(int pinsDown) {
        if(pinsDown<0) {
            throw new InvalidRollValueException("negative pin roll");
        }
        if(pinsDown>10) {
            throw new InvalidRollValueException("pin roll over 10");
        }
        // if first roll create a new frame
        if (frames.isEmpty()) {
            frames.add(new Frame(pinsDown));
        } else if (frames.getLast().isEnded()) {
            // if last frame is endend(stike or second roll) create a new frame add it to the list and link to previus frame
            AFrame newFrame;
            if (frames.size() == 9) {
                //i need the last frame
                newFrame = new LastFrame(pinsDown);
            } else {
                newFrame = new Frame(pinsDown);
            }
            frames.getLast().setNextFrame(newFrame);
            frames.add(newFrame);
        } else {
            frames.getLast().setAnotherRoll(pinsDown);
        }
    }

    public int score() {
        return frames.stream().map(AFrame::score).reduce(0, Integer::sum);
    }

    public boolean isSpareRoll() {
        return frames.getLast().isSpare();
    }

    public boolean isStrikeRoll() {
        return frames.getLast().isStrike();
    }
}
