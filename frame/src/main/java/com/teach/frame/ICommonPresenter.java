package com.teach.frame;

/**
 * Created by mi on 2020/5/28.
 */
public interface ICommonPresenter<P> extends ICommonView {
    void getData(int whichApi,P... pPS);
}
