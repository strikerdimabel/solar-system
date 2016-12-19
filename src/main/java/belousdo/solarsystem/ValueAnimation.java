package belousdo.solarsystem;

/**
 * Created by Dmitri on 14.12.2016.
 */
public class ValueAnimation {

    private double aim;
    private double lastStep;
    private double scale;
    private boolean started;
    private boolean fixedStep;

    public ValueAnimation() {
        this(false);
    }

    public ValueAnimation(boolean fixedStep) {
        reset();
        this.fixedStep = fixedStep;
    }

    public void reset() {
        started = false;
        lastStep = 0;
    }

    public synchronized double step(double value) {
        if (!started) {
            return 0;
        }
        if (!fixedStep) {
            lastStep *= Math.min(2, Math.abs(aim - value) / Math.abs(5*lastStep));
        }
        if (Math.abs(aim - value) < scale) {
            reset();
            return aim - value;
        }
        return lastStep * Math.signum(aim - value);
    }

    public synchronized void init(double firstStep, double aim) {
        lastStep = firstStep;
        this.scale = firstStep;
        this.aim = aim;
        started = true;
    }

    public void setAim(double aim) {
        this.aim = aim;
    }
}
