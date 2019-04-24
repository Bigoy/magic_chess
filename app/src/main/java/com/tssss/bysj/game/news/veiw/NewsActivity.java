package com.tssss.bysj.game.news.veiw;


import android.view.View;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.componet.GTextView;
import com.tssss.bysj.componet.menu.Menu;
import com.tssss.bysj.componet.menu.OnMenuItemClickListener;
import com.tssss.bysj.util.AnimationUtil;

import java.util.ArrayList;
import java.util.List;

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

    private Menu menu;

    @Override
    protected void findViews() {
        categoryRecommend = findViewById(R.id.news_category_recommend);
        categoryAttention = findViewById(R.id.news_category_attention);
        categoryOther = findViewById(R.id.news_category_other);
        categoryNewest = findViewById(R.id.news_category_newest);
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
        recommendFragment = new RecommendFragment();
        attentionFragment = new AttentionFragment();
        otherFragment = new OtherFragment();
        newestFragment = new NewestFragment();
        // 默认显示推荐动态
        startColorAnimation(categoryRecommend);
        replaceFragment(R.id.news_fragment_container, recommendFragment);
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
        super.clickTopBarRight();
        List<String> items = new ArrayList<>();
        items.add("我关注的");
        items.add("屏蔽内容");
        menu = new Menu.Builder(this, this)
                .items(items)
                .build();
        menu.display();
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
                break;
            case 1:
                menu.dismiss();
                break;
            default:

        }
    }
}
