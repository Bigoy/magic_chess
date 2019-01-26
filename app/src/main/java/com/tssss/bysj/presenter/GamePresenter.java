package com.tssss.bysj.presenter;

import com.tssss.bysj.contract.PresenterImp;
import com.tssss.bysj.interfaces.OnGameListener;
import com.tssss.bysj.user.role.GameRoleManager;

public class GamePresenter extends PresenterImp {

    public GamePresenter() {
    }

    public void requestRolesInfo(OnGameListener listener) {

        GameRoleManager gameRoleManager = GameRoleManager.getGameRoleManager();

        if (gameRoleManager.getRole(GameRoleManager.RIVAL) != null)
            listener.onComplete(gameRoleManager.getRole(GameRoleManager.SELF),
                    gameRoleManager.getRole(GameRoleManager.RIVAL));

        else
            listener.onFailure();
    }
}
