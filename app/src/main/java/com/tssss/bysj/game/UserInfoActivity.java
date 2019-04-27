package com.tssss.bysj.game;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.componet.GTextView;
import com.tssss.bysj.other.Constant;

import java.util.Map;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;

@ViewInject(layoutId = R.layout.activity_user_info)
public class UserInfoActivity extends BaseActivity {
    private ImageView avatar;
    private GTextView name;
    private GTextView sex;
    private GTextView level;
    private GTextView signature;
    private ScrollView container;
    private GTextView error;

    @Override
    protected void findViews() {
        avatar = findViewById(R.id.user_info_avatar);
        name = findViewById(R.id.user_info_name);
        sex = findViewById(R.id.user_info_sex);
        level = findViewById(R.id.user_info_level);
        signature = findViewById(R.id.user_info_signature);
        container = findViewById(R.id.user_info_container);
        error = findViewById(R.id.user_info_error);
    }

    @Override
    protected void setEventListeners() {
    }

    @Override
    protected void afterBindView() {
        Intent intent = getIntent();
        try {
            JMessageClient.getUserInfo(intent.getStringExtra(Constant.ACCOUNT_ID), new GetUserInfoCallback() {
                @Override
                public void gotResult(int i, String s, UserInfo userInfo) {
                    if (i == 0) {
                        Glide.with(UserInfoActivity.this)
                                .load(userInfo.getAvatarFile())
                                .into(avatar);
                        Map<String, String> map = (Map<String, String>) JSON.parse(userInfo.getSignature());
                        name.setText(map.get(Constant.ROLE_NICK_NAME));
                        sex.setText(map.get(Constant.ROLE_SEX));
                        signature.setText(map.get(Constant.ROLE_SIGNATURE));
                        signature.setText(map.get(Constant.ROLE_LEVEL));
                    }
                }
            });

        } catch (Exception e) {
            loadError();
        }


    }

    @Override
    protected int getTopBarCenterViewStyle() {
        return R.drawable.user_info_title;
    }

    private void loadError() {
        container.setVisibility(View.GONE);
        error.setVisibility(View.VISIBLE);
    }

}
