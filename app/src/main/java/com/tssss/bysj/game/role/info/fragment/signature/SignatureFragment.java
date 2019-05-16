package com.tssss.bysj.game.role.info.fragment.signature;

import com.tssss.bysj.R;
import com.tssss.bysj.base.BaseFragment;
import com.tssss.bysj.base.annoation.ViewInject;
import com.tssss.bysj.componet.GTextView;
import com.tssss.bysj.game.role.info.UserInfoActivity;

@ViewInject(layoutId = R.layout.fragment_signature)
public class SignatureFragment extends BaseFragment implements ISignatureContract.IView{
    private GTextView signature;
    private SignaturePresenter signaturePresenter;

    @Override
    protected void findViews() {
        signature = (GTextView) findViewById(R.id.fragment_signature);
    }

    @Override
    protected void afterBindView() {
        UserInfoActivity activity = (UserInfoActivity) getActivity();
        signaturePresenter = new SignaturePresenter(this);
        signaturePresenter.loadSignature(activity.getUserAccount());
    }

    @Override
    protected void setEventListeners() {

    }

    @Override
    public void showSignature(String signature) {
        this.signature.setText(signature);
    }

}
