package com.tssss.bysj.game.role.info;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.componet.GTextView;
import com.tssss.bysj.game.core.other.GameRole;
import com.tssss.bysj.game.role.info.fragment.news.NewsFragment;
import com.tssss.bysj.game.role.info.fragment.battle_record.BattleRecordFragment;
import com.tssss.bysj.game.role.info.fragment.battle_record.BattleRecordPresenter;
import com.tssss.bysj.game.role.info.fragment.signature.SignatureFragment;
import com.tssss.bysj.game.role.info.fragment.signature.SignaturePresenter;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.util.AnimationUtil;

@ViewInject(layoutId = R.layout.activity_user_info)
public class UserInfoActivity extends BaseActivity implements
        IUserInfoContract.IView {
    private ImageView avatar;
    private GTextView name;
    private GTextView sex;
    private GTextView level;
    private GTextView exp;
    private GTextView signature;
    private GTextView battleRecord;
    private GTextView news;
    private String userAccount;
    private GameRole gameRole;
    private SignatureFragment signatureFragment;
    private BattleRecordFragment battleRecordFragment;
    private NewsFragment newsFragment;
    private UserInfoPresenter userInfoPresenter;

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
        userInfoPresenter = new UserInfoPresenter(this);
        userInfoPresenter.loadUserInfo(userAccount);
        initFragment();
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

    private void initFragment() {
        startColorAnimation(signature);
        signatureFragment = new SignatureFragment();
        replaceFragment(R.id.user_info_fragment_container, signatureFragment);
        battleRecordFragment = new BattleRecordFragment();
        newsFragment = new NewsFragment();
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

    @Override
    protected int getTopBarCenterViewStyle() {
        return R.drawable.user_info_title;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void showInfo(GameRole role) {
        this.gameRole = role;
        if (null != gameRole) {
            Glide.with(UserInfoActivity.this)
                    .load(gameRole.getAvatarFile())
                    .into(avatar);
            name.setText(gameRole.getName());
            sex.setText(gameRole.getSex());
            String levelStr = gameRole.getLevel();
            String expStr = String.valueOf(gameRole.getExp());
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
                exp.setText(expStr + " / 最高级");

            }
        }
    }

}
