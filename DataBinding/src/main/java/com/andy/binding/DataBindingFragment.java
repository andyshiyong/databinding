package com.andy.binding;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.andy.binding.vm.BaseViewModel;
import com.andy.utils.IsFastClick;
import com.andy.utils.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 *  DataBinding基类Fragment
 */
public abstract class DataBindingFragment<VM extends BaseViewModel, DB extends ViewDataBinding> extends Fragment implements IView {
    private RxPermissions rxPermissions;
    public RxPermissions getRxPermissions() {
        if(rxPermissions==null){
            try {
                rxPermissions=new RxPermissions(getActivity());
            }catch (Exception e){}
        }
        return rxPermissions;
    }
    public void requestPermissions(Consumer<Boolean> consumer, String... permissions) {
        RxPermissions rxPermission = getRxPermissions();
        if(rxPermission!=null){
            rxPermission.request(permissions)
                    .subscribe(consumer);
        }
    }
    protected VM viewModel;
    public DB dataBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private boolean isInitData=false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        return dataBinding.getRoot();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (viewModel == null) {
            viewModel = createViewModel();
            viewModel.init(this,dataBinding);
        }
        if(menuVisible&&!isInitData){
            isInitData=true;
            initData();
        }
    }
    protected abstract int getLayoutId();

    protected abstract VM createViewModel();
    /**
     * 初始化数据
     */
    protected abstract void initData();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        // when destroy UI
        super.onDestroy();
        if (viewModel != null) {
            viewModel.onCleared(); // clear时网络请求会随即cancel
        }
    }
    private boolean menuVisible;
    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        this.menuVisible=menuVisible;
        if(dataBinding!=null&&!isInitData&&menuVisible){
            isInitData=true;
            initData();
        }
    }
    public LoadingDialig mLoadingDialig;
    @Override
    public void showLoading() {
        if (mLoadingDialig == null) {
            mLoadingDialig = new LoadingDialig(getContext());
        }
        mLoadingDialig.show();
    }
    @Override
    public void hideLoading() {
        if (mLoadingDialig != null) {
            mLoadingDialig.dismiss();
        }
    }
    @Override
    public void showLoadFail(String msg) {
        if(TextUtils.isEmpty(msg)){
            return;
        }
        if(msg.contains("timeout")){
            ToastUtils.showShort("网络连接超时");
        }else {
            ToastUtils.showShort(msg);
        }
    }

    @Override
    public void activityFinish() {
        Activity activity=getActivity();
        if(activity!=null){
            activity.finish();
        }
    }

    /**
     * 不带数据的界面跳转方法
     * @param c 需要跳转至的界面
     */
    public void startToActivity(Class c) {
        if(IsFastClick.isFastClick()) {
            Intent intent = new Intent(getContext(), c);
            startActivity(intent);
        }
    }
}
