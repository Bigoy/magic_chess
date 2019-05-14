package com.tssss.bysj.game.role.fragment.battle_record;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseRvViewHolder;
import com.tssss.bysj.componet.GTextView;
import com.tssss.bysj.other.Constant;
import com.tssss.bysj.util.AnimationUtil;

import java.util.List;
import java.util.Map;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;

public class BattleRecordAdapter extends RecyclerView.Adapter<BattleRecordAdapter.BattleRecordViewHolder> {
    private List<BattleRecord> battleRecordList;
    private Context context;
    private BattleRecordViewHolder.OnBattleRecordItemClickListener onBattleRecordItemClickListener;

    public BattleRecordAdapter(Context context,
                               List<BattleRecord> battleRecordList,
                               BattleRecordViewHolder.OnBattleRecordItemClickListener onBattleRecordItemClickListener) {
        this.context = context;
        this.battleRecordList = battleRecordList;
        this.onBattleRecordItemClickListener = onBattleRecordItemClickListener;
    }

    @NonNull
    @Override
    public BattleRecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_battle_record, parent, false);
        return new BattleRecordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BattleRecordViewHolder holder, int position) {
        holder.fillData(battleRecordList.get(position));
        holder.setListeners(onBattleRecordItemClickListener, position);
    }

    @Override
    public int getItemCount() {
        return battleRecordList.size();
    }

    public static class BattleRecordViewHolder extends BaseRvViewHolder<BattleRecord> {
        private GTextView result;
        private GTextView opponent;
        private GTextView time;
        private LinearLayout descLL;
        private LinearLayout operationLL;
        private GTextView share;
        private GTextView lookInfo;
        private GTextView cancelOperation;

        public BattleRecordViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void instantiateObject() {

        }

        @Override
        public void fillData(BattleRecord data) {
            if (null != data) {
                String resultStr = data.getResult();
                if (Constant.GAME_RESULT_WIN.equals(resultStr)) {
                    result.setTextColor(0XFF4CAF50);
                } else if (Constant.GAME_RESULT_LOSE.equals(resultStr)) {
                    result.setTextColor(0XFF854141);

                }
                result.setText(resultStr);
                time.setText(data.getTime());
                JMessageClient.getUserInfo(data.getAccountID(), new GetUserInfoCallback() {
                    @SuppressWarnings("unchecked")
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void gotResult(int i, String s, UserInfo userInfo) {
                        if (i == 0) {
                            Map<String, String> map = (Map<String, String>) JSON.parse(userInfo.getSignature());
                            opponent.setText("对手： " + map.get(Constant.ROLE_NICK_NAME));
                        }
                    }
                });

            }
        }

        @Override
        protected void findViews() {
            result = findGTextView(R.id.item_battle_record_result);
            opponent = findGTextView(R.id.item_battle_record_opponent);
            time = findGTextView(R.id.item_battle_record_time);
            descLL = findLinearLayout(R.id.item_battle_record_container_desc);
            operationLL = findLinearLayout(R.id.item_battle_record_container_operation);
            share = findGTextView(R.id.item_battle_record_share);
            lookInfo = findGTextView(R.id.item_battle_record_look_info);
            cancelOperation = findGTextView(R.id.item_battle_record_cancel);
        }

        public void setListeners(OnBattleRecordItemClickListener listeners, int position) {
            descLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AnimationUtil.switchViewsTraslate(getContext(), operationLL, descLL);
                }
            });
            lookInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != listeners) {
                        listeners.onBattleRecordItemClick(v, position);
                    }
                }
            });
            cancelOperation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AnimationUtil.switchViewsTraslate(getContext(), descLL, operationLL);
                }
            });
        }

        public interface OnBattleRecordItemClickListener {

            void onBattleRecordItemClick(View v, int position);
        }
    }
}
