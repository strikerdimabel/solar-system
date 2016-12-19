package belousdo.solarsystem;

import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Random;

import static belousdo.solarsystem.DrawPanel.MAX_RECT;

/**
 * Created by Dmitri on 13.12.2016.
 */
public class Planet extends CircleUiObject {

    private static final Random RANDOM = new Random();

    private final double orbitRadius;
    private final double speed;
    private double xOffset = 0;
    private double yOffset = 0;
    private double x;
    private double y;

    private final java.util.List<Planet> moons = new ArrayList<>();
    private final java.util.List<Ring> rings = new ArrayList<>();

    private double phi = 2 * Math.PI * RANDOM.nextDouble();

    public Planet(String title, int key, char ch, String subTitle, double orbitRadius, double radius, double speed, Color color) {
        super(title, key, ch, subTitle, radius, color);
        this.orbitRadius = orbitRadius;
        this.speed = speed;
        recalcX();
        recalcY();
    }

    private void recalcX() {
        x = xOffset + orbitRadius * Math.cos(phi);
    }

    private void recalcY() {
        y = yOffset + orbitRadius * Math.sin(phi);
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    public void onDraw(Graphics2D graphics2D, AffineTransform transform) {
        graphics2D.setColor(getColor());
        Rectangle bounds = graphics2D.getClipBounds();
        bounds.setBounds(-1, 0, bounds.width + 1, bounds.height);
        if (!hidden()) {
            Shape orbit = new Arc2D.Double();
            ((Arc2D) orbit).setArcByCenter(
                xOffset, yOffset, orbitRadius, 0, 360, Arc2D.OPEN
            );
            ((Arc2D) orbit).setAngleStart(new Point2D.Double(getX(), getY()));
            orbit = transform.createTransformedShape(orbit);
            if (!MAX_RECT.contains(orbit.getBounds2D())) {
                Area orbitArea = new Area(orbit);
                orbitArea.intersect(new Area(bounds));
                orbit = orbitArea;
            }
            graphics2D.draw(orbit);
        }

        for (Ring ring : rings) {
            Shape outerRing = new Arc2D.Double();
            ((Arc2D) outerRing).setArcByCenter(
                getX(), getY(), ring.getOutR(), 0, 360, Arc2D.OPEN
            );
            outerRing = transform.createTransformedShape(outerRing);
            Shape innerRing = new Arc2D.Double();
            ((Arc2D) innerRing).setArcByCenter(
                getX(), getY(), ring.getInR(), 0, 360, Arc2D.OPEN
            );
            innerRing = transform.createTransformedShape(innerRing);
            Area ringArea = new Area(outerRing);
            ringArea.subtract(new Area(innerRing));
            if (!MAX_RECT.contains(ringArea.getBounds2D())) {
                ringArea.intersect(new Area(bounds));
            }
            graphics2D.setColor(ring.getColor());
            graphics2D.fill(ringArea);
        }
        super.onDraw(graphics2D, transform);
    }

    public void setXOffset(double xOffset) {
        this.xOffset = xOffset;
        recalcX();
    }

    public void setYOffset(double yOffset) {
        this.yOffset = yOffset;
        recalcY();
    }

    public void onTick(double timePassed) {
        phi += timePassed * speed;
        if (phi == Double.POSITIVE_INFINITY) {
            phi = 0;
        }
        recalcX();
        recalcY();
        for (Planet moon : moons) {
            moon.setXOffset(getX());
            moon.setYOffset(getY());
        }
    }

    public void addMoon(Planet planet) {
        moons.add(planet);
    }

    public void addRing(Ring ring) {
        rings.add(ring);
    }

    @Override
    public double getT() {
        return 2 * Math.PI / speed;
    }

    @Override
    public double getOrbitRaduis() {
        return orbitRadius;
    }

    @Override
    public int getMoons() {
        return moons.size();
    }

    @Override
    public int getRings() {
        return rings.size();
    }
}
