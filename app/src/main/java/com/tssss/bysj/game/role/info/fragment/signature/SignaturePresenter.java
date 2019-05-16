package com.tssss.bysj.game.role.info.fragment.signature;

import android.os.Handler;

import com.tssss.bysj.game.core.other.GameRole;
import com.tssss.bysj.mvp.base.BaseMvpPresenter;
import com.tssss.bysj.other.jmessage.JMessageHelper;
import com.tssss.bysj.other.jmessage.callback.IGetGameRoleCallBack;

public class SignaturePresenter extends BaseMvpPresenter<ISignatureContract.IView>
        implements ISignatureContract.IPresenter {

    private Handler handler;

    public SignaturePresenter(ISignatureContract.IView view) {
        super(view);
        handler = new Handler();
    }


    @Override
    protected ISignatureContract.IView getEmptyView() {
        return ISignatureContract.emptyView;
    }

    @Override
    public void loadSignature(String accountID) {
        JMessageHelper.getGameRoleByAccountID(accountID, new IGetGameRoleCallBack() {
            @Override
            public void onComplete(GameRole role) {
                String roleSignature = "这家伙懒得啥都没写...";
                if (null != role) {
                    roleSignature = role.getSignature();
                }
                String finalRoleSignature = roleSignature;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        getView().showSignature(finalRoleSignature);
                    }
                });
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }
}
