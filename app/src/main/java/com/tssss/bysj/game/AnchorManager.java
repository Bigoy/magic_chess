package com.tssss.bysj.game;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Manage nine anchor points on chess board.
 *
 * <P>The nine anchor points are named ONE,TWO,THREE,FOUR,FIVE,SIX,SEVEN,EIGHT,NINE in
 * order from left to right and from top to bottom.</p>
 *
 * <p>
 * one   two   three
 * four  five  six
 * seven eight nine
 * </P>
 *
 * <P>If you need an anchor, just call the method {@code getAnchor(String anchorKey)}.</P>
 *
 * @author Tssss
 * @date 2019-1-24
 */
class AnchorManager {
    private static AnchorManager mAnchorManager;

    static String ONE = "one";
    static String TWO = "two";
    static String THREE = "three";
    static String FOUR = "four";
    private static String FIVE = "five";
    static String SIX = "six";
    static String SEVEN = "seven";
    static String EIGHT = "eight";
    static String NINE = "nine";

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

    static AnchorManager getAnchorManager() {
        if (mAnchorManager == null) {
            mAnchorManager = new AnchorManager();
        }
        return mAnchorManager;
    }

    Anchor getAnchor(String position) {
        return anchors.get(position);
    }

    private void initAnchors() {
        one = new Anchor(ONE);
        two = new Anchor(TWO);
        three = new Anchor(THREE);
        four = new Anchor(FOUR);
        five = new Anchor(FIVE);
        six = new Anchor(SIX);
        seven = new Anchor(SEVEN);
        eight = new Anchor(EIGHT);
        nine = new Anchor(NINE);

        anchors.put(ONE, one);
        anchors.put(TWO, two);
        anchors.put(THREE, three);
        anchors.put(FOUR, four);
        anchors.put(FIVE, five);
        anchors.put(SIX, six);
        anchors.put(SEVEN, seven);
        anchors.put(EIGHT, eight);
        anchors.put(NINE, nine);

        Log.wtf(getClass().getSimpleName(), "anchors initialized, total " + this.anchors.size());
    }

    private void initAnchorUseState() {
        Objects.requireNonNull(anchors.get(ONE)).setUsed(true);
        Objects.requireNonNull(anchors.get(TWO)).setUsed(false);
        Objects.requireNonNull(anchors.get(THREE)).setUsed(true);
        Objects.requireNonNull(anchors.get(FOUR)).setUsed(true);
        Objects.requireNonNull(anchors.get(FIVE)).setUsed(false);
        Objects.requireNonNull(anchors.get(SIX)).setUsed(true);
        Objects.requireNonNull(anchors.get(SEVEN)).setUsed(true);
        Objects.requireNonNull(anchors.get(EIGHT)).setUsed(false);
        Objects.requireNonNull(anchors.get(NINE)).setUsed(true);

        Log.wtf(getClass().getSimpleName(), "anchors state initialized");
    }

    /**
     * Create nine anchor points based on the size of game surface view.
     */
    void createAnchors(int chessboardSize) {
        int temp = chessboardSize / 4;

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

        /*Log.wtf(getClass().getSimpleName(), "anchors data created");
        Log.wtf(getClass().getSimpleName(), one.toString());
        Log.wtf(getClass().getSimpleName(), two.toString());
        Log.wtf(getClass().getSimpleName(), three.toString());
        Log.wtf(getClass().getSimpleName(), four.toString());
        Log.wtf(getClass().getSimpleName(), five.toString());
        Log.wtf(getClass().getSimpleName(), six.toString());
        Log.wtf(getClass().getSimpleName(), seven.toString());
        Log.wtf(getClass().getSimpleName(), eight.toString());
        Log.wtf(getClass().getSimpleName(), nine.toString());*/
    }

    /**
     * Judge if the user's click position is within an anchor point.
     */
    boolean inRange(int x, int y) {
        int temp = GameHelper.getGameHelper().getSurfaceSize() / 16;

        if (x >= Objects.requireNonNull(anchors.get(AnchorManager.ONE)).getX() - temp &&
                x <= Objects.requireNonNull(anchors.get(AnchorManager.ONE)).getX() + temp &&
                y >= Objects.requireNonNull(anchors.get(AnchorManager.ONE)).getY() - temp &&
                y <= Objects.requireNonNull(anchors.get(AnchorManager.ONE)).getY() + temp) {
            return true;

        } else if (x >= Objects.requireNonNull(anchors.get(AnchorManager.TWO)).getX() - temp &&
                x <= Objects.requireNonNull(anchors.get(AnchorManager.TWO)).getX() + temp &&
                y >= Objects.requireNonNull(anchors.get(AnchorManager.TWO)).getY() - temp &&
                y <= Objects.requireNonNull(anchors.get(AnchorManager.TWO)).getY() + temp) {
            return true;

        } else if (x >= Objects.requireNonNull(anchors.get(AnchorManager.THREE)).getX() - temp &&
                x <= Objects.requireNonNull(anchors.get(AnchorManager.THREE)).getX() + temp &&
                y >= Objects.requireNonNull(anchors.get(AnchorManager.THREE)).getY() - temp &&
                y <= Objects.requireNonNull(anchors.get(AnchorManager.THREE)).getY() + temp) {

            return true;
        } else if (x >= Objects.requireNonNull(anchors.get(AnchorManager.FOUR)).getX() - temp &&
                x <= Objects.requireNonNull(anchors.get(AnchorManager.FOUR)).getX() + temp &&
                y >= Objects.requireNonNull(anchors.get(AnchorManager.FOUR)).getY() - temp &&
                y <= Objects.requireNonNull(anchors.get(AnchorManager.FOUR)).getY() + temp) {
            return true;

        } else if (x >= Objects.requireNonNull(anchors.get(AnchorManager.FIVE)).getX() - temp &&
                x <= Objects.requireNonNull(anchors.get(AnchorManager.FIVE)).getX() + temp &&
                y >= Objects.requireNonNull(anchors.get(AnchorManager.FIVE)).getY() - temp &&
                y <= Objects.requireNonNull(anchors.get(AnchorManager.FIVE)).getY() + temp) {
            return true;

        } else if (x >= Objects.requireNonNull(anchors.get(AnchorManager.SIX)).getX() - temp &&
                x <= Objects.requireNonNull(anchors.get(AnchorManager.SIX)).getX() + temp &&
                y >= Objects.requireNonNull(anchors.get(AnchorManager.SIX)).getY() - temp &&
                y <= Objects.requireNonNull(anchors.get(AnchorManager.SIX)).getY() + temp) {
            return true;

        } else if (x >= Objects.requireNonNull(anchors.get(AnchorManager.SEVEN)).getX() - temp &&
                x <= Objects.requireNonNull(anchors.get(AnchorManager.SEVEN)).getX() + temp &&
                y >= Objects.requireNonNull(anchors.get(AnchorManager.SEVEN)).getY() - temp &&
                y <= Objects.requireNonNull(anchors.get(AnchorManager.SEVEN)).getY() + temp) {
            return true;

        } else if (x >= Objects.requireNonNull(anchors.get(AnchorManager.EIGHT)).getX() - temp &&
                x <= Objects.requireNonNull(anchors.get(AnchorManager.EIGHT)).getX() + temp &&
                y >= Objects.requireNonNull(anchors.get(AnchorManager.EIGHT)).getY() - temp &&
                y <= Objects.requireNonNull(anchors.get(AnchorManager.EIGHT)).getY() + temp) {
            return true;

        } else return x >= Objects.requireNonNull(anchors.get(AnchorManager.NINE)).getX() - temp &&
                x <= Objects.requireNonNull(anchors.get(AnchorManager.NINE)).getX() + temp &&
                y >= Objects.requireNonNull(anchors.get(AnchorManager.NINE)).getY() - temp &&
                y <= Objects.requireNonNull(anchors.get(AnchorManager.NINE)).getY() + temp;
    }

    /**
     * Identify the identity of anchor based on coordinates.
     *
     * @return key of the anchor.
     */
    Anchor identifyAnchor(int x, int y) {
        int temp = GameHelper.getGameHelper().getSurfaceSize() / 16;

        if (x >= Objects.requireNonNull(anchors.get(ONE)).getX() - temp && x <= Objects.requireNonNull(anchors.get(ONE)).getX() + temp &&
                y >= Objects.requireNonNull(anchors.get(ONE)).getY() - temp && y <= Objects.requireNonNull(anchors.get(ONE)).getY() + temp) {
            return anchors.get(ONE);

        } else if (x >= Objects.requireNonNull(anchors.get(TWO)).getX() - temp && x <= Objects.requireNonNull(anchors.get(TWO)).getX() + temp &&
                y >= Objects.requireNonNull(anchors.get(TWO)).getY() - temp && y <= Objects.requireNonNull(anchors.get(TWO)).getY() + temp) {
            return anchors.get(TWO);

        } else if (x >= Objects.requireNonNull(anchors.get(THREE)).getX() - temp && x <= Objects.requireNonNull(anchors.get(THREE)).getX() + temp &&
                y >= Objects.requireNonNull(anchors.get(THREE)).getY() - temp && y <= Objects.requireNonNull(anchors.get(THREE)).getY() + temp) {
            return anchors.get(THREE);

        } else if (x >= Objects.requireNonNull(anchors.get(FOUR)).getX() - temp && x <= Objects.requireNonNull(anchors.get(FOUR)).getX() + temp &&
                y >= Objects.requireNonNull(anchors.get(FOUR)).getY() - temp && y <= Objects.requireNonNull(anchors.get(FOUR)).getY() + temp) {
            return anchors.get(FOUR);

        } else if (x >= Objects.requireNonNull(anchors.get(FIVE)).getX() - temp && x <= Objects.requireNonNull(anchors.get(FIVE)).getX() + temp &&
                y >= Objects.requireNonNull(anchors.get(FIVE)).getY() - temp && y <= Objects.requireNonNull(anchors.get(FIVE)).getY() + temp) {
            return anchors.get(FIVE);

        } else if (x >= Objects.requireNonNull(anchors.get(SIX)).getX() - temp && x <= Objects.requireNonNull(anchors.get(SIX)).getX() + temp &&
                y >= Objects.requireNonNull(anchors.get(SIX)).getY() - temp && y <= Objects.requireNonNull(anchors.get(SIX)).getY() + temp) {
            return anchors.get(SIX);

        } else if (x >= Objects.requireNonNull(anchors.get(SEVEN)).getX() - temp && x <= Objects.requireNonNull(anchors.get(SEVEN)).getX() + temp &&
                y >= Objects.requireNonNull(anchors.get(SEVEN)).getY() - temp && y <= Objects.requireNonNull(anchors.get(SEVEN)).getY() + temp) {
            return anchors.get(SEVEN);

        } else if (x >= Objects.requireNonNull(anchors.get(EIGHT)).getX() - temp && x <= Objects.requireNonNull(anchors.get(EIGHT)).getX() + temp &&
                y >= Objects.requireNonNull(anchors.get(EIGHT)).getY() - temp && y <= Objects.requireNonNull(anchors.get(EIGHT)).getY() + temp) {
            return anchors.get(EIGHT);

        } else if (x >= Objects.requireNonNull(anchors.get(NINE)).getX() - temp && x <= Objects.requireNonNull(anchors.get(NINE)).getX() + temp &&
                y >= Objects.requireNonNull(anchors.get(NINE)).getY() - temp && y <= Objects.requireNonNull(anchors.get(NINE)).getY() + temp) {
            return anchors.get(NINE);
        }

        return null;
    }

}
