package com.tssss.bysj.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.tssss.bysj.R;
import com.tssss.bysj.contract.BaseActivity;
import com.tssss.bysj.contract.PresenterImp;
import com.tssss.bysj.interfaces.OnCreateRoleListener;
import com.tssss.bysj.presenter.RolePresenter;
import com.tssss.bysj.user.role.GameRole;
import com.tssss.bysj.util.StringUtil;
import com.tssss.bysj.util.ToastUtil;

import androidx.annotation.Nullable;

/**
 * The user fill in the various attributes of role
 * when creates a new game role({@link com.tssss.bysj.user.role.GameRole})
 *
 * @author Tssss
 * @date 2019-1-21
 */
public class NewRoleActivity extends BaseActivity implements OnCreateRoleListener {
    public static int CODE_LOAD_IMG = 0;

    private EditText mNameEt;
    private ImageButton mHeadBoyIb,
            mHeadGirlIb, mCreateRoleIb, mAddImgsIb;

    private GameRole mNewRole = new GameRole();
    private RolePresenter mPresenter;

    @Override
    protected void findViews() {
        mNameEt = findViewById(R.id.new_role_name_et);
        mHeadBoyIb = findViewById(R.id.new_role_sex_boy_ib);
        mHeadGirlIb = findViewById(R.id.new_role_sex_girl_ib);
        mAddImgsIb = findViewById(R.id.new_role_add_imgs_ib);
        mCreateRoleIb = findViewById(R.id.new_role_create_role_ib);
    }

    @Override
    protected void setEventListeners() {
        mNameEt.setOnClickListener(this);
        mHeadBoyIb.setOnClickListener(this);
        mHeadGirlIb.setOnClickListener(this);
        mAddImgsIb.setOnClickListener(this);
        mCreateRoleIb.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_role;
    }

    @Override
    protected int getCenterIvStyle() {
        return R.drawable.role_title;
    }

    @Override
    protected PresenterImp attachPresenter() {
        mPresenter = new RolePresenter();
        return mPresenter;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.new_role_sex_boy_ib:
                boy();
                break;
            case R.id.new_role_sex_girl_ib:
                girl();
                break;
            case R.id.new_role_add_imgs_ib:
                addImgs();
                break;
            case R.id.new_role_create_role_ib:
                createNewRole();
                break;
        }
    }

    /**
     * Create game role
     */
    private void createNewRole() {
        if (StringUtil.isBlank(mNameEt.getText().toString())) {
            ToastUtil.showToast(this, getString(R.string.new_role_null_name),
                    ToastUtil.TOAST_ERROR);
        } else if (StringUtil.isBlank(mNewRole.getRoleSex())) {
            ToastUtil.showToast(this, getString(R.string.new_role_null_sex),
                    ToastUtil.TOAST_ERROR);
        } else {
            mNewRole.setRoleName(mNameEt.getText().toString());
            mNewRole.setRoleExperience(0);
            mNewRole.setRoleLevel(GameRole.ROLE_LEVEL_ROOKIE);

            mPresenter.requestCreateRole(mNewRole, this);
        }
    }

    /**
     * Initialize sex
     */
    private void initSex() {
        mHeadBoyIb.setImageResource(R.drawable.sex_boy);
        mHeadGirlIb.setImageResource(R.drawable.sex_girl);
    }

    /**
     * Sex is boy
     */
    private void boy() {
        initSex();
        mHeadBoyIb.setImageResource(R.drawable.sex_boy_checked);

        mNewRole.setRoleSex(GameRole.ROLE_SEX_BOY);
    }

    /**
     * Sex is girl
     */
    private void girl() {
        initSex();
        mHeadGirlIb.setImageResource(R.drawable.sex_girl_checked);

        mNewRole.setRoleSex(GameRole.ROLE_SEX_GIRL);
    }

    /**
     * Add pictures from storage
     */
    private void addImgs() {
    }

    @Override
    public void onSuccess() {
        openActivity(HallActivity.class);
        finish();
    }

    @Override
    public void onFail() {
        ToastUtil.showToast(this, getString(R.string.error), ToastUtil.TOAST_ERROR);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
