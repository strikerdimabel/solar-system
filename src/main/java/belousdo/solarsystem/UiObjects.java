package belousdo.solarsystem;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Dmitri on 16.12.2016.
 */
public class UiObjects {

    private final Map<Integer, UiObject> priorityMap = new TreeMap<>();

    public Map<Integer, UiObject> getPriorityMap() {
        return priorityMap;
    }

    public List<UiObject> uiObjects() {
        List<UiObject> uiObjects = new ArrayList<>();

        Sun sun = new Sun("Солнце", '0', '0', "Жёлтый карлик", 695700);
        uiObjects.add(sun);
        priorityMap.put(0, sun);

        Planet mercury = new Planet("Меркурий", '1', '1', "Планета земной группы",
            57910000, 2440, 8.26678173e-7, new Color(200, 150, 150));
        uiObjects.add(mercury);
        priorityMap.put(1, mercury);

        Planet venus = new Planet("Венера", '2', '2', "Планета земной группы",
            108208000, 6052, 3.23639201e-7, new Color(192, 192, 192));
        uiObjects.add(venus);
        priorityMap.put(2, venus);

        Planet earth = new Planet("Земля", '3', '3', "Планета земной группы",
            149597870, 6371, 1.99098658e-7, new Color(50, 200, 200));
        priorityMap.put(3, earth);

        Planet moon = new Planet("Луна", 75, 'Л', "Спутник планеты",
            384399, 1737.1, 2.66169953e-6, new Color(192, 192, 192));
        uiObjects.add(moon);
        earth.addMoon(moon);
        priorityMap.put(16, null);
        priorityMap.put(17, moon);

        Planet iss = new Planet("МКС", 81, 'Й', "Международная космическая станция",
            405 + 6371, 0.040, 0.00112743321, new Color(168, 198, 198));
        iss.setShowHint(false);
        uiObjects.add(iss);
        earth.addMoon(iss);
        priorityMap.put(18, iss);

        uiObjects.add(earth);

        Planet mars = new Planet("Марс", '4', '4', "Планета земной группы",
            227939133, 3389.5, 1.05858984e-7, new Color(255, 100, 100));
        priorityMap.put(4, mars);
        Planet phobos = new Planet("Фобос", 65, 'Ф', "Спутник планеты",
            9376, 11.2667,
            2 * Math.PI / (0.31891023 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(phobos);
        mars.addMoon(phobos);
        priorityMap.put(19, null);
        priorityMap.put(20, phobos);
        Planet deimos = new Planet("Деймос", 76, 'Д', "Спутник планеты",
            23463.2, 6.2,
            2 * Math.PI / (1.263 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(deimos);
        mars.addMoon(deimos);
        priorityMap.put(21, deimos);
        uiObjects.add(mars);

        Planet ceres = new Planet("Церера", 87, 'Ц', "Карликовая планета",
            414010000, 473, 4.32449779e-8, new Color(128, 128, 128));
        uiObjects.add(ceres);
        priorityMap.put(14, ceres);

        int jR = 255;
        int jG = 200;
        int jB = 100;
        Planet jupiter = new Planet("Юпитер", '5', '5', "Газовый гигант",
            778297882, 69911, 1.67848913e-8, new Color(jR, jG, jB));
        priorityMap.put(5, jupiter);

        Ring jRing;

        jRing = new Ring(122500, 129000, new Color(jR, jG, jB, 5));
        jupiter.addRing(jRing);

        jRing = new Ring(92000, 122500, new Color(jR, jG, jB, 2));
        jupiter.addRing(jRing);

        jRing = new Ring(129000, 182000, new Color(jR, jG, jB, 2));
        jupiter.addRing(jRing);

        jRing = new Ring(182000, 226000, new Color(jR, jG, jB, 1));
        jupiter.addRing(jRing);

        Planet metis = new Planet("Метида", 0, '\0', "Спутник планеты",
            128000, 21.5,
            2 * Math.PI / (0.294780 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(metis);
        jupiter.addMoon(metis);

        Planet adrastea = new Planet("Адрастея", 0, '\0', "Спутник планеты",
            129000, 8.2,
            2 * Math.PI / (0.29826 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(adrastea);
        jupiter.addMoon(adrastea);

        Planet amalthea = new Planet("Амальтея", 0, '\0', "Спутник планеты",
            181365.84, 83.5,
            2 * Math.PI / (0.49817943 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(amalthea);
        jupiter.addMoon(amalthea);

        Planet thebe = new Planet("Фива", 0, '\0', "Спутник планеты",
            221889, 49.3,
            2 * Math.PI / (0.674536 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(thebe);
        jupiter.addMoon(thebe);

        Planet io = new Planet("Ио", 66, 'И', "Спутник планеты",
            421700, 1821.6,
            2 * Math.PI / (1.769137786 * 24 * 3600), new Color(128, 255, 0));
        uiObjects.add(io);
        jupiter.addMoon(io);
        priorityMap.put(22, null);
        priorityMap.put(23, io);

        Planet europa = new Planet("Европа", 84, 'Е', "Спутник планеты",
            670900, 1560.8,
            2 * Math.PI / (3.551181 * 24 * 3600), new Color(255, 200, 200));
        uiObjects.add(europa);
        jupiter.addMoon(europa);
        priorityMap.put(24, europa);

        Planet ganymede = new Planet("Ганимед", 85, 'Г', "Спутник планеты",
            1070400, 2634.1,
            2 * Math.PI / (7.15455296 * 24 * 3600), new Color(250, 150, 250));
        uiObjects.add(ganymede);
        jupiter.addMoon(ganymede);
        priorityMap.put(25, ganymede);

        Planet callisto = new Planet("Каллисто", 82, 'К', "Спутник планеты",
            1882700, 2410.3,
            2 * Math.PI / (16.6890184 * 24 * 3600), new Color(50, 200, 200));
        uiObjects.add(callisto);
        jupiter.addMoon(callisto);
        priorityMap.put(26, callisto);

        Planet themisto = new Planet("Фемисто", 0, '\0',"Спутник планеты",
            7391650, 4,
            2 * Math.PI / (129.82761 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(themisto);
        jupiter.addMoon(themisto);

        Planet leda = new Planet("Леда", 0, '\0',"Спутник планеты",
            11160000, 10,
            2 * Math.PI / (240.92 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(leda);
        jupiter.addMoon(leda);

        Planet himalia = new Planet("Гималия", 0, '\0',"Спутник планеты",
            11460000, 85,
            2 * Math.PI / (250.56 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(himalia);
        jupiter.addMoon(himalia);

        Planet lysithea = new Planet("Лиситея", 0, '\0',"Спутник планеты",
            11720000, 18,
            2 * Math.PI / (259.20 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(lysithea);
        jupiter.addMoon(lysithea);

        Planet elara = new Planet("Элара", 0, '\0',"Спутник планеты",
            11740000, 43,
            2 * Math.PI / (259.64 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(elara);
        jupiter.addMoon(elara);

        Planet dia = new Planet("Дия", 0, '\0',"Спутник планеты",
            12100000, 2,
            2 * Math.PI / (287.93 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(dia);
        jupiter.addMoon(dia);

        Planet carpo = new Planet("Карпо", 0, '\0',"Спутник планеты",
            17145000, 1.5,
            2 * Math.PI / (458.625 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(carpo);
        jupiter.addMoon(carpo);

        Planet j12 = new Planet("S/2003 J 12", 0, '\0',"Спутник планеты",
            17739539, 0.5,
            2 * Math.PI / (-482.69 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(j12);
        jupiter.addMoon(j12);

        Planet Эвпорие = new Planet("Эвпорие", 0, '\0',"Спутник планеты",
            19088434, 1,
            2 * Math.PI / (-538.78 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Эвпорие);
        jupiter.addMoon(Эвпорие);

        Planet j3 = new Planet("S/2003 J 3", 0, '\0',"Спутник планеты",
            19621780, 1,
            2 * Math.PI / (-561.52 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(j3);
        jupiter.addMoon(j3);

        Planet j18 = new Planet("S/2003 J 18", 0, '\0',"Спутник планеты",
            19812577, 1,
            2 * Math.PI / (-569.73 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(j18);
        jupiter.addMoon(j18);

        Planet j1 = new Planet("S/2011 J 1", 0, '\0',"Спутник планеты",
            20101000, 0.5,
            2 * Math.PI / (-580.7 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(j1);
        jupiter.addMoon(j1);

        Planet j2 = new Planet("S/2010 J 2", 0, '\0',"Спутник планеты",
            20307150, 0.5,
            2 * Math.PI / (-588.82 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(j2);
        jupiter.addMoon(j2);

        Planet Тельксиное = new Planet("Тельксиное", 0, '\0',"Спутник планеты",
            20453753, 1,
            2 * Math.PI / (-597.61 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Тельксиное);
        jupiter.addMoon(Тельксиное);

        Planet Эванте = new Planet("Эванте", 0, '\0',"Спутник планеты",
            20464854, 1.5,
            2 * Math.PI / (-598.09 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Эванте);
        jupiter.addMoon(Эванте);

        Planet Гелике = new Planet("Гелике", 0, '\0',"Спутник планеты",
            20540266, 2,
            2 * Math.PI / (-601.40 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Гелике);
        jupiter.addMoon(Гелике);

        Planet Ортозие = new Planet("Ортозие", 0, '\0',"Спутник планеты",
            20567971, 1,
            2 * Math.PI / (-602.62 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Ортозие);
        jupiter.addMoon(Ортозие);

        Planet Иокасте = new Planet("Иокасте", 0, '\0',"Спутник планеты",
            20722566, 2.5,
            2 * Math.PI / (-609.43 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Иокасте);
        jupiter.addMoon(Иокасте);

        Planet j16 = new Planet("S/2003 J 16", 0, '\0',"Спутник планеты",
            20743779, 1,
            2 * Math.PI / (-610.36 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(j16);
        jupiter.addMoon(j16);

        Planet Праксидике = new Planet("Праксидике", 0, '\0',"Спутник планеты",
            20823948, 3.5,
            2 * Math.PI / (-613.90 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Праксидике);
        jupiter.addMoon(Праксидике);

        Planet Гарпалике = new Planet("Гарпалике", 0, '\0',"Спутник планеты",
            21063814, 2,
        2 * Math.PI / (-624.54 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Гарпалике);
        jupiter.addMoon(Гарпалике);

        Planet Мнеме = new Planet("Мнеме", 0, '\0',"Спутник планеты",
            21129786, 2 / 2.,
        2 * Math.PI / (-627.48 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Мнеме);
        jupiter.addMoon(Мнеме);

        Planet Гермиппе = new Planet("Гермиппе", 0, '\0',"Спутник планеты",
            21182086, 4 / 2.,
        2 * Math.PI / (-629.81 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Гермиппе);
        jupiter.addMoon(Гермиппе);

        Planet Тионе = new Planet("Тионе", 0, '\0',"Спутник планеты",
            21405570, 4 / 2.,
        2 * Math.PI / (-639.80 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Тионе);
        jupiter.addMoon(Тионе);

        Planet Ананке = new Planet("Ананке", 0, '\0',"Спутник планеты",
            21454952, 28 / 2.,
        2 * Math.PI / (-642.02 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Ананке);
        jupiter.addMoon(Ананке);

        Planet Герсе = new Planet("Герсе", 0, '\0',"Спутник планеты",
            22134306, 2 / 2.,
        2 * Math.PI / (-672.75 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Герсе);
        jupiter.addMoon(Герсе);

        Planet Этне = new Planet("Этне", 0, '\0',"Спутник планеты",
            22285161, 3 / 2.,
        2 * Math.PI / (-679.64 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Этне);
        jupiter.addMoon(Этне);

        Planet Кале = new Planet("Кале", 0, '\0',"Спутник планеты",
            22409207, 2 / 2.,
        2 * Math.PI / (-685.32 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Кале);
        jupiter.addMoon(Кале);

        Planet Тайгете = new Planet("Тайгете", 0, '\0',"Спутник планеты",
            22438648, 5 / 2.,
        2 * Math.PI / (-686.67 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Тайгете);
        jupiter.addMoon(Тайгете);

        Planet j19 = new Planet("S/2003 J 19", 0, '\0',"Спутник планеты",
            22709061, 2 / 2.,
        2 * Math.PI / (-699.12 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(j19);
        jupiter.addMoon(j19);

        Planet Халдене = new Planet("Халдене", 0, '\0',"Спутник планеты",
            22713444, 4 / 2.,
        2 * Math.PI / (-699.33 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Халдене);
        jupiter.addMoon(Халдене);

        Planet j15 = new Planet("S/2003 J 15", 0, '\0',"Спутник планеты",
            22720999, 2 / 2.,
        2 * Math.PI / (-699.68 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(j15);
        jupiter.addMoon(j15);

        Planet j10 = new Planet("S/2003 J 10", 0, '\0',"Спутник планеты",
            22730813, 2 / 2.,
        2 * Math.PI / (-700.13 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(j10);
        jupiter.addMoon(j10);

        Planet j23 = new Planet("S/2003 J 23", 0, '\0',"Спутник планеты",
            22739654, 2 / 2.,
        2 * Math.PI / (-700.54 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(j23);
        jupiter.addMoon(j23);

        Planet Эриноме = new Planet("Эриноме", 0, '\0',"Спутник планеты",
            22986266, 3 / 2.,
        2 * Math.PI / (-711.96 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Эриноме);
        jupiter.addMoon(Эриноме);

        Planet Аойде = new Planet("Аойде", 0, '\0',"Спутник планеты",
            23044175, 4 / 2.,
        2 * Math.PI / (-714.66 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Аойде);
        jupiter.addMoon(Аойде);

        Planet Каллихоре = new Planet("Каллихоре", 0, '\0',"Спутник планеты",
            23111823, 2 / 2.,
        2 * Math.PI / (-717.81 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Каллихоре);
        jupiter.addMoon(Каллихоре);

        Planet Калике = new Planet("Калике", 0, '\0',"Спутник планеты",
            23180773, 5 / 2.,
        2 * Math.PI / (-721.02 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Калике);
        jupiter.addMoon(Калике);

        Planet Карме = new Planet("Карме", 0, '\0',"Спутник планеты",
            23197992, 46 / 2.,
        2 * Math.PI / (-721.82 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Карме);
        jupiter.addMoon(Карме);

        Planet Каллирое = new Planet("Каллирое", 0, '\0',"Спутник планеты",
            23214986, 9 / 2.,
        2 * Math.PI / (-722.62 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Каллирое);
        jupiter.addMoon(Каллирое);

        Planet Эвридоме = new Planet("Эвридоме", 0, '\0',"Спутник планеты",
            23230858, 3 / 2.,
        2 * Math.PI / (-723.36 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Эвридоме);
        jupiter.addMoon(Эвридоме);

        Planet j2_2011 = new Planet("S/2011 J 2", 0, '\0',"Спутник планеты",
            23267000, 1 / 2.,
        2 * Math.PI / (-726.8 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(j2_2011);
        jupiter.addMoon(j2_2011);

        Planet Пазифее = new Planet("Пазифее", 0, '\0',"Спутник планеты",
            23307318, 2 / 2.,
        2 * Math.PI / (-726.93 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Пазифее);
        jupiter.addMoon(Пазифее);

        Planet j1_2010 = new Planet("S/2010 J 1", 0, '\0',"Спутник планеты",
            23314335, 2 / 2.,
        2 * Math.PI / (-724.34 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(j1_2010);
        jupiter.addMoon(j1_2010);

        Planet Коре = new Planet("Коре", 0, '\0',"Спутник планеты",
            23345093, 2 / 2.,
        2 * Math.PI / (-776.02 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Коре);
        jupiter.addMoon(Коре);

        Planet Киллене = new Planet("Киллене", 0, '\0',"Спутник планеты",
            23396269, 2 / 2.,
        2 * Math.PI / (-731.10 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Киллене);
        jupiter.addMoon(Киллене);

        Planet Эвкеладе = new Planet("Эвкеладе", 0, '\0',"Спутник планеты",
            23483694, 4 / 2.,
        2 * Math.PI / (-735.20 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Эвкеладе);
        jupiter.addMoon(Эвкеладе);

        Planet j4 = new Planet("S/2003 J 4", 0, '\0',"Спутник планеты",
            23570790, 2 / 2.,
        2 * Math.PI / (-739.29 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(j4);
        jupiter.addMoon(j4);

        Planet Пасифе = new Planet("Пасифе", 0, '\0',"Спутник планеты",
            23609042, 60 / 2.,
        2 * Math.PI / (-741.09 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Пасифе);
        jupiter.addMoon(Пасифе);

        Planet Гегемоне = new Planet("Гегемоне", 0, '\0',"Спутник планеты",
            23702511, 3 / 2.,
        2 * Math.PI / (-745.5 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Гегемоне);
        jupiter.addMoon(Гегемоне);

        Planet Архе = new Planet("Архе", 0, '\0',"Спутник планеты",
            23717051, 3 / 2.,
        2 * Math.PI / (-746.19 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Архе);
        jupiter.addMoon(Архе);

        Planet Исоное = new Planet("Исоное", 0, '\0',"Спутник планеты",
            23800647, 4 / 2.,
        2 * Math.PI / (-750.13 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Исоное);
        jupiter.addMoon(Исоное);

        Planet j9 = new Planet("S/2003 J 9", 0, '\0',"Спутник планеты",
            23857808, 1 / 2.,
        2 * Math.PI / (-752.84 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(j9);
        jupiter.addMoon(j9);

        Planet j5 = new Planet("S/2003 J 5", 0, '\0',"Спутник планеты",
            23973926, 4 / 2.,
        2 * Math.PI / (-758.34 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(j5);
        jupiter.addMoon(j5);

        Planet Синопе = new Planet("Синопе", 0, '\0',"Спутник планеты",
            24057865, 38 / 2.,
        2 * Math.PI / (-762.33 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Синопе);
        jupiter.addMoon(Синопе);

        Planet Спонде = new Planet("Спонде", 0, '\0',"Спутник планеты",
            24252627, 2 / 2.,
        2 * Math.PI / (-771.60 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Спонде);
        jupiter.addMoon(Спонде);

        Planet Автоное = new Planet("Автоное", 0, '\0',"Спутник планеты",
            24264445, 4 / 2.,
        2 * Math.PI / (-772.17 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Автоное);
        jupiter.addMoon(Автоное);

        Planet Мегаклите = new Planet("Мегаклите", 0, '\0',"Спутник планеты",
            24687239, 5 / 2.,
        2 * Math.PI / (-792.44 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Мегаклите);
        jupiter.addMoon(Мегаклите);

        Planet j2_2003 = new Planet("S/2003 J 2", 0, '\0',"Спутник планеты",
            30290846, 2 / 2.,
            2 * Math.PI / (-1077.02 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(j2_2003);
        jupiter.addMoon(j2_2003);

        uiObjects.add(jupiter);

        int satR = 255;
        int satG = 100;
        int satB = 255;
        Planet saturn = new Planet("Сатурн", '6', '6', "Газовый гигант",
            9.554909 * 149597870, 58232,
            2 * Math.PI / (29.4571 * 31557600), new Color(satR, satG, satB));
        priorityMap.put(6, saturn);

        Ring satRing;

        satRing = new Ring(67000, 74500, new Color(satR, satG, satB, 10));
        saturn.addRing(satRing);

        satRing = new Ring(74500, 77800, new Color(satR, satG, satB, 40));
        saturn.addRing(satRing);

        satRing = new Ring(77900, 87500, new Color(satR, satG, satB, 40));
        saturn.addRing(satRing);

        satRing = new Ring(87770, 88690, new Color(satR, satG, satB, 40));
        saturn.addRing(satRing);

        satRing = new Ring(88720, 90200, new Color(satR, satG, satB, 40));
        saturn.addRing(satRing);

        satRing = new Ring(90220, 92000, new Color(satR, satG, satB, 40));
        saturn.addRing(satRing);

        satRing = new Ring(92000, 117500, new Color(satR, satG, satB, 80));
        saturn.addRing(satRing);

        satRing = new Ring(117500, 117680, new Color(satR, satG, satB, 80));
        saturn.addRing(satRing);

        satRing = new Ring(118042.5, 118183, new Color(satR, satG, satB, 80));
        saturn.addRing(satRing);

        satRing = new Ring(118285, 118597, new Color(satR, satG, satB, 80));
        saturn.addRing(satRing);

        satRing = new Ring(118630, 118931, new Color(satR, satG, satB, 80));
        saturn.addRing(satRing);

        satRing = new Ring(118969, 119403, new Color(satR, satG, satB, 80));
        saturn.addRing(satRing);

        satRing = new Ring(119406, 119848, new Color(satR, satG, satB, 80));
        saturn.addRing(satRing);

        satRing = new Ring(120086, 120236, new Color(satR, satG, satB, 80));
        saturn.addRing(satRing);

        satRing = new Ring(120246, 120305, new Color(satR, satG, satB, 80));
        saturn.addRing(satRing);

        satRing = new Ring(120318, 122200, new Color(satR, satG, satB, 80));
        saturn.addRing(satRing);

        satRing = new Ring(122200, 133407.5, new Color(satR, satG, satB, 60));
        saturn.addRing(satRing);

        satRing = new Ring(133762.5, 136482.5, new Color(satR, satG, satB, 60));
        saturn.addRing(satRing);

        satRing = new Ring(136517.5, 136800, new Color(satR, satG, satB, 60));
        saturn.addRing(satRing);

        satRing = new Ring(136800, 137630, new Color(satR, satG, satB, 10));
        saturn.addRing(satRing);

        satRing = new Ring(137630, 137930, new Color(satR, satG, satB, 80));
        saturn.addRing(satRing);

        satRing = new Ring(137930, 138900, new Color(satR, satG, satB, 10));
        saturn.addRing(satRing);

        satRing = new Ring(138900, 139200, new Color(satR, satG, satB, 80));
        saturn.addRing(satRing);

        satRing = new Ring(139200, 139380, new Color(satR, satG, satB, 10));
        saturn.addRing(satRing);

        satRing = new Ring(140210, 140475, new Color(satR, satG, satB, 60));
        saturn.addRing(satRing);

        satRing = new Ring(165800, 173800, new Color(satR, satG, satB, 10));
        saturn.addRing(satRing);

        satRing = new Ring(180000, 480000, new Color(satR, satG, satB, 2));
        saturn.addRing(satRing);

        satRing = new Ring(4000000, 13000000, new Color(satR, satG, satB, 1));
        saturn.addRing(satRing);

        Planet satMoon;

        satMoon = new Planet("Мимас", 0, '\0', "Спутник планеты",
            185539, 397 / 2.,
            2 * Math.PI / (0.940 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Энцелад", 0, '\0', "Спутник планеты",
            238042, 499 / 2.,
            2 * Math.PI / (1.370 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Тефия", 0, '\0', "Спутник планеты",
            294672, 1060 / 2.,
            2 * Math.PI / (1.890 * 24 * 3600), new Color(128, 255, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Диона", 0, '\0', "Спутник планеты",
            377415, 1118 / 2.,
            2 * Math.PI / (2.740 * 24 * 3600), new Color(128, 150, 255));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Рея", 0, '\0', "Спутник планеты",
            527068, 1528 / 2.,
            2 * Math.PI / (4.518 * 24 * 3600), new Color(255, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Титан", 77, 'Ь', "Спутник планеты",
            1221865, 5150 / 2.,
            2 * Math.PI / (15.950 * 24 * 3600), new Color(200, 200, 255));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);
        priorityMap.put(27, null);
        priorityMap.put(28, satMoon);

        satMoon = new Planet("Гиперион", 0, '\0', "Спутник планеты",
            1500933, 266 / 2.,
            2 * Math.PI / (21.280 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Япет", 90, 'Я', "Спутник планеты",
            3560854, 1436 / 2.,
            2 * Math.PI / (79.330 * 24 * 3600), new Color(255, 200, 150));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);
        priorityMap.put(29, satMoon);

        satMoon = new Planet("Феба", 0, '\0', "Спутник планеты",
            12944300, 240 / 2.,
            2 * Math.PI / (-548.2 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Янус", 0, '\0', "Спутник планеты",
            151500, 178 / 2.,
            2 * Math.PI / (0.700 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Эпиметей", 0, '\0', "Спутник планеты",
            151400, 119 / 2.,
            2 * Math.PI / (0.690 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Елена", 0, '\0', "Спутник планеты",
            377440, 32 / 2.,
            2 * Math.PI / (2.740 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Телесто", 0, '\0', "Спутник планеты",
            294720, 24 / 2.,
            2 * Math.PI / (1.890 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Калипсо", 0, '\0', "Спутник планеты",
            294720, 19 / 2.,
            2 * Math.PI / (1.890 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Атлас", 0, '\0', "Спутник планеты",
            137215, 32 / 2.,
            2 * Math.PI / (0.602 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Прометей", 0, '\0', "Спутник планеты",
            139795, 100 / 2.,
            2 * Math.PI / (0.613 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Пандора", 0, '\0', "Спутник планеты",
            141700, 84 / 2.,
            2 * Math.PI / (0.629 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Пан", 0, '\0', "Спутник планеты",
            133600, 20 / 2.,
            2 * Math.PI / (0.575 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Имир", 0, '\0', "Спутник планеты",
            23040000, 18 / 2.,
            2 * Math.PI / (-1315.4 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Палиак", 0, '\0', "Спутник планеты",
            15200000, 22 / 2.,
            2 * Math.PI / (686.9 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Тарвос", 0, '\0', "Спутник планеты",
            17983000, 15 / 2.,
            2 * Math.PI / (926.2 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Иджирак", 0, '\0', "Спутник планеты",
            11124000, 12 / 2.,
            2 * Math.PI / (451.4 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Суттунг", 0, '\0', "Спутник планеты",
            19459000, 7 / 2.,
            2 * Math.PI / (-1016.7 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Кивиок", 0, '\0', "Спутник планеты",
            11111000, 16 / 2.,
            2 * Math.PI / (449.2 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Мундильфари", 0, '\0', "Спутник планеты",
            18685000, 7 / 2.,
            2 * Math.PI / (-952.6 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Альбиорикс", 0, '\0', "Спутник планеты",
            16182000, 32 / 2.,
            2 * Math.PI / (783.5 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Скади", 0, '\0', "Спутник планеты",
            15541000, 8 / 2.,
            2 * Math.PI / (-728.2 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Эррипо", 0, '\0', "Спутник планеты",
            17343000, 10 / 2.,
            2 * Math.PI / (871.2 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Сиарнак", 0, '\0', "Спутник планеты",
            17531000, 40 / 2.,
            2 * Math.PI / (895.6 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Трюм", 0, '\0', "Спутник планеты",
            20474000, 7 / 2.,
            2 * Math.PI / (-1094.3 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Нарви", 0, '\0', "Спутник планеты",
            19007000, 7 / 2.,
            2 * Math.PI / (-1003.9 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Мефона", 0, '\0', "Спутник планеты",
            194000, 3 / 2.,
            2 * Math.PI / (1.010 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Паллена", 0, '\0', "Спутник планеты",
            211000, 4 / 2.,
            2 * Math.PI / (1.140 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Полидевк", 0, '\0', "Спутник планеты",
            377220, 4 / 2.,
            2 * Math.PI / (2.740 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Дафнис", 0, '\0', "Спутник планеты",
            136535, 7 / 2.,
            2 * Math.PI / (0.594 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Эгир", 0, '\0', "Спутник планеты",
            20735000, 6 / 2.,
            2 * Math.PI / (-1116.5 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Бефинд", 0, '\0', "Спутник планеты",
            17119000, 6 / 2.,
            2 * Math.PI / (834.8 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Бергельмир", 0, '\0', "Спутник планеты",
            19338000, 6 / 2.,
            2 * Math.PI / (-1005.9 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Бестла", 0, '\0', "Спутник планеты",
            20129000, 7 / 2.,
            2 * Math.PI / (-1083.6 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Фарбаути", 0, '\0', "Спутник планеты",
            20390000, 5 / 2.,
            2 * Math.PI / (-1086.1 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Фенрир", 0, '\0', "Спутник планеты",
            22453000, 4 / 2.,
            2 * Math.PI / (-1260.3 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Форньот", 0, '\0', "Спутник планеты",
            25108000, 6 / 2.,
            2 * Math.PI / (-1490.9 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Хати", 0, '\0', "Спутник планеты",
            19856000, 6 / 2.,
            2 * Math.PI / (-1038.7 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Гирроккин", 0, '\0', "Спутник планеты",
            18437000, 8 / 2.,
            2 * Math.PI / (-931.8 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Кари", 0, '\0', "Спутник планеты",
            22118000, 7 / 2.,
            2 * Math.PI / (-1233.6 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Логи", 0, '\0', "Спутник планеты",
            23065000, 6 / 2.,
            2 * Math.PI / (-1312.0 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Сколл", 0, '\0', "Спутник планеты",
            17665000, 6 / 2.,
            2 * Math.PI / (-878.3 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Сурт", 0, '\0', "Спутник планеты",
            22707000, 6 / 2.,
            2 * Math.PI / (-1297.7 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Анфа", 0, '\0', "Спутник планеты",
            197700, 1 / 2.,
            2 * Math.PI / (1.04 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Ярнсакса", 0, '\0', "Спутник планеты",
            18811000, 6 / 2.,
            2 * Math.PI / (-964.7 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Грейп", 0, '\0', "Спутник планеты",
            18206000, 6 / 2.,
            2 * Math.PI / (-921.2 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Таркек", 0, '\0', "Спутник планеты",
            18009000, 7 / 2.,
            2 * Math.PI / (887.5 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("Эгеон", 0, '\0', "Спутник планеты",
            167500, 0.5 / 2.,
            2 * Math.PI / (0.80812 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("S/2004 S 7", 0, '\0', "Спутник планеты",
            19800000, 6 / 2.,
            2 * Math.PI / (-1103. * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("S/2004 S 12", 0, '\0', "Спутник планеты",
            19650000, 5 / 2.,
            2 * Math.PI / (-1048. * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("S/2004 S 13", 0, '\0', "Спутник планеты",
            18450000, 6 / 2.,
            2 * Math.PI / (-906. * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("S/2004 S 17", 0, '\0', "Спутник планеты",
            18600000, 4 / 2.,
            2 * Math.PI / (-986. * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("S/2006 S 1", 0, '\0', "Спутник планеты",
            18981135, 6 / 2.,
            2 * Math.PI / (-970. * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("S/2006 S 3", 0, '\0', "Спутник планеты",
            21132000, 6 / 2.,
            2 * Math.PI / (-1142. * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("S/2007 S 2", 0, '\0', "Спутник планеты",
            16560000, 6 / 2.,
            2 * Math.PI / (-800. * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("S/2007 S 3", 0, '\0', "Спутник планеты",
            20518500, 5 / 2.,
            2 * Math.PI / (-1100. * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        satMoon = new Planet("S/2009 S 1", 0, '\0', "Спутник планеты",
            117000, 0.3 / 2.,
            2 * Math.PI / (0.47 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(satMoon);
        saturn.addMoon(satMoon);

        uiObjects.add(saturn);

        int uR = 200;
        int uG = 255;
        int uB = 255;
        Planet uranus = new Planet("Уран", '7', '7', "Ледяной гигант",
            19.2184 * 149597870, 25362,
            2 * Math.PI / (84.0205 * 31557600), new Color(uR, uG, uB));
        priorityMap.put(7, uranus);

        Ring uRing;

        uRing = new Ring(51149, 51210, new Color(uR, uG, uB, 80));
        uranus.addRing(uRing);

        uRing = new Ring(32000, 37850, new Color(uR, uG, uB, 2));
        uranus.addRing(uRing);

        uRing = new Ring(37000, 39500, new Color(uR, uG, uB, 2));
        uranus.addRing(uRing);

        uRing = new Ring(37850, 41350, new Color(uR, uG, uB, 2));
        uranus.addRing(uRing);

        uRing = new Ring(41837, 41838.9, new Color(uR, uG, uB, 20));
        uranus.addRing(uRing);

        uRing = new Ring(42234, 42237.4, new Color(uR, uG, uB, 20));
        uranus.addRing(uRing);

        uRing = new Ring(42570, 42573.4, new Color(uR, uG, uB, 20));
        uranus.addRing(uRing);

        uRing = new Ring(44718, 44725.4, new Color(uR, uG, uB, 20));
        uranus.addRing(uRing);

        uRing = new Ring(45661, 45663.3, new Color(uR, uG, uB, 20));
        uranus.addRing(uRing);

        uRing = new Ring(47176, 47216, new Color(uR, uG, uB, 20));
        uranus.addRing(uRing);

        uRing = new Ring(47627, 47631.15, new Color(uR, uG, uB, 20));
        uranus.addRing(uRing);

        uRing = new Ring(48300, 48311, new Color(uR, uG, uB, 20));
        uranus.addRing(uRing);

        uRing = new Ring(50023, 50024.5, new Color(uR, uG, uB, 20));
        uranus.addRing(uRing);

        uRing = new Ring(66100, 69900, new Color(uR, uG, uB, 1));
        uranus.addRing(uRing);

        uRing = new Ring(86000, 103000, new Color(uR, uG, uB, 1));
        uranus.addRing(uRing);

        Planet Миранда = new Planet("Миранда", 0, '\0',"Спутник планеты",
            129390, 471.6 / 2.,
            2 * Math.PI / (1.413479 * 24 * 3600), new Color(255, 128, 128));
        uiObjects.add(Миранда);
        uranus.addMoon(Миранда);

        Planet Ариэль = new Planet("Ариэль", 70, 'А', "Спутник планеты",
            191020, 1157.8 / 2.,
            2 * Math.PI / (2.520379 * 24 * 3600), new Color(255, 0, 100));
        uiObjects.add(Ариэль);
        uranus.addMoon(Ариэль);
        priorityMap.put(32, null);
        priorityMap.put(33, Ариэль);

        Planet Умбриэль = new Planet("Умбриэль", 69, 'У', "Спутник планеты",
            266300, 1169.4 / 2.,
            2 * Math.PI / (4.144177 * 24 * 3600), new Color(128, 128, 255));
        uiObjects.add(Умбриэль);
        uranus.addMoon(Умбриэль);
        priorityMap.put(34, Умбриэль);

        Planet Титания = new Planet("Титания", 0, '\0',"Спутник планеты",
            435910, 1577.8 / 2.,
            2 * Math.PI / (8.705872 * 24 * 3600), new Color(200, 200, 128));
        uiObjects.add(Титания);
        uranus.addMoon(Титания);

        Planet Оберон = new Planet("Оберон", 74, 'О', "Спутник планеты",
            583520, 1522.8 / 2.,
            2 * Math.PI / (13.463239 * 24 * 3600), new Color(150, 255, 150));
        uiObjects.add(Оберон);
        uranus.addMoon(Оберон);
        priorityMap.put(35, Оберон);

        Planet Корделия = new Planet("Корделия", 0, '\0',"Спутник планеты",
            49751, 21,
            2 * Math.PI / (0.335034 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Корделия);
        uranus.addMoon(Корделия);

        Planet Офелия = new Planet("Офелия", 0, '\0',"Спутник планеты",
            53764, 23,
            2 * Math.PI / (0.376400 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Офелия);
        uranus.addMoon(Офелия);

        Planet Бианка = new Planet("Бианка", 0, '\0',"Спутник планеты",
            59165, 27,
            2 * Math.PI / (0.434579 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Бианка);
        uranus.addMoon(Бианка);

        Planet Крессида = new Planet("Крессида", 0, '\0',"Спутник планеты",
            61766, 41,
            2 * Math.PI / (0.463570 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Крессида);
        uranus.addMoon(Крессида);

        Planet Дездемона = new Planet("Дездемона", 0, '\0',"Спутник планеты",
            62658, 34,
            2 * Math.PI / (0.473650 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Дездемона);
        uranus.addMoon(Дездемона);

        Planet Джульетта = new Planet("Джульетта", 0, '\0',"Спутник планеты",
            64360, 53,
            2 * Math.PI / (0.493065 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Джульетта);
        uranus.addMoon(Джульетта);

        Planet Порция = new Planet("Порция", 0, '\0',"Спутник планеты",
            66097, 70,
            2 * Math.PI / (0.513196 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Порция);
        uranus.addMoon(Порция);

        Planet Розалинда = new Planet("Розалинда", 0, '\0',"Спутник планеты",
            69927, 36,
            2 * Math.PI / (0.558460 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Розалинда);
        uranus.addMoon(Розалинда);

        Planet Купидон = new Planet("Купидон", 0, '\0',"Спутник планеты",
            74800, 9,
            2 * Math.PI / (0.618 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Купидон);
        uranus.addMoon(Купидон);

        Planet Белинда = new Planet("Белинда", 0, '\0',"Спутник планеты",
            75255, 45,
            2 * Math.PI / (0.623527 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Белинда);
        uranus.addMoon(Белинда);

        Planet Пердита = new Planet("Пердита", 0, '\0',"Спутник планеты",
            76420, 15,
            2 * Math.PI / (0.638 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Пердита);
        uranus.addMoon(Пердита);

        Planet Пак = new Planet("Пак", 0, '\0',"Спутник планеты",
            86004, 81,
            2 * Math.PI / (0.761833 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Пак);
        uranus.addMoon(Пак);

        Planet Маб = new Planet("Маб", 0, '\0',"Спутник планеты",
            97734, 12.5,
            2 * Math.PI / (0.923 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Маб);
        uranus.addMoon(Маб);

        Planet Франциско = new Planet("Франциско", 0, '\0',"Спутник планеты",
            4276000, 11,
            2 * Math.PI / (-267.12 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Франциско);
        uranus.addMoon(Франциско);

        Planet Калибан = new Planet("Калибан", 0, '\0',"Спутник планеты",
            7231000, 49,
            2 * Math.PI / (-579.39 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Калибан);
        uranus.addMoon(Калибан);

        Planet Стефано = new Planet("Стефано", 0, '\0',"Спутник планеты",
            8004000, 10,
            2 * Math.PI / (-677.48 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Стефано);
        uranus.addMoon(Стефано);

        Planet Тринкуло = new Planet("Тринкуло", 0, '\0',"Спутник планеты",
            8504000, 5,
            2 * Math.PI / (-748.83 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Тринкуло);
        uranus.addMoon(Тринкуло);

        Planet Сикоракса = new Planet("Сикоракса", 0, '\0',"Спутник планеты",
            12179000, 95,
            2 * Math.PI / (-1285.62 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Сикоракса);
        uranus.addMoon(Сикоракса);

        Planet Маргарита = new Planet("Маргарита", 0, '\0',"Спутник планеты",
            14345000, 5.5,
            2 * Math.PI / (1654.32 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Маргарита);
        uranus.addMoon(Маргарита);

        Planet Просперо = new Planet("Просперо", 0, '\0',"Спутник планеты",
            16256000, 15,
            2 * Math.PI / (-1962.95 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Просперо);
        uranus.addMoon(Просперо);

        Planet Сетебос = new Planet("Сетебос", 0, '\0',"Спутник планеты",
            17418000, 15,
            2 * Math.PI / (-2196.35 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Сетебос);
        uranus.addMoon(Сетебос);

        Planet Фердинанд = new Planet("Фердинанд", 0, '\0',"Спутник планеты",
            20901000, 6,
            2 * Math.PI / (-2805.51 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Фердинанд);
        uranus.addMoon(Фердинанд);

        uiObjects.add(uranus);

        int nR = 50;
        int nG = 200;
        int nB = 200;
        Planet neptun = new Planet("Нептун", '8', '8', "Ледяной гигант",
            30.110387 * 149597870, 24622,
            2 * Math.PI / (164.8 * 31557600), new Color(nR, nG, nB));
        priorityMap.put(8, neptun);

        Ring nRing;
        nRing = new Ring(40900, 42900, new Color(nR, nG, nB, 5));
        neptun.addRing(nRing);
        nRing = new Ring(53200, 53313, new Color(nR, nG, nB, 30));
        neptun.addRing(nRing);
        nRing = new Ring(53313, 57200, new Color(nR, nG, nB, 5));
        neptun.addRing(nRing);
        nRing = new Ring(62932, 62964.5, new Color(nR, nG, nB, 50));
        neptun.addRing(nRing);

        Planet triton = new Planet("Тритон", 78, 'Т', "Спутник планеты",
            354800, 1353.5,
            2 * Math.PI / (-5.877 * 24 * 3600), new Color(200, 200, 255));
        uiObjects.add(triton);
        neptun.addMoon(triton);
        priorityMap.put(36, null);
        priorityMap.put(37, triton);

        Planet nereida = new Planet("Нереида", 0, '\0',"Спутник планеты",
            5513400, 170,
            2 * Math.PI / (360.14 * 24 * 3600), new Color(255, 128, 128));
        uiObjects.add(nereida);
        neptun.addMoon(nereida);

        Planet nayada = new Planet("Наяда", 0, '\0',"Спутник планеты",
            48227, 33.5,
            2 * Math.PI / (0.294 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(nayada);
        neptun.addMoon(nayada);

        Planet talassa = new Planet("Таласса", 0, '\0',"Спутник планеты",
            50075, 40.5,
            2 * Math.PI / (0.311 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(talassa);
        neptun.addMoon(talassa);

        Planet Деспина = new Planet("Деспина", 0, '\0',"Спутник планеты",
            52526, 75,
            2 * Math.PI / (0.335 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Деспина);
        neptun.addMoon(Деспина);

        Planet Галатея = new Planet("Галатея", 0, '\0',"Спутник планеты",
            61953, 87.5,
            2 * Math.PI / (0.429 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Галатея);
        neptun.addMoon(Галатея);

        Planet Ларисса = new Planet("Ларисса", 0, '\0',"Спутник планеты",
            73548, 97.5,
            2 * Math.PI / (0.555 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Ларисса);
        neptun.addMoon(Ларисса);

        Planet Полифем = new Planet("Полифем", 0, '\0',"Спутник планеты",
            105300, 9,
            2 * Math.PI / (0.96 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Полифем);
        neptun.addMoon(Полифем);

        Planet Протей = new Planet("Протей", 0, '\0',"Спутник планеты",
            117647, 210,
            2 * Math.PI / (1.122 * 24 * 3600), new Color(128, 128, 255));
        uiObjects.add(Протей);
        neptun.addMoon(Протей);

        Planet Галимеда = new Planet("Галимеда", 0, '\0',"Спутник планеты",
            15728000, 24,
            2 * Math.PI / (-1879.71 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Галимеда);
        neptun.addMoon(Галимеда);

        Planet Псамафа = new Planet("Псамафа", 0, '\0',"Спутник планеты",
            46695000, 14,
            2 * Math.PI / (-9115.9 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Псамафа);
        neptun.addMoon(Псамафа);

        Planet Сао = new Planet("Сао", 0, '\0',"Спутник планеты",
            22422000, 22,
            2 * Math.PI / (2914.0 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Сао);
        neptun.addMoon(Сао);

        Planet Лаомедея = new Planet("Лаомедея", 0, '\0',"Спутник планеты",
            23571000, 21,
            2 * Math.PI / (3167.85 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Лаомедея);
        neptun.addMoon(Лаомедея);

        Planet Несо = new Planet("Несо", 0, '\0',"Спутник планеты",
            48387000, 30,
            2 * Math.PI / (-9374 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(Несо);
        neptun.addMoon(Несо);

        uiObjects.add(neptun);

        Planet pluto = new Planet("Плутон", '9', '9', "Карликовая планета",
            39.48 * 149597870, 1187,
            2 * Math.PI / (248.0 * 31557600), new Color(200, 200, 200));
        priorityMap.put(9, null);
        priorityMap.put(10, pluto);

        Planet haron = new Planet("Харон", 0, '\0',"Спутник планеты",
            19571, 606,
            2 * Math.PI / (6.387230 * 24 * 3600), new Color(190, 150, 150));
        uiObjects.add(haron);
        pluto.addMoon(haron);

        Planet sticks = new Planet("Стикс", 0, '\0',"Спутник планеты",
            42000, 3,
            2 * Math.PI / (19 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(sticks);
        pluto.addMoon(sticks);

        Planet nikta = new Planet("Никта", 0, '\0',"Спутник планеты",
            48675, 20.5,
            2 * Math.PI / (24.856 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(nikta);
        pluto.addMoon(nikta);

        Planet cerber = new Planet("Кербер", 0, '\0',"Спутник планеты",
            59000, 4.5,
            2 * Math.PI / (32.1 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(cerber);
        pluto.addMoon(cerber);

        Planet hydra = new Planet("Гидра", 0, '\0',"Спутник планеты",
            64780, 38,
            2 * Math.PI / (38.206 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(hydra);
        pluto.addMoon(hydra);

        uiObjects.add(pluto);

        Planet haumea = new Planet("Хаумеа", 91, 'Х', "Карликовая планета",
            43.218 * 149597870, 620,
            2 * Math.PI / (284.12 * 31557600), new Color(128, 128, 128));
        priorityMap.put(12, haumea);

        Planet hiiaca = new Planet("Хииака", 0, '\0',"Спутник планеты",
            49880, 195,
            2 * Math.PI / (49.12 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(hiiaca);
        haumea.addMoon(hiiaca);

        Planet namaca = new Planet("Намака", 0, '\0',"Спутник планеты",
            25657, 85,
            2 * Math.PI / (18.2783 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(namaca);
        haumea.addMoon(namaca);

        uiObjects.add(haumea);

        Planet makemake = new Planet("Макемаке", 86, 'М', "Карликовая планета",
            45.715 * 149597870, 720,
            2 * Math.PI / (309.09 * 31557600), new Color(200, 200, 0));
        priorityMap.put(13, makemake);

        Planet mk2 = new Planet("S/2015 (136472) 1", 0, '\0',"Спутник планеты",
            21100, 103.5,
            2 * Math.PI / (12.4 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(mk2);
        makemake.addMoon(mk2);

        uiObjects.add(makemake);

        Planet eris = new Planet("Эрида", 222, 'Э', "Карликовая планета",
            67.781 * 149597870, 1163,
            2 * Math.PI / (558.04 * 31557600), new Color(255, 0, 0));
        priorityMap.put(11, eris);

        Planet dysnomia = new Planet("Дисномия", 0, '\0',"Спутник планеты",
            37350, 342,
            2 * Math.PI / (15.774 * 24 * 3600), new Color(128, 128, 128));
        uiObjects.add(dysnomia);
        eris.addMoon(dysnomia);

        uiObjects.add(eris);

        Planet Седна = new Planet("Седна", 67, 'С', "Карликовая планета",
            506.2 * 149597870, 995 / 2.,
            2 * Math.PI / (11400. * 31557600), new Color(255, 255, 0));
        uiObjects.add(Седна);
        priorityMap.put(15, Седна);

        return uiObjects;
    }
}
