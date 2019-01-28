package com.tssss.bysj.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.tssss.bysj.R;
import com.tssss.bysj.contract.BaseActivity;
import com.tssss.bysj.contract.PresenterImp;
import com.tssss.bysj.game.GameHelper;
import com.tssss.bysj.game.GameScene;
import com.tssss.bysj.interfaces.OnGameListener;
import com.tssss.bysj.presenter.GamePresenter;
import com.tssss.bysj.user.role.GameRole;
import com.tssss.bysj.util.ToastUtil;
import com.tssss.bysj.widget.GTextView;

import androidx.annotation.Nullable;

public class GameActivity extends BaseActivity implements View.OnTouchListener, OnGameListener {
    private GameScene mGameScene;
    private ImageView mHeadImgIv,
            mHeadImg02Iv,
            mSexIv,
            mSex02Iv;
    private GTextView mNameGtv,
            mName02Gtv,
            mLevelGtv,
            mLevel02Gtv;

    private GamePresenter mPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter.requestRolesInfo(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        GameHelper.getGameHelper().setContext(getContext());
    }

    @Override
    protected void findViews() {
        mGameScene = findViewById(R.id.game_gsv);
        mHeadImgIv = findViewById(R.id.game_role_head_img_iv);
        mHeadImg02Iv = findViewById(R.id.game_role_02_head_img_iv);
        mSexIv = findViewById(R.id.game_role_sex_iv);
        mSex02Iv = findViewById(R.id.game_role_02_sex_iv);
        mNameGtv = findViewById(R.id.game_role_name_gtv);
        mName02Gtv = findViewById(R.id.game_role_02_name_gtv);
        mLevelGtv = findViewById(R.id.game_role_level_gtv);
        mLevel02Gtv = findViewById(R.id.game_role_02_level_gtv);
    }

    @Override
    protected PresenterImp attachPresenter() {
        mPresenter = new GamePresenter();
        return mPresenter;
    }

    @Override
    protected void setEventListeners() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_game;
    }

    @Override
    protected boolean isFullScreen() {
        return true;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mGameScene.doTouch(event);
        return false;
    }

    @Override
    public void onComplete(GameRole self, GameRole rival) {
        showRoleInfo(self, rival);
    }

    @Override
    public void onFailure() {
        ToastUtil.showToast(this, getString(R.string.error), ToastUtil.TOAST_ERROR);
    }

    private void showRoleInfo(GameRole self, GameRole rival) {
        // Self
//        mHeadImgIv.setImageBitmap(BitmapUtil.toBitmap(self.getRoleHeadImg()));
        mNameGtv.setText(self.getRoleName());
        mLevelGtv.setText(self.getRoleLevel());

        if (self.getRoleSex().equals(GameRole.ROLE_SEX_BOY))
            mSexIv.setImageResource(R.drawable.sex_boy);
        else if (self.getRoleSex().equals(GameRole.ROLE_SEX_GIRL))
            mSexIv.setImageResource(R.drawable.sex_girl);

        if (self.getRoleLevel().equals(GameRole.ROLE_LEVEL_ROOKIE))
            mLevelGtv.setBackgroundResource(R.drawable.bg_role_level_rookie);
        else if (self.getRoleLevel().equals(GameRole.ROLE_LEVEL_POSITIVE))
            mLevelGtv.setBackgroundResource(R.drawable.bg_role_level_positive);
        else if (self.getRoleLevel().equals(GameRole.ROLE_LEVEL_JUNIOR))
            mLevelGtv.setBackgroundResource(R.drawable.bg_role_level_junior);
        else if (self.getRoleLevel().equals(GameRole.ROLE_LEVEL_INTERMEDIATE))
            mLevelGtv.setBackgroundResource(R.drawable.bg_role_level_intermediate);
        else if (self.getRoleLevel().equals(GameRole.ROLE_LEVEL_SENIOR))
            mLevelGtv.setBackgroundResource(R.drawable.bg_role_level_senior);
        else if (self.getRoleLevel().equals(GameRole.ROLE_LEVEL_MASTER))
            mLevelGtv.setBackgroundResource(R.drawable.bg_role_level_master);
        else if (self.getRoleLevel().equals(GameRole.ROLE_LEVEL_GURU))
            mLevelGtv.setBackgroundResource(R.drawable.bg_role_level_guru);

        // Rival
//        mHeadImg02Iv.setImageBitmap(BitmapUtil.toBitmap(rival.getRoleHeadImg()));
        mName02Gtv.setText(rival.getRoleName());
        mLevel02Gtv.setText(rival.getRoleLevel());

        if (rival.getRoleSex().equals(GameRole.ROLE_SEX_BOY))
            mSex02Iv.setImageResource(R.drawable.sex_boy);
        else if (rival.getRoleSex().equals(GameRole.ROLE_SEX_GIRL))
            mSex02Iv.setImageResource(R.drawable.sex_girl);

        if (rival.getRoleLevel().equals(GameRole.ROLE_LEVEL_ROOKIE))
            mLevel02Gtv.setBackgroundResource(R.drawable.bg_role_level_rookie);
        else if (rival.getRoleLevel().equals(GameRole.ROLE_LEVEL_POSITIVE))
            mLevel02Gtv.setBackgroundResource(R.drawable.bg_role_level_positive);
        else if (rival.getRoleLevel().equals(GameRole.ROLE_LEVEL_JUNIOR))
            mLevel02Gtv.setBackgroundResource(R.drawable.bg_role_level_junior);
        else if (rival.getRoleLevel().equals(GameRole.ROLE_LEVEL_INTERMEDIATE))
            mLevel02Gtv.setBackgroundResource(R.drawable.bg_role_level_intermediate);
        else if (rival.getRoleLevel().equals(GameRole.ROLE_LEVEL_SENIOR))
            mLevel02Gtv.setBackgroundResource(R.drawable.bg_role_level_senior);
        else if (rival.getRoleLevel().equals(GameRole.ROLE_LEVEL_MASTER))
            mLevel02Gtv.setBackgroundResource(R.drawable.bg_role_level_master);
        else if (rival.getRoleLevel().equals(GameRole.ROLE_LEVEL_GURU))
            mLevel02Gtv.setBackgroundResource(R.drawable.bg_role_level_guru);

    }
}
