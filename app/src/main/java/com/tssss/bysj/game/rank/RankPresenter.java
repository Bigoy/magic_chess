package com.tssss.bysj.game.rank;

import android.content.Context;
import android.os.Handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tssss.bysj.game.core.other.GameRole;
import com.tssss.bysj.http.HttpCallback;
import com.tssss.bysj.http.HttpUrl;
import com.tssss.bysj.http.OkHttpProvider;
import com.tssss.bysj.mvp.base.BaseMvpPresenter;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.other.Logger;
import com.tssss.bysj.user.User;

import java.util.HashMap;
import java.util.Map;

import androidx.recyclerview.widget.SortedList;

public class RankPresenter extends BaseMvpPresenter<IRankContract.IView> implements IRankContract.IPresenter {
    private Handler handler;
    private RankSortedListCallBack rankSortedListCallBack;
    private RankAdapter rankAdapter;
    private RankAdapter.RankViewHolder.OnRankItemClickListener onRankItemClickListener;
    private boolean viewDestroyed;


    public RankPresenter(Context context, IRankContract.IView view) {
        super(view);
        handler = new Handler();
        rankAdapter = new RankAdapter(context, null);
        rankSortedListCallBack = new RankSortedListCallBack(rankAdapter);
    }

    public void setOnRankItemClickListener(RankAdapter.RankViewHolder.OnRankItemClickListener onRankItemClickListener) {
        this.onRankItemClickListener = onRankItemClickListener;

    }

    @Override
    public void loadRankData() {
        Map<String, Integer> paramMap = new HashMap<>();
        paramMap.put("startTimer", 0);
        paramMap.put("count", 100);
        SortedList<Rank> rankSortedList = new SortedList<>(Rank.class, rankSortedListCallBack);
        OkHttpProvider.getInstance().requestAsyncGet(HttpUrl.URL_IM_USER, paramMap, new HttpCallback() {
            @Override
            public void onSuccess(String result) {
                Logger.log(result);
                JSONObject resultJson = JSON.parseObject(result);
                JSONArray usersJsonArray = resultJson.getJSONArray("users");
                GameRole role;
                for (int i = 0; i < usersJsonArray.size(); i++) {
                    JSONObject roleJson = JSON.parseObject(usersJsonArray.getJSONObject(i).getString("signature"));
                    role = new GameRole();
                    role.setUser(new User(usersJsonArray.getJSONObject(i).getString("username"), null));
                    role.setName(roleJson.getString(Constant.ROLE_NICK_NAME));
                    role.setSex(roleJson.getString(Constant.ROLE_SEX));
                    role.setSignature(roleJson.getString(Constant.ROLE_SIGNATURE));
                    role.setLevel(roleJson.getString(Constant.ROLE_LEVEL));

                    try {
                        role.setRoleExperience(Integer.getInteger(roleJson.getString(Constant.ROLE_EXP)));
                        role.setScore(Integer.getInteger(roleJson.getString(Constant.ROLE_SCORE)));

                    } catch (Exception e) {
                        role.setRoleExperience(0);
                        role.setScore(0);

                    }
                    rankSortedList.add(new Rank(role, i));
                }

                Logger.log("获取到的玩家个数" + rankSortedList.size());

                rankAdapter.setRankSortedList(rankSortedList);
                rankAdapter.setOnRankItemClickListener(onRankItemClickListener);
                if (!viewDestroyed) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            getView().showRank(rankAdapter);

                        }
                    });
                }

            }

            @Override
            public void onFailure(String errorMsg) {
                Logger.log(errorMsg);
                if (!viewDestroyed) {
                    handler.post(() -> getView().loadError());

                }
            }
        });
    }

    @Override
    public void onViewDestroy() {
        super.onViewDestroy();
        viewDestroyed = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewDestroyed = true;
    }

    @Override
    public void destroyView() {
        super.destroyView();
        viewDestroyed = true;
    }

    @Override
    protected IRankContract.IView getEmptyView() {
        return IRankContract.emptyView;
    }
}
