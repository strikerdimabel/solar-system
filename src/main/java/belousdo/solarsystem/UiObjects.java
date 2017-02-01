package belousdo.solarsystem;

import java.awt.*;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Created by Dmitri on 16.12.2016.
 */
public class UiObjects {

    private final Map<Integer, CircleUiObject> priorityMap = new TreeMap<>();

    public Map<Integer, CircleUiObject> getPriorityMap() {
        return priorityMap;
    }

    public TreeMap<Integer, CircleUiObject> uiObjects() {
        try (
            Scanner dataScanner = new Scanner(new InputStreamReader(ClassLoader.getSystemResourceAsStream("data.csv"), "UTF-8"));
            Scanner ringsScanner = new Scanner(new InputStreamReader(ClassLoader.getSystemResourceAsStream("rings.csv"), "UTF-8"))
        ) {
            TreeMap<Integer, CircleUiObject> uiObjectMap = new TreeMap<>();
            dataScanner.nextLine();
            while (dataScanner.hasNextLine()) {
                String[] tokens = (dataScanner.nextLine() + ";end").split(";");
                int id = Integer.parseInt(tokens[0]);
                String title = tokens[1];
                String subTitle = tokens[2];
                double radius = Double.parseDouble(tokens[3]);
                int color = (tokens[8].length() == 0 ? -8355712 : Integer.parseInt(tokens[8]));
                char key = (tokens[9].length() == 0 ? '\0' : tokens[9].charAt(0));
                int keyCode = (tokens[10].length() == 0 ? 0 : Integer.parseInt(tokens[10]));
                CircleUiObject newObject;
                if (tokens[4].length() != 0) {
                    int connectedToId = Integer.parseInt(tokens[4]);
                    double semiMajorAxis = Double.parseDouble(tokens[5]);
                    double t = Double.parseDouble(tokens[6]);
                    double e = Double.parseDouble(tokens[7]);
                    newObject = new Planet(title, keyCode, key, subTitle, semiMajorAxis, radius, 2 * Math.PI / t, new Color(color));
                    if (title.equals("МКС")) {
                        newObject.setShowHint(false);
                    }
                    CircleUiObject connectedTo = uiObjectMap.get(connectedToId);
                    connectedTo.addMoon((Planet) newObject);
                } else {
                    newObject = new Sun(title, keyCode, key, subTitle, radius, new Color(color));
                }
                uiObjectMap.put(id, newObject);
                Integer priority = (tokens[11].length() == 0 ? null : Integer.valueOf(tokens[11]));
                if (priority != null) {
                    priorityMap.put(priority, newObject);
                }
            }
            ringsScanner.nextLine();
            while (ringsScanner.hasNextLine()) {
                String[] tokens = (ringsScanner.nextLine() + ";end").split(";");
                int id = Integer.parseInt(tokens[0]);
                double inR = Double.parseDouble(tokens[1]);
                double outR = Double.parseDouble(tokens[2]);
                CircleUiObject object = uiObjectMap.get(id);
                Color color = (tokens[3].length() == 0 ? object.getColor() : new Color(Integer.parseInt(tokens[3]), true));
                Ring ring = new Ring(inR, outR, color);
                object.addRing(ring);
            }
            return uiObjectMap;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException();
        }
    }
}
