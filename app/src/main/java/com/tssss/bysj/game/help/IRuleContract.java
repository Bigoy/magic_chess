package com.tssss.bysj.game.help;

import android.content.Context;
import android.widget.ImageView;

import com.tssss.bysj.mvp.ILifeCircle;
import com.tssss.bysj.mvp.IMvpView;
import com.tssss.bysj.mvp.MvpController;

import java.util.List;

public interface IRuleContract {
    IView emptyView = new IView() {
        @Override
        public void onShowRules(List<ImageView> rules) {

        }

        @Override
        public MvpController getMvpController() {
            return null;
        }
    };

    interface IView extends IMvpView {
        void onShowRules(List<ImageView> rules);
    }

    interface IPresenter extends ILifeCircle {
        void loadRules(Context context);
    }
}
