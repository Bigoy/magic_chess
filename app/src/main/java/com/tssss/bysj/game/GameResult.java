package com.tssss.bysj.game;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;

import com.tssss.bysj.R;
import com.tssss.bysj.activity.HallActivity;
import com.tssss.bysj.interfaces.OnGDialogListener;
import com.tssss.bysj.user.role.GameRoleManager;
import com.tssss.bysj.widget.GDialog;

public class GameResult {

    /**
     * Show game result.
     */
    public void showGameResult(final GameProgress gameProgress) {
        final Context context = GameHelper.getGameHelper().getContext();

        String sb = "你当前经验为：" +
                GameRoleManager.getGameRoleManager().getRole(GameRoleManager.SELF).getRoleExperience() +
                "\n" + "继续吗？";

        Looper.prepare();

        final GDialog dialog = new GDialog(context, context.getString(R.string.dialog_title), sb);
        dialog.setOnGDialogListener(new OnGDialogListener() {
            @Override
            public void onPassive() {
                gameProgress.endGame();
                context.startActivity(new Intent(context, HallActivity.class));
                dialog.dismiss();
            }

            @Override
            public void onPositive() {
                gameProgress.resumeGame();
            }
        });
        dialog.show();

        Looper.loop();
    }
}
