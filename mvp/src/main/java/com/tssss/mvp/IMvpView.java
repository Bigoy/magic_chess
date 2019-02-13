package com.tssss.mvp;

/**
 * Created by tssss on 2019/2/13.
 * <p>
 * 本接口担任中介者设计模式中的抽象同事角色，位于 View 层。
 */
public interface IMvpView {
    MvpController getMvpController();
}
