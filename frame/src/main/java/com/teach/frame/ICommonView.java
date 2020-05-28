package com.teach.frame;

/**
 * Created by mi on 2020/5/28.
 */
public interface ICommonView<D> {
    void onSuccess(int whichApi,int loadType,D... pD);
    void onFailed(int whichApi,Throwable pThrowable);
}
