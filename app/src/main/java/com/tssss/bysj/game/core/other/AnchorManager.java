package com.tssss.bysj.game.core.other;

import java.util.HashMap;
import java.util.Map;

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
    public static String UNKNOWN = "UNKNOWN";

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
        one.setUsed(true);
        two.setUsed(false);
        three.setUsed(true);
        four.setUsed(true);
        five.setUsed(false);
        six.setUsed(true);
        seven.setUsed(true);
        eight.setUsed(false);
        nine.setUsed(true);
    }

    /*
    生成锚点。
     */
    public void createAnchors() {
        GameUtil gameUtil = GameUtil.getGameUtil();
        int temp = gameUtil.getSurfaceSize() / 4;

        one.setX(temp);
        one.setY(temp);
        two.setX(temp * 2);
        two.setY(temp);
        three.setX(temp * 3);
        three.setY(temp);

        four.setX(temp);
        four.setY(temp * 2);
        five.setX(temp * 2);
        five.setY(temp * 2);
        six.setX(temp * 3);
        six.setY(temp * 2);

        seven.setX(temp);
        seven.setY(temp * 3);
        eight.setX(temp * 2);
        eight.setY(temp * 3);
        nine.setX(temp * 3);
        nine.setY(temp * 3);
    }

    /*
    用户是否点击在锚点上。
     */
    public boolean inRange(int x, int y) {
        GameUtil gameUtil = GameUtil.getGameUtil();
        int temp = gameUtil.getSurfaceSize() / 16;

        if (x >= one.getX() - temp &&
                x <= one.getX() + temp &&
                y >= one.getY() - temp &&
                y <= one.getY() + temp) {
            return true;
        } else if (x >= two.getX() - temp &&
                x <= two.getX() + temp &&
                y >= two.getY() - temp &&
                y <= two.getY() + temp) {
            return true;
        } else if (x >= three.getX() - temp &&
                x <= three.getX() + temp &&
                y >= three.getY() - temp &&
                y <= three.getY() + temp) {
            return true;
        } else if (x >= four.getX() - temp &&
                x <= four.getX() + temp &&
                y >= four.getY() - temp &&
                y <= four.getY() + temp) {
            return true;
        } else if (x >= five.getX() - temp &&
                x <= five.getX() + temp &&
                y >= five.getY() - temp &&
                y <= five.getY() + temp) {
            return true;
        } else if (x >= six.getX() - temp &&
                x <= six.getX() + temp &&
                y >= six.getY() - temp &&
                y <= six.getY() + temp) {
            return true;
        } else if (x >= seven.getX() - temp &&
                x <= seven.getX() + temp &&
                y >= seven.getY() - temp &&
                y <= seven.getY() + temp) {
            return true;
        } else if (x >= eight.getX() - temp &&
                x <= eight.getX() + temp &&
                y >= eight.getY() - temp &&
                y <= eight.getY() + temp) {
            return true;
        } else return x >= nine.getX() - temp &&
                x <= nine.getX() + temp &&
                y >= nine.getY() - temp &&
                y <= nine.getY() + temp;

    }

    /*
    根据坐标返回锚点的key。
     */
    public String identifyAnchor(int x, int y) {
        GameUtil gameUtil = GameUtil.getGameUtil();
        int temp = gameUtil.getSurfaceSize() / 16;
        if (x >= one.getX() - temp && x <= one.getX() + temp &&
                y >= one.getY() - temp && y <= one.getY() + temp) {
            return ONE;
        } else if (x >= two.getX() - temp && x <= two.getX() + temp &&
                y >= two.getY() - temp && y <= two.getY() + temp) {
            return TWO;
        } else if (x >= three.getX() - temp && x <= three.getX() + temp &&
                y >= three.getY() - temp && y <= three.getY() + temp) {
            return THREE;
        } else if (x >= four.getX() - temp && x <= four.getX() + temp &&
                y >= four.getY() - temp && y <= four.getY() + temp) {
            return FOUR;
        } else if (x >= five.getX() - temp && x <= five.getX() + temp &&
                y >= five.getY() - temp && y <= five.getY() + temp) {
            return FIVE;
        } else if (x >= six.getX() - temp && x <= six.getX() + temp &&
                y >= six.getY() - temp && y <= six.getY() + temp) {
            return SIX;
        } else if (x >= seven.getX() - temp && x <= seven.getX() + temp &&
                y >= seven.getY() - temp && y <= seven.getY() + temp) {
            return SEVEN;
        } else if (x >= eight.getX() - temp && x <= eight.getX() + temp &&
                y >= eight.getY() - temp && y <= eight.getY() + temp) {
            return EIGHT;
        } else if (x >= nine.getX() - temp && x <= nine.getX() + temp &&
                y >= nine.getY() - temp && y <= nine.getY() + temp) {
            return NINE;
        }
        return UNKNOWN;
    }
}
