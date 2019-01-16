package com.tssss.bysj.user.role;

/**
 * Game role，one account can apply for multiple roles.
 * name,sex,level,experience
 *
 * @author Tssss
 * @date 2019-01-16
 */
public class GameRole {
    private String mRoleName, mRoleSex, mRoleLevel;
    private int mRoleExperience;

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
}
