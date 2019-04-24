package com.tssss.bysj.game.friend;

import android.os.Handler;

import com.tssss.bysj.game.core.Role;
import com.tssss.bysj.http.HttpCallback;
import com.tssss.bysj.http.HttpUrl;
import com.tssss.bysj.http.OkHttpProvider;
import com.tssss.bysj.mvp.base.BaseMvpPresenter;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.other.Logger;
import com.tssss.bysj.user.UserDataCache;
import com.tssss.bysj.util.StringUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendPresenter extends BaseMvpPresenter<IFriendContract.IView> implements IFriendContract.IPresenter {
    private boolean cancelLoad;
    private Handler handler;


    public FriendPresenter(IFriendContract.IView view) {
        super(view);
        handler = new Handler();
    }

    @Override
    public void loadFriendList() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put(Constant.ACCOUNT_ID, UserDataCache.readAccount(Constant.ACCOUNT_ID));
        OkHttpProvider.getInstance().requestAsyncGet(HttpUrl.URL_FRIEND, paramMap, new HttpCallback() {
            @Override
            public void onSuccess(String result) {
                if (!cancelLoad) {
                    if (!StringUtil.isBlank(result)) {
                        List<Role> friendList = new ArrayList<>();
                        try {
                            JSONObject resultJson = new JSONObject(result);
                            JSONArray friendListJson = resultJson.getJSONArray(Constant.JSON_KEY_FRIEND_LIST);
                            for (int i = 0; i < friendListJson.length(); i++) {
                                JSONObject singleFriendJson = friendListJson.getJSONObject(i);
                                friendList.add(new Role(
                                        singleFriendJson.getString(Constant.JSON_KEY_FRIEND_AVATAR),
                                        singleFriendJson.getString(Constant.JSON_KEY_FRIEND_NAME),
                                        singleFriendJson.getString(Constant.JSON_KEY_FRIEND_SEX),
                                        singleFriendJson.getString(Constant.JSON_KEY_FRIEND_SIGNATURE),
                                        singleFriendJson.getString(Constant.JSON_KEY_FRIEND_LEVEL)
                                ));

                            }
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    getView().showFriend(friendList);
                                }
                            });
                        } catch (JSONException e) {
                            Logger.log("json解析异常");
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    getView().showFriend(null);
                                }
                            });
                        }

                    }
                } else {
                    Logger.log("操作取消，丢弃数据");
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        getView().showFriend(null);
                    }
                });
            }
        });
    }

    @Override
    public void onViewDestroy() {
        super.onViewDestroy();
        cancelLoad = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelLoad = true;
    }

    @Override
    public void destroyView() {
        super.destroyView();
        cancelLoad = true;
    }

    @Override
    protected IFriendContract.IView getEmptyView() {
        return IFriendContract.emptyView;
    }
}
