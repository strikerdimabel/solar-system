package belousdo.solarsystem;

/**
 * Created by belous.dmitri on 14.12.2016.
 */
public class CameraAnimation {

    private static final int ANIMATION_STEPS = 10;

    private double fixedX;
    private double fixedY;
    private double currX;
    private double currY;
    private double dx;
    private double dy;
    private double dScale;
    private double currScale;
    private int steps = 0;

    public CameraAnimation() {
        reset();
    }

    public synchronized void step() {
        if (steps == 0) {
            return;
        }
        double newScale = currScale + dScale;
        double newCurrX = (currX - fixedX) * (currScale / newScale) + fixedX;
        dx = newCurrX - currX;
        currX = newCurrX;
        double newCurrY = (currY - fixedY) * (currScale / newScale) + fixedY;
        dy = newCurrY - currY;
        currY = newCurrY;
        currScale = newScale;
        steps--;
        if (steps == 0) {
            reset();
        }
    }

    public void reset() {
        dx = 0;
        dy = 0;
        dScale = 0;
        steps = 0;
    }

    public synchronized double getDx() {
        return dx;
    }

    public synchronized double getDy() {
        return dy;
    }

    public synchronized double getDScale() {
        return dScale;
    }

    public synchronized void init(double dScale, double scale, double fixedX, double fixedY, double x, double y) {
        this.steps = ANIMATION_STEPS;
        this.dScale = dScale / steps;
        this.fixedX = fixedX;
        this.fixedY = fixedY;
        this.currX = x;
        this.currY = y;
        this.currScale = scale;
    }
}
