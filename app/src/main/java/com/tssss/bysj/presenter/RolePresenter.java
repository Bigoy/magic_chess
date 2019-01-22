package com.tssss.bysj.presenter;

import com.tssss.bysj.contract.PresenterImp;
import com.tssss.bysj.interfaces.OnCreateRoleListener;
import com.tssss.bysj.interfaces.OnUploadRoleDataListener;
import com.tssss.bysj.model.RoleModel;
import com.tssss.bysj.user.role.GameRole;
import com.tssss.bysj.user.role.GameRoleManager;

public class RolePresenter extends PresenterImp implements OnUploadRoleDataListener {
    private RoleModel mModel;
    private OnCreateRoleListener mListener;

    private GameRole mNewRole;

    public RolePresenter() {
        mModel = new RoleModel();
    }

    public void requestCreateRole(GameRole gameRole, OnCreateRoleListener listener) {
        this.mListener = listener;

        mNewRole = gameRole;
        mModel.uploadRoleData(mNewRole, this);
    }

    @Override
    public void onSuccess() {
        GameRoleManager roleManager = GameRoleManager.getGameRoleManager();
        roleManager.addRole(GameRoleManager.SELF, mNewRole);

        mListener.onSuccess();
    }

    @Override
    public void onFail() {
        mListener.onFail();
    }
}
