package com.teach.frame;

import com.teach.data.TestInfo;
import com.teach.frame.utils.ParamHashMap;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mi on 2020/5/28.
 */
public class TestModel implements ICommonModel {

    @Override
    public void getData(ICommonPresenter pPresenter, int whichApi, Object[] params) {
        int loadType = (int) params[0];
        Map param = (Map) params[1];
        int pageId = (int) params[2];
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://static.owspace.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();

        Observable<TestInfo> data = retrofit.create(IService.class).getTestData(param,pageId);
        data.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TestInfo>() {
                    @Override
                    public void accept(TestInfo pTestInfo) throws Exception {
                        pPresenter.onSuccess(whichApi,loadType,pTestInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable pThrowable) throws Exception {
                        pPresenter.onFailed(whichApi,pThrowable);
                    }
                });
    }

}
