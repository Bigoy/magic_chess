package com.tssss.bysj.game.role.info.fragment.battle_record;

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

    public BattleRecordPresenter(IBattleRecordContract.IView view) {
        super(view);
        handler = new Handler();
    }

    @Override
    public void loadBattleRecord(String accountID) {
        if (!StringUtil.isBlank(accountID)) {
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put(Constant.ACCOUNT_ID, accountID);
            OkHttpProvider.getInstance().requestAsyncGet(HttpUrl.URL_BATTLE_RECORD, paramMap, new IHttpCallback() {
                @Override
                public void onSuccess(String result) {
                    List<BattleRecord> battleRecordList = new ArrayList<>();
                    getView().showBattleRecord(battleRecordList);
                }

                @Override
                public void onFailure(String errorMsg) {
                    getView().showBattleRecord(null);
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
