package com.xmzt.databinding;

import android.view.View;

/**
 * 列表item及item子item监听器
 */
public interface ItemChildItemListener<CObj> {
    /**
     * @param view 点击列表的view
     * @param position 点击列表的位置
     * @param childPosition 点击列表子列表的位置 -1表示没有子列表
     * @param childObj 点击列表子列表的数据
     */
    void onItemClick(View view, int position,int childPosition,CObj childObj);
}