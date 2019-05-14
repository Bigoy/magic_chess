package com.tssss.bysj.login;

import androidx.annotation.NonNull;

import com.tssss.bysj.mvp.ILifeCircle;
import com.tssss.bysj.mvp.IMvpView;
import com.tssss.bysj.mvp.MvpController;
import com.tssss.bysj.user.User;

public interface IAccountContract {
    interface IView extends IMvpView{
        /**
         * 用户输入的账户格式错误
         */
        void onAccountFormatError();

        /**
         * 用户输入的密码格式错误
         */
        void onPasswordFormatError();

        /**
         * 用户输入的账户格式正确
         */
        void onValidAccount();

        /**
         * 用户输入的密码格式正确
         */
        void onValidPassword();

        /**
         * 当前输入的账户不存在
         */
        void onAccountNotFound(User user);

        /**
         * 账户与密码不一致，密码不一致
         */
        void onPasswordError();


        /**
         * 账户操作进行中
         * 正在登陆
         * 正在注册
         */
        void onProcess();

        void onNullRoleInfo(User user);

        /**
         * 连接服务器失败
         * 用户当前没有开启网络
         * 服务器出现故障
         * @param code 错误码，判定失败原因
         */
        void onConnectionFailure(int code);

        /**
         * 账户操作成功，返回用户信息
         */

        void onSuccess();

        /**
         * 其它错误
         */
        void onError(String s);
    }

    interface IPresenter extends ILifeCircle{
        /**
         * 检验用户输入的账户和密码的格式
         */
        void verifyAccountFormat(User user);

        /**
         * 执行账户通用操作
         * 登录
         * 注册
         */
        void confirmAccountOperation(String account, String password);

        /**
         * 返回用户基本信息
         */
        void returnUser(@NonNull User user);
    }

    /**
     * 空 View， 防止空指针异常
     */
    IMvpView emptyView = new IView() {
        @Override
        public void onAccountFormatError() {

        }

        @Override
        public void onPasswordFormatError() {

        }

        @Override
        public void onValidAccount() {

        }

        @Override
        public void onValidPassword() {

        }

        @Override
        public void onAccountNotFound(User user) {

        }

        @Override
        public void onPasswordError() {

        }

        @Override
        public void onProcess() {

        }

        @Override
        public void onNullRoleInfo(User user) {

        }

        @Override
        public void onConnectionFailure(int code) {

        }

        @Override
        public void onSuccess() {

        }

        @Override
        public void onError(String s) {

        }

        @Override
        public MvpController getMvpController() {
            return null;
        }
    };

}
