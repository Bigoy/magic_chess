package com.tssss.bysj.componet.menu;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseDialog;
import com.tssss.bysj.componet.GTextView;
import com.tssss.bysj.util.AnimationUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Menu extends BaseDialog {
    private LinearLayout itemsLl;
    private LinearLayout noLl;
    private RecyclerView itemsRv;
    private GTextView noTv;

    private Context context;
    private Handler handler;
    private List<String> menuItems;
    private OnMenuItemClickListener listener;
    private OnMenuItemLongClickListener longClickListener;
    private MenuAdapter adapter;

    public Menu(@NonNull Context context, OnMenuItemClickListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
    }

    public Menu(@NonNull Context context, int themeResId, OnMenuItemClickListener listener) {
        super(context, themeResId);
        this.context = context;
        this.listener = listener;
    }

    protected Menu(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener, OnMenuItemClickListener listener) {
        super(context, cancelable, cancelListener);
        this.context = context;
        this.listener = listener;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        window.setGravity(Gravity.BOTTOM);
    }

    @Override
    protected void instantiateObject() {
        handler = new Handler();
        if (null == this.menuItems) {
            menuItems = new ArrayList<>();
        }
    }

    @Override
    protected void findViews() {
        itemsLl = findViewById(R.id.dialog_menu_items_ll);
        noLl = findViewById(R.id.dialog_menu_no_container_ll);
        itemsRv = findViewById(R.id.dialog_menu_items_rv);
        noTv = findViewById(R.id.dialog_menu_no_tv);
    }

    @Override
    protected void fillData() {
        adapter = new MenuAdapter(context, menuItems, listener);
        itemsRv.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        itemsRv.setAdapter(adapter);
    }

    @Override
    protected int layout() {
        return R.layout.dialog_menu;
    }

    @Override
    protected void setListeners() {
        noTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationUtil.startBackgroundColorAnimator(noTv);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                }, 110);
            }
        });
    }

    @Override
    protected void customDialog() {

    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void show() {
        super.show();
    }

    public void display() {
        noTv.setBackgroundColor(0x00000000);
        noTv.setTextColor(0xFFE7A1A1);
        AnimationUtil.startAlphaSlideIn(context, itemsLl);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 110);
        AnimationUtil.startAlphaSlideIn(context, noLl);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                show();
            }
        }, 110);
    }

    public void setMenuItems(List<String> menuItems) {
        this.menuItems = menuItems;
    }


    public static class Builder {
        private Menu menu;

        public Builder(Context context, OnMenuItemClickListener listener) {
            this.menu = new Menu(context, false, null, listener);
        }

        public Builder items(List<String> items) {
            menu.setMenuItems(items);
            return this;
        }

        public Menu build() {
            return this.menu;
        }

        public void display() {
            menu.display();
        }
    }
}
