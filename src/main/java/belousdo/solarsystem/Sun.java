package belousdo.solarsystem;

import java.awt.*;

/**
 * Created by Dmitri on 14.12.2016.
 */
public class Sun extends CircleUiObject {

    public Sun(String title, int key, char ch, String subTitle, double radius, Color color) {
        super(title, key, ch, subTitle, radius, color);
    }

    @Override
    public double getX() {
        return 0;
    }

    @Override
    public double getY() {
        return 0;
    }

    public void onTick(double timePassed) {
    }

    public double getT() {
        return 0;
    }

    public double getOrbitRaduis() {
        return 0;
    }

    public int getMoonsCount() {
        return 0;
    }

    public int getRingsCount() {
        return 0;
    }
}
