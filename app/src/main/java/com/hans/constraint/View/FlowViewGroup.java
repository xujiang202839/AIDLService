package com.hans.constraint.View;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.hans.constraint.adapter.TagFlowAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建者 xu
 * @创建时间 2020/5/8
 * @描述
 */
public class FlowViewGroup extends ViewGroup {
    private Context mContext;
    private int paddingLeft;
    private int paddingTop;
    private int paddingRight;
    private int paddingBottom;

    public FlowViewGroup(Context context) {
        super(context);
        mContext = context;
    }

    public FlowViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public FlowViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    protected void onLayout(boolean change, int l, int t, int r, int b) {
        int top = 0;
        int left;
        int lineHeight = 0;
        for (List<View> views : lines) {
            left = 0;
            top += lineHeight;
            lineHeight = 0;
            for (View view : views) {
                if (view.getVisibility() == GONE) {
                    continue;
                }
                MarginLayoutParams layoutParams = (MarginLayoutParams) view.getLayoutParams();
                view.layout(left + layoutParams.leftMargin + paddingLeft, top + layoutParams.topMargin + paddingTop,
                        left + view.getMeasuredWidth() + layoutParams.leftMargin + paddingLeft, top + view.getMeasuredHeight() + layoutParams.topMargin + paddingTop);

                lineHeight = Math.max(lineHeight, view.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin);
                left = left + view.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            }
        }
    }

    //计算后要显示行的数据的集合
    private List<List<View>> lines = new ArrayList<>();

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        lines.clear();
        //自身的padding
        paddingLeft = getPaddingLeft();
        paddingTop = getPaddingTop();
        paddingRight = getPaddingRight();
        paddingBottom = getPaddingBottom();

        int px2dip = px2dip(mContext, paddingLeft);
        Log.d("onMeasure", px2dip + "==" + paddingTop + "==" + paddingRight + "==" + paddingBottom);

        //测量模式及值
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //通过计算获取的总行高（包括 ViewGroup的padding和子View的margin）
        int lineHeight = 0;
        //最宽行的宽度
        int widthMax = 0;
        //当前行已占有的宽度
        int lineEmployWidth = 0;
        //计算当前行的最大高度
        int currentLineHeightMax = 0;
        //每一行中View的数据集
        List<View> lineInfo = new ArrayList<>();

        //获取子View的个数
        int childCount = getChildCount();
        //遍历子View对其进行测算
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            //判断子view的显示状态 Gone就不进行测算
            if (childAt.getVisibility() == GONE) {
                continue;
            }
            measureChild(childAt, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams layoutParams = (MarginLayoutParams) childAt.getLayoutParams();

            int childWidth = 0;
            int childHeight = 0;
            //获取view的测量宽度
            childWidth += childAt.getMeasuredWidth();
            //每一行的第一个添加父布局的paddingLeft
            if (0 == i) {
                childWidth += paddingLeft;
            }
            //获取子view自身的margin属性
            childWidth += (layoutParams.leftMargin + layoutParams.rightMargin);
            //当前的行高
            childHeight = childHeight + (childAt.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin);

            //当前行放不下时，自动换一行显示
            if (lineEmployWidth + childWidth > widthSize - paddingRight) {
                //初始化当前行的宽度
                lineEmployWidth = childWidth + paddingLeft;
                //添加一行高
                lineHeight += currentLineHeightMax;
                //初始化行高
                currentLineHeightMax = childHeight;

                lines.add(lineInfo);
                lineInfo = new ArrayList<>();
                lineInfo.add(childAt);
            } else {
                //当前行可以显示时
                lineInfo.add(childAt);
                //增加当前行已显示的宽度
                lineEmployWidth += childWidth;
                //为了显示最大的行高
                currentLineHeightMax = Math.max(currentLineHeightMax, childHeight);
                //显示最大的行款
                widthMax = Math.max(widthMax, lineEmployWidth);
            }
        }

        lines.add(lineInfo);
        lineHeight += (paddingTop + paddingBottom + currentLineHeightMax);
        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? widthSize : widthMax + paddingRight,
                (heightMode == MeasureSpec.EXACTLY) ? heightSize : lineHeight);
    }

    /**
     * 重写该方法是为了使用MarginLayoutParams获取子View的margin值
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    /**
     * 当使用adapter添加数据时使用。
     */
    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return super.generateDefaultLayoutParams();
    }

    private TagFlowAdapter mAdapter;

    public void setAdapter(TagFlowAdapter adapter) {
        if (null == adapter) {
            throw new NullPointerException("TagFlowAdapter is null, please check setAdapter(TagFlowAdapter adapter)...");
        }
        mAdapter = adapter;
        adapter.setOnNotifyDataSetChangedListener(new TagFlowAdapter.OnNotifyDataSetChangedListener() {
            @Override
            public void OnNotifyDataSetChanged() {
                notifyDataSetChanged();
            }
        });
        adapter.notifyDataSetChanged();
    }

    private void notifyDataSetChanged() {
        removeAllViews();
        if (mAdapter == null || mAdapter.getCount() == 0) {
            return;
        }
        MarginLayoutParams layoutParams = new MarginLayoutParams(generateDefaultLayoutParams());
        for (int i = 0; i < mAdapter.getCount(); i++) {
            View view = mAdapter.getView(i);
            if (view == null) {
                throw new NullPointerException("item layout is null, please check getView()...");
            }
            addView(view, i, layoutParams);
        }
    }

    /**
     * 12      * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     * 13
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
