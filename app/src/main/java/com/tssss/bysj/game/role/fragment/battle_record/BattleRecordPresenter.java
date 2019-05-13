package com.tssss.bysj.game.role.fragment.battle_record;

import android.os.Handler;

import com.tssss.bysj.http.HttpUrl;
import com.tssss.bysj.http.IHttpCallback;
import com.tssss.bysj.http.OkHttpProvider;
import com.tssss.bysj.mvp.base.BaseMvpPresenter;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BattleRecordPresenter extends BaseMvpPresenter<IBattleRecordContract.IView>
        implements IBattleRecordContract.IPresent {

    private Handler handler;
    private boolean isCancel;


    public BattleRecordPresenter(IBattleRecordContract.IView view) {
        super(view);
        handler = new Handler();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isCancel = true;
    }

    @Override
    public void onViewDestroy() {
        super.onViewDestroy();
        isCancel = true;
    }

    @Override
    public void loadBattleRecord(String accountID) {
        if (!StringUtil.isBlank(accountID)) {
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put(Constant.ACCOUNT_ID, accountID);
            OkHttpProvider.getInstance().requestAsyncGet(HttpUrl.URL_BATTLE_RECORD, paramMap, new IHttpCallback() {
                @Override
                public void onSuccess(String result) {
                    if (!isCancel) {
                        List<BattleRecord> battleRecordList = new ArrayList<>();
//                        battleRecordList.add(new BattleRecord(Constant.GAME_RESULT_WIN, "15196071596", "2019-05-01"));
                        getView().showBattleRecord(battleRecordList);

                    }
                }

                @Override
                public void onFailure(String errorMsg) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (!isCancel) {
                                getView().showBattleRecord(null);

                            }
                        }
                    });
                }
            });


        } else {
            throw new IllegalArgumentException("accountID非法");
        }

    }

    @Override
    protected IBattleRecordContract.IView getEmptyView() {
        return IBattleRecordContract.emptyView;
    }
}
