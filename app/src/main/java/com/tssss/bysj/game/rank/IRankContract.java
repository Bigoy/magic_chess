package com.tssss.bysj.game.rank;

import com.tssss.bysj.mvp.ILifeCircle;
import com.tssss.bysj.mvp.IMvpView;
import com.tssss.bysj.mvp.MvpController;

public interface IRankContract {

    interface IView extends IMvpView {

        void showRank(RankAdapter rankAdapter);

        void loadError();
    }

    interface IPresenter extends ILifeCircle {

        void loadRankData();
    }

    IView emptyView = new IView() {

        @Override
        public void showRank(RankAdapter rankAdapter) {

        }

        @Override
        public void loadError() {

        }

        @Override
        public MvpController getMvpController() {
            return null;
        }
    };
}
