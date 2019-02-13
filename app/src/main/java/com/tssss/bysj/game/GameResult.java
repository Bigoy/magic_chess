package com.tssss.bysj.game;

public class GameResult {
    private String mResultDescription,
            mWinnerName,
            mExpSettlement;

    public String getResultDescription() {
        return mResultDescription;
    }

    public void setResultDescription(String mResultDescription) {
        this.mResultDescription = mResultDescription;
    }

    public String getWinnerName() {
        return mWinnerName;
    }

    public void setWinnerName(String mWinnerName) {
        this.mWinnerName = mWinnerName;
    }

    public String getExpSettlement() {
        return mExpSettlement;
    }

    void setExpSettlement(String mExpSettlement) {
        this.mExpSettlement = mExpSettlement;
    }
}
