package belousdo.solarsystem;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.text.AttributedString;

import static belousdo.solarsystem.DrawPanel.*;

/**
 * Created by belous.dmitri on 14.12.2016.
 */
public abstract class CircleUiObject implements UiObject {

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

    @Override
    public String getSubTitle() {
        return subTitle;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void showSubtitle(boolean show) {
        showSubTitle = show;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public int getKey() {
        return key;
    }

    public void onDraw(Graphics2D graphics2D, AffineTransform transform) {
        graphics2D.setColor(color);

        shape = transform.createTransformedShape(new Arc2D.Double(
            getX() - radius,
            getY() - radius,
            2*radius, 2*radius,
            0, 360, Arc2D.OPEN
        ));
        graphics2D.fill(shape);

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

    @Override
    public void text(Graphics2D graphics2D, AffineTransform transform) {
        if (hidden()) {
            titleRectangle.setRect(-1, -1, 0, 0);
            subTitleRectangle.setRect(-1, -1, 0, 0);
            return;
        }
        Point2D distPoint = transform.transform(new Point2D.Double(getX(), getY()), new Point2D.Double());

        graphics2D.setFont(FONT);
        FontMetrics titleMetrics = graphics2D.getFontMetrics();
        graphics2D.setFont(SUB_FONT);
        FontMetrics subTitleMetrics = graphics2D.getFontMetrics();

        int titleHeight = titleMetrics.getHeight();
        int subTitleHeight = subTitleMetrics.getHeight();

        int titleWidth = titleMetrics.stringWidth(title);
        int subTitleWidth = subTitleMetrics.stringWidth(subTitle);

        int width = (showSubTitle ? Math.max(titleWidth, subTitleWidth) : titleWidth);

        double y;
        Color labelColor;
        Color fillColor;
        if (2 * radius * transform.getScaleX() > width + titleHeight / 2. + (showSubTitle ? subTitleHeight /2. : 0)) {
            y = distPoint.getY() + titleHeight / 2. - 3;
            labelColor = DrawPanel.BACKGROUND_COLOR;
            fillColor = null;
        } else {
            y = distPoint.getY() + titleHeight - 3 +
                            (radius < 0.5 / transform.getScaleX() ? HINT_RADIUS : 0) +
                            radius * transform.getScaleX();
            labelColor = getColor();
            fillColor = DrawPanel.BACKGROUND_COLOR;
        }

        AttributedString titleString = new AttributedString(title);
        if (selected) {
            titleString.addAttribute(TextAttribute.FONT, FONT_BOLD);
            titleString.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        } else {
            titleString.addAttribute(TextAttribute.FONT, FONT);
        }
        double titleX = distPoint.getX() - titleWidth / 2.;
        titleRectangle.setRect(titleX - 2, y - titleHeight + 5, titleWidth + 5, titleHeight);
        if (fillColor != null) {
            graphics2D.setColor(fillColor);
            graphics2D.fill(titleRectangle);
        }
        graphics2D.setColor(labelColor);
        graphics2D.drawString(titleString.getIterator(), (float) titleX, (float) y);

        if (!showSubTitle) {
            subTitleRectangle.setRect(-1, -1, 0, 0);
            return;
        }

        double subTitleX = distPoint.getX() - subTitleWidth / 2;
        subTitleRectangle.setRect(subTitleX - 2, y + 2, subTitleWidth + 5, subTitleHeight);
        if (fillColor != null) {
            graphics2D.setColor(fillColor);
            graphics2D.fill(subTitleRectangle);
        }
        graphics2D.setColor(labelColor);
        graphics2D.drawString(subTitle, (float) subTitleX, (float) y + subTitleHeight - 3);
    }

    @Override
    public boolean in(double x, double y) {
        return
            titleRectangle.contains(x, y) ||
            hintShape.contains(x, y) ||
            shape.contains(x, y) ||
            showSubTitle && subTitleRectangle.contains(x, y);
    }
}
