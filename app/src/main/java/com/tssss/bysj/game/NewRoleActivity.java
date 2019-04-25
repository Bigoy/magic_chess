package com.tssss.bysj.game;

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
import com.tssss.bysj.game.core.Role;
import com.tssss.bysj.game.hall.HallActivity;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.other.Logger;
import com.tssss.bysj.user.User;
import com.tssss.bysj.user.UserDataCache;
import com.tssss.bysj.util.ImageUtil;
import com.tssss.bysj.util.StringUtil;
import com.tssss.bysj.util.SystemUtil;
import com.tssss.bysj.util.ToastUtil;
import com.wildma.pictureselector.PictureSelector;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.android.api.options.RegisterOptionalUserInfo;
import cn.jpush.im.api.BasicCallback;

import static cn.jpush.im.android.api.model.UserInfo.Gender.male;

@ViewInject(layoutId = R.layout.activity_role)
public class NewRoleActivity extends BaseActivity {
    private ImageButton addImg;
    private GTextView man;
    private GTextView woman;
    private EditText nickNameEt;
    private EditText signatureEt;
    private ImageButton create;

    private Map<String, String> userExtraInfo;
    private Handler handler;

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
        userExtraInfo = new HashMap<>();
        handler = new Handler();
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
                userExtraInfo.put(Constant.ROLE_SEX, Constant.ROLE_SEX_MAN);
                break;
            case R.id.new_role_sex_woman:
                initSex();
                woman.setBackgroundResource(R.drawable.sex_woman_select);
                userExtraInfo.put(Constant.ROLE_SEX, Constant.ROLE_SEX_WOMAN);
                break;
            case R.id.new_role_add_avatar:
                choiceImage();
                break;
            case R.id.new_role_create:
                uploadNewRoleInfo();
                break;
            default:
        }
    }

    private void initSex() {
        man.setBackgroundResource(R.drawable.sex_man);
        woman.setBackgroundResource(R.drawable.sex_woman);
    }

    private void uploadNewRoleInfo() {
        String nickName = nickNameEt.getText().toString();
        if (StringUtil.isBlank(nickName)) {
            ToastUtil.showToast(this, "昵称不能为空白", ToastUtil.TOAST_ERROR);
            return;
        }
        if (StringUtil.isBlank(userExtraInfo.get(Constant.ROLE_SEX))) {
            userExtraInfo.put(Constant.ROLE_SEX, Constant.ROLE_SEX_SECRET);
        }
        userExtraInfo.put(Constant.ROLE_NICK_NAME, nickName);
        String signature = signatureEt.getText().toString();
        if (StringUtil.isBlank(signature)) {
            signature = "这家伙懒得什么都不想写...";
        }
        userExtraInfo.put(Constant.ROLE_SIGNATURE, signature);
        Intent intent = getIntent();
        if (null != intent) {
            userExtraInfo.put(Constant.ACCOUNT_ID, intent.getStringExtra(Constant.ACCOUNT_ID));
            userExtraInfo.put(Constant.ACCOUNT_PASSWORD, intent.getStringExtra(Constant.ACCOUNT_PASSWORD));
        }
//        Logger.log(userExtraInfo, Constant.ROLE_AVATAR);
        String userJsonInfo = JSON.toJSONString(userExtraInfo);
        Logger.log(userJsonInfo);
        RegisterOptionalUserInfo optionalUserInfo = new RegisterOptionalUserInfo();
        optionalUserInfo.setExtras(userExtraInfo);
        JMessageClient.register(userExtraInfo.get(Constant.ACCOUNT_ID),
                userExtraInfo.get(Constant.ACCOUNT_PASSWORD),
                optionalUserInfo, new BasicCallback() {
                    @Override
                    public void gotResult(int i, String s) {
                        Logger.log(i + s);
                        if (i == 898001) {
                            JMessageClient.updateMyInfo(UserInfo.Field.all, UserInfo.fromJson(userJsonInfo), new BasicCallback() {
                                @Override
                                public void gotResult(int i, String s) {
                                    Logger.log(i + s);
                                    if (i == 0) {
                                        Role role = new Role();
                                        role.setUser(new User(userExtraInfo.get(Constant.ACCOUNT_ID), userExtraInfo.get(Constant.ACCOUNT_PASSWORD)));
                                        role.setAvatar(userExtraInfo.get(Constant.ROLE_AVATAR));
                                        role.setAvatar(userExtraInfo.get(Constant.ROLE_NICK_NAME));
                                        role.setAvatar(userExtraInfo.get(Constant.ROLE_SEX));
                                        role.setAvatar(userExtraInfo.get(Constant.ROLE_SIGNATURE));
                                        role.setAvatar(userExtraInfo.get(Constant.ROLE_LEVEL));
                                        UserDataCache.keepLastLoginTime(SystemUtil.getCurrentTime());
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                ToastUtil.showToast(NewRoleActivity.this, "创建角色成功", ToastUtil.TOAST_DEFAULT);
                                                UserDataCache.keepRole(role);
                                                openActivityAndFinishSelf(HallActivity.class);
                                            }
                                        });

                                    }
                                }
                            });
                        }
                    }
                });


        /*OkHttpProvider.getInstance().requestPost(HttpUrl.URL_REGISTER, userExtraInfo, new HttpCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String registerResult = jsonObject.getString(Constant.REGISTER_RESULT);
                    if (Constant.REGISTER_RESULT_SUCCESS.equals(registerResult)) {
                        SQLiteFactory.getInstance().getUserDataBase(NewRoleActivity.this,
                                userExtraInfo.get(Constant.ACCOUNT_ID)).getHistoryTable().createChatHistoryTable();
                        JSONObject roleJson = jsonObject.getJSONObject(Constant.JSON_KEY_ROLE);
                        Role role = new Role();
                        role.setAvatar(roleJson.getString(Constant.ROLE_AVATAR));
                        role.setName(roleJson.getString(Constant.ROLE_NICK_NAME));
                        role.setSex(roleJson.getString(Constant.ROLE_SEX));
                        role.setSignature(roleJson.getString(Constant.ROLE_SIGNATURE));
                        role.setLevel(roleJson.getString(Constant.ROLE_LEVEL));
                        UserDataCache.keepRole(role);
                        JMessageClient.register(userExtraInfo.get(Constant.ACCOUNT_ID),
                                userExtraInfo.get(Constant.ACCOUNT_PASSWORD), new BasicCallback() {
                                    @Override
                                    public void gotResult(int i, String s) {
                                        if (i == 0) {
                                            // 本地服务器注册成功
                                            // JMessage注册成功
                                            User user = new User();
                                            user.setUserId(userExtraInfo.get(Constant.ACCOUNT_ID));
                                            user.setUserPassword(userExtraInfo.get(Constant.ACCOUNT_PASSWORD));
                                            UserDataCache.saveAccount(user);
//                                            JMessageClient.updateMyInfo(UserInfo.Field.extras,UserInfo.f);
                                            openActivityAndFinishSelf(HallActivity.class);

                                        } else {
                                            handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    ToastUtil.showToast(NewRoleActivity.this, "注册失败", ToastUtil.TOAST_ERROR);
                                                }
                                            });
                                            Logger.log(s);
                                        }
                                    }
                                });
                    } else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.showToast(NewRoleActivity.this, "注册失败", ToastUtil.TOAST_ERROR);
                            }
                        });
                        Logger.log("注册失败");
                    }

                } catch (JSONException e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showToast(NewRoleActivity.this, "注册失败", ToastUtil.TOAST_ERROR);
                        }
                    });
                    Logger.log("json解析异常");
                }
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });*/
    }

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
        /*结果回调*/
        if (requestCode == PictureSelector.SELECT_REQUEST_CODE) {
            if (data != null) {
                String picturePath = data.getStringExtra(PictureSelector.PICTURE_PATH);
                userExtraInfo.put(Constant.ROLE_AVATAR, ImageUtil.getImageStr(picturePath));
                Logger.log(picturePath);
//                Logger.log(ImageUtil.getImageStr(picturePath));
                addImg.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            }
        }
    }
}
