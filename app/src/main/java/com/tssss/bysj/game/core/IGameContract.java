package com.tssss.bysj.game.core;

import com.tssss.bysj.game.core.other.GameResult;
import com.tssss.bysj.mvp.ILifeCircle;
import com.tssss.bysj.mvp.IMvpView;
import com.tssss.bysj.mvp.MvpController;

import java.util.Map;

public interface IGameContract {

    interface IView extends IMvpView {
        void prepareGame();

        void start(boolean isFirst);

        void syncChessmanPosition(String chessmanKey, String position);

        void turnMe();

        void result(GameResult gameResult);

        void isNotFirst();

        void showMyChessmenCamp(String camp);

        void beingUrged();

        void peace();

        void stepBack();

        void timer(int time);

        void showArmyInfo(String armyName);

        void error();

        void peaceReject(String s);

        void stopDrawing();

    }

    interface IPresenter extends ILifeCircle {
        void prepareGame(String myAccountID, String armyAccountID);

        void startGame();

        void adversityReady();

        /**
         * 正在游戏
         */
        void cancelAndResetTimer();

        void urge();

        void beingUrged();

        void stepBack();

        void adversityStepBack();

        void checkResult();

        void updateChessmanPosition(Map<String, String> positionMap);

        /**
         * 游戏顺序
         */

        void notFirst();

        void turn();

        /**
         * 游戏结果
         */
        void surrender();

        void adversitySurrender();

        void win();

        void lose();

        void peace();

        void adversityPeace();

        void peaceGranted();

        void peaceDenied();

        void adversityGrantedPeace();

        void adversityDeniedPeace();

    }

    /**
     * 空view，防止null pointer
     */
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
        public void result(GameResult gameResult) {

        }

        @Override
        public void isNotFirst() {

        }

        @Override
        public void showMyChessmenCamp(String camp) {

        }

        @Override
        public void beingUrged() {

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
        public void peaceReject(String s) {

        }

        @Override
        public void stopDrawing() {

        }

        @Override
        public MvpController getMvpController() {
            return null;
        }
    };

}
