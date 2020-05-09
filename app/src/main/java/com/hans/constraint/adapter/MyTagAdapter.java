package com.hans.constraint.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.hans.constraint.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建者 xu
 * @创建时间 2020/5/8
 * @描述
 */
public class MyTagAdapter extends TagFlowAdapter {
    private List<String> mList = new ArrayList<>();
    private Context mContext;

    public MyTagAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<String> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position) {
        View view = View.inflate(mContext, R.layout.item_falg, null);
        TextView tv = (TextView) view.findViewById(R.id.tv_title);
        tv.setText(mList.get(position));
        return view;
    }
}
