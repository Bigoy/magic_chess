package com.tssss.bysj.activity;

import android.os.Bundle;
import android.view.View;

import com.tssss.bysj.R;
import com.tssss.bysj.contract.BaseActivity;
import com.tssss.bysj.user.role.GameRole;
import com.tssss.bysj.user.role.GameRoleManager;

import androidx.annotation.Nullable;

public class MatchActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        match();
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void setEventListeners() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_match;
    }

    @Override
    protected boolean isFullScreen() {
        return true;
    }

    /**
     * Cancel matching
     */
    public void cancelMatch(View view) {
        finish();
    }

    /**
     * Match rival role.
     */
    private void match() {
        GameRole rival = new GameRole(
                "Cml",
                GameRole.ROLE_SEX_GIRL,
                GameRole.ROLE_LEVEL_GURU,
                GameRole.ROLE_STATE_ONLINE,
                250,
                null
        );

        if (rival != null) {
            GameRoleManager.getGameRoleManager().addRole(GameRoleManager.RIVAL, rival);
            openActivity(GameActivity.class);
        }
    }
}
