package com.tssss.bysj.login;

import com.alibaba.fastjson.JSONObject;
import com.tssss.bysj.net.http.HttpConstant;
import com.tssss.bysj.net.http.IDataListener;
import com.tssss.bysj.net.http.JsonHttpRequest;
import com.tssss.bysj.net.http.JsonHttpResponse;
import com.tssss.bysj.net.http.TaoHttpClient;
import com.tssss.bysj.user.User;
import com.tssss.bysj.user.role.GameRole;

public class LoginModel implements IDataListener {
    public static String USER_NOT_EXIT = "user_not_exit",
            USER_PASSWORD_WRONG = "user_password_wrong",
            USER_VALID = "user_valid";

    private OnLoginDataListener mListener;

    /**
     * Create http task, request login state
     *
     * @param listener callback listener
     */
    void loadUserData(User user, OnLoginDataListener listener) {
        this.mListener = listener;

        TaoHttpClient taoHttpClient = new TaoHttpClient(HttpConstant.BASE_URL, user,
                new JsonHttpRequest(), new JsonHttpResponse(this));
        taoHttpClient.request();
    }

    /**
     * @param data json object (game role)
     */
    @Override
    public void onSuccess(Object data) {
        JSONObject userJson = (JSONObject) data;

        if (userJson.getString("user_state").equals(USER_NOT_EXIT)) {
            mListener.onUserNotExit();
        } else if (userJson.getString("user_state").equals(USER_PASSWORD_WRONG)) {
            mListener.onUserPasswordError();
        } else if (userJson.getString("user_state").equals(USER_VALID)) {
            JSONObject roleJson = userJson.getJSONObject("role");

            GameRole role = new GameRole();
            role.setRoleName(roleJson.getString("role_name"));
            role.setRoleSex(roleJson.getString("role_sex"));
            role.setRoleLevel(roleJson.getString("role_level"));
            role.setRoleExperience(roleJson.getIntValue("role_experience"));

            mListener.onLoadDataCompleted(role);
        }
    }

    @Override
    public void onFailure() {
        mListener.onFailure();
    }
}
