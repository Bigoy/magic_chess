package com.tssss.bysj.componet.menu;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseRvViewHolder;
import com.tssss.bysj.componet.GTextView;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    private Context context;
    private List<String> menuItems;
    private OnMenuItemClickListener listener;

    public MenuAdapter(Context context, List<String> menuItems, OnMenuItemClickListener listener) {
        this.context = context;
        this.menuItems = menuItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.dialog_menu_item, parent, false);
        return new MenuViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        holder.fillData(menuItems.get(position));
        holder.setListeners(listener, position);
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    public static class MenuViewHolder extends BaseRvViewHolder<String> {
        private GTextView menuItem;
        private Handler handler;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void instantiateObject() {
            handler = new Handler();
        }

        @Override
        public void fillData(String data) {
            menuItem.setText(data);
        }

        @Override
        protected void findViews() {
            menuItem = findGTextView(R.id.dialog_menu_item_tv);
        }

        public void setListeners(OnMenuItemClickListener listener, int position) {
            if (null == listener) {
                return;
            }
            menuItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            listener.onMenuItemClick(v, position);
                        }
                    }, 100);
                }
            });
        }

    }
}

