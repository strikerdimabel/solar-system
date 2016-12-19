package belousdo.solarsystem;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;

public class SolarSystem extends JFrame {
    private JPanel contentPane;
    private JPanel drawPanel;

    public SolarSystem() {
        setContentPane(contentPane);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    @Override
    public void dispose() {
        try {
            ((DrawPanel) drawPanel).close();
        } catch (IOException ignored) {
        }
        super.dispose();
    }

    public static void main(String[] args) {
        SolarSystem dialog = new SolarSystem();
        dialog.setTitle("Солнечная система");
        try {
            dialog.setIconImage(ImageIO.read(ClassLoader.getSystemResourceAsStream("icon2.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dialog.setExtendedState( dialog.getExtendedState()|JFrame.MAXIMIZED_BOTH );
        dialog.pack();
        dialog.setVisible(true);
    }

    private void createUIComponents() {
        drawPanel = new DrawPanel();
    }
}
