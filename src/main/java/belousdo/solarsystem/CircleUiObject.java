package belousdo.solarsystem;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;

import static belousdo.solarsystem.DrawPanel.*;

/**
 * Created by belous.dmitri on 14.12.2016.
 */
@EqualsAndHashCode(of="title")
public abstract class CircleUiObject {

    private static final int HINT_RADIUS = 10;
    public static final Rectangle EMPTY_AREA = new Rectangle();

    private final String title;
    private final int key;
    private final char ch;
    private final String subTitle;
    @Getter private final double radius;
    @Getter private final Color color;
    @Setter private boolean showHint = true;
    private boolean selected = false;
    private boolean showSubTitle = false;

    private Shape shape = new Rectangle();
    private Shape hintShape = EMPTY_AREA;
    private Rectangle2D titleRectangle = new Rectangle2D.Double();
    private Rectangle2D subTitleRectangle = new Rectangle2D.Double();

    private final List<Planet> moons = new ArrayList<>();
    private final List<Ring> rings = new ArrayList<>();

    protected CircleUiObject(String title, int key, char ch, String subTitle, double radius, Color color) {
        this.title = title;
        this.ch = ch;
        this.key = key;
        this.subTitle = subTitle;
        this.radius = radius;
        this.color = color;
    }

    public boolean hidden() {
        return !showHint && !selected;
    }

    public char getCh() {
        return ch;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public String getTitle() {
        return title;
    }

    public void showSubtitle(boolean show) {
        showSubTitle = show;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public abstract void onTick(double timePassed);

    public int getKey() {
        return key;
    }

    public void onDraw(Graphics2D graphics2D, AffineTransform transform) {
        graphics2D.setColor(color);
        shape(graphics2D, transform);
        hint(graphics2D, transform);
    }

    private void hint(Graphics2D graphics2D, AffineTransform transform) {
        if (hidden() || radius >= 0.5 / transform.getScaleX()) {
            hintShape = EMPTY_AREA;
            return;
        }
        Point2D point = transform.transform(new Point2D.Double(getX(), getY()), new Point2D.Double());
        hintShape = new Arc2D.Double(
            point.getX() - HINT_RADIUS,
            point.getY() - HINT_RADIUS,
            2 * HINT_RADIUS, 2 * HINT_RADIUS,
            0, 360, Arc2D.OPEN
        );
        graphics2D.draw(hintShape);
    }

    private void shape(Graphics2D graphics2D, AffineTransform transform) {
        Rectangle bounds = graphics2D.getClipBounds();
        bounds.setBounds(-1, 0, bounds.width + 1, bounds.height);
        shape = transform.createTransformedShape(new Arc2D.Double(
            getX() - radius,
            getY() - radius,
            2*radius, 2*radius,
            0, 360, Arc2D.OPEN
        ));
        if (!shape.intersects(bounds)) {
            return;
        }
        if (!MAX_RECT.contains(shape.getBounds2D())) {
            Area shapeArea = new Area(shape);
            shapeArea.intersect(new Area(bounds));
            shape = shapeArea;
        }
        graphics2D.fill(shape);
    }

    protected abstract double getY();

    protected abstract double getX();

    public void text(Graphics2D graphics2D, AffineTransform transform) {
        if (hidden()) {
            titleRectangle.setRect(-1, -1, 0, 0);
            subTitleRectangle.setRect(-1, -1, 0, 0);
            return;
        }


        Point2D distPoint = transform.transform(new Point2D.Double(getX(), getY()), new Point2D.Double());

        Rectangle2D titleBounds = FONT.getStringBounds(title, graphics2D.getFontRenderContext());
        Rectangle2D subTitleBounds = SUB_FONT.getStringBounds(subTitle, graphics2D.getFontRenderContext());

        double width = (showSubTitle ? Math.max(subTitleBounds.getWidth(), titleBounds.getWidth()) : titleBounds.getWidth());

        double y;
        Color labelColor;
        Color fillColor;
        if (2 * radius * transform.getScaleX() > width + titleBounds.getHeight() / 2. + (showSubTitle ? subTitleBounds.getHeight() /2. : 0)) {
            y = distPoint.getY() + titleBounds.getHeight() / 2. - 3;
            labelColor = DrawPanel.BACKGROUND_COLOR;
            fillColor = null;
        } else {
            y = distPoint.getY() + titleBounds.getHeight() - 3 +
                            (radius < 0.5 / transform.getScaleX() ? HINT_RADIUS : 0) +
                            radius * transform.getScaleX();
            labelColor = getColor();
            fillColor = DrawPanel.BACKGROUND_COLOR;
        }

        double titleX = distPoint.getX() - titleBounds.getWidth() / 2.;
        titleRectangle.setRect(titleX - 2, y - titleBounds.getHeight() + 3, titleBounds.getWidth() + 4, titleBounds.getHeight());
        if (fillColor != null) {
            graphics2D.setColor(fillColor);
            graphics2D.fill(titleRectangle);
        }
        graphics2D.setColor(labelColor);
        graphics2D.setFont(selected ? FONT_BOLD : FONT);
        graphics2D.drawString(title, (float) titleX, (float) y);

        if (!showSubTitle) {
            subTitleRectangle.setRect(-1, -1, 0, 0);
            return;
        }

        double subTitleX = distPoint.getX() - subTitleBounds.getWidth() / 2.;
        subTitleRectangle.setRect(subTitleX - 2, y + 2, subTitleBounds.getWidth() + 5, subTitleBounds.getHeight());
        if (fillColor != null) {
            graphics2D.setColor(fillColor);
            graphics2D.fill(subTitleRectangle);
        }
        graphics2D.setColor(labelColor);
        graphics2D.setFont(SUB_FONT);
        graphics2D.drawString(subTitle, (float) subTitleX, (float) (y + subTitleBounds.getHeight() - 3));
    }

    public boolean in(double x, double y) {
        return
            titleRectangle.contains(x, y) ||
            hintShape.contains(x, y) ||
            shape.contains(x, y) ||
            showSubTitle && subTitleRectangle.contains(x, y);
    }

    public int getMoonsCount() {
        return moons.size();
    }

    public int getRingsCount() {
        return rings.size();
    }

    public List<Planet> getMoons() {
        return moons;
    }

    public List<Ring> getRings() {
        return rings;
    }

    public void addMoon(Planet planet) {
        moons.add(planet);
    }

    public void addRing(Ring ring) {
        rings.add(ring);
    }
}
