package com.tssss.bysj.model;

import com.tssss.bysj.interfaces.OnUploadRoleDataListener;
import com.tssss.bysj.net.http.HttpConstant;
import com.tssss.bysj.net.http.IDataListener;
import com.tssss.bysj.net.http.JsonHttpRequest;
import com.tssss.bysj.net.http.JsonHttpResponse;
import com.tssss.bysj.net.http.TaoHttpClient;
import com.tssss.bysj.user.role.GameRole;

public class RoleModel implements IDataListener {
    private OnUploadRoleDataListener mListener;

    public void uploadRoleData(GameRole gameRole, OnUploadRoleDataListener listener) {
        this.mListener = listener;

        TaoHttpClient client = new TaoHttpClient(
                HttpConstant.BASE_URL,
                gameRole,
                new JsonHttpRequest(),
                new JsonHttpResponse(this));
        client.request();
    }

    @Override
    public void onSuccess(Object data) {
        mListener.onSuccess();
    }

    @Override
    public void onFailure() {
        mListener.onFail();
    }
}
