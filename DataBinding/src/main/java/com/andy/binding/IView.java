package com.andy.binding;

/**
 * @describe
 */
public interface IView {
    void showLoading();
    void hideLoading();
    void showLoadFail(String msg);
    void activityFinish();//activity关闭
}
