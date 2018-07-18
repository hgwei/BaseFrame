package com.hgw.baseframe.ui.main.contract;

import com.hgw.baseframe.base.mvp.AbstractPresenter;
import com.hgw.baseframe.base.mvp.BaseView;

/**
 * 描述：引导页
 * @author hgw
 */
public interface GuideContract {

    /** 定义操作View层的接口 */
    interface View extends BaseView {

    }

    /** 定义操作Presenter层业务处理的接口 */
    interface Presenter extends AbstractPresenter<View> {


    }

}
