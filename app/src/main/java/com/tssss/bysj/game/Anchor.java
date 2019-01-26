package com.tssss.bysj.game;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * A marker for drawing chessboard and positioning of pieces.
 *
 * <p>There are a total of nine anchor points and each anchor
 * provides x and y coordinates. Only connect nine anchor points
 * when drawing chess board.</p>
 *
 * @author Tssss
 * @date 2019-1-24
 */
public class Anchor {
    private int x;
    private int y;
    private String name;

    private boolean used = false;


    public Anchor(String name) {
        this.name = name;
    }

    public Anchor(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Anchor)
            return ((Anchor) obj).getX() == x && ((Anchor) obj).getY() == y;

        return false;
    }

    @NonNull
    @Override
    public String toString() {

        return "name = " + this.name +
                "\t" +
                "x = " +
                this.x +
                "\t" +
                "y = " +
                this.y +
                "\t" +
                "useState = " +
                this.used +
                "\n";
    }
}

