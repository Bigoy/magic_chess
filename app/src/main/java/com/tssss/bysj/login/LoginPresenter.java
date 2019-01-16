package com.tssss.bysj.login;

import com.tssss.bysj.contract.PresenterImp;

public class LoginPresenter extends PresenterImp implements OnLoginDataListener {
    public static String USER_NOT_EXIT = "user_not_exit",
            USER_PASSWORD_WRONG = "user_password_wrong",
            USER_VALID = "user_valid";

    private LoginModel mModel;
    private OnLoginListener mLoginListener;

    public LoginPresenter() {
        mModel = new LoginModel();
    }

    public void requestLogin(long userId, String password, OnLoginListener listener) {
        mLoginListener = listener;
        mModel.loadUserData(userId, password, this);
    }

    @Override
    public void onLoadDataCompleted(com.alibaba.fastjson.JSONObject userJson) {
        mLoginListener.onLoginSuccess();

        /*try {
            String userState = userJson.getString(Constant.JSON_KEY_USER_STATE);

            if (userState.equals(USER_NOT_EXIT)) {
                mLoginListener.onUserNotExit();
            } else if (userState.equals(USER_PASSWORD_WRONG)) {
                mLoginListener.onUserPasswordError();
            } else if (userState.equals(USER_VALID)) {
                JSONObject gameRoleJO = userJson.getJSONObject(Constant.JSON_KEY_GAME_ROLE);

                GameRole self = new GameRole();
                self.setHead(gameRoleJO.getInt(Constant.JSON_KEY_ROLE_HEAD));
                self.setName(gameRoleJO.getString(Constant.JSON_KEY_ROLE_NAME));
                self.setSex(gameRoleJO.getInt(Constant.JSON_KEY_ROLE_SEX));
                self.setLevel(gameRoleJO.getInt(Constant.JSON_KEY_ROLE_LEVEL));
                self.setExperience(gameRoleJO.getInt(Constant.JSON_KEY_ROLE_EXPERIENCE));

                GameRoleManager grm = GameRoleManager.getGameRoleManager();
                grm.addPlayer(GameRoleManager.SELF, self);

                mLoginListener.onLoginSuccess();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            mLoginListener.onLoginError();
        }*/
    }

    @Override
    public void onUserNotExit() {
        mLoginListener.onUserNotExit();
    }

    @Override
    public void onUserPasswordError() {
        mLoginListener.onUserPasswordError();
    }

    @Override
    public void onFailure() {
        mLoginListener.onLoginError();
    }
}
