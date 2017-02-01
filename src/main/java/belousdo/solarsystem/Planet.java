package belousdo.solarsystem;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.util.Random;

import static belousdo.solarsystem.DrawPanel.MAX_RECT;

/**
 * Created by Dmitri on 13.12.2016.
 */
public class Planet extends CircleUiObject {

    private static final Random RANDOM = new Random();
    private static final double EPSILON = 0.001;

    private final double orbitRadius;
    private final double semiMinorAxis;
    private final double speed;
    private double xOffset;
    private double yOffset;
    private double x;
    private double y;
    private double notRotatedX;
    private double notRotatedY;

    private double e;
    private double t;
    private final double rotation = 2 * Math.PI * RANDOM.nextDouble();
    private AffineTransform rotationTransform = new AffineTransform();

    // remove
    private double phi = 2 * Math.PI * RANDOM.nextDouble();

    public Planet(String title, int key, char ch, String subTitle, double orbitRadius, double e, double radius, double t, Color color) {
        super(title, key, ch, subTitle, radius, color);
        this.orbitRadius = orbitRadius;
        semiMinorAxis = orbitRadius * (1 - e*e);
        this.speed = 2 * Math.PI / t;
        this.t = t * RANDOM.nextDouble();
        this.e = e;
        setOffset(0, 0);
    }

    public void setOffset(double xOffset, double yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        rotationTransform.setToIdentity();
        rotationTransform.rotate(rotation, xOffset, yOffset);
        recalc();
    }

    private void recalc() {
//        notRotatedX = xOffset + orbitRadius * FastMath.cos(phi);
//        notRotatedY = yOffset + orbitRadius * FastMath.sin(phi);
        double M = t*speed;
        double EPrev = M;
        while (true) {
            double ENew = e * Math.sin(EPrev) + M;
            double diff = Math.abs(ENew - EPrev);
            EPrev = ENew;
            if (diff < EPSILON) {
//                System.out.println(count);
                break;
            }
        }
        notRotatedX = orbitRadius * (Math.cos(EPrev) - e) + xOffset;
        notRotatedY = semiMinorAxis * Math.sin(EPrev) + yOffset;
        Point2D coords = rotationTransform.transform(new Point2D.Double(notRotatedX, notRotatedY), new Point2D.Double());
        x = coords.getX();
        y = coords.getY();
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
            Shape newOrbit = new Arc2D.Double(
                xOffset - orbitRadius * (1 + e), yOffset - semiMinorAxis,
                2 * orbitRadius, 2 * semiMinorAxis,
                0, 360, Arc2D.OPEN
            );
            ((Arc2D) newOrbit).setAngleStart(new Point2D.Double(notRotatedX, notRotatedY));
            newOrbit = rotationTransform.createTransformedShape(newOrbit);
            newOrbit = transform.createTransformedShape(newOrbit);
            if (!MAX_RECT.contains(newOrbit.getBounds2D())) {
                Area orbitArea = new Area(newOrbit);
                orbitArea.intersect(new Area(bounds));
                newOrbit = orbitArea;
            }
            graphics2D.draw(newOrbit);
        }

        for (Ring ring : getRings()) {
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

    public void onTick(double timePassed) {
        t += timePassed;
        if (t == Double.POSITIVE_INFINITY) {
            t = 0;
        }
        recalc();
        for (Planet moon : getMoons()) {
            moon.setOffset(getX(), getY());
        }
    }

    public double getT() {
        return 2 * Math.PI / speed;
    }

    public double getOrbitRaduis() {
        return orbitRadius;
    }

}
