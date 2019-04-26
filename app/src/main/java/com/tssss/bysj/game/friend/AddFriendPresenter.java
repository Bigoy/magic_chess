package com.tssss.bysj.game.friend;

import android.os.Handler;

import com.tssss.bysj.game.core.GameRole;
import com.tssss.bysj.http.HttpCallback;
import com.tssss.bysj.http.HttpUrl;
import com.tssss.bysj.http.OkHttpProvider;
import com.tssss.bysj.mvp.base.BaseMvpPresenter;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.other.Logger;
import com.tssss.bysj.user.User;
import com.tssss.bysj.user.UserDataCache;
import com.tssss.bysj.util.StringUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.api.BasicCallback;

public class AddFriendPresenter extends BaseMvpPresenter<IAddFriendContract.IView>
        implements IAddFriendContract.IPresenter {

    private boolean cancel;
    private Handler handler;

    public AddFriendPresenter(IAddFriendContract.IView view) {
        super(view);
        handler = new Handler();
    }

    @Override
    public void loadRecommendRoles() {
        /*JMessageClient.getUserInfo("a16d4094af87e2e8", null, new GetUserInfoCallback() {
            @Override
            public void gotResult(int i, String s, UserInfo userInfo) {
                Logger.log(i + s);
                if (i == 0) {

                }
            }
        });*/
        Map<String, String> paraMap = new HashMap<>();
        paraMap.put(Constant.ACCOUNT_ID, UserDataCache.readAccount(Constant.ACCOUNT_ID));
        OkHttpProvider.getInstance().requestAsyncGet(HttpUrl.URL_FRIEND_ADD_RECOMMEND, paraMap, new HttpCallback() {
            @Override
            public void onSuccess(String result) {
                if (!cancel) {
                    if (!StringUtil.isBlank(result)) {
                        List<GameRole> gameRoleList = new ArrayList<>();
                        try {
                            JSONObject resultJson = new JSONObject(result);

                            JSONArray recommendRoleList = resultJson.getJSONArray(Constant.JSON_KEY_FRIEND_ADD_RECOMMEND);
                            for (int i = 0; i < recommendRoleList.length(); i++) {
                                JSONObject sigleRoleJson = recommendRoleList.getJSONObject(i);
                                gameRoleList.add(new GameRole(
                                        new User(sigleRoleJson.getString(Constant.JSON_KEY_FRIEND_ADD_ID), null),
                                        sigleRoleJson.getString(Constant.JSON_KEY_FRIEND_ADD_AVATAR),
                                        sigleRoleJson.getString(Constant.JSON_KEY_FRIEND_ADD_NAME),
                                        sigleRoleJson.getString(Constant.JSON_KEY_FRIEND_ADD_SEX),
                                        sigleRoleJson.getString(Constant.JSON_KEY_FRIEND_ADD_SIGNATURE),
                                        sigleRoleJson.getString(Constant.JSON_KEY_FRIEND_ADD_LEVEL)
                                ));
                            }

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    getView().showRecommendRoles(gameRoleList);

                                }
                            });

                        } catch (JSONException e) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    getView().showRecommendRoles(null);
                                }
                            });
                        }

                    } else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                getView().showRecommendRoles(null);
                            }
                        });
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
                        getView().showRecommendRoles(null);
                    }
                });
            }
        });


    }

    @Override
    public void sendAddFriendRequest(String targetUserAccountID) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                getView().showRequesting();
            }
        });
        ContactManager.sendInvitationRequest(targetUserAccountID,
                Constant.JMESSAGE_APP_KEY,
                UserDataCache.readAccount("id 为 " + Constant.ACCOUNT_ID) + " 的玩家希望和你成为朋友", new BasicCallback() {
                    @Override
                    public void gotResult(int i, String s) {
                        if (i == 0) {
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    getView().showRequestSucceed();
                                }
                            }, 1000);
                        } else if (i == 898002) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    getView().showNotUser();
                                }
                            });
                        } else if (i == 805002) {
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    getView().showRequestFailed("你们已经是好友了哟");
                                    Logger.log(i + s);
                                }
                            }, 1000);

                        } else {
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    getView().showRequestFailed(s);
                                    Logger.log(i + s);
                                }
                            }, 1000);
                        }
                    }
                });
    }

    @Override
    protected IAddFriendContract.IView getEmptyView() {
        return IAddFriendContract.emptyView;
    }
}
