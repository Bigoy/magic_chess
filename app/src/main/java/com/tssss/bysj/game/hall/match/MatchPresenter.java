package com.tssss.bysj.game.hall.match;

import android.os.Handler;

import com.tssss.bysj.mvp.base.BaseMvpPresenter;

public class MatchPresenter extends BaseMvpPresenter<IMatchGameContract.IView>
        implements IMatchGameContract.IPresenter {

    public MatchPresenter(IMatchGameContract.IView view) {
        super(view);
    }

    @Override
    public void matchPlayer() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getView().onMatchPlayerFailure("暂无玩家可匹配");
            }
        }, 2000);
    }

    @Override
    protected IMatchGameContract.IView getEmptyView() {
        return IMatchGameContract.empty;
    }
}
