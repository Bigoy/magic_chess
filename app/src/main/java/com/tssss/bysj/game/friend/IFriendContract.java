package com.tssss.bysj.game.friend;

import com.tssss.bysj.game.core.Role;
import com.tssss.bysj.mvp.ILifeCircle;
import com.tssss.bysj.mvp.IMvpView;
import com.tssss.bysj.mvp.MvpController;

import java.util.List;

public interface IFriendContract {

    interface IView extends IMvpView {
        void showFriend(List<Role> roleList);

    }

    interface IPresenter extends ILifeCircle {
        void loadFriendList();
    }

    IView emptyView = new IView() {
        @Override
        public void showFriend(List<Role> roleList) {

        }

        @Override
        public MvpController getMvpController() {
            return null;
        }
    };
}
