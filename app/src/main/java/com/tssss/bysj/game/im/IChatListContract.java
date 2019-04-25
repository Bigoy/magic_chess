package com.tssss.bysj.game.im;

import com.tssss.bysj.mvp.ILifeCircle;
import com.tssss.bysj.mvp.IMvpView;
import com.tssss.bysj.mvp.MvpController;

import java.util.List;

public interface IChatListContract {

    interface IView extends IMvpView {
        void showNullList();

        void showChatList(List<Conversation> chatList);

        void showLoadError(String errorMsg);
    }

    interface IPresenter extends ILifeCircle {
        void loadChatList(String id);
    }

    IView emptyView = new IView() {
        @Override
        public void showNullList() {

        }

        @Override
        public void showChatList(List<Conversation> chatList) {

        }

        @Override
        public void showLoadError(String errorMsg) {

        }

        @Override
        public MvpController getMvpController() {
            return null;
        }
    };
}
