/*
package com.tssss.bysj.user.role;

import com.tssss.bysj.game.Rule;

import androidx.annotation.Nullable;

*/
/*
  Game role，one account can apply for multiple roles.
  name,sex,level,experience

  @author Tssss
 * @date 2019-01-16
 *//*

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

    public static String ROLE_STATE_ONLINE = "在线";
    public static String ROLE_STATE_OFFLINE = "不在线";

    private String mRoleName,
            mRoleSex,
            mRoleLevel,
            mRoleState;
    private int mRoleExperience = 0;
    private byte[] mRoleHeadImg;

    private Rule mRule;


    public GameRole() {
        this.mRule = new Rule();
    }

    public GameRole(String roleName,
                    String roleSex,
                    String roleLevel,
                    String roleState,
                    int roleExperience,
                    byte[] roleHeadImg) {

        this.mRoleName = roleName;
        this.mRoleSex = roleSex;
        this.mRoleLevel = roleLevel;
        this.mRoleState = roleState;
        this.mRoleExperience = roleExperience;
        this.mRoleHeadImg = roleHeadImg;

        this.mRule = new Rule();
    }

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
        int range = mRule.getExperienceRange(this);

        if (this.mRoleExperience + mRoleExperience > range) {
            // Upgrade and reset exp.
            upgrade();
            this.mRoleExperience = this.mRoleExperience + mRoleExperience - range;
        }

    }

    public void setRoleHeadImg(byte[] img) {
        this.mRoleHeadImg = img;
    }

    public byte[] getRoleHeadImg() {
        return this.mRoleHeadImg;
    }

    public String getRoleState() {
        return mRoleState;
    }

    public void setRoleState(String roleState) {
        this.mRoleState = roleState;
    }

    */
/*
  Upgrade level.
 *//*

    private void upgrade() {
        if (this.mRoleLevel.equals(GameRole.ROLE_LEVEL_ROOKIE))
            this.mRoleLevel = ROLE_LEVEL_POSITIVE;

        else if (this.mRoleLevel.equals(GameRole.ROLE_LEVEL_POSITIVE))
            this.mRoleLevel = ROLE_LEVEL_JUNIOR;

        else if (this.mRoleLevel.equals(GameRole.ROLE_LEVEL_JUNIOR))
            this.mRoleLevel = ROLE_LEVEL_INTERMEDIATE;

        else if (this.mRoleLevel.equals(GameRole.ROLE_LEVEL_INTERMEDIATE))
            this.mRoleLevel = ROLE_LEVEL_SENIOR;

        else if (this.mRoleLevel.equals(GameRole.ROLE_LEVEL_SENIOR))
            this.mRoleLevel = ROLE_LEVEL_MASTER;

        else if (this.mRoleLevel.equals(GameRole.ROLE_LEVEL_MASTER))
            this.mRoleLevel = ROLE_LEVEL_GURU;

        else if (this.mRoleLevel.equals(GameRole.ROLE_LEVEL_GURU)) {
            this.mRoleLevel = ROLE_LEVEL_GURU;
        }
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof GameRole)
            return ((GameRole) obj).getRoleName().equals(mRoleName);

        return false;
    }

}
*/
