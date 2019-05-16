package com.tssss.bysj.game.role.info;

import android.os.Handler;

import com.tssss.bysj.game.core.other.GameRole;
import com.tssss.bysj.mvp.base.BaseMvpPresenter;
import com.tssss.bysj.other.jmessage.JMessageHelper;
import com.tssss.bysj.other.jmessage.callback.IGetGameRoleCallBack;
import com.tssss.bysj.util.StringUtil;

public class UserInfoPresenter extends BaseMvpPresenter<IUserInfoContract.IView>
        implements IUserInfoContract.IPresenter {

    public UserInfoPresenter(IUserInfoContract.IView view) {
        super(view);
    }

    @Override
    public void loadUserInfo(String accountID) {
        Handler handler = new Handler();
        if (!StringUtil.isBlank(accountID)) {
            JMessageHelper.getGameRoleByAccountID(accountID, new IGetGameRoleCallBack() {
                @Override
                public void onComplete(GameRole role) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            getView().showInfo(role);
                        }
                    });
                }

                @Override
                public void onFailure(String errorMsg) {

                }
            });

        }
    }

    @Override
    protected IUserInfoContract.IView getEmptyView() {
        return IUserInfoContract.emptyView;
    }
}
