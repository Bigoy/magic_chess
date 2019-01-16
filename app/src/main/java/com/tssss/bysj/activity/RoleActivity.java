package com.tssss.bysj.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.tssss.bysj.R;
import com.tssss.bysj.annotation.ContentView;
import com.tssss.bysj.contract.BaseActivity;
import com.tssss.bysj.contract.PresenterImp;
import com.tssss.bysj.interfaces.OnCreateRoleListener;
import com.tssss.bysj.presenter.RolePresenter;
import com.tssss.bysj.user.role.GameRole;
import com.tssss.bysj.util.StringUtil;
import com.tssss.bysj.util.ToastUtil;

@ContentView(R.layout.activity_role)
public class RoleActivity extends BaseActivity implements OnCreateRoleListener {
    private ImageButton mCreateRoleIb, mHomeIb;
    private ImageView mHeadBoyAIv, mHeadBoyBIv, mHeadGirlAIv, mHeadGirlBIv, mSexBoyIv, mSexGirlIv;
    private EditText mNameEt;

    private GameRole mGameRole;
    private RolePresenter mPresenter;

    @Override
    protected void onStart() {
        super.onStart();
        newObject();
    }

    @Override
    protected PresenterImp attachPresenter() {
        mPresenter = new RolePresenter();
        return mPresenter;
    }

    @Override
    protected void findViews() {
        /*mHeadBoyAIv = findViewById(R.id.role_head_boy_01_iv);
        mHeadBoyBIv = findViewById(R.id.role_head_boy_02_iv);
        mHeadGirlAIv = findViewById(R.id.role_head_girl_01_iv);
        mHeadGirlBIv = findViewById(R.id.role_head_girl_02_iv);
        mSexBoyIv = findViewById(R.id.role_sex_boy_iv);
        mSexGirlIv = findViewById(R.id.role_sex_girl_iv);
        mNameEt = findViewById(R.id.role_name_et);
        mHomeIb = findViewById(R.id.role_home_ib);
        mCreateRoleIb = findViewById(R.id.role_create_ib);*/
    }

    @Override
    protected void setEventListeners() {
        /*mHeadBoyAIv.setOnClickListener(this);
        mHeadBoyBIv.setOnClickListener(this);
        mHeadGirlAIv.setOnClickListener(this);
        mHeadGirlBIv.setOnClickListener(this);
        mSexBoyIv.setOnClickListener(this);
        mSexGirlIv.setOnClickListener(this);
        mHomeIb.setOnClickListener(this);
        mCreateRoleIb.setOnClickListener(this);*/
    }

   /* @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.role_home_ib:
                openActivity(StartActivity.class);
                break;
            case R.id.role_create_ib:
                createRole();
                break;
            case R.id.role_head_boy_01_iv:
                initHead();
                mHeadBoyAIv.setImageResource(R.drawable.head_boy_01_checked);
                mGameRole.setHead(R.drawable.head_boy_01);
                break;
            case R.id.role_head_boy_02_iv:
                initHead();
                mHeadBoyBIv.setImageResource(R.drawable.head_boy_02_checked);
                mGameRole.setHead(R.drawable.head_boy_02);
                break;
            case R.id.role_head_girl_01_iv:
                initHead();
                mHeadGirlAIv.setImageResource(R.drawable.head_girl_01_checked);
                mGameRole.setHead(R.drawable.head_girl_01);
                break;
            case R.id.role_head_girl_02_iv:
                initHead();
                mHeadGirlBIv.setImageResource(R.drawable.head_girl_02_cheched);
                mGameRole.setHead(R.drawable.head_girl_02);
                break;
            case R.id.role_sex_boy_iv:
                initSex();
                mSexBoyIv.setImageResource(R.drawable.sex_boy_checked);
                mGameRole.setSex(R.drawable.sex_boy);
                break;
            case R.id.role_sex_girl_iv:
                initSex();
                mSexGirlIv.setImageResource(R.drawable.sex_girl_checked);
                mGameRole.setSex(R.drawable.sex_girl);
                break;
        }
    }

    private void initHead() {
        mHeadBoyAIv.setImageResource(R.drawable.head_boy_01);
        mHeadBoyBIv.setImageResource(R.drawable.head_boy_02);
        mHeadGirlAIv.setImageResource(R.drawable.head_girl_01);
        mHeadGirlBIv.setImageResource(R.drawable.head_girl_02);
    }

    private void initSex() {
        mSexBoyIv.setImageResource(R.drawable.sex_boy);
        mSexGirlIv.setImageResource(R.drawable.sex_girl);
    }*/

    private void createRole() {
        String playerName = mNameEt.getText().toString();

        if (StringUtil.empty(playerName)) {
            mGameRole.setRoleName(StringUtil.filterString(playerName, 4));
            mPresenter.requestCreateRole(mGameRole, this);
        } else {
            ToastUtil.showToast(this, "昵称不能空！", ToastUtil.TOAST_ERROR);
        }
    }

    private void newObject() {
        mGameRole = new GameRole();
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFail() {

    }
}
