package com.andy.binding;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.andy.binding.vm.BaseViewModel;

import io.reactivex.disposables.CompositeDisposable;
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
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
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
        if(isVisible()){
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
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear(); // clear时网络请求会随即cancel
            mCompositeDisposable = null;
        }
        super.onDestroy();
    }
    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if(dataBinding!=null&&!isInitData&&menuVisible){
            isInitData=true;
            initData();
        }
    }
    @Override
    public abstract void showLoading();

    @Override
    public abstract void hideLoading();
    @Override
    public abstract void showLoadFail(String msg);
    @Override
    public CompositeDisposable getCompositeDisposable() {
        if(mCompositeDisposable==null){
            mCompositeDisposable = new CompositeDisposable();
        }
        return mCompositeDisposable;
    }
    @Override
    public void activityFinish() {
        Activity activity=getActivity();
        if(activity!=null){
            activity.finish();
        }
    }
}
