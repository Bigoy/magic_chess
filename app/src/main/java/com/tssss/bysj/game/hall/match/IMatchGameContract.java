package com.tssss.bysj.game.hall.match;

import com.tssss.bysj.game.core.other.GameRole;
import com.tssss.bysj.mvp.ILifeCircle;
import com.tssss.bysj.mvp.IMvpView;
import com.tssss.bysj.mvp.MvpController;

public interface IMatchGameContract {
    IView empty = new IView() {
        @Override
        public void onMatchPlayerSuccess(GameRole role) {

        }

        @Override
        public void onMatchPlayerFailure(String errorMsg) {

        }

        @Override
        public MvpController getMvpController() {
            return null;
        }
    };

    interface IView extends IMvpView {
        void onMatchPlayerSuccess(GameRole role);

        void onMatchPlayerFailure(String errorMsg);
    }

    interface IPresenter extends ILifeCircle {
        void matchPlayer();
    }
}
