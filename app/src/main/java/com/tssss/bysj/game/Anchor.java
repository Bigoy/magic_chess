package com.tssss.bysj.game;

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

    private boolean used = false;

    public Anchor() {
    }

    public Anchor(int x, int y) {
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

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Anchor)
            return ((Anchor) obj).getX() == x && ((Anchor) obj).getY() == y;

        return false;
    }
}

