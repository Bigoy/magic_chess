package com.tssss.bysj.game.role;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.componet.GTextView;
import com.tssss.bysj.game.core.GameRole;
import com.tssss.bysj.game.role.fragment.battle_record.BattleRecordFragment;
import com.tssss.bysj.game.role.fragment.NewsFragment;
import com.tssss.bysj.game.role.fragment.SignatureFragment;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.other.Logger;
import com.tssss.bysj.user.User;
import com.tssss.bysj.user.UserDataCache;
import com.tssss.bysj.util.AnimationUtil;
import com.tssss.bysj.util.StringUtil;

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
    private GTextView exp;
    private GTextView signature;
    private GTextView battleRecord;
    private GTextView news;

    String userAccount;
    GameRole gameRole;

    private SignatureFragment signatureFragment;
    private BattleRecordFragment battleRecordFragment;
    private NewsFragment newsFragment;

    public String getUserAccount() {
        return this.userAccount;

    }

    @Override
    protected void findViews() {
        avatar = findViewById(R.id.user_info_avatar);
        name = findViewById(R.id.user_info_name);
        sex = findViewById(R.id.user_info_sex);
        level = findViewById(R.id.user_info_level);
        exp = findViewById(R.id.user_info_exp);
        signature = findViewById(R.id.user_info_signature);
        battleRecord = findViewById(R.id.user_info_battle_record);
        news = findViewById(R.id.user_info_news);
    }

    @Override
    protected void setEventListeners() {
        signature.setOnClickListener(this);
        battleRecord.setOnClickListener(this);
        news.setOnClickListener(this);
    }

    @Override
    protected void afterBindView() {
        Intent intent = getIntent();
        userAccount = intent.getStringExtra(Constant.ACCOUNT_ID);
        loadUserInfo();

        signatureFragment = new SignatureFragment();
        startColorAnimation(signature);
        replaceFragment(R.id.user_info_fragment_container, signatureFragment);
        battleRecordFragment = new BattleRecordFragment();
        newsFragment = new NewsFragment();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.user_info_signature:
                startColorAnimation(signature);
                replaceFragment(R.id.user_info_fragment_container, signatureFragment);
                break;
            case R.id.user_info_battle_record:
                startColorAnimation(battleRecord);
                replaceFragment(R.id.user_info_fragment_container, battleRecordFragment);
                break;
            case R.id.user_info_news:
                startColorAnimation(news);
                replaceFragment(R.id.user_info_fragment_container, newsFragment);
                break;
        }
    }

    private void startColorAnimation(GTextView view) {
        initCategoryTabState();
        AnimationUtil.startBackgroundColorAnimator(view);
    }

    private void initCategoryTabState() {
        signature.setTextColor(0xFF7E561B);
        battleRecord.setTextColor(0xFF7E561B);
        news.setTextColor(0xFF7E561B);
        signature.setBackgroundColor(0x00ffffff);
        battleRecord.setBackgroundColor(0x00ffffff);
        news.setBackgroundColor(0x00ffffff);
    }

    @SuppressLint("SetTextI18n")
    private void showUserInfo() {
        if (null != gameRole) {
            Glide.with(UserInfoActivity.this)
                    .load(gameRole.getAvatarFile())
                    .into(avatar);
            name.setText(gameRole.getName());
            sex.setText(gameRole.getSex());
            String levelStr = gameRole.getLevel();
            String expStr = String.valueOf(gameRole.getRoleExperience());
            level.setText(levelStr);
            if (Constant.ROLE_SX_I.equals(levelStr)) {
                exp.setText(expStr + " / 50");

            } else if (Constant.ROLE_SX_II.equals(levelStr)) {
                exp.setText(expStr + " / 100");


            } else if (Constant.ROLE_SX_III.equals(levelStr)) {
                exp.setText(expStr + " / 150");


            } else if (Constant.ROLE_ZSQS_I.equals(levelStr)) {
                exp.setText(expStr + " / 100");

            } else if (Constant.ROLE_ZSQS_II.equals(levelStr)) {
                exp.setText(expStr + " / 200");

            } else if (Constant.ROLE_ZSQS_III.equals(levelStr)) {
                exp.setText(expStr + " / 300");

            } else if (Constant.ROLE_LSJ_I.equals(levelStr)) {
                exp.setText(expStr + " / 120");

            } else if (Constant.ROLE_LSJ_II.equals(levelStr)) {
                exp.setText(expStr + " / 240");

            } else if (Constant.ROLE_LSJ_III.equals(levelStr)) {
                exp.setText(expStr + " / 360");

            } else if (Constant.ROLE_SQDL_I.equals(levelStr)) {
                exp.setText(expStr + " / 150");

            } else if (Constant.ROLE_SQDL_II.equals(levelStr)) {
                exp.setText(expStr + " / 300");

            } else if (Constant.ROLE_SQDL_III.equals(levelStr)) {
                exp.setText(expStr + " / 450");

            } else if (Constant.ROLE_DS_I.equals(levelStr)) {
                exp.setText(expStr + " / 200");

            } else if (Constant.ROLE_DS_II.equals(levelStr)) {
                exp.setText(expStr + " / 400");

            } else if (Constant.ROLE_DS_III.equals(levelStr)) {
                exp.setText(expStr + " / 600");

            } else if (Constant.ROLE_DJQS_I.equals(levelStr)) {
                exp.setText(expStr + " / 300");

            } else if (Constant.ROLE_DJQS_II.equals(levelStr)) {
                exp.setText(expStr + " / 600");

            } else if (Constant.ROLE_DJQS_III.equals(levelStr)) {
                exp.setText(expStr + " / 900");

            }
        }
    }

    private void loadUserInfo() {
        if (!StringUtil.isBlank(userAccount)) {
            gameRole = new GameRole();
            gameRole.setUser(new User(userAccount, null));
            JMessageClient.getUserInfo(userAccount, new GetUserInfoCallback() {
                @SuppressLint("SetTextI18n")
                @Override
                public void gotResult(int i, String s, UserInfo userInfo) {
                    if (i == 0) {
                        Map<String, String> userInfoMap = (Map<String, String>) JSON.parse(userInfo.getSignature());
                        if (null != userInfoMap) {
                            gameRole.setName(userInfoMap.get(Constant.ROLE_NICK_NAME));
                            gameRole.setAvatarFile(userInfo.getAvatarFile());
                            gameRole.setSex(userInfoMap.get(Constant.ROLE_SEX));
                            gameRole.setRoleExperience(Integer.valueOf(userInfoMap.get(Constant.ROLE_EXP)));
                            gameRole.setLevel(userInfoMap.get(Constant.ROLE_LEVEL));
                            gameRole.setSignature(userInfoMap.get(Constant.ROLE_SIGNATURE));
                            showUserInfo();
                            Logger.log(userInfo.getSignature());
                        }

                    }
                }
            });
        }
    }

    @Override
    protected int getTopBarCenterViewStyle() {
        return R.drawable.user_info_title;

    }

}
