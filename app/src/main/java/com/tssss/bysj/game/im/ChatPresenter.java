package com.tssss.bysj.game.im;

import android.os.Handler;

import com.tssss.bysj.mvp.base.BaseMvpPresenter;

public class ChatPresenter extends BaseMvpPresenter<IChatContract.IView> implements IChatContract.IPresenter {

    private Handler handler;

    public ChatPresenter(IChatContract.IView view) {
        super(view);
        handler = new Handler();
    }

    @Override
    public void loadChatHistory(String targetAccountID) {
        // 从本地数据库加载聊天历史记录
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getEmptyView().showHistory(null);
            }
        }, 1000);
    }

    @Override
    protected IChatContract.IView getEmptyView() {
        return IChatContract.emptyView;
    }
}
