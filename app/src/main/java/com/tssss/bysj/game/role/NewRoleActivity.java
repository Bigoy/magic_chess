package com.tssss.bysj.game.role;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;

import com.alibaba.fastjson.JSON;
import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.componet.GTextView;
import com.tssss.bysj.componet.dialog.AlertDialog;
import com.tssss.bysj.game.core.other.GameRole;
import com.tssss.bysj.game.hall.HallActivity;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.other.Logger;
import com.tssss.bysj.user.User;
import com.tssss.bysj.user.UserDataCache;
import com.tssss.bysj.util.StringUtil;
import com.tssss.bysj.util.ToastUtil;
import com.wildma.pictureselector.PictureSelector;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

@ViewInject(layoutId = R.layout.activity_role)
public class NewRoleActivity extends BaseActivity {
    private ImageButton addImg;
    private GTextView man;
    private GTextView woman;
    private EditText nickNameEt;
    private EditText signatureEt;
    private ImageButton create;

    private Handler handler;
    private boolean validName;
    private boolean canUpdateInfo;

    private GameRole oldRole;
    private Map<String, String> newestRoleInfoMap;
    private String name;
    private File avatarFile;
    private String sex;
    private String signature;
    private String level;
    private int exp;
    private int score;


    @Override
    protected void findViews() {
        addImg = findViewById(R.id.new_role_add_avatar);
        man = findViewById(R.id.new_role_sex_man);
        woman = findViewById(R.id.new_role_sex_woman);
        nickNameEt = findViewById(R.id.new_role_input_nick_name);
        signatureEt = findViewById(R.id.new_role_input_signature);
        create = findViewById(R.id.new_role_create);
    }

    @Override
    protected void setEventListeners() {
        addImg.setOnClickListener(this);
        man.setOnClickListener(this);
        woman.setOnClickListener(this);
        create.setOnClickListener(this);
    }

    @Override
    protected void afterBindView() {
        newestRoleInfoMap = new HashMap<>();
        handler = new Handler();
        loadOldRoleInfo();

    }


    /**
     * 加载更新前的角色信息
     */
    private void loadOldRoleInfo() {
        oldRole = new GameRole();
        UserInfo oldUserInfo = JMessageClient.getMyInfo();
        if (null == oldUserInfo) {
            createErrorRole();
            return;

        }
        Map<String, String> oldUserInfoMap = (Map<String, String>) JSON.parse(oldUserInfo.getSignature());
        // 所有信息都有
        try {
            oldRole.setUser(new User(oldUserInfo.getUserName(), null));
            oldRole.setAvatarFile(oldUserInfo.getAvatarFile());
            oldRole.setName(oldUserInfoMap.get(Constant.ROLE_NICK_NAME));
            oldRole.setSex(oldUserInfoMap.get(Constant.ROLE_SEX));
            oldRole.setSignature(oldUserInfoMap.get(Constant.ROLE_SIGNATURE));
            oldRole.setLevel(oldUserInfoMap.get(Constant.ROLE_LEVEL));
            oldRole.setRoleExperience(Integer.valueOf(oldUserInfoMap.get(Constant.ROLE_EXP)));
            oldRole.setScore(Integer.valueOf(oldUserInfoMap.get(Constant.ROLE_SCORE)));

        } catch (Exception e) {
            e.printStackTrace();
            createErrorRole();

        }
    }

    /**
     * 创建容错角色
     */
    private void createErrorRole() {
        oldRole.setUser(new User(UserDataCache.readAccount(Constant.ACCOUNT_ID), null));
        oldRole.setAvatarFile(null);
        oldRole.setName(null);
        oldRole.setSex(null);
        oldRole.setSignature(null);
        oldRole.setLevel(null);
        oldRole.setRoleExperience(-1);
        oldRole.setScore(-1);

    }

    @Override
    protected void clickTopBarCenter() {
        ScrollView scrollView = findViewById(R.id.new_role_scroll);
        scrollView.fullScroll(View.FOCUS_UP);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.new_role_sex_man:
                initSex();
                man.setBackgroundResource(R.drawable.sex_man_select);
                sex = Constant.ROLE_SEX_MAN;
                break;
            case R.id.new_role_sex_woman:
                initSex();
                woman.setBackgroundResource(R.drawable.sex_woman_select);
                sex = Constant.ROLE_SEX_WOMAN;
                break;
            case R.id.new_role_add_avatar:
                choiceImage();
                break;
            case R.id.new_role_create:
                uploadNewestRoleInfo();
                break;
            default:
        }
    }

    private void initSex() {
        man.setBackgroundResource(R.drawable.sex_man);
        woman.setBackgroundResource(R.drawable.sex_woman);
    }

    /**
     * 上传账户信息
     */
    private void uploadMyUser() {
        newestRoleInfoMap.put(Constant.ACCOUNT_ID, JMessageClient.getMyInfo().getUserName());
        newestRoleInfoMap.put(Constant.ACCOUNT_PASSWORD, "");

    }

    /**
     * 检查、上传头像文件
     */
    private void uploadAvatar() {
        if (null != avatarFile) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // 用户选择了新头像
                    JMessageClient.updateUserAvatar(avatarFile, new BasicCallback() {
                        @Override
                        public void gotResult(int i, String s) {
                            if (i != 0) {
                                Logger.log(i + s);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtil.showToast(NewRoleActivity.this, "头像更新失败", ToastUtil.TOAST_ERROR);
                                    }
                                });
                            }
                        }
                    });
                }
            }).start();

        }
    }

    /**
     * 检查、判断昵称信息
     */
    private void uploadName() {
        name = nickNameEt.getText().toString();
        if (StringUtil.isBlank(name)) {
            name = oldRole.getName();
            if (StringUtil.isBlank(name)) {
                validName = false;
                ToastUtil.showToast(this, "昵称不能为空白", ToastUtil.TOAST_ERROR);

            } else {
                validName = true;
                newestRoleInfoMap.put(Constant.ROLE_NICK_NAME, name);

            }
        } else {
            validName = true;
            newestRoleInfoMap.put(Constant.ROLE_NICK_NAME, name);

        }
    }

    /**
     * 检查、上传性别信息
     */
    private void uploadSex() {
        if (StringUtil.isBlank(sex)) {
            sex = oldRole.getSex();
            if (StringUtil.isBlank(sex)) {
                sex = Constant.ROLE_SEX_SECRET;
                newestRoleInfoMap.put(Constant.ROLE_SEX, sex);

            } else {
                newestRoleInfoMap.put(Constant.ROLE_SEX, sex);

            }
        } else {
            newestRoleInfoMap.put(Constant.ROLE_SEX, sex);

        }

    }

    /**
     * 检查、上传签名
     */
    private void uploadSignature() {
        signature = signatureEt.getText().toString();
        if (StringUtil.isBlank(signature)) {
            signature = oldRole.getSignature();
            if (StringUtil.isBlank(signature)) {
                signature = "这家伙懒得什么都没有写...";
                newestRoleInfoMap.put(Constant.ROLE_SIGNATURE, signature);

            } else {
                newestRoleInfoMap.put(Constant.ROLE_SIGNATURE, signature);

            }
        } else {
            newestRoleInfoMap.put(Constant.ROLE_SIGNATURE, signature);

        }

    }

    /**
     * 上传level
     */
    private void uploadLevel() {
        level = oldRole.getLevel();
        if (StringUtil.isBlank(level)) {
            level = Constant.ROLE_SX_I;
            newestRoleInfoMap.put(Constant.ROLE_LEVEL, level);

        } else {
            newestRoleInfoMap.put(Constant.ROLE_LEVEL, level);

        }

    }

    /**
     * 上传经验值
     */
    private void uploadExp() {
        exp = oldRole.getRoleExperience();
        if (exp == -1) {
            exp = 0;
            newestRoleInfoMap.put(Constant.ROLE_EXP, String.valueOf(exp));

        } else {
            newestRoleInfoMap.put(Constant.ROLE_EXP, String.valueOf(exp));

        }

    }

    /**
     * 上传积分
     */
    private void uploadScore() {
        score = oldRole.getScore();
        if (score == -1) {
            score = 0;
            newestRoleInfoMap.put(Constant.ROLE_SCORE, String.valueOf(score));

        } else {
            newestRoleInfoMap.put(Constant.ROLE_SCORE, String.valueOf(score));

        }
    }

    /**
     * 上传最新的角色信息到极光服务器
     */
    private void uploadNewestRoleInfo() {
        uploadMyUser();
        uploadAvatar();
        uploadName();
        uploadSex();
        uploadSignature();
        uploadLevel();
        uploadExp();
        uploadScore();

        canUpdateInfo = validName;
        if (canUpdateInfo) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .operationType(AlertDialog.OPERATION_TYPE_SIMPLE)
                    .desc("更新资料中，请稍后...");
            builder.display();
            UserInfo myNewestUserInfo = JMessageClient.getMyInfo();
            myNewestUserInfo.setSignature(JSON.toJSONString(newestRoleInfoMap));
            JMessageClient.updateMyInfo(UserInfo.Field.signature, myNewestUserInfo, new BasicCallback() {
                @Override
                public void gotResult(int i, String s) {
                    if (i == 0) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                builder.dismiss();
                                ToastUtil.showToast(NewRoleActivity.this, "资料更新成功", ToastUtil.TOAST_DEFAULT);
                                String myRoleInfoJson = JMessageClient.getMyInfo().getSignature();
                                Logger.log(myRoleInfoJson);
                                openActivityDelay(HallActivity.class, 100);
                            }
                        });
                    } else {
                        builder.dismiss();
                        ToastUtil.showToast(NewRoleActivity.this, "资料更新失败", ToastUtil.TOAST_ERROR);

                    }
                }
            });


        } else {
            ToastUtil.showToast(this, "昵称不能为空哟", ToastUtil.TOAST_ERROR);

        }
    }

    /**
     * 选择图片
     * 相机、相册两种模式
     */
    private void choiceImage() {
        PictureSelector.create(this, PictureSelector.SELECT_REQUEST_CODE)
                .selectPicture();
    }

    @Override
    protected int getTopBarCenterViewStyle() {
        return R.drawable.role_title;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PictureSelector.SELECT_REQUEST_CODE) {
            if (data != null) {
                String picturePath = data.getStringExtra(PictureSelector.PICTURE_PATH);
                if (!StringUtil.isBlank(picturePath)) {
                    avatarFile = new File(picturePath);
                    addImg.setImageBitmap(BitmapFactory.decodeFile(picturePath));

                }
            }
        }
    }
}
