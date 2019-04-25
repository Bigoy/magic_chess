package com.tssss.bysj.game.im;

import com.tssss.bysj.mvp.ILifeCircle;
import com.tssss.bysj.mvp.IMvpView;
import com.tssss.bysj.mvp.MvpController;

import java.util.List;

public interface IChatContract {

    interface IView extends IMvpView {
        void showHistory(List<ChatMessage> messageList);
    }

    interface IPresenter extends ILifeCircle {
        void loadChatHistory(String targetAccountID);
    }


    IView emptyView = new IView() {
        @Override
        public void showHistory(List<ChatMessage> messageList) {

        }

        @Override
        public MvpController getMvpController() {
            return null;
        }
    };
}
