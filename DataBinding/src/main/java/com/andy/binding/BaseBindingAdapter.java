package com.andy.binding;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseBindingAdapter<M, B extends ViewDataBinding> extends RecyclerView.Adapter {
    protected ObservableArrayList<M> items;

    public BaseBindingAdapter() {
        this.items = new ObservableArrayList<>();
    }

    public ObservableArrayList<M> getItems() {
        return items;
    }
    public void setDataList(List<M> dataList) {
        this.items.clear();
        if(dataList!=null){
            this.items.addAll(dataList);
        }
    }

    public void addDataList(List<M> dataList) {
        if(dataList!=null&&dataList.size()>0){
            this.items.addAll(dataList);
        }
    }
    public void addData(int index,M data) {
        if(data==null){
            return;
        }
        if(this.items.size()>=index){
            this.items.add(index,data) ;
        }
    }
    public void addDataList(int index,List<M> dataList) {
        if(dataList==null){
            return;
        }
        if(this.items.size()>=index){
            this.items.addAll(index,dataList) ;
        }
    }
    public class BaseBindingViewHolder<DB extends ViewDataBinding> extends RecyclerView.ViewHolder{
        public DB dbBinding;
        public BaseBindingViewHolder(DB dbBinding) {
            super(dbBinding.getRoot());
            this.dbBinding=dbBinding;
        }
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        B binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), this.getLayoutResId(viewType), parent, false);
        BaseBindingViewHolder holder=new BaseBindingViewHolder(binding);
        onBindingHolderCreate(holder);
        return holder;
    }
    protected void onBindingHolderCreate(BaseBindingViewHolder holder) {

    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        B binding = DataBindingUtil.getBinding(holder.itemView);
        this.onBindItem(binding, this.items.get(position));
    }

    @LayoutRes
    protected abstract int getLayoutResId(int viewType);

    protected abstract void onBindItem(B binding, M item);

}
