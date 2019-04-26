package com.tssss.bysj.game;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;
import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.componet.GTextView;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.util.StringUtil;

import cn.jpush.im.android.api.JMessageClient;

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

            Glide.with(this)
                    .load(JMessageClient.getMyInfo().getAvatarFile())
                    .into(avatar);
            name.setText(intent.getStringExtra(Constant.ROLE_NICK_NAME));
            sex.setText(intent.getStringExtra(Constant.ROLE_SEX));
            String l = intent.getStringExtra(Constant.ROLE_LEVEL);
            if (StringUtil.isBlank(l)) {
                l = Constant.ROLE_SX;
            }
            level.setText(l);
            signature.setText(intent.getStringExtra(Constant.ROLE_SIGNATURE));
            /*JMessageClient.getMyInfo().getAvatarBitmap(new GetAvatarBitmapCallback() {
                @Override
                public void gotResult(int i, String s, Bitmap bitmap) {
                    if (i == 0) {
                        avatar.setImageBitmap(bitmap);
                        String infoJson = intent.getStringExtra("my_info");
                        try {
                            JSONObject jsonObject = new JSONObject(infoJson);
                            name.setText(jsonObject.getString(Constant.ROLE_NICK_NAME));
                            sex.setText(jsonObject.getString(Constant.ROLE_SEX));
                            level.setText(jsonObject.getString(Constant.ROLE_LEVEL));
                            signature.setText(jsonObject.getString(Constant.ROLE_SIGNATURE));
                        } catch (JSONException e) {
                            loadError();
                        }
                    } else {
                        loadError();
                    }
                }
            });
*/
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
