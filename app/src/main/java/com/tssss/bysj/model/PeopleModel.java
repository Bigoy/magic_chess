package com.tssss.bysj.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tssss.bysj.interfaces.OnPeopleDataListener;
import com.tssss.bysj.net.http.HttpConstant;
import com.tssss.bysj.net.http.IDataListener;
import com.tssss.bysj.net.http.JsonHttpRequest;
import com.tssss.bysj.net.http.JsonHttpResponse;
import com.tssss.bysj.net.http.TaoHttpClient;
import com.tssss.bysj.user.role.GameRole;

import java.util.ArrayList;
import java.util.List;

public class PeopleModel implements IDataListener {
    private OnPeopleDataListener mListener;


    /**
     * Load all registered players.
     */
    public void loadAllPlayers(OnPeopleDataListener listener) {
        mListener = listener;

        TaoHttpClient client = new TaoHttpClient(
                HttpConstant.BASE_URL,
                null,
                new JsonHttpRequest(),
                new JsonHttpResponse(this));
        client.request();
    }

    @Override
    public void onSuccess(Object data) {
        List<GameRole> allPlayers = new ArrayList<>();

        JSONObject peopleJson = (JSONObject) data;
        JSONArray ja = peopleJson.getJSONArray("allPlayers");
        for (int i = 0; i < ja.size(); i++) {
            allPlayers.add(new GameRole.GameRoleBuilder()
                    .setRoleName(ja.getJSONObject(i).getString("roleName"))
                    .setRoleLevel(ja.getJSONObject(i).getString("roleLevel"))
                    .setRoleSex(ja.getJSONObject(i).getString("roleSex"))
                    .setRoleExperience(ja.getJSONObject(i).getInteger("roleExperience"))
                    .setRoleHeadImg(ja.getJSONObject(i).getBytes("roleHeadImg"))
                    .setRoleState(ja.getJSONObject(i).getString("roleState"))
                    .build());
        }

        mListener.onComplete(allPlayers);
    }

    @Override
    public void onFailure() {
        mListener.onFailure();
    }
}
