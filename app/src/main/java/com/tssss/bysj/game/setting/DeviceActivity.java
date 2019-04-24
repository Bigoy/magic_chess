package com.tssss.bysj.game.setting;

import android.os.Build;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.componet.GTextView;
import com.tssss.bysj.user.UserDataCache;
import com.tssss.bysj.util.StringUtil;

@ViewInject(layoutId = R.layout.activity_device)
public class DeviceActivity extends BaseActivity {
    private GTextView nameTv;
    private GTextView timeTv;

    @Override
    protected void findViews() {
        nameTv = findViewById(R.id.device_name);
        timeTv = findViewById(R.id.device_time);
    }

    @Override
    protected void setEventListeners() {

    }

    @Override
    protected void afterBindView() {
        String time = UserDataCache.readLastLoginTime();
        String model = Build.MODEL;
        if (StringUtil.isBlank(time) || StringUtil.isBlank(model)) {
            nameTv.setText("未知");
            timeTv.setText("未知");
        } else {
            nameTv.setText(model);
            timeTv.setText(time);
        }
    }

    @Override
    protected int getTopBarCenterViewStyle() {
        return R.drawable.device_title;
    }
}
