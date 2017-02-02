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
    private static final double PI_2 = Math.PI * 2;

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
    private double T;
    private final double rotation = PI_2 * RANDOM.nextDouble();
    private AffineTransform rotationTransform = new AffineTransform();

    private boolean ellipseOrbit = false;

    // remove
    private double phi = PI_2 * RANDOM.nextDouble();

    public Planet(String title, int key, char ch, String subTitle, double orbitRadius, double e, double radius, double t, Color color) {
        super(title, key, ch, subTitle, radius, color);
        this.orbitRadius = orbitRadius;
        semiMinorAxis = orbitRadius * (1 - e*e);
        this.T = t;
        this.speed = PI_2 / t;
        this.e = e;
        setOffset(0, 0);
        rotationTransform.rotate(rotation, xOffset, yOffset);
    }

    public void setOffset(double xOffset, double yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        if (ellipseOrbit) {
            rotationTransform.setToIdentity();
            rotationTransform.rotate(rotation, xOffset, yOffset);
        }
        recalc();
    }

    public boolean isEllipseOrbit() {
        return ellipseOrbit;
    }

    public void setEllipseOrbit(boolean ellipseOrbit) {
        if (ellipseOrbit != this.ellipseOrbit) {
            this.ellipseOrbit = ellipseOrbit;
            if (ellipseOrbit) {
                phi -= rotation;
            } else {
                phi += rotation;
            }
        }
    }

    private void recalc() {
        if (ellipseOrbit) {
            double M = phi;
            double EPrev = M;
            while (true) {
                double ENew = e * Math.sin(EPrev) + M;
                double diff = Math.abs(ENew - EPrev);
                EPrev = ENew;
                if (diff < EPSILON) {
                    break;
                }
            }
            notRotatedX = orbitRadius * (Math.cos(EPrev) - e) + xOffset;
            notRotatedY = semiMinorAxis * Math.sin(EPrev) + yOffset;
            Point2D coords = rotationTransform.transform(new Point2D.Double(notRotatedX, notRotatedY), new Point2D.Double());
            x = coords.getX();
            y = coords.getY();
        } else {
            x = orbitRadius * Math.cos(phi) + xOffset;
            y = orbitRadius * Math.sin(phi) + yOffset;
        }
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

        orbit(graphics2D, transform, bounds);
        rings(graphics2D, transform, bounds);

        super.onDraw(graphics2D, transform);
    }

    private void orbit(Graphics2D graphics2D, AffineTransform transform, Rectangle bounds) {
        if (!hidden()) {
            Shape newOrbit;
            if (ellipseOrbit) {
                newOrbit = new Arc2D.Double(
                    xOffset - orbitRadius * (1 + e), yOffset - semiMinorAxis,
                    2 * orbitRadius, 2 * semiMinorAxis,
                    0, 360, Arc2D.OPEN
                );
                ((Arc2D) newOrbit).setAngleStart(new Point2D.Double(notRotatedX, notRotatedY));
                newOrbit = rotationTransform.createTransformedShape(newOrbit);
            } else {
                newOrbit = new Arc2D.Double();
                ((Arc2D) newOrbit).setArcByCenter(xOffset, yOffset, orbitRadius, 0, 360, Arc2D.OPEN);
                ((Arc2D) newOrbit).setAngleStart(new Point2D.Double(x, y));
            }
            newOrbit = transform.createTransformedShape(newOrbit);
            if (!newOrbit.intersects(bounds)) {
                return;
            }
            if (!MAX_RECT.contains(newOrbit.getBounds2D())) {
                Area orbitArea = new Area(newOrbit);
                orbitArea.intersect(new Area(bounds));
                newOrbit = orbitArea;
            }
            graphics2D.draw(newOrbit);
        }
    }

    private void rings(Graphics2D graphics2D, AffineTransform transform, Rectangle bounds) {
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
            if (!ringArea.intersects(bounds)) {
                continue;
            }
            if (!MAX_RECT.contains(ringArea.getBounds2D())) {
                ringArea.intersect(new Area(bounds));
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            }
            graphics2D.setColor(ring.getColor());
            graphics2D.fill(ringArea);
        }
    }

    public void onTick(double timePassed) {
        phi += speed * timePassed;
        if (phi > PI_2) {
            phi = phi - Math.floor(phi / PI_2) * PI_2;
        }
        recalc();
        for (Planet moon : getMoons()) {
            moon.setOffset(getX(), getY());
        }
    }

    public double getT() {
        return T;
    }

    public double getOrbitRaduis() {
        return orbitRadius;
    }

}
