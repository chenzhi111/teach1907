package com.teach.frame;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;

/**
 * Created by mi on 2020/5/28.
 */
public interface ICommonModel<T> {
    void getData(ICommonPresenter pPresenter,int whichApi,T... params);
}
