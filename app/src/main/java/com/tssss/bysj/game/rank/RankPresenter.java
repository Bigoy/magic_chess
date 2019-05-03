package com.tssss.bysj.game.rank;

import android.os.Handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tssss.bysj.game.core.GameRole;
import com.tssss.bysj.http.HttpCallback;
import com.tssss.bysj.http.HttpUrl;
import com.tssss.bysj.http.OkHttpProvider;
import com.tssss.bysj.mvp.base.BaseMvpPresenter;
import com.tssss.bysj.other.Logger;

import java.util.ArrayList;
import java.util.List;

public class RankPresenter extends BaseMvpPresenter<IRankContract.IView> implements IRankContract.IPresenter {
    private Handler handler;


    public RankPresenter(IRankContract.IView view) {
        super(view);
        handler = new Handler();
    }

    @Override
    public void loadRankData() {
        OkHttpProvider.getInstance().requestAsyncGet(HttpUrl.URL_RANK, null, new HttpCallback() {
            @Override
            public void onSuccess(String result) {
                /*List<Rank> rankList = new ArrayList<>();
                JSONArray rankListJson = JSON.parseArray("rankList");
                GameRole role;
                int rankNum;
                for (int i = 0; i < rankListJson.size(); i++) {
                    JSONArray singleJson = (JSONArray) rankListJson.get(i);
                    role = (GameRole) JSON.parse(JSON.toJSONString(singleJson.getJSONObject(0)));
                    JSONObject numJson = (JSONObject) singleJson.get(1);
                    rankNum = numJson.getIntValue("rankNum");

                    if (rankNum == 1) {
                        GameRole finalRole = role;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                getView().showOne(finalRole);
                            }
                        });
                    } else if (rankNum == 2) {
                        GameRole finalRole = role;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                getView().showTwo(finalRole);
                            }
                        });
                    } else if (rankNum == 3) {
                        GameRole finalRole = role;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                getView().showThree(finalRole);
                            }
                        });

                    } else {
                        // 从第四名开始添加
                        rankList.add(new Rank(role, rankNum));
                    }
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        getView().showOther(rankList);
                    }
                });*/


            }

            @Override
            public void onFailure(String errorMsg) {
                Logger.log("加载失败" + errorMsg);
            }
        });
    }

    @Override
    protected IRankContract.IView getEmptyView() {
        return IRankContract.emptyView;
    }
}
