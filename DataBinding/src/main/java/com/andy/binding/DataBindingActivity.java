package com.andy.binding;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.andy.binding.vm.BaseViewModel;
import com.andy.utils.IsFastClick;
import com.andy.utils.ToastUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;


/**
 *  DataBinding基类AppCompatActivity
 */
public abstract class DataBindingActivity<VM extends BaseViewModel, DB extends ViewDataBinding> extends AppCompatActivity implements IView {
    private RxPermissions rxPermissions;
    public RxPermissions getRxPermissions() {
        if(rxPermissions==null){
            rxPermissions=new RxPermissions(this);
        }
        return rxPermissions;
    }
    public void requestPermissions(Consumer<Boolean> consumer, String... permissions) {
        RxPermissions rxPermission = getRxPermissions();
        rxPermission.request(permissions)
                .subscribe(consumer);
    }
    protected VM viewModel;
    public DB dataBinding;
    public ImmersionBar immersionBar;
    protected boolean isDarkFont=true;
    private View statusBarView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, setLayoutId());
        if (viewModel == null) {
            viewModel = createViewModel();
        }
        if(viewModel!=null){
            viewModel.init(this,dataBinding);
        }
        statusBarView=dataBinding.getRoot().findViewById(R.id.statusBarView);
        if(statusBarView!=null){
            int statusBarHeight= ImmersionBar.getStatusBarHeight(this);
            ViewGroup.LayoutParams mLayoutParams= statusBarView.getLayoutParams();
            mLayoutParams.height=statusBarHeight;

            immersionBar=ImmersionBar.with(this)
                    .transparentStatusBar()
                    .statusBarView(statusBarView)
                    .fitsSystemWindows(false)
                    .statusBarDarkFont(isDarkFont, 0f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                    .keyboardEnable(false);
            immersionBar.init();
        }else {
            immersionBar=ImmersionBar.with(this)
                    .statusBarColor(R.color.colorPrimary)
                    .navigationBarColor(R.color.colorPrimary)
                    .fitsSystemWindows(true)
                    .keyboardEnable(false)
                    .statusBarDarkFont(isDarkFont, 0.2f); //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
            immersionBar.init();
        }
        // 注入 QMUISkinManager
        initData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected abstract int setLayoutId();

    protected abstract VM createViewModel();

    protected abstract void initData();

    @Override
    public void activityFinish() {
        finish();
    }
    /**
     * 不带数据的界面跳转方法
     * @param c 需要跳转至的界面
     */
    public void startToActivity(Class c) {
        if(IsFastClick.isFastClick()) {
            Intent intent = new Intent(this, c);
            startActivity(intent);
        }
    }

    public void startToActivity(Intent intent) {
        if(IsFastClick.isFastClick()) {
            startActivity(intent);
        }
    }

    public LoadingDialig mLoadingDialig;
    @Override
    public void showLoading() {
        if (mLoadingDialig == null) {
            mLoadingDialig = new LoadingDialig(this);
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
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        // when destroy UI
        super.onDestroy();
        if(viewModel!=null){
            viewModel.onCleared();
        }
    }
}
