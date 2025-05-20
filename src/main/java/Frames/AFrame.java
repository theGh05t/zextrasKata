package Frames;

public abstract class AFrame {

    protected int firstRoll;
    protected Integer secondRoll;

    protected AFrame nextFrame;

    /**
     * score for the frame calculated by the possible bonus for the next frame
     *
     */
    public abstract int score();

    /**
     * bonus for strike is related two subsequent rolls
     *
     */
    public abstract int bonusForStrike();

    /**
     * The frame cant contain other rolls
     *
     */
    public abstract boolean isEnded();

    /**
     * add a roll at frame
     *
     */
    public abstract void setAnotherRoll(int roll);

    /**
     * set the frame after this one
     *
     */
    public void setNextFrame(AFrame nextFrame) {
        this.nextFrame = nextFrame;
    }

    public final boolean isStrike() {
        return firstRoll == 10;
    }

    public final boolean isSpare() {
        if (secondRoll == null) return false;
        return firstRoll + secondRoll == 10;
    }
}
