package com.tssss.bysj.game.rank;

import com.tssss.bysj.game.core.GameRole;
import com.tssss.bysj.mvp.ILifeCircle;
import com.tssss.bysj.mvp.IMvpView;
import com.tssss.bysj.mvp.MvpController;

import java.util.List;

public interface IRankContract {

    interface IView extends IMvpView {

        void showOne(GameRole role);

        void showTwo(GameRole role);

        void showThree(GameRole role);

        void showOther(List<Rank> rankList);
    }

    interface IPresenter extends ILifeCircle {

        void loadRankData();
    }

    IView emptyView = new IView() {
        @Override
        public void showOne(GameRole role) {

        }

        @Override
        public void showTwo(GameRole role) {

        }

        @Override
        public void showThree(GameRole role) {

        }

        @Override
        public void showOther(List<Rank> rankList) {

        }

        @Override
        public MvpController getMvpController() {
            return null;
        }
    };
}
