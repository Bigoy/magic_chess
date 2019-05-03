package com.tssss.bysj.game.rank;

import android.annotation.SuppressLint;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseActivity;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.componet.GTextView;
import com.tssss.bysj.game.core.GameRole;
import com.tssss.bysj.util.SystemUtil;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

@ViewInject(layoutId = R.layout.activity_rank)
public class RankActivity extends BaseActivity implements IRankContract.IView {
    private GTextView updateTime;
    private GTextView oneName;
    private GTextView twoName;
    private GTextView threeName;
    private GTextView oneScore;
    private GTextView twoScore;
    private GTextView threeScore;
    private ImageView oneAvatar;
    private ImageView twoAvatar;
    private ImageView threeAvatar;
    private RecyclerView rankRv;

    private RankPresenter rankPresenter;


    @Override
    protected void findViews() {
        updateTime = findViewById(R.id.rank_update_time);
        oneName = findViewById(R.id.rank_one_name);
        twoName = findViewById(R.id.rank_two_name);
        threeName = findViewById(R.id.rank_three_name);
        oneScore = findViewById(R.id.rank_one_score);
        twoScore = findViewById(R.id.rank_two_score);
        threeScore = findViewById(R.id.rank_three_score);
        oneAvatar = findViewById(R.id.rank_one_avatar);
        twoAvatar = findViewById(R.id.rank_two_avatar);
        threeAvatar = findViewById(R.id.rank_three_avatar);
        rankRv = findViewById(R.id.rank_rv);
    }

    @Override
    protected void setEventListeners() {

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void afterBindView() {
        updateTime.setText(getString(R.string.rank_update_time) + SystemUtil.getCurrentTime());
        rankPresenter = new RankPresenter(this);
        rankPresenter.loadRankData();
    }

    @Override
    protected int getTopBarCenterViewStyle() {
        return R.drawable.rank_title;
    }

    @Override
    public void showOne(GameRole role) {
        if (null != role) {
            Glide.with(this)
                    .load(role.getAvatarFile())
                    .into(oneAvatar);
            oneName.setText(role.getName());
            oneScore.setText("积分：" + role.getScore());
        }
    }

    @Override
    public void showTwo(GameRole role) {
        if (null != role) {
            Glide.with(this)
                    .load(role.getAvatarFile())
                    .into(twoAvatar);
            twoName.setText(role.getName());
            twoScore.setText("积分：" + role.getScore());
        }

    }

    @Override
    public void showThree(GameRole role) {
        if (null != role) {
            Glide.with(this)
                    .load(role.getAvatarFile())
                    .into(threeAvatar);
            threeName.setText(role.getName());
            threeScore.setText("积分：" + role.getScore());
        }

    }

    @Override
    public void showOther(List<Rank> rankList) {
        if (null != rankList || rankList.size() > 0) {
            rankRv.setLayoutManager(new LinearLayoutManager(this));
            rankRv.setAdapter(new RankAdapter(this, rankList));

        }
    }
}
