package com.tssss.bysj.game.news.veiw;


import android.view.View;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.componet.GTextView;
import com.tssss.bysj.componet.dialog.AlertDialog;
import com.tssss.bysj.componet.menu.Menu;
import com.tssss.bysj.componet.menu.OnMenuItemClickListener;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.other.Logger;
import com.tssss.bysj.user.UserDataCache;
import com.tssss.bysj.util.AnimationUtil;
import com.tssss.bysj.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;

@ViewInject(layoutId = R.layout.activity_news)
public class NewsActivity extends BaseActivity implements OnMenuItemClickListener {
    private GTextView categoryRecommend;
    private GTextView categoryAttention;
    private GTextView categoryOther;
    private GTextView categoryNewest;
    private RecommendFragment recommendFragment;
    private AttentionFragment attentionFragment;
    private OtherFragment otherFragment;
    private NewestFragment newestFragment;
    private ConstraintLayout container;
    private GTextView functionClosed;

    private Menu menu;

    @Override
    protected void findViews() {
        categoryRecommend = findViewById(R.id.news_category_recommend);
        categoryAttention = findViewById(R.id.news_category_attention);
        categoryOther = findViewById(R.id.news_category_other);
        categoryNewest = findViewById(R.id.news_category_newest);
        container = findViewById(R.id.news_container);
        functionClosed = findViewById(R.id.news_function_closed);
    }

    @Override
    protected void setEventListeners() {
        categoryRecommend.setOnClickListener(this);
        categoryAttention.setOnClickListener(this);
        categoryOther.setOnClickListener(this);
        categoryNewest.setOnClickListener(this);
    }

    @Override
    protected void afterBindView() {
        if (UserDataCache.readString(Constant.SP_FUNCTION_NEWS_STATE).equals(Constant.SP_FUNCTION_NEWS_STATE_CLOSED)) {
            container.setVisibility(View.GONE);
            functionClosed.setVisibility(View.VISIBLE);
            String sb = "动态功能已经被关闭" +
                    "\n" +
                    "重新登录后可重新开启";
            functionClosed.setText(sb);
        } else {
            recommendFragment = new RecommendFragment();
            attentionFragment = new AttentionFragment();
            otherFragment = new OtherFragment();
            newestFragment = new NewestFragment();
            // 默认显示推荐动态
            startColorAnimation(categoryRecommend);
            replaceFragment(R.id.news_fragment_container, recommendFragment);


        }
    }

    @Override
    protected int getTopBarCenterViewStyle() {
        return R.drawable.news_title;
    }

    @Override
    protected int getTopBarRightViewStyle() {
        return R.drawable.ic_btn_more;
    }

    @Override
    protected void clickTopBarRight() {
        if (UserDataCache.readString(Constant.SP_FUNCTION_NEWS_STATE).equals(Constant.SP_FUNCTION_NEWS_STATE_CLOSED)) {
            Logger.log("动态功能被关闭");

        } else {
            List<String> items = new ArrayList<>();
            items.add("关闭动态功能");
            menu = new Menu.Builder(this, this)
                    .items(items)
                    .build();
            menu.display();

        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.news_category_recommend:
                startColorAnimation(categoryRecommend);
                replaceFragment(R.id.news_fragment_container, recommendFragment);
                break;
            case R.id.news_category_attention:
                startColorAnimation(categoryAttention);
                replaceFragment(R.id.news_fragment_container, attentionFragment);
                break;
            case R.id.news_category_other:
                startColorAnimation(categoryOther);
                replaceFragment(R.id.news_fragment_container, otherFragment);
                break;
            case R.id.news_category_newest:
                startColorAnimation(categoryNewest);
                replaceFragment(R.id.news_fragment_container, newestFragment);
                break;
            default:
        }
    }

    private void startColorAnimation(GTextView view) {
        initCategoryTabState();
        AnimationUtil.startBackgroundColorAnimator(view);
    }

    private void initCategoryTabState() {
        categoryRecommend.setTextColor(0xFF7E561B);
        categoryAttention.setTextColor(0xFF7E561B);
        categoryOther.setTextColor(0xFF7E561B);
        categoryNewest.setTextColor(0xFF7E561B);
        categoryRecommend.setBackgroundColor(0x00ffffff);
        categoryAttention.setBackgroundColor(0x00ffffff);
        categoryOther.setBackgroundColor(0x00ffffff);
        categoryNewest.setBackgroundColor(0x00ffffff);
    }

    @Override
    public void onMenuItemClick(View v, int position) {
        switch (position) {
            case 0:
                menu.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .desc("关闭动态功能")
                        .subDesc("确定吗？" + "\n" + "重新登录后将自动开启")
                        .operationListener(new AlertDialog.OnDialogOperationListener() {
                            @Override
                            public void ok() {
                                UserDataCache.keepString(Constant.SP_FUNCTION_NEWS_STATE, Constant.SP_FUNCTION_NEWS_STATE_CLOSED);
                                if (UserDataCache.readString(Constant.SP_FUNCTION_NEWS_STATE).equals(Constant.SP_FUNCTION_NEWS_STATE_CLOSED)) {
                                    ToastUtil.showToast(NewsActivity.this, "关闭成功", ToastUtil.TOAST_DEFAULT);
                                    finish();
                                } else {
                                    ToastUtil.showToast(NewsActivity.this, "出现错误，我们会尽快修复", ToastUtil.TOAST_ERROR);
                                }
                            }

                            @Override
                            public void no() {

                            }
                        });
                builder.display();
                break;

            default:

        }
    }
}
