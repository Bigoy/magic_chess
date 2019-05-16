package com.tssss.bysj.game.role.info.fragment.battle_record;

public class BattleRecord {

    /**
     * 对局结果
     */
    private String result;

    /**
     * 对方的 accountID
     */
    private String accountID;


    public BattleRecord(String result, String accountID, String time) {
        this.result = result;
        this.accountID = accountID;
        this.time = time;
    }

    public BattleRecord() {
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    /**
     * 对战时间
     */
    private String time;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }


}
