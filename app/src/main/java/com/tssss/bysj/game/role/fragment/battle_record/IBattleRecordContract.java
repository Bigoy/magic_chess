package com.tssss.bysj.game.role.fragment.battle_record;

import com.tssss.bysj.mvp.ILifeCircle;
import com.tssss.bysj.mvp.IMvpView;
import com.tssss.bysj.mvp.MvpController;

import java.util.List;

public interface IBattleRecordContract {
    interface IView extends IMvpView {
        void showBattleRecord(List<BattleRecord> battleRecordList);
    }

    interface IPresent extends ILifeCircle {
        void loadBattleRecord(String accountID);
    }

    IView emptyView = new IView() {
        @Override
        public void showBattleRecord(List<BattleRecord> battleRecordList) {

        }

        @Override
        public MvpController getMvpController() {
            return null;
        }
    };
}
