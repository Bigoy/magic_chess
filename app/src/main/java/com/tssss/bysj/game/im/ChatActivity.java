package com.tssss.bysj.game.im;

import android.view.View;
import android.widget.ImageButton;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.componet.menu.Menu;
import com.tssss.bysj.componet.menu.OnMenuItemClickListener;

import java.util.ArrayList;
import java.util.List;

@ViewInject(layoutId = R.layout.activity_chat)
public class ChatActivity extends BaseActivity implements OnMenuItemClickListener {
    private ImageButton sendMsg;

    private Menu menu;

    @Override
    protected void findViews() {
        sendMsg = findViewById(R.id.chat_chat_ib);
    }

    @Override
    protected void setEventListeners() {
        sendMsg.setOnClickListener(this);
    }

    @Override
    protected void afterBindView() {

    }

    @Override
    protected int getTopBarRightViewStyle() {
        return R.drawable.ic_btn_more;
    }

    @Override
    protected int getTopBarCenterViewStyle() {
        return R.drawable.chat_title;
    }

    @Override
    protected void clickTopBarRight() {
        super.clickTopBarRight();
        List<String> items = new ArrayList<>();
        items.add("清空与TA的聊天记录");
        items.add("查看更多历史记录");
        items.add("加入黑名单");
        menu = new Menu.Builder(this, this)
                .items(items)
                .build();
        menu.display();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
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
            case 2:
                menu.dismiss();
                break;
            default:
        }
    }
}
