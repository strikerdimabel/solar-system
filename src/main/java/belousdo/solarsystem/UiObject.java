package belousdo.solarsystem;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by Dmitri on 13.12.2016.
 */
public interface UiObject {

    double getX();
    double getY();
    void onDraw(Graphics2D graphics2D, AffineTransform transform);
    void text(Graphics2D graphics2D, AffineTransform transform);
    void onTick(double timePassed);
    void setSelected(boolean selected);
    int getKey();
    char getCh();
    String getTitle();
    String getSubTitle();
    void showSubtitle(boolean show);
    boolean in(double x, double y);
    double getT();
    double getOrbitRaduis();
    double getRadius();

    int getMoons();
    int getRings();
}
