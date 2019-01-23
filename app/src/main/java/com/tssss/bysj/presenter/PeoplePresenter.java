package com.tssss.bysj.presenter;

import com.tssss.bysj.contract.PresenterImp;
import com.tssss.bysj.interfaces.OnPeopleDataListener;
import com.tssss.bysj.interfaces.OnPeopleListener;
import com.tssss.bysj.model.PeopleModel;
import com.tssss.bysj.user.role.GameRole;

import java.util.List;

public class PeoplePresenter extends PresenterImp implements OnPeopleDataListener {
    private PeopleModel mModel;
    private OnPeopleListener mListener;


    public void listPeople(OnPeopleListener listener) {
        mListener = listener;

        mModel = new PeopleModel();
        mModel.loadAllPlayers(this);
    }

    @Override
    public void onComplete(List<GameRole> allPlayers) {
        mListener.onComplete(allPlayers);
    }

    @Override
    public void onFailure() {
        mListener.onFailure();
    }
}
