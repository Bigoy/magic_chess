package com.tssss.bysj.game.friend;

import android.content.Context;
import android.os.Handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tssss.bysj.game.core.GameRole;
import com.tssss.bysj.game.im.JMessageManager;
import com.tssss.bysj.http.HttpCallback;
import com.tssss.bysj.http.HttpUrl;
import com.tssss.bysj.http.OkHttpProvider;
import com.tssss.bysj.mvp.base.BaseMvpPresenter;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.other.Logger;
import com.tssss.bysj.user.User;
import com.tssss.bysj.user.UserDataCache;
import com.tssss.bysj.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.callback.GetUserInfoListCallback;
import cn.jpush.im.android.api.model.UserInfo;

public class AddFriendPresenter extends BaseMvpPresenter<IAddFriendContract.IView>
        implements IAddFriendContract.IPresenter {

    private boolean cancel;
    private Handler handler;
    private Context context;

    public AddFriendPresenter(Context context, IAddFriendContract.IView view) {
        super(view);
        this.context = context;
        handler = new Handler();
    }

    @Override
    public void loadRecommendRoles() {
        Map<String, Integer> paramMap = new HashMap<>();
        paramMap.put("start", 0);
        // 默认加载五个推荐玩家
        paramMap.put("count", 100);
        ContactManager.getFriendList(new GetUserInfoListCallback() {
            @Override
            public void gotResult(int i, String s, List<UserInfo> list) {
                if (i == 0) {
                    List<String> accountIDList = new ArrayList<>();
                    for (int x = 0; x < list.size(); x++) {
                        accountIDList.add(list.get(x).getUserName());

                    }

                    // 请求app用户列表
                    OkHttpProvider.getInstance().requestAsyncGet(HttpUrl.URL_IM_USER, paramMap, new HttpCallback() {
                        @Override
                        public void onSuccess(String result) {

                            List<GameRole> recommendRoleList = new ArrayList<>();
                            if (!cancel) {
                                Logger.log(result);
                                JSONObject resultJson = JSON.parseObject(result);
                                JSONArray usersJsonArray = resultJson.getJSONArray("users");
                                GameRole role;
                                String accountID;
                                for (int i = 0; i < usersJsonArray.size(); i++) {
                                    JSONObject roleJson = JSON.parseObject(usersJsonArray.getJSONObject(i).getString("signature"));
                                    role = new GameRole();
                                    accountID = usersJsonArray.getJSONObject(i).getString("username");
                                    role.setUser(new User(accountID, null));
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
                                    // 玩家不能是自己也不能已经是好友
                                    if (!accountID.equals(UserDataCache.readAccount(Constant.ACCOUNT_ID))) {
                                        if (!accountIDList.contains(accountID)) {
                                            recommendRoleList.add(role);

                                        }
                                    }

                                }

                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        getView().showRecommendRoles(recommendRoleList);

                                    }
                                });

                            } else {
                                Logger.log("操作取消，丢弃数据");

                            }
                        }

                        @Override
                        public void onFailure(String errorMsg) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    getView().showRecommendRoles(null);
                                }
                            });
                        }
                    });


                }
            }
        });


    }

    @Override
    public void sendAddFriendRequest(String targetUserAccountID) {
        JMessageManager.addFriend(targetUserAccountID, new AddFriendCallBackHandler(context));
    }

    @Override
    protected IAddFriendContract.IView getEmptyView() {
        return IAddFriendContract.emptyView;

    }
}
