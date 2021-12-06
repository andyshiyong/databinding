package com.andy.binding.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.andy.binding.IView;

import java.lang.reflect.ParameterizedType;

import io.reactivex.disposables.CompositeDisposable;

/**
 * AndroidViewModel 基类
 */
public class BaseViewModel<DB extends ViewDataBinding,T> extends AndroidViewModel {
    protected IView mView;
    protected DB dataBinding;
    public IView getIView() {
        return mView;
    }
    public void init(IView mView,DB dataBinding) {
        this.mView = mView;
        this.dataBinding = dataBinding;
    }
    //生命周期观察的数据
    private MutableLiveData<T> liveObservableData = new MutableLiveData<>();
    //UI使用可观察的数据 ObservableField是一个包装类
    private ObservableField<T> uiObservableData = new ObservableField<>();
    private final CompositeDisposable mDisposable = new CompositeDisposable();

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * LiveData支持了lifecycle生命周期检测
     * @return
     */
    public LiveData<T> getLiveObservableData() {
        return liveObservableData;
    }

    /**
     * 当主动改变数据时重新设置被观察的数据
     * @param product
     */
    public void setUiObservableData(T product) {
        this.uiObservableData.set(product);
    }

    public Class<T> getTClass(){
        Class<T> tClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return tClass;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mDisposable.clear();
    }
}
