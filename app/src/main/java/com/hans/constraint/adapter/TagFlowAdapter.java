package com.hans.constraint.adapter;

import android.view.View;

/**
 * @创建者 xu
 * @创建时间 2020/5/8
 * @描述
 */
public abstract class TagFlowAdapter {
    public abstract int getCount();

    public abstract Object getItem(int position);

    public abstract long getItemId(int position);

    public abstract View getView(int position);

    public void notifyDataSetChanged() {
        if (null != mOnNotifyDataSetChangedListener) {
            mOnNotifyDataSetChangedListener.OnNotifyDataSetChanged();
        }
    }

    /**
     * 释放一个接口 串联adapter与view中间的数据刷新
     */
    public interface OnNotifyDataSetChangedListener {
        void OnNotifyDataSetChanged();
    }

    private OnNotifyDataSetChangedListener mOnNotifyDataSetChangedListener;

    public void setOnNotifyDataSetChangedListener(OnNotifyDataSetChangedListener listener) {
        mOnNotifyDataSetChangedListener = listener;
    }
}
