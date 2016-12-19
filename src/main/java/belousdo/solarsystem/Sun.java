package belousdo.solarsystem;

import java.awt.*;

/**
 * Created by Dmitri on 14.12.2016.
 */
public class Sun extends CircleUiObject {

    public Sun(String title, int key, char ch, String subTitle, double radius) {
        super(title, key, ch, subTitle, radius, new Color(255,255,220));
        setSelected(true);
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

    @Override
    public double getT() {
        return 0;
    }

    @Override
    public double getOrbitRaduis() {
        return 0;
    }

    @Override
    public int getMoons() {
        return 0;
    }

    @Override
    public int getRings() {
        return 0;
    }
}
