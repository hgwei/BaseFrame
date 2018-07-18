package com.hgw.baseframe.ui.main.contract;

import com.hgw.baseframe.base.mvp.AbstractPresenter;
import com.hgw.baseframe.base.mvp.BaseView;

/**
 * 描述：启动页
 * @author hgw
 */
public interface SplashContract {

    /** 定义操作View层的接口 */
    interface View extends BaseView {
        /**
         * 跳转引导介绍页
         * */
        void toSplash();

        /**
         * 跳转首页
         * */
        void toMain();
    }

    /** 定义操作Presenter层业务处理的接口 */
    interface Presenter extends AbstractPresenter<View> {

        /**
         * 业务判断
         * */
        void start(android.view.View view);
    }

}
