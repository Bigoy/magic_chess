package com.tssss.bysj.game.friend;

import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.componet.GTextView;
import com.tssss.bysj.componet.dialog.AlertDialog;
import com.tssss.bysj.game.core.other.GameRole;
import com.tssss.bysj.game.friend.adapter.RecommendAdapter;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.user.UserDataCache;
import com.tssss.bysj.util.AccountUtil;
import com.tssss.bysj.util.AnimationUtil;
import com.tssss.bysj.util.ToastUtil;

import java.util.List;

@ViewInject(layoutId = R.layout.activity_add_friend)
public class AddFriendActivity extends BaseActivity implements IAddFriendContract.IView {

    private EditText accountIDEt;
    private ImageButton sendAddFriendRequest;
    private RecyclerView recommendRv;
    private GTextView loading;
    private GTextView feedBack;
    private Handler handler;
    private AddFriendPresenter presenter;
    private AlertDialog.Builder sendingBuilder;

    @Override
    protected void findViews() {
        accountIDEt = findViewById(R.id.add_friend_input_id);
        sendAddFriendRequest = findViewById(R.id.add_friend_request);
        recommendRv = findViewById(R.id.add_friend_recommend_roles);
        loading = findViewById(R.id.add_friend_recommend_loading);
        feedBack = findViewById(R.id.add_friend_feed_back);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.add_friend_request) {
            String targetAccountID = accountIDEt.getText().toString();
            if (!AccountUtil.validPhoneNumber(targetAccountID)) {
                errorFeedBack("手机号码格式错误");
            } else if (targetAccountID.equals(UserDataCache.readAccount(Constant.ACCOUNT_ID))) {
                errorFeedBack("不能添加自己");

            } else {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        presenter.sendAddFriendRequest(targetAccountID);
                    }
                }, 300);
            }
        }
    }

    private void errorFeedBack(String errorText) {
        feedBack.setText(errorText);
        AnimationUtil.flipView(this, sendAddFriendRequest, feedBack);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AnimationUtil.flipView(AddFriendActivity.this, feedBack, sendAddFriendRequest);
            }
        }, 1500);
    }

    @Override
    protected void setEventListeners() {
        sendAddFriendRequest.setOnClickListener(this);
    }

    @Override
    protected void afterBindView() {
        presenter = new AddFriendPresenter(this, this);
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
    public void showRecommendRoles(List<GameRole> recommendGameRoles) {
        if (null != recommendGameRoles && recommendGameRoles.size() > 0) {
            loading.setVisibility(View.GONE);
            recommendRv.setVisibility(View.VISIBLE);
            recommendRv.setLayoutManager(new LinearLayoutManager(this));
            recommendRv.setAdapter(new RecommendAdapter(this, recommendGameRoles));
        } else {
            recommendRv.setVisibility(View.GONE);
            loading.setVisibility(View.GONE);
            loading.setText("暂无推荐");
            AnimationUtil.flipView(this, loading);
        }
    }

    @Override
    public void showRequesting() {
        sendingBuilder = new AlertDialog.Builder(this)
                .operationType(AlertDialog.OPERATION_TYPE_SIMPLE)
                .subDesc("正在发送请求");
        sendingBuilder.display();
    }

    @Override
    public void showRequestSucceed() {
        sendingBuilder.dismiss();
        ToastUtil.showToast(this, "发送成功，慢慢等待TA回复吧", ToastUtil.TOAST_DEFAULT);
//        finish();
    }

    @Override
    public void showRequestFailed(String s) {
//        finish();
        sendingBuilder.dismiss();
        ToastUtil.showToast(this, s, ToastUtil.TOAST_ERROR);
    }

    @Override
    public void showNotUser() {
        sendingBuilder.dismiss();
        ToastUtil.showToast(this, "用户不存在", ToastUtil.TOAST_ERROR);
//        finish();
    }


}
