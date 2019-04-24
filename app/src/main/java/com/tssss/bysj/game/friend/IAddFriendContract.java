package com.tssss.bysj.game.friend;

import com.tssss.bysj.game.core.Role;
import com.tssss.bysj.mvp.ILifeCircle;
import com.tssss.bysj.mvp.IMvpView;
import com.tssss.bysj.mvp.MvpController;

import java.util.List;

public interface IAddFriendContract {
    interface IView extends IMvpView {
        void showRecommendRoles(List<Role> recommendRoles);

        void showRequesting();

        void showRequestSucceed();

        void showRequestFailed();
    }

    interface IPresenter extends ILifeCircle{
        void loadRecommendRoles();

        void sendAddFriendRequest(String targetUserAccountID);
    }

    IView emptyView = new IView() {
        @Override
        public void showRecommendRoles(List<Role> recommendRoles) {

        }

        @Override
        public void showRequesting() {

        }

        @Override
        public void showRequestSucceed() {

        }

        @Override
        public void showRequestFailed() {

        }

        @Override
        public MvpController getMvpController() {
            return null;
        }
    };
}
