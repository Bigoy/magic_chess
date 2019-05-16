package com.tssss.bysj.game.role.info.fragment.signature;

import com.tssss.bysj.game.core.other.GameRole;
import com.tssss.bysj.mvp.ILifeCircle;
import com.tssss.bysj.mvp.IMvpView;
import com.tssss.bysj.mvp.MvpController;

public interface ISignatureContract {

    interface IView extends IMvpView{
        void showSignature(String signature);
    }

    interface IPresenter extends ILifeCircle{
        void loadSignature(String accountID);
    }

    IView emptyView = new IView() {
        @Override
        public void showSignature(String signature) {

        }

        @Override
        public MvpController getMvpController() {
            return null;
        }
    };

}
