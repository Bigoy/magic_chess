package com.tssss.bysj.game.friend;

import com.tssss.bysj.game.core.other.GameRole;
import com.tssss.bysj.mvp.ILifeCircle;
import com.tssss.bysj.mvp.IMvpView;
import com.tssss.bysj.mvp.MvpController;

import java.util.List;

public interface IAddFriendContract {
    interface IView extends IMvpView {
        void showRecommendRoles(List<GameRole> recommendGameRoles);

        void showRequesting();

        void showRequestSucceed();

        void showRequestFailed(String s);

        void showNotUser();
    }

    interface IPresenter extends ILifeCircle{
        void loadRecommendRoles();

        void sendAddFriendRequest(String targetUserAccountID);
    }

    IView emptyView = new IView() {
        @Override
        public void showRecommendRoles(List<GameRole> recommendGameRoles) {

        }

        @Override
        public void showRequesting() {

        }

        @Override
        public void showRequestSucceed() {

        }

        @Override
        public void showRequestFailed(String s) {

        }

        @Override
        public void showNotUser() {

        }

        @Override
        public MvpController getMvpController() {
            return null;
        }
    };
}
