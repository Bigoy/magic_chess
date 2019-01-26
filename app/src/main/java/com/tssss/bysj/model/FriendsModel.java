package com.tssss.bysj.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tssss.bysj.interfaces.OnFriendsDataListener;
import com.tssss.bysj.net.http.HttpConstant;
import com.tssss.bysj.net.http.IDataListener;
import com.tssss.bysj.net.http.JsonHttpRequest;
import com.tssss.bysj.net.http.JsonHttpResponse;
import com.tssss.bysj.net.http.TaoHttpClient;
import com.tssss.bysj.user.User;
import com.tssss.bysj.user.role.GameRole;

import java.util.ArrayList;
import java.util.List;

public class FriendsModel implements IDataListener {
    private OnFriendsDataListener mListener;


    public void loadFriendsData(User user, OnFriendsDataListener listener) {
        mListener = listener;

        if (user == null) {
            mListener.onFailure();
        }

        TaoHttpClient client = new TaoHttpClient(
                HttpConstant.BASE_URL,
                user,
                new JsonHttpRequest(),
                new JsonHttpResponse(this));
        client.request();
    }

    /**
     * @param data user json.
     */
    @Override
    public void onSuccess(Object data) {
        List<GameRole> friends = new ArrayList<>();

        JSONObject userJson = (JSONObject) data;
        JSONArray friendsJa = userJson.getJSONArray("friends");

        for (int i = 0; i < friendsJa.size(); i++) {
            friends.add(new GameRole(
                    friendsJa.getJSONObject(i).getString("roleName"),
                    friendsJa.getJSONObject(i).getString("roleSex"),
                    friendsJa.getJSONObject(i).getString("roleLevel"),
                    friendsJa.getJSONObject(i).getString("roleState"),
                    friendsJa.getJSONObject(i).getInteger("roleExperience"),
                    friendsJa.getJSONObject(i).getString("roleHeadImg").getBytes()
            ));
        }

        mListener.onComplete(friends);
    }

    @Override
    public void onFailure() {
        mListener.onFailure();
    }
}
