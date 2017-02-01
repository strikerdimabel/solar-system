package belousdo.solarsystem;

import org.apache.commons.math3.util.FastMath;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.Closeable;
import java.io.IOException;
import java.text.AttributedString;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created dragY Dmitri on 13.12.2016.
 */
public class DrawPanel extends JPanel implements Closeable {

    public static final Rectangle MAX_RECT = new Rectangle(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
    private static final int TIMER_DELAY = 33;
    private static final int MOVE_SIZE = 10;
    private static final double MOVE_SCALE = 0.03;

    public static final Color BACKGROUND_COLOR = new Color(16, 16, 32);
    public static final Font FONT = new Font("Arial", Font.PLAIN, 12);
    public static final Font FONT_BOLD = new Font("Arial", Font.BOLD, 12);
    public static final Font SUB_FONT = new Font(Font.MONOSPACED, Font.PLAIN, 11);

    private double x = 0;
    private double y = 0;
    private double scale = 0.0001;
    private double timeScale = 1;

    private CircleUiObject selected;

    private boolean help = false;
    private CircleUiObject infoObject;

    private int dragX;
    private int dragY;

    private final TreeMap<Integer, CircleUiObject> uiObjects;
    private final Map<Integer, CircleUiObject> priorityMapForLabels;
    private final Timer timer;
    private final CameraAnimation cameraAnimation = new CameraAnimation();
    private final ValueAnimation xAnimation = new ValueAnimation();
    private final ValueAnimation yAnimation = new ValueAnimation();
    private Rectangle2D selLineRect = new Rectangle();

    public DrawPanel() {
        UiObjects uiObjectsGenerator = new UiObjects();
        uiObjects = uiObjectsGenerator.uiObjects();
        selected = uiObjects.get(0);
        selected.setSelected(true);
        priorityMapForLabels = uiObjectsGenerator.getPriorityMap();

        timer = new Timer(TIMER_DELAY, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double oldX = selected.getX();
                double oldY = selected.getY();
                for (CircleUiObject uiObject : uiObjects.values()) {
                    uiObject.onTick(TIMER_DELAY * timeScale / 1000);
                }
                x += selected.getX() - oldX;
                y += selected.getY() - oldY;
                cameraAnimation.step();
                x += cameraAnimation.getDx();
                y += cameraAnimation.getDy();
                scale += cameraAnimation.getDScale();
                xAnimation.setAim(selected.getX());
                x += xAnimation.step(x);
                yAnimation.setAim(selected.getY());
                y += yAnimation.step(y);
                repaint();
            }
        });
        timer.start();

        addMouseWheelListener(e -> {
            double scaleScale = e.getWheelRotation();
            if (scaleScale < 0) {
                scaleScale = 0.5 - scaleScale;
            } else {
                scaleScale = 1 / (scaleScale + 0.5);
            }
            Rectangle rectangle = DrawPanel.this.getBounds();
            double width = rectangle.getWidth() / scale;
            double height = rectangle.getHeight() / scale;
            double mouseX = e.getX() / scale + x - width / 2.;
            double mouseY = -e.getY() / scale + y + height / 2.;
            cameraAnimation.init((scaleScale - 1) * scale, scale, mouseX, mouseY, x, y);
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (infoObject != null) {
                    infoObject.showSubtitle(false);
                    infoObject = null;
                }
                if (selected.in(e.getX(), e.getY())) {
                    infoObject = selected;
                    infoObject.showSubtitle(true);
                } else {
                    for (CircleUiObject uiObject : uiObjects.values()) {
                        if (uiObject.in(e.getX(), e.getY())) {
                            infoObject = uiObject;
                            infoObject.showSubtitle(true);
                            break;
                        }
                    }
                }
                setCursor(Cursor.getPredefinedCursor(
                    infoObject != null || selLineRect.contains(e.getX(), e.getY()) ?
                        Cursor.HAND_CURSOR :
                        Cursor.DEFAULT_CURSOR)
                );
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                xAnimation.reset();
                yAnimation.reset();
                x -= (e.getX() - dragX) / scale;
                if (x > 9.46073047258e32) { // 1e20 light years
                    x = 9.46073047258e32;
                } else if (x < -9.46073047258e32) { // -1e20 light years
                    x = -9.46073047258e32;
                }
                dragX = e.getX();
                y += (e.getY() - dragY) / scale;
                if (y > 9.46073047258e32) { // 1e20 light years
                    y = 9.46073047258e32;
                } else if (y < -9.46073047258e32) { // -1e20 light years
                    y = -9.46073047258e32;
                }
                dragY = e.getY();
            }
        });

        addMouseListener(new MouseInputAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (infoObject != null) {
                    selected.setSelected(false);
                    infoObject.setSelected(true);
                    selected = infoObject;
                    goToSelected();
                }
//                if (getCursor() == Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)) {
//                    for (CircleUiObject uiObject : uiObjects.values()) {
//                        if (uiObject.in(e.getX(), e.getY())) {
//                            selected.setSelected(false);
//                            uiObject.setSelected(true);
//                            selected = uiObject;
//                            goToSelected();
//                        }
//                    }
//                }
                dragX = e.getX();
                dragY = e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (selLineRect.contains(e.getX(), e.getY())) {
                    goToSelected();
                }
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
//                System.out.println(e.getKeyChar());
//                System.out.println((int) e.getKeyChar());
//                System.out.println(e.getKeyCode());
//                System.out.println(e.getExtendedKeyCode());
//                System.out.println();
                int keyCode = e.getKeyCode();
                if (keyCode == 46 || e.getExtendedKeyCode() == 16778318) {
                    timeScale *= 1.2;
                    if (timeScale > 3.15576e22) { // 1е15 years
                        timeScale = 3.15576e22;
                    }
                    return;
                }
                if (keyCode == 44 || e.getExtendedKeyCode() == 16778289) {
                    timeScale /= 1.2;
                    if (timeScale < 1e-15) {
                        timeScale = 1e-15;
                    }
                    return;
                }
                if (keyCode == 61 || keyCode == 107) {
                    scale = scale * (1 + MOVE_SCALE);
                    if (scale > 1e18) { // 1e-15 m per pixel
                        scale = 1e18;
                    }
                    repaint();
                    return;
                }
                if (keyCode == '-' || keyCode == 109) {
                    scale = scale * (1 - MOVE_SCALE);
                    if (scale < 1.0570008340247048437705108306581e-28) { // 1e15 light years per pixel
                        scale = 1.0570008340247048437705108306581e-28;
                    }
                    repaint();
                    return;
                }
                if (keyCode == 32) {
                    help = !help;
                    return;
                }
                if (keyCode == 37) {
                    x -= MOVE_SIZE / scale;
                    if (x < -9.46073047258e32) { // -1e20 light years
                        x = -9.46073047258e32;
                    }
                    repaint();
                    return;
                }
                if (keyCode == 38) {
                    y += MOVE_SIZE / scale;
                    if (y > 9.46073047258e32) { // 1e20 light years
                        y = 9.46073047258e32;
                    }
                    repaint();
                    return;
                }
                if (keyCode == 39) {
                    x += MOVE_SIZE / scale;
                    if (x > 9.46073047258e32) { // 1e20 light years
                        x = 9.46073047258e32;
                    }
                    repaint();
                    return;
                }
                if (keyCode == 40) {
                    y -= MOVE_SIZE / scale;
                    if (y < -9.46073047258e32) { // -1e20 light years
                        y = -9.46073047258e32;
                    }
                    repaint();
                    return;
                }
                for (CircleUiObject uiObject : priorityMapForLabels.values()) {
                    if (uiObject != null && (uiObject.getKey() == keyCode || (int) Character.toLowerCase(uiObject.getCh()) == (int) Character.toLowerCase(e.getKeyChar()))) {
                        selected.setSelected(false);
                        uiObject.setSelected(true);
                        selected = uiObject;
                        goToSelected();
                        return;
                    }
                }
            }
        });
    }

    private void goToSelected() {
        xAnimation.init(1 / scale, selected.getX());
        yAnimation.init(1 / scale, selected.getY());
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D graphics2D = (Graphics2D) g;

        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.setStroke(new BasicStroke(0.000001f));

        AffineTransform transform = new AffineTransform();
        transform.scale(scale, -scale);
        rectangle = graphics2D.getClipBounds();
        transform.translate(-x + rectangle.getWidth() / (2. * scale), -y - rectangle.getHeight() / (2. * scale));

        for (CircleUiObject uiObject : uiObjects.descendingMap().values()) {
            if (uiObject != selected) {
                uiObject.onDraw(graphics2D, transform);
            }
        }
        selected.onDraw(graphics2D, transform);

        for (CircleUiObject uiObject : uiObjects.descendingMap().values()) {
            if (uiObject != selected) {
                uiObject.text(graphics2D, transform);
            }
        }
        selected.text(graphics2D, transform);

        graphics2D.setColor(Color.gray);

        fontMetrics = graphics2D.getFontMetrics();
        textHeight = fontMetrics.getHeight();

        infoHeight = rectangle.height + textHeight;
        if (help) {
            drawInfoString(graphics2D, "Dmitri Belous, IIIT, 2016, v1.1.7.2");
        }
        if (infoObject != null) {
            if (infoObject instanceof Planet) {
                Planet planet = (Planet) infoObject;
                drawInfoString(graphics2D, "Период обращения: " + time(planet.getT()));
                drawInfoString(graphics2D, "Радиус орбиты: " + distance(planet.getOrbitRaduis()));
            }
            drawInfoString(graphics2D, "Радиус: " + distance(infoObject.getRadius()));
            int rings = infoObject.getRingsCount();
            if (rings != 0) {
                drawInfoString(graphics2D, "Колец: " + rings);
            }
            int moons = infoObject.getMoonsCount();
            if (moons != 0) {
                drawInfoString(graphics2D, "Спутников: " + moons);
            }
            drawInfoString(graphics2D, infoObject.getSubTitle());
            drawInfoString(graphics2D, infoObject.getTitle());
        }

        mainHeight = 0;
        leftSide = true;
        drawMainString(graphics2D, "В одном пикселе " + distance(1 / scale));
        drawMainString(graphics2D, distance(Math.sqrt(x * x + y * y)) + " от Солнца");
        drawMainString(graphics2D, "В одной секунде " + time(timeScale));
        drawSelLabel(graphics2D);

        drawMainString(graphics2D, "");
        drawMainString(graphics2D, "Пробел - вкл./выкл. помощь");
        drawMainString(graphics2D, "");

        if (help) {
            drawMainString(graphics2D, "-/+ или колесо мыши - изменить масштаб");
            drawMainString(graphics2D, "б/ю - замедлить/ускорить время");
            drawMainString(graphics2D, "\u2190\u2191\u2192\u2193 или мышь - перемещение");
            drawMainString(graphics2D, "");
            drawMainString(graphics2D, "Перейти к объекту:");
            drawMainString(graphics2D, "");
            int priority = 0;
            for (Map.Entry<Integer, CircleUiObject> uiObject : priorityMapForLabels.entrySet()) {
                if (priority != uiObject.getKey()) {
                    drawMainString(graphics2D, "");
                }
                priority = uiObject.getKey() + 1;
                drawMainString(graphics2D, uiObject.getValue().getCh() + " - " + uiObject.getValue().getTitle());
            }
        }
    }

    int mainHeight;
    int infoHeight;
    boolean leftSide;
    Rectangle rectangle;
    int textHeight;
    FontMetrics fontMetrics;

    private void drawMainString(Graphics2D graphics2D, String str) {
        mainHeight += textHeight;
        if (mainHeight > rectangle.height && leftSide) {
            mainHeight = textHeight;
            leftSide = false;
            if (str.length() == 0) {
                mainHeight = 0;
                return;
            }
        }
        if (!leftSide && mainHeight >= infoHeight - textHeight) {
            return;
        }
        graphics2D.drawString(str, leftSide ? 5 : rectangle.width - fontMetrics.stringWidth(str) - 5, mainHeight);
    }

    private void drawInfoString(Graphics2D graphics2D, String str) {
        infoHeight -= textHeight;
        graphics2D.drawString(str, rectangle.width - fontMetrics.stringWidth(str) - 5, infoHeight - 5);
    }

    private void drawSelLabel(Graphics2D graphics2D) {
        String selLabel = "Отслеживается: ";
        String text = selLabel + selected.getTitle();
        AttributedString selLine = new AttributedString(text);
        selLine.addAttribute(
            TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON,
            selLabel.length(), text.length()
        );
        selLine.addAttribute(TextAttribute.FONT, graphics2D.getFont());
        selLineRect = fontMetrics.getStringBounds(selected.getTitle(), graphics2D);

        mainHeight += textHeight;
        if (mainHeight > rectangle.height && leftSide) {
            mainHeight = textHeight;
            leftSide = false;
        }
        if (!leftSide && mainHeight >= infoHeight - textHeight) {
            return;
        }
        int x = leftSide ? 5 : rectangle.width - fontMetrics.stringWidth(text) - 5;
        selLineRect.setRect(x + fontMetrics.stringWidth(selLabel), mainHeight - textHeight + 5, selLineRect.getWidth(), selLineRect.getHeight());
        graphics2D.drawString(selLine.getIterator(), x, mainHeight);
    }

    private static String distance(double value) {
        if (value == 0) {
            return "0.00 м";
        }
        if (FastMath.abs(value) < 1e-6) {
            return String.format("%.2e м", value * 1e3);
        }
        if (FastMath.abs(value) < 1e-5) {
            return String.format("%.2f мм", value * 1e6);
        }
        if (FastMath.abs(value) < 1e-3) {
            return String.format("%.2f см", value * 1e5);
        }
        if (FastMath.abs(value) < 1) {
            return String.format("%.2f м", value * 1e3);
        }
        if (FastMath.abs(value) < 9460730472580d) {
            return value(value) + " км";
        }
        return value(value / 9460730472580d) + " световых лет";
    }

    private static String time(double time) {
        if (time == 0) {
            return "0.00 c";
        }
        if (FastMath.abs(time) < 1e-3) {
            return String.format("%.2e c", time);
        }
        if (FastMath.abs(time) < 1) {
            return String.format("%.2f мс", time * 1e3);
        }
        if (FastMath.abs(time) < 60) {
            return String.format("%.2f с", time);
        }
        if (FastMath.abs(time) < 3600) {
            return String.format("%.2f минут", time / 60);
        }
        if (FastMath.abs(time) < 3600 * 24) {
            return String.format("%.2f часов", time / 3600);
        }
        if (FastMath.abs(time) < 3600 * 24 * 365.25) {
            return String.format("%.2f суток", time / 86400);
        }
        return value(time / 31557600) + " лет";
    }

    private static String value(double value) {
        if (FastMath.abs(value) < 1e3) {
            return String.format("%.2f", value);
        }
        if (FastMath.abs(value) < 1e6) {
            return String.format("%.2f тыс.", value / 1e3);
        }
        if (FastMath.abs(value) < 1e9) {
            return String.format("%.2f млн", value / 1e6);
        }
        if (FastMath.abs(value) < 1e12) {
            return String.format("%.2f млрд", value / 1e9);
        }
        if (FastMath.abs(value) < 1e15) {
            return String.format("%.2f трлн", value / 1e12);
        }
        return String.format("%.2e", value);
    }

    @Override
    public void close() throws IOException {
        timer.stop();
    }
}
