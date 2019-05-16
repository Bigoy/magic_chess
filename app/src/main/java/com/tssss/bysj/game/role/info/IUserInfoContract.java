package com.tssss.bysj.game.role.info;

import com.tssss.bysj.game.core.other.GameRole;
import com.tssss.bysj.mvp.ILifeCircle;
import com.tssss.bysj.mvp.IMvpView;
import com.tssss.bysj.mvp.MvpController;

public interface IUserInfoContract {

    interface IView extends IMvpView{
        void showInfo(GameRole role);
    }

    interface IPresenter extends ILifeCircle{
        void loadUserInfo(String accountID);
    }

    IView emptyView = new IView() {
        @Override
        public void showInfo(GameRole role) {

        }

        @Override
        public MvpController getMvpController() {
            return null;
        }
    };
}
