package com.tssss.bysj.user.role;

/**
 * Game role，one account can apply for multiple roles.
 * name,sex,level,experience
 *
 * @author Tssss
 * @date 2019-01-16
 */
public class GameRole {
    public static String ROLE_SEX_BOY = "♂";
    public static String ROLE_SEX_GIRL = "♀";

    public static String ROLE_LEVEL_ROOKIE = "菜鸟";
    public static String ROLE_LEVEL_POSITIVE = "转正";
    public static String ROLE_LEVEL_JUNIOR = "初级棋手";
    public static String ROLE_LEVEL_INTERMEDIATE = "中级棋手";
    public static String ROLE_LEVEL_SENIOR = "高级棋手";
    public static String ROLE_LEVEL_MASTER = "大师";
    public static String ROLE_LEVEL_GURU = "宗师";

    private String mRoleName, mRoleSex, mRoleLevel;
    private int mRoleExperience;
    private byte[] mRoleHeadImg;

    public String getRoleName() {
        return mRoleName;
    }

    public void setRoleName(String mRoleName) {
        this.mRoleName = mRoleName;
    }

    public String getRoleSex() {
        return mRoleSex;
    }

    public void setRoleSex(String mRoleSex) {
        this.mRoleSex = mRoleSex;
    }

    public String getRoleLevel() {
        return mRoleLevel;
    }

    public void setRoleLevel(String mRoleLevel) {
        this.mRoleLevel = mRoleLevel;
    }

    public int getRoleExperience() {
        return mRoleExperience;
    }

    public void setRoleExperience(int mRoleExperience) {
        this.mRoleExperience = mRoleExperience;
    }

    public void setRoleHeadImg(byte[] img) {
        this.mRoleHeadImg = img;
    }

    public byte[] getRoleHeadImg() {
        return this.mRoleHeadImg;
    }

}
