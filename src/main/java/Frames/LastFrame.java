package Frames;

import Exceptions.EndGameException;
import Exceptions.EndedFrameException;

public class LastFrame extends AFrame {

    private Integer bonusRoll;

    public LastFrame(int firstRoll) {
        this.firstRoll = firstRoll;
    }

    @Override
    public int score() {
        return firstRoll + (secondRoll == null ? 0 : secondRoll) + (bonusRoll==null?0:bonusRoll);
    }

    @Override
    public int bonusForStrike() {
        return firstRoll + (secondRoll == null ? 0 : secondRoll);
    }

    @Override
    public boolean isEnded() {
        var isStrikeOrSpare = (isStrike() || isSpare());
        return (secondRoll != null && !isStrikeOrSpare) || (isStrikeOrSpare && bonusRoll != null);
    }

    @Override
    public void setAnotherRoll(int roll) {
        if (isEnded()) throw new EndedFrameException("Frame is ended, create a new one.");
        if (secondRoll == null) {
            secondRoll = roll;
        } else if(bonusRoll == null){
            bonusRoll=roll;
        }else {
            throw new EndGameException("Game ended");
        }
    }

    @Override
    public void setNextFrame(AFrame nextFrame) {
        throw new EndGameException("The last frame of the game.");
    }
}
