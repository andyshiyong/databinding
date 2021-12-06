package com.andy.binding;


import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecycleViewAdapter<T,VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected ItemListener itemListener;
    protected List<T> dataList = new ArrayList<T>();

    public List<T> getDataList() {
        if (dataList==null){
            dataList = new ArrayList<T>();
        }
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        if(this.dataList==null){
            this.dataList = new ArrayList<T>();
        }
        this.dataList.clear();
        if(dataList!=null){
            this.dataList.addAll(dataList) ;
        }
        notifyDataSetChanged();
    }
    public void addDataList(List<T> dataList) {
        if(this.dataList==null){
            this.dataList = new ArrayList<T>();
        }
        if(dataList!=null&&dataList.size()>0){
            this.dataList.addAll(dataList) ;
            notifyDataSetChanged();
        }
    }
    public void addData(int index,T data) {
        if(data==null){
            return;
        }
        if(this.dataList==null){
            this.dataList = new ArrayList<T>();
        }
        if(this.dataList.size()>=index){
            this.dataList.add(index,data) ;
            notifyDataSetChanged();
        }
    }
    public void addDataList(int index,List<T> dataList) {
        if(dataList==null){
            return;
        }
        if(this.dataList==null){
            this.dataList = new ArrayList<T>();
        }
        if(this.dataList.size()>=index){
            this.dataList.addAll(index,dataList) ;
            notifyDataSetChanged();
        }
    }

    /**
     * 把position项替换成dataList
     * @param index
     * @param dataList
     */
    public void replaceItemToList(int index,List<T> dataList) {
        if(dataList==null){
            return;
        }
        if(this.dataList==null){
            this.dataList = new ArrayList<T>();
        }
        if(this.dataList.size()>index){
            this.dataList.remove(index);
            this.dataList.addAll(index,dataList) ;
            notifyDataSetChanged();
        }
    }
    public abstract T getItem(int position);
    public void setItemListener(ItemListener listener){
        this.itemListener = listener;
    }

}