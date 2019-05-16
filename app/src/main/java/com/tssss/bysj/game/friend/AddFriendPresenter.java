package com.tssss.bysj.game.friend;

import android.content.Context;
import android.os.Handler;

import com.tssss.bysj.game.core.other.GameRole;
import com.tssss.bysj.mvp.base.BaseMvpPresenter;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.other.jmessage.JMessageHelper;
import com.tssss.bysj.other.jmessage.JMessageManager;
import com.tssss.bysj.other.jmessage.callback.IGetAllFriendsCallBack;
import com.tssss.bysj.other.jmessage.callback.IGetUserInfoCallBack;
import com.tssss.bysj.user.UserDataCache;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.model.UserInfo;

public class AddFriendPresenter extends BaseMvpPresenter<IAddFriendContract.IView>
        implements IAddFriendContract.IPresenter {

    private Handler handler;
    private Context context;

    public AddFriendPresenter(Context context, IAddFriendContract.IView view) {
        super(view);
        this.context = context;
        handler = new Handler();
    }

    @Override
    public void loadRecommendRoles() {
        // 所有的好友
        JMessageHelper.listAllFriends(new IGetAllFriendsCallBack() {
            @Override
            public void onSuccess(List<UserInfo> friendsUserInfoList) {
                List<String> friendsAccountIDList = new ArrayList<>();
                for (int x = 0; x < friendsUserInfoList.size(); x++) {
                    friendsAccountIDList.add(friendsUserInfoList.get(x).getUserName());
                }
                // 所有好友获取成功后，获取100个用户
                JMessageHelper.listAllUserInfo(new IGetUserInfoCallBack() {
                    @Override
                    public void onCompleted(List<UserInfo> userInfoList) {
                        int size = userInfoList.size();
                        if (size <= 0) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    getView().showRecommendRoles(null);
                                }
                            });
                        } else {
                            List<GameRole> recommendRoleList = new ArrayList<>();
                            for (int k = 0; k < size; k++) {
                                UserInfo userInfo = userInfoList.get(k);
                                String accountID = userInfo.getUserName();
                                // 玩家不能是自己也不能已经是好友
                                boolean notMe = !accountID.equals(UserDataCache.readAccount(Constant.ACCOUNT_ID));
                                boolean notFriend = !friendsAccountIDList.contains(accountID);
                                if (notMe && notFriend) {
                                    GameRole role = JMessageHelper.toGameRole(userInfo);
                                    recommendRoleList.add(role);
                                }
                            }
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    getView().showRecommendRoles(recommendRoleList);
                                }
                            });

                        }
                    }
                });

            }

            @Override
            public void onFailure(String errorMsg) {

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
