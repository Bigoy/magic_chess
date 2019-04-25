package com.tssss.bysj.game.friend;

import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.componet.GTextView;
import com.tssss.bysj.componet.dialog.AlertDialog;
import com.tssss.bysj.game.core.Role;
import com.tssss.bysj.game.friend.adapter.RecommendAdapter;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.user.UserDataCache;
import com.tssss.bysj.util.AccountUtil;
import com.tssss.bysj.util.AnimationUtil;
import com.tssss.bysj.util.ToastUtil;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

@ViewInject(layoutId = R.layout.activity_add_friend)
public class AddFriendActivity extends BaseActivity implements IAddFriendContract.IView {

    private EditText accountIDEt;
    private ImageButton sendAddFriendRequest;
    private RecyclerView recommendRv;
    private GTextView loading;

    private Handler handler;
    private AddFriendPresenter presenter;

    @Override
    protected void findViews() {
        accountIDEt = findViewById(R.id.add_friend_input_id);
        sendAddFriendRequest = findViewById(R.id.add_friend_request);
        recommendRv = findViewById(R.id.add_friend_recommend_roles);
        loading = findViewById(R.id.add_friend_recommend_loading);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.add_friend_request:
                String targetAccountID = accountIDEt.getText().toString();
                if (!AccountUtil.validPhoneNumber(targetAccountID)) {
                    ToastUtil.showToast(this, "请重新输入对方的手机号码", ToastUtil.TOAST_ERROR);
                } else if (targetAccountID.equals(UserDataCache.readAccount(Constant.ACCOUNT_ID))) {
                    ToastUtil.showToast(this, "不能添加自己", ToastUtil.TOAST_ERROR);

                } else {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            presenter.sendAddFriendRequest(targetAccountID);
                        }
                    }, 300);
                }
                break;

        }
    }

    @Override
    protected void setEventListeners() {
        sendAddFriendRequest.setOnClickListener(this);
    }

    @Override
    protected void afterBindView() {
        presenter = new AddFriendPresenter(this);
        handler = new Handler();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.loadRecommendRoles();
    }

    @Override
    protected int getTopBarCenterViewStyle() {
        return R.drawable.add_friend_title;
    }

    @Override
    public void showRecommendRoles(List<Role> recommendRoles) {
        if (null != recommendRoles && recommendRoles.size() > 0) {
            loading.setVisibility(View.GONE);
            recommendRv.setVisibility(View.VISIBLE);
            recommendRv.setLayoutManager(new LinearLayoutManager(this));
            recommendRv.setAdapter(new RecommendAdapter(this, recommendRoles));
        } else {
            recommendRv.setVisibility(View.GONE);
            loading.setVisibility(View.GONE);
            loading.setText("暂无推荐");
            AnimationUtil.flipView(this, loading);
        }
    }

    @Override
    public void showRequesting() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .operationType(AlertDialog.OPERATION_TYPE_SIMPLE)
                .subDesc("正在发送请求");
        builder.display();
    }

    @Override
    public void showRequestSucceed() {
        ToastUtil.showToast(this, "发送成功", ToastUtil.TOAST_DEFAULT);
        finish();
    }

    @Override
    public void showRequestFailed() {
        finish();
        ToastUtil.showToast(this, "发送失败，请稍后再试", ToastUtil.TOAST_ERROR);
    }


}
