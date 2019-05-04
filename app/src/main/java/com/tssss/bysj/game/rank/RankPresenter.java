package com.tssss.bysj.game.rank;

import android.content.Context;
import android.os.Handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tssss.bysj.game.core.GameRole;
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


    public RankPresenter(Context context, IRankContract.IView view) {
        super(view);
        handler = new Handler();
        rankAdapter = new RankAdapter(context, null);
        rankSortedListCallBack = new RankSortedListCallBack(rankAdapter);
    }

    @Override
    public void loadRankData() {
        Map<String, Integer> paramMap = new HashMap<>();
        paramMap.put("start", 0);
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
                    role.setAvatarStr(usersJsonArray.getJSONObject(i).getString("avatar"));
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
                handler.post(() -> getView().showRank(rankAdapter));

//                if (roleList.size() > 0) {
//                    // 已经排好序的列表
//                    List<GameRole> orderedList = new ArrayList<>();
//                    // 积分相同
//                    List<GameRole> sameScoreList = new ArrayList<>();
//
//                    for (int j = 0; j < roleList.size() - 1; j++) {
//                        for (int o = 0; o < roleList.size() - j - 1; o++) {
//                            int temp;
//                            int oScore = roleList.get(o).getScore();
//                            int o1Score = roleList.get(o + 1).getScore();
//                            if (oScore > o1Score) {
//                                orderedList.add(roleList.get(o + 1));
//
//                            } else if (oScore < o1Score) {
//                                orderedList.add(roleList.get(o));
//
//                            } else if (oScore == o1Score) {
//                                // 积分相同的 加入到 sameScoreList 等待其它策略排序
//                                if (!sameScoreList.contains(roleList.get(o))) {
//                                    sameScoreList.add(roleList.get(o));
//
//                                }
//                            }
//
//                        }
//                    }
//
//                    // 判断是否有没有排序的对象   积分相同  比较手机号码
//                    if (sameScoreList.size() > 0) {
//                        for (int x = 0; x < sameScoreList.size() - 1; x++) {
//                            for (int z = 0; z < sameScoreList.size() - x - 1; z++) {
//                                long temp;
//                                long oID = Long.valueOf(sameScoreList.get(z).getUser().getUserId());
//                                long o1ID = Long.valueOf(sameScoreList.get(z + 1).getUser().getUserId());
//                                if (oID > o1ID) {
//                                    if (!orderedList.contains(sameScoreList.get(z + 1))) {
//                                        orderedList.add(sameScoreList.get(z + 1));
//                                    }
//
//                                }
//
//                            }
//                        }
//                    }
//
//
//                    // 将最终全部排好序的列表显示在界面上
//                    List<Rank> finalOrderRankList = new ArrayList<>();
//                    for (int g = 0; g < orderedList.size(); g++) {
//                        // 排序从 1 开始， 所以 g + 1
//                        finalOrderRankList.add(new Rank(orderedList.get(g), g + 1));
//
//                    }
//
//                    if (finalOrderRankList.size() > 0) {
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                // 第一名
//                                getView().showOne(finalOrderRankList.get(0).getRole());
//                                // 第二名
//                                getView().showOne(finalOrderRankList.get(1).getRole());
//                                // 第三名
//                                getView().showOne(finalOrderRankList.get(2).getRole());
//                                // 以后的玩家
//                                getView().showRank(finalOrderRankList);
//                            }
//                        });
//
//                    } else {
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                getView().loadError();
//                            }
//                        });
//
//                    }
//                }
            }

            @Override
            public void onFailure(String errorMsg) {
                Logger.log("加载失败" + errorMsg);
                handler.post(() -> getView().loadError());
            }
        });
    }

    @Override
    protected IRankContract.IView getEmptyView() {
        return IRankContract.emptyView;
    }
}
