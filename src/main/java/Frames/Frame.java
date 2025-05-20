package Frames;

import Exceptions.EndedFrameException;
import Exceptions.InvalidRollValueException;

public class Frame extends AFrame {


    public Frame(int firstRoll) {
        this.firstRoll = firstRoll;
    }

    public boolean isEnded() {
        return isStrike() || secondRoll != null;
    }

    public void setAnotherRoll(int otherRollResult) {
        if (isEnded()) throw new EndedFrameException("Frame is ended, create a new one.");
        if (otherRollResult + firstRoll > 10) throw new InvalidRollValueException("pin roll over 10");
        this.secondRoll = otherRollResult;
    }

    @Override
    public int score() {
        if (nextFrame != null) {
            if (isSpare()) {
                return 10 + nextFrame.firstRoll;
            }
            if (isStrike()) {
                return 10 + nextFrame.bonusForStrike();
            }
        }
        return firstRoll + (secondRoll == null ? 0 : secondRoll);
    }

    @Override
    public int bonusForStrike() {
        int nextFrameFirstRoll = nextFrame == null ? 0 : nextFrame.firstRoll;
        int nextRoll = secondRoll == null ? nextFrameFirstRoll : secondRoll;
        return firstRoll + nextRoll;
    }

}
