package com.tssss.bysj.game.role.fragment;

import com.alibaba.fastjson.JSON;
import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseFragment;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.componet.GTextView;
import com.tssss.bysj.game.role.UserInfoActivity;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.util.StringUtil;

import java.util.Map;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;

@ViewInject(layoutId = R.layout.fragment_signature)
public class SignatureFragment extends BaseFragment {
    private GTextView signature;
    private UserInfoActivity activity;
    private String accountID;
    private boolean destroyed;

    @Override
    protected void findViews() {
        signature = (GTextView) findViewById(R.id.fragment_signature);
    }

    @Override
    protected void afterBindView() {
        activity = (UserInfoActivity) getActivity();
        accountID = activity.getUserAccount();
        if (!StringUtil.isBlank(accountID)) {
            JMessageClient.getUserInfo(accountID, new GetUserInfoCallback() {
                @Override
                public void gotResult(int i, String s, UserInfo userInfo) {
                    if (!destroyed) {
                        if (i == 0) {
                            Map<String, String> map = (Map<String, String>) JSON.parse(userInfo.getSignature());
                            signature.setText(map.get(Constant.ROLE_SIGNATURE));
                        }
                    }
                }
            });


        } else {
            signature.setText("这家伙懒得什么都没有写...");
        }

    }

    @Override
    protected void setEventListeners() {

    }
}
