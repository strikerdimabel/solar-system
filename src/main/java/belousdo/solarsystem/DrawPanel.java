package belousdo.solarsystem;

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

/**
 * Created dragY Dmitri on 13.12.2016.
 */
public class DrawPanel extends JPanel implements Closeable {

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

    private UiObject selected;

    private boolean help = false;
    private UiObject infoObject;

    private boolean mousePressed = false;
    private int dragX;
    private int dragY;

    private final java.util.List<UiObject> uiObjects;
    private final Map<Integer, UiObject> priorityMapForLabels;
    private final Timer timer;
    private final CameraAnimation cameraAnimation = new CameraAnimation();
    private final ValueAnimation xAnimation = new ValueAnimation();
    private final ValueAnimation yAnimation = new ValueAnimation();
    private Rectangle2D selLineRect = new Rectangle();

    public DrawPanel() {
        UiObjects uiObjectsGenerator = new UiObjects();
        uiObjects = uiObjectsGenerator.uiObjects();
        selected = uiObjects.get(0);
        priorityMapForLabels = uiObjectsGenerator.getPriorityMap();

        timer = new Timer(TIMER_DELAY, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double oldX = selected.getX();
                double oldY = selected.getY();
                for (UiObject uiObject : uiObjects) {
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

        addMouseWheelListener(e -> changeScale(e.getWheelRotation(), e.getX(), e.getY()));

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                boolean in = false;
                for (UiObject uiObject : uiObjects) {
                    if (uiObject.in(e.getX(), e.getY())) {
                        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        uiObject.showSubtitle(true);
                        infoObject = uiObject;
                        in = true;
                    } else {
                        uiObject.showSubtitle(false);
                    }
                }
                if (!in) {
                    infoObject = null;
                }
                if (selLineRect.contains(e.getX(), e.getY())) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    in = true;
                }
                if (!in) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (mousePressed) {
                    xAnimation.reset();
                    yAnimation.reset();
                    x -= (e.getX() - dragX) / scale;
                    dragX = e.getX();
                    y += (e.getY() - dragY) / scale;
                    dragY = e.getY();
                }
            }
        });

        addMouseListener(new MouseInputAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (getCursor() == Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)) {
                    for (int i = uiObjects.size() - 1; i >= 0; --i) {
                        UiObject uiObject = uiObjects.get(i);
                        if (uiObject.in(e.getX(), e.getY())) {
                            selected.setSelected(false);
                            uiObject.setSelected(true);
                            selected = uiObject;
                            goToSelected();
                        }
                    }
                }
                dragX = e.getX();
                dragY = e.getY();
                mousePressed = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mousePressed = false;
                if (selLineRect.contains(e.getX(), e.getY())) {
                    goToSelected();
                }
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == 61 || keyCode == 107 || keyCode == '-' || keyCode == 109) {
                    cameraAnimation.reset();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
//                System.out.println(e.getKeyChar());
//                System.out.println((int) e.getKeyChar());
//                System.out.println(e.getKeyCode());
//                System.out.println(e.getExtendedKeyCode());
//                System.out.println();
                int keyCode = e.getKeyCode();
                if (keyCode == 46 || e.getExtendedKeyCode() == 16778318) {
                    timeScale *= 1.5;
                    return;
                }
                if (keyCode == 44 || e.getExtendedKeyCode() == 16778289) {
                    timeScale /= 1.5;
                    if (timeScale < 0.01) {
                        timeScale = 0.01;
                    }
                    return;
                }
                if (keyCode == 61 || keyCode == 107) {
                    scale = scale * (1 + MOVE_SCALE);
                    repaint();
                    return;
                }
                if (keyCode == '-' || keyCode == 109) {
                    scale = scale * (1 - MOVE_SCALE);
                    repaint();
                    return;
                }
                if (keyCode == 32) {
                    help = !help;
                    return;
                }
                if (keyCode == 37) {
                    x -= MOVE_SIZE / scale;
                    repaint();
                    return;
                }
                if (keyCode == 38) {
                    y += MOVE_SIZE / scale;
                    repaint();
                    return;
                }
                if (keyCode == 39) {
                    x += MOVE_SIZE / scale;
                    repaint();
                    return;
                }
                if (keyCode == 40) {
                    y -= MOVE_SIZE / scale;
                    repaint();
                    return;
                }
                for (UiObject uiObject : priorityMapForLabels.values()) {
                    if (uiObject != null && (uiObject.getKey() == keyCode || (int) Character.toLowerCase(uiObject.getCh()) == (int) e.getKeyChar())) {
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

    public void changeScale(int rotation, double pixMouseX, double pixMouseY) {
        double scaleScale = rotation;
        if (scaleScale < 0) {
            scaleScale = 0.5 - scaleScale;
        } else {
            scaleScale = 1 / (scaleScale + 0.5);
        }
        Rectangle rectangle = DrawPanel.this.getBounds();
        double width = rectangle.getWidth() / scale;
        double height = rectangle.getHeight() / scale;
        double mouseX = pixMouseX / scale + x - width / 2.;
        double mouseY = -pixMouseY / scale + y + height / 2.;
        cameraAnimation.init((scaleScale - 1) * scale, scale, mouseX, mouseY, x, y);
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
        transform.translate(-x + rectangle.getWidth() / 2 / scale, -y - rectangle.getHeight() / 2 / scale);

        for (UiObject uiObject : uiObjects) {
            if (uiObject != selected) {
                uiObject.onDraw(graphics2D, transform);
            }
        }
        selected.onDraw(graphics2D, transform);

        for (UiObject uiObject : uiObjects) {
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
            drawInfoString(graphics2D, "Dmitri Belous, BSU, 2016, v1.1.4.2");
        }
        if (infoObject != null) {
            if (infoObject instanceof Planet) {
                drawInfoString(graphics2D, "Период обращения: " + time(infoObject.getT()));
                drawInfoString(graphics2D, "Радиус орбиты: " + distance(infoObject.getOrbitRaduis()));
            }
            drawInfoString(graphics2D, "Радиус: " + distance(infoObject.getRadius()));
            int rings = infoObject.getRings();
            if (rings != 0) {
                drawInfoString(graphics2D, "Колец: " + rings);
            }
            int moons = infoObject.getMoons();
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
            for (UiObject uiObject : priorityMapForLabels.values()) {
                if (uiObject != null) {
                    drawMainString(graphics2D, uiObject.getCh() + " - " + (uiObject.getTitle().length() == 0 ? uiObject.getSubTitle() : uiObject.getTitle()));
                } else {
                    drawMainString(graphics2D, "");
                }
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
        String secondsStr;
        double scale;
        if (value < 1) {
            scale = 1000;
            secondsStr = "м";
        } else if (value < 1000) {
            scale = 1;
            secondsStr = "км";
        } else if (value < 1000000) {
            scale = 1. / 1000;
            secondsStr = "тыс. км";
        } else if (value < 1000000000) {
            scale = 1. / 1000000;
            secondsStr = "млн км";
        } else if (value < 1e12) {
            scale = 1. / 1000000000;
            secondsStr = "млрд км";
        } else if (value < 9460730472580d) {
            scale = 1. / 1e12;
            secondsStr = "трлн км";
        } else {
            scale = 1. / 9460730472580d;
            secondsStr = "световых лет";
        }
        return String.format("%.2f %s", value * scale, secondsStr);
    }

    private static String time(double time) {
        String secondsStr;
        double scale;
        if (Math.abs(time) < 1) {
            scale = 1000;
            secondsStr = "миллисекунд";
        } else if (Math.abs(time) < 60) {
            secondsStr = "секунд";
            scale = 1;
        } else if (Math.abs(time) < 3600) {
            scale = 1. / 60;
            secondsStr = "минут";
        } else if (Math.abs(time) < 3600 * 24) {
            scale = 1. / 3600;
            secondsStr = "часов";
        } else if (Math.abs(time) < 3600 * 24 * 365.25) {
            scale = 1. / (3600 * 24);
            secondsStr = "суток";
        } else {
            scale = 1. / (3600 * 24 * 365.25);
            secondsStr = "лет";
        }
        return String.format("%.2f %s", time * scale, secondsStr);
    }

    @Override
    public void close() throws IOException {
        timer.stop();
    }
}
