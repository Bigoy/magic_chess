package com.tssss.bysj.game.friend;

import android.os.Handler;

import com.alibaba.fastjson.JSON;
import com.tssss.bysj.game.core.other.GameRole;
import com.tssss.bysj.mvp.base.BaseMvpPresenter;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.other.jmessage.JMessageHelper;
import com.tssss.bysj.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.callback.GetUserInfoListCallback;
import cn.jpush.im.android.api.model.UserInfo;

public class FriendPresenter extends BaseMvpPresenter<IFriendContract.IView> implements IFriendContract.IPresenter {
    private Handler handler;


    public FriendPresenter(IFriendContract.IView view) {
        super(view);
        handler = new Handler();
    }

    @Override
    public void loadFriendList() {
        List<GameRole> gameRoleList = new ArrayList<>();
        ContactManager.getFriendList(new GetUserInfoListCallback() {
            @Override
            public void gotResult(int i, String s, List<UserInfo> list) {
                if (i == 0) {
                    for (int b = 0; b < list.size(); b++) {
                        UserInfo userInfo = list.get(b);
                        GameRole role = JMessageHelper.toGameRole(userInfo);
                        gameRoleList.add(role);
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            getView().showFriend(gameRoleList);
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
    }

    @Override
    protected IFriendContract.IView getEmptyView() {
        return IFriendContract.emptyView;
    }
}
