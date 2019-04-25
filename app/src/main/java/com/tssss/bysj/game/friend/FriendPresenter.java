package com.tssss.bysj.game.friend;

import android.os.Handler;

import com.tssss.bysj.game.core.Role;
import com.tssss.bysj.mvp.base.BaseMvpPresenter;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.user.User;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.callback.GetUserInfoListCallback;
import cn.jpush.im.android.api.model.UserInfo;

public class FriendPresenter extends BaseMvpPresenter<IFriendContract.IView> implements IFriendContract.IPresenter {
    private boolean cancelLoad;
    private Handler handler;


    public FriendPresenter(IFriendContract.IView view) {
        super(view);
        handler = new Handler();
    }

    @Override
    public void loadFriendList() {
        List<Role> roleList = new ArrayList<>();
        ContactManager.getFriendList(new GetUserInfoListCallback() {
            @Override
            public void gotResult(int i, String s, List<UserInfo> list) {
                if (i == 0) {
                    for (int b = 0; b < list.size(); b++) {
                        roleList.add(new Role(
                                new User(list.get(b).getUserName(), null),
                                list.get(b).getExtra(Constant.ROLE_AVATAR),
                                list.get(b).getExtra(Constant.ROLE_NICK_NAME),
                                list.get(b).getExtra(Constant.ROLE_SEX),
                                list.get(b).getExtra(Constant.ROLE_SIGNATURE),
                                list.get(b).getExtra(Constant.ROLE_LEVEL)
                        ));

                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            getView().showFriend(roleList);
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            getView().showFriend(null);
                        }
                    });

                }
            }
        });


        /*Map<String, String> paramMap = new HashMap<>();
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
        });*/
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
