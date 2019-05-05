package com.tssss.bysj.game.rank;

import com.tssss.bysj.game.core.other.GameRole;

public class Rank {
    private GameRole role;
    private int rankNum;


    public Rank(GameRole role, int rankNum) {
        this.role = role;
        this.rankNum = rankNum;
    }

    public Rank() {

    }


    public GameRole getRole() {
        return role;
    }

    public void setRole(GameRole role) {
        this.role = role;
    }

    public int getRankNum() {
        return rankNum;
    }

    public void setRankNum(int rankNum) {
        this.rankNum = rankNum;
    }


}
