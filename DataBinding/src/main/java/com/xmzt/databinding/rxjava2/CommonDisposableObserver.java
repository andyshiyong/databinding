package com.xmzt.databinding.rxjava2;


import androidx.annotation.RequiresPermission;

import com.xmzt.databinding.IView;
import com.xmzt.utils.NetWorkUtils;

import java.net.ConnectException;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;

/**
 * 统一处理异常信息(用於Observer)
 *
 * @param <T>
 */
public abstract class CommonDisposableObserver<T> extends DisposableObserver<T> {
    private IView mView;
    public CommonDisposableObserver() {
    }

    public CommonDisposableObserver(IView mView) {
        this.mView = mView;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @RequiresPermission(ACCESS_NETWORK_STATE)
    @Override
    public void onError(Throwable throwable) {
        if (throwable instanceof QzdsException) {
            QzdsException q = (QzdsException) throwable;
            if (mView != null) {
                mView.showLoadFail(throwable.getMessage());
            }
        } else if (throwable instanceof ConnectException) {
            if (!NetWorkUtils.isNetConnected()) {
                if (mView != null) {
                    mView.showLoadFail("网络异常");
                }
            } else {
                if (mView != null) {
                    mView.showLoadFail("服务器异常，请稍后再试！");
                }
            }
        } else if (throwable instanceof HttpException) {
            if (mView != null) {
                mView.showLoadFail("服务器异常，请稍后再试！");
            }
        }else {
            if (mView != null) {
                mView.showLoadFail(throwable.getMessage());
            }
        }
    }

    @Override
    public void onComplete() {
    }
}
