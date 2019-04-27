package com.tssss.bysj.game.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AnchorManager {
    private static AnchorManager mAnchorManager;

    public static String ONE = "one";
    public static String TWO = "two";
    public static String THREE = "three";
    public static String FOUR = "four";
    public static String FIVE = "five";
    public static String SIX = "six";
    public static String SEVEN = "seven";
    public static String EIGHT = "eight";
    public static String NINE = "nine";

    private Anchor one;
    private Anchor two;
    private Anchor three;
    private Anchor four;
    private Anchor five;
    private Anchor six;
    private Anchor seven;
    private Anchor eight;
    private Anchor nine;

    private Map<String, Anchor> anchors;

    private AnchorManager() {
        anchors = new HashMap<>();
        initAnchors();
        initAnchorUseState();
    }

    public static AnchorManager getAnchorManager() {
        if (mAnchorManager == null) {
            mAnchorManager = new AnchorManager();
        }
        return mAnchorManager;
    }

    public Anchor getAnchor(String position) {
        return anchors.get(position);
    }

    private void initAnchors() {
        one = new Anchor();
        two = new Anchor();
        three = new Anchor();
        four = new Anchor();
        five = new Anchor();
        six = new Anchor();
        seven = new Anchor();
        eight = new Anchor();
        nine = new Anchor();

        anchors.put(ONE, one);
        anchors.put(TWO, two);
        anchors.put(THREE, three);
        anchors.put(FOUR, four);
        anchors.put(FIVE, five);
        anchors.put(SIX, six);
        anchors.put(SEVEN, seven);
        anchors.put(EIGHT, eight);
        anchors.put(NINE, nine);

    }

    public void initAnchorUseState() {
        anchors.get(ONE).setUsed(true);
        anchors.get(TWO).setUsed(false);
        anchors.get(THREE).setUsed(true);
        anchors.get(FOUR).setUsed(true);
        anchors.get(FIVE).setUsed(false);
        anchors.get(SIX).setUsed(true);
        anchors.get(SEVEN).setUsed(true);
        anchors.get(EIGHT).setUsed(false);
        anchors.get(NINE).setUsed(true);
    }

    /*
    生成锚点。
     */
    public void createAnchors() {
        GameUtil gameUtil = GameUtil.getGameUtil();
        int temp = gameUtil.getSurfaceSize() / 4;

        Objects.requireNonNull(anchors.get(ONE)).setX(temp);
        Objects.requireNonNull(anchors.get(ONE)).setY(temp);
        Objects.requireNonNull(anchors.get(TWO)).setX(temp * 2);
        Objects.requireNonNull(anchors.get(TWO)).setY(temp);
        Objects.requireNonNull(anchors.get(THREE)).setX(temp * 3);
        Objects.requireNonNull(anchors.get(THREE)).setY(temp);

        Objects.requireNonNull(anchors.get(FOUR)).setX(temp);
        Objects.requireNonNull(anchors.get(FOUR)).setY(temp * 2);
        Objects.requireNonNull(anchors.get(FIVE)).setX(temp * 2);
        Objects.requireNonNull(anchors.get(FIVE)).setY(temp * 2);
        Objects.requireNonNull(anchors.get(SIX)).setX(temp * 3);
        Objects.requireNonNull(anchors.get(SIX)).setY(temp * 2);

        Objects.requireNonNull(anchors.get(SEVEN)).setX(temp);
        Objects.requireNonNull(anchors.get(SEVEN)).setY(temp * 3);
        Objects.requireNonNull(anchors.get(EIGHT)).setX(temp * 2);
        Objects.requireNonNull(anchors.get(EIGHT)).setY(temp * 3);
        Objects.requireNonNull(anchors.get(NINE)).setX(temp * 3);
        Objects.requireNonNull(anchors.get(NINE)).setY(temp * 3);
    }

    /*
    用户是否点击在锚点上。
     */
    public boolean inRange(int x, int y) {
        GameUtil gameUtil = GameUtil.getGameUtil();
        int temp = gameUtil.getSurfaceSize() / 16;

        if (x >= anchors.get(AnchorManager.ONE).getX() - temp &&
                x <= anchors.get(AnchorManager.ONE).getX() + temp &&
                y >= anchors.get(AnchorManager.ONE).getY() - temp &&
                y <= anchors.get(AnchorManager.ONE).getY() + temp) {
            return true;
        } else if (x >= anchors.get(AnchorManager.TWO).getX() - temp &&
                x <= anchors.get(AnchorManager.TWO).getX() + temp &&
                y >= anchors.get(AnchorManager.TWO).getY() - temp &&
                y <= anchors.get(AnchorManager.TWO).getY() + temp) {
            return true;
        } else if (x >= anchors.get(AnchorManager.THREE).getX() - temp &&
                x <= anchors.get(AnchorManager.THREE).getX() + temp &&
                y >= anchors.get(AnchorManager.THREE).getY() - temp &&
                y <= anchors.get(AnchorManager.THREE).getY() + temp) {
            return true;
        } else if (x >= anchors.get(AnchorManager.FOUR).getX() - temp &&
                x <= anchors.get(AnchorManager.FOUR).getX() + temp &&
                y >= anchors.get(AnchorManager.FOUR).getY() - temp &&
                y <= anchors.get(AnchorManager.FOUR).getY() + temp) {
            return true;
        } else if (x >= anchors.get(AnchorManager.FIVE).getX() - temp &&
                x <= anchors.get(AnchorManager.FIVE).getX() + temp &&
                y >= anchors.get(AnchorManager.FIVE).getY() - temp &&
                y <= anchors.get(AnchorManager.FIVE).getY() + temp) {
            return true;
        } else if (x >= anchors.get(AnchorManager.SIX).getX() - temp &&
                x <= anchors.get(AnchorManager.SIX).getX() + temp &&
                y >= anchors.get(AnchorManager.SIX).getY() - temp &&
                y <= anchors.get(AnchorManager.SIX).getY() + temp) {
            return true;
        } else if (x >= anchors.get(AnchorManager.SEVEN).getX() - temp &&
                x <= anchors.get(AnchorManager.SEVEN).getX() + temp &&
                y >= anchors.get(AnchorManager.SEVEN).getY() - temp &&
                y <= anchors.get(AnchorManager.SEVEN).getY() + temp) {
            return true;
        } else if (x >= anchors.get(AnchorManager.EIGHT).getX() - temp &&
                x <= anchors.get(AnchorManager.EIGHT).getX() + temp &&
                y >= anchors.get(AnchorManager.EIGHT).getY() - temp &&
                y <= anchors.get(AnchorManager.EIGHT).getY() + temp) {
            return true;
        } else if (x >= anchors.get(AnchorManager.NINE).getX() - temp &&
                x <= anchors.get(AnchorManager.NINE).getX() + temp &&
                y >= anchors.get(AnchorManager.NINE).getY() - temp &&
                y <= anchors.get(AnchorManager.NINE).getY() + temp) {
            return true;
        } else {
            return false;
        }

    }

    /*
    根据坐标返回锚点的key。
     */
    public String identifyAnchor(int x, int y) {
        GameUtil gameUtil = GameUtil.getGameUtil();
        int temp = gameUtil.getSurfaceSize() / 16;
        if (x >= anchors.get(ONE).getX() - temp && x <= anchors.get(ONE).getX() + temp &&
                y >= anchors.get(ONE).getY() - temp && y <= anchors.get(ONE).getY() + temp) {
            return ONE;
        } else if (x >= anchors.get(TWO).getX() - temp && x <= anchors.get(TWO).getX() + temp &&
                y >= anchors.get(TWO).getY() - temp && y <= anchors.get(TWO).getY() + temp) {
            return TWO;
        } else if (x >= anchors.get(THREE).getX() - temp && x <= anchors.get(THREE).getX() + temp &&
                y >= anchors.get(THREE).getY() - temp && y <= anchors.get(THREE).getY() + temp) {
            return THREE;
        } else if (x >= anchors.get(FOUR).getX() - temp && x <= anchors.get(FOUR).getX() + temp &&
                y >= anchors.get(FOUR).getY() - temp && y <= anchors.get(FOUR).getY() + temp) {
            return FOUR;
        } else if (x >= anchors.get(FIVE).getX() - temp && x <= anchors.get(FIVE).getX() + temp &&
                y >= anchors.get(FIVE).getY() - temp && y <= anchors.get(FIVE).getY() + temp) {
            return FIVE;
        } else if (x >= anchors.get(SIX).getX() - temp && x <= anchors.get(SIX).getX() + temp &&
                y >= anchors.get(SIX).getY() - temp && y <= anchors.get(SIX).getY() + temp) {
            return SIX;
        } else if (x >= anchors.get(SEVEN).getX() - temp && x <= anchors.get(SEVEN).getX() + temp &&
                y >= anchors.get(SEVEN).getY() - temp && y <= anchors.get(SEVEN).getY() + temp) {
            return SEVEN;
        } else if (x >= anchors.get(EIGHT).getX() - temp && x <= anchors.get(EIGHT).getX() + temp &&
                y >= anchors.get(EIGHT).getY() - temp && y <= anchors.get(EIGHT).getY() + temp) {
            return EIGHT;
        } else if (x >= anchors.get(NINE).getX() - temp && x <= anchors.get(NINE).getX() + temp &&
                y >= anchors.get(NINE).getY() - temp && y <= anchors.get(NINE).getY() + temp) {
            return NINE;
        }
        return "";
    }
}
