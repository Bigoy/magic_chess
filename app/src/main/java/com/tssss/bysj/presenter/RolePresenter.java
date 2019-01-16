package com.tssss.bysj.presenter;

import com.tssss.bysj.contract.PresenterImp;
import com.tssss.bysj.interfaces.OnCreateRoleListener;
import com.tssss.bysj.interfaces.OnUploadRoleDataListener;
import com.tssss.bysj.model.RoleModel;
import com.tssss.bysj.user.role.GameRole;

public class RolePresenter extends PresenterImp implements OnUploadRoleDataListener {
    private RoleModel mModel;
    private OnCreateRoleListener mListener;

    public RolePresenter() {
        mModel = new RoleModel();
    }

    public void requestCreateRole(GameRole gameRole, OnCreateRoleListener listener) {
        this.mListener = listener;
        mModel.uploadRoleData(gameRole, this);
    }

    @Override
    public void onSuccess() {
        mListener.onSuccess();
    }

    @Override
    public void onFail() {
        mListener.onFail();
    }
}
