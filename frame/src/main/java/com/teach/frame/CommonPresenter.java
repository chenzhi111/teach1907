package com.teach.frame;

/**
 * Created by mi on 2020/5/28.
 */
public class CommonPresenter implements ICommonPresenter {
    private ICommonView mView;
    private ICommonModel mModel;

    public CommonPresenter(ICommonView pView, ICommonModel pModel) {
        mView = pView;
        mModel = pModel;
    }

    @Override
    public void onSuccess(int whichApi, int loadType, Object... pD) {
        mView.onSuccess(whichApi, loadType, pD);
    }

    @Override
    public void onFailed(int whichApi, Throwable pThrowable) {
        mView.onFailed(whichApi, pThrowable);
    }

    @Override
    public void getData(int whichApi, Object... pObjects) {
        mModel.getData(this,whichApi,pObjects);
    }


}
