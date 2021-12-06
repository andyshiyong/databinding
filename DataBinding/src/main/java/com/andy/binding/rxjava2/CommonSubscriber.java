package com.andy.binding.rxjava2;

import androidx.annotation.RequiresPermission;

import com.andy.binding.IView;
import com.andy.utils.NetWorkUtils;

import java.net.ConnectException;
import io.reactivex.subscribers.ResourceSubscriber;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;

/**
 * 统一处理异常信息(用於FlowableSubscriber)
 *
 * @param <T>
 */
public abstract class CommonSubscriber<T> extends ResourceSubscriber<T> {
    private IView mView;

    public CommonSubscriber(IView mView) {
        this.mView = mView;
    }

    @RequiresPermission(ACCESS_NETWORK_STATE)
    @Override
    public void onError(Throwable throwable) {
        if (throwable instanceof QzdsException) {
            QzdsException q = (QzdsException) throwable;
            if(mView!=null){
                mView.showLoadFail(throwable.getMessage());
            }

        } else if (throwable instanceof ConnectException) {
            if (!NetWorkUtils.isNetConnected()) {
                if(mView!=null){
                    mView.showLoadFail("网络异常");
                }
            } else {
                if(mView!=null){
                    mView.showLoadFail("服务器异常");
                }
            }
        } else {
            if(mView!=null){
                mView.showLoadFail(throwable.getMessage());
            }
        }
    }

    @Override
    public void onComplete() {

    }
}
