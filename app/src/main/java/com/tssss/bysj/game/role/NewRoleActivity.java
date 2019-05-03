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
import com.tssss.bysj.game.core.GameRole;
import com.tssss.bysj.game.hall.HallActivity;
import com.tssss.bysj.game.main.MainActivity;
import com.tssss.bysj.other.AppDataCache;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.other.Logger;
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

    private Map<String, String> userRole;
    private Handler handler;
    //    private RegisterOptionalUserInfo optionalUserInfo;
    private GameRole gameRole;
    private UserInfo updateUserInfo;
    private boolean avatarUpdateSuccess;
    private String avatar;
    private File avatarFile;

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
        userRole = new HashMap<>();
        handler = new Handler();
//        optionalUserInfo = new RegisterOptionalUserInfo();
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
                userRole.put(Constant.ROLE_SEX, Constant.ROLE_SEX_MAN);
//                optionalUserInfo.setGender(UserInfo.Gender.male);
                break;
            case R.id.new_role_sex_woman:
                initSex();
                woman.setBackgroundResource(R.drawable.sex_woman_select);
                userRole.put(Constant.ROLE_SEX, Constant.ROLE_SEX_WOMAN);
//                optionalUserInfo.setGender(UserInfo.Gender.female);
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
        if (avatarUpdateSuccess) {
            String nickName = nickNameEt.getText().toString();
            if (StringUtil.isBlank(nickName)) {
                ToastUtil.showToast(this, "昵称不能为空白", ToastUtil.TOAST_ERROR);
                return;
            }
            if (StringUtil.isBlank(userRole.get(Constant.ROLE_SEX))) {
                userRole.put(Constant.ROLE_SEX, Constant.ROLE_SEX_SECRET);
//            optionalUserInfo.setGender(UserInfo.Gender.unknown);
            }
            userRole.put(Constant.ROLE_NICK_NAME, nickName);
            String signature = signatureEt.getText().toString();
            if (StringUtil.isBlank(signature)) {
                signature = "这家伙懒得什么都不想写...";
            }
            userRole.put(Constant.ROLE_SIGNATURE, signature);
            Intent intent = getIntent();
            userRole.put(Constant.ACCOUNT_ID, intent.getStringExtra(Constant.ACCOUNT_ID));
            userRole.put(Constant.ACCOUNT_PASSWORD, intent.getStringExtra(Constant.ACCOUNT_PASSWORD));
            userRole.put(Constant.ROLE_EXP, "0");
            userRole.put(Constant.ROLE_LEVEL, Constant.ROLE_SX_I);
            userRole.put(Constant.ROLE_SCORE, "0");
            updateUserInfo = JMessageClient.getMyInfo();
            updateUserInfo.setSignature(JSON.toJSONString(userRole));
            JMessageClient.updateMyInfo(UserInfo.Field.signature, updateUserInfo, new BasicCallback() {
                @Override
                public void gotResult(int i, String s) {
                    if (i == 0) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.showToast(NewRoleActivity.this, "资料更新成功", ToastUtil.TOAST_DEFAULT);
                                String myRoleInfoJson = JMessageClient.getMyInfo().getSignature();
                                Logger.log(myRoleInfoJson);
                                Intent hallIntent = new Intent(NewRoleActivity.this, HallActivity.class);
                                startActivity(hallIntent);
                            }
                        });
                    } else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.showToast(NewRoleActivity.this, "资料更新失败！", ToastUtil.TOAST_ERROR);
                                JMessageClient.logout();
                                AppDataCache.keepAccountState(Constant.ACCOUNT_STATE_LOGOUT);
                                openActivity(MainActivity.class);
                            }
                        });
                    }
                }
            });
        } else {
            ToastUtil.showToast(this, "头像上传失败", ToastUtil.TOAST_ERROR);
        }
        /*GameRole gameRole = new GameRole();
        gameRole.setRoleExperience(0);
        gameRole.setAvatarStr(userRole.get(Constant.ROLE_AVATAR));
        gameRole.setName(userRole.get(Constant.ROLE_NICK_NAME));
        gameRole.setSex(userRole.get(Constant.ROLE_SEX));
        gameRole.setLevel(Constant.ROLE_SX_I);
        gameRole.setSignature(userRole.get(Constant.ROLE_SIGNATURE));
        gameRole.setUser(new User(userRole.get(Constant.ACCOUNT_ID), userRole.get(Constant.ACCOUNT_PASSWORD)));*/
//        AppDataCache.keepRole(gameRole);
//        AppDataCache.keepAccountState(Constant.ACCOUNT_STATE_LOGIN);
//        Logger.log(userRole, Constant.ROLE_AVATAR);
        /*String userJsonInfo = JSON.toJSONString(userRole);
        Logger.log(userJsonInfo);
        optionalUserInfo.setExtras(userRole);
        JMessageClient.register(userRole.get(Constant.ACCOUNT_ID),
                userRole.get(Constant.ACCOUNT_PASSWORD),
                optionalUserInfo, new BasicCallback() {
                    @Override
                     public void gotResult(int i, String s) {
                        Logger.log(i + s);
                        if (i == 898001) {
                            updateUserInfo = JMessageClient.getMyInfo();

                            if (null != updateUserInfo) {
                                *//*if (userRole.get(Constant.ROLE_SEX).equals(Constant.ROLE_SEX_MAN)) {
                                    updateUserInfo.setGender(UserInfo.Gender.male);
                                } else if (userRole.get(Constant.ROLE_SEX).equals(Constant.ROLE_SEX_WOMAN)) {
                                     updateUserInfo.setGender(UserInfo.Gender.female);
                                } else {
                                    updateUserInfo.setGender(UserInfo.Gender.unknown);
                                }*//*
                                updateUserInfo.setUserExtras(userRole);
                                    @Override
                                    public void gotResult(int i, String s) {
                                        Logger.log(i + s);
                                        if (i == 0) {
                                            updateUserInfo = JMessageClient.getMyInfo();
                                            GameRole gameRole = new GameRole();
                                            gameRole.setUser(new User(userRole.get(Constant.ACCOUNT_ID), userRole.get(Constant.ACCOUNT_PASSWORD)));
                                            gameRole.setAvatarStr(userRole.get(Constant.ROLE_AVATAR));
                                            gameRole.setAvatarStr(userRole.get(Constant.ROLE_NICK_NAME));
                                            gameRole.setAvatarStr(userRole.get(Constant.ROLE_SEX));
                                            gameRole.setAvatarStr(userRole.get(Constant.ROLE_SIGNATURE));
                                            gameRole.setAvatarStr(userRole.get(Constant.ROLE_LEVEL));
                                            gameRole.setRoleExperience(0);
                                            AppDataCache.keepRole(gameRole);
                                            UserDataCache.keepLastLoginTime(SystemUtil.getCurrentTime());
                                            handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    ToastUtil.showToast(NewRoleActivity.this, "创建角色成功", ToastUtil.TOAST_DEFAULT);
                                                    openActivityAndFinishSelf(HallActivity.class);
                                                }
                                            });

                                        } else {
                                            *//*GameRole gameRole = new GameRole();
                                            gameRole.setUser(new User(userRole.get(Constant.ACCOUNT_ID), userRole.get(Constant.ACCOUNT_PASSWORD)));
                                            gameRole.setAvatarStr(userRole.get(Constant.ROLE_AVATAR));
                                            gameRole.setAvatarStr(userRole.get(Constant.ROLE_NICK_NAME));
                                            gameRole.setAvatarStr(userRole.get(Constant.ROLE_SEX));
                                            gameRole.setAvatarStr(userRole.get(Constant.ROLE_SIGNATURE));
                                            gameRole.setAvatarStr(userRole.get(Constant.ROLE_LEVEL));
                                            UserDataCache.keepLastLoginTime(SystemUtil.getCurrentTime());*//*

                                        }
                                    }
                                });

                            }
                        } else if (i == 0) {
                            Map<String, String> myInfo = JMessageClient.getMyInfo().getExtras();
                            if (myInfo.size() <= 0) {
                                ToastUtil.showToast(NewRoleActivity.this, "创建角色失败", ToastUtil.TOAST_ERROR);
                            } else {
                                GameRole gameRole = new GameRole();
                                gameRole.setUser(new User(userRole.get(Constant.ACCOUNT_ID), userRole.get(Constant.ACCOUNT_PASSWORD)));
                                gameRole.setAvatarStr(userRole.get(Constant.ROLE_AVATAR));
                                gameRole.setAvatarStr(userRole.get(Constant.ROLE_NICK_NAME));
                                gameRole.setAvatarStr(userRole.get(Constant.ROLE_SEX));
                                gameRole.setAvatarStr(userRole.get(Constant.ROLE_SIGNATURE));
                                gameRole.setAvatarStr(userRole.get(Constant.ROLE_LEVEL));
                                UserDataCache.keepLastLoginTime(SystemUtil.getCurrentTime());
                                UserDataCache.keepRole(gameRole);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtil.showToast(NewRoleActivity.this, "创建成功", ToastUtil.TOAST_DEFAULT);
                                        openActivity(HallActivity.class);
                                    }
                                });

                            }
                        }
                    }
                });
*/

        /*OkHttpProvider.getInstance().requestPost(HttpUrl.URL_REGISTER, userRole, new HttpCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String registerResult = jsonObject.getString(Constant.REGISTER_RESULT);
                    if (Constant.REGISTER_RESULT_SUCCESS.equals(registerResult)) {
                        SQLiteFactory.getInstance().getUserDataBase(NewRoleActivity.this,
                                userRole.get(Constant.ACCOUNT_ID)).getHistoryTable().createChatHistoryTable();
                        JSONObject roleJson = jsonObject.getJSONObject(Constant.JSON_KEY_ROLE);
                        GameRole gameRole = new GameRole();
                        gameRole.setAvatarStr(roleJson.getString(Constant.ROLE_AVATAR));
                        gameRole.setName(roleJson.getString(Constant.ROLE_NICK_NAME));
                        gameRole.setSex(roleJson.getString(Constant.ROLE_SEX));
                        gameRole.setSignature(roleJson.getString(Constant.ROLE_SIGNATURE));
                        gameRole.setLevel(roleJson.getString(Constant.ROLE_LEVEL));
                        UserDataCache.keepRole(gameRole);
                        JMessageClient.register(userRole.get(Constant.ACCOUNT_ID),
                                userRole.get(Constant.ACCOUNT_PASSWORD), new BasicCallback() {
                                    @Override
                                    public void gotResult(int i, String s) {
                                        if (i == 0) {
                                            // 本地服务器注册成功
                                            // JMessage注册成功
                                            User user = new User();
                                            user.setUserId(userRole.get(Constant.ACCOUNT_ID));
                                            user.setUserPassword(userRole.get(Constant.ACCOUNT_PASSWORD));
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

                avatarFile = new File(picturePath);
                JMessageClient.updateUserAvatar(avatarFile, new BasicCallback() {
                    @Override
                    public void gotResult(int i, String s) {
                        if (i == 0) {
                            avatarUpdateSuccess = true;
//                            userRole.put(Constant.ROLE_AVATAR, ImageUtil.getImageStr(picturePath));
                            Logger.log("头像更新成功");
                            Logger.log(picturePath);


                        } else {
                            Logger.log("用户头像上传失败");
                            Logger.log(i + s);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtil.showToast(NewRoleActivity.this, "你的头像上传失败", ToastUtil.TOAST_ERROR);
                                }
                            });
                        }
                    }
                });
//                Logger.log(ImageUtil.getImageStr(picturePath));
                AppDataCache.keepString(userRole.get(Constant.ACCOUNT_ID) + "_head", picturePath);
                addImg.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            }
        }
    }
}
