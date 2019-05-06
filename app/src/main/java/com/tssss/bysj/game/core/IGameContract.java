package com.tssss.bysj.game.core;

import com.tssss.bysj.game.core.other.GameResult;
import com.tssss.bysj.mvp.ILifeCircle;
import com.tssss.bysj.mvp.IMvpView;
import com.tssss.bysj.mvp.MvpController;

import cn.jpush.im.android.api.event.MessageEvent;

public interface IGameContract {

    interface IView extends IMvpView {
        void prepareGame();

        void start(boolean isFirst);

        void syncChessmanPosition(String chessmanKey, String position);

        void turnMe();

        void result(String chessmanKey, String position, GameResult gameResult);

        void isNotFirst();

        void surrender(GameResult gameResult);

        void showChessmanCamp(String camp);

        /**
         * 被对方催促
         */
        void urge();

        void peace();

        void stepBack();

        void timer(int time);

        void showArmyInfo(String armyName);

        void error();

    }

    interface IPresenter extends ILifeCircle {
        void prepareGame(String myAccountID, String armyAccountID);

        void handlerJMessageEvent(MessageEvent event);

        void surrender();

        void cancelAndResetTimer();

        void urge();

        void stepBack();

    }

    IView emptyView = new IView() {
        @Override
        public void prepareGame() {

        }

        @Override
        public void start(boolean isFirst) {

        }

        @Override
        public void syncChessmanPosition(String chessmanKey, String position) {

        }

        @Override
        public void turnMe() {

        }

        @Override
        public void result(String chessmanKey, String position, GameResult gameResult) {

        }

        @Override
        public void isNotFirst() {

        }

        @Override
        public void surrender(GameResult gameResult) {

        }

        @Override
        public void showChessmanCamp(String camp) {

        }

        @Override
        public void urge() {

        }

        @Override
        public void peace() {

        }

        @Override
        public void stepBack() {

        }

        @Override
        public void timer(int time) {

        }

        @Override
        public void showArmyInfo(String armyName) {

        }

        @Override
        public void error() {

        }

        @Override
        public MvpController getMvpController() {
            return null;
        }
    };

}
