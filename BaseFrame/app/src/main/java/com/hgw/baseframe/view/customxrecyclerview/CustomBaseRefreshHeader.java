package com.hgw.baseframe.view.customxrecyclerview;

/**
 * 描述：自定义（BaseRefreshHeader）
 * @author hgw
 */

public interface CustomBaseRefreshHeader {
    int STATE_NORMAL = 0;
    int STATE_RELEASE_TO_REFRESH = 1;
    int STATE_REFRESHING = 2;
    int STATE_DONE = 3;

    void onMove(float delta);

    boolean releaseAction();

    void refreshComplete();
}
