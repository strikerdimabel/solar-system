package belousdo.solarsystem;

/**
 * Created by Dmitri on 14.12.2016.
 */
public class ValueAnimation {

    private final static double ANIMATION_SPEED = 1.5;

    private double aim;
    private double step;
    private boolean started;
    private double minStep;

    public ValueAnimation() {
        reset();
    }

    public void reset() {
        started = false;
        step = 0;
    }

    public synchronized double step(double value) {
        if (!started) {
            return 0;
        }
        if (Math.abs(aim - value) <= step) {
            reset();
            return aim - value;
        }
        double dValue = step * Math.signum(aim - value);
        if ((step * 3 - minStep) / (ANIMATION_SPEED - 1) < Math.abs(aim - value)) {
            step *= ANIMATION_SPEED;
        } else {
            step /= ANIMATION_SPEED;
        }
        return dValue;
    }

    public synchronized void init(double firstStep, double aim) {
        step = firstStep;
        minStep = firstStep;
        this.aim = aim;
        started = true;
    }

    public void setAim(double aim) {
        this.aim = aim;
    }
}
