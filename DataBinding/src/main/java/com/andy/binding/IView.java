package com.andy.binding;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author tanlei
 * @date 2019/7/27
 * @describe
 */
public interface IView {
    void showLoading();
    void hideLoading();
    void showLoadFail(String msg);
    void activityFinish();//activity关闭
    CompositeDisposable getCompositeDisposable();
}
