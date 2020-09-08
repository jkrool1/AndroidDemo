package com.hsy.drap;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Field;

public class DragFrameLayout extends FrameLayout {

    public int margin_edge;

    private int width,height;

    private int viewHeight;

    private int viewWidth;

    private int statusBarHeight;
    public DragFrameLayout(@NonNull Context context) {
        super(context);
    }

    public DragFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DragFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context,AttributeSet attrs){
        resolveAttr(context, attrs);
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;//
        statusBarHeight = getStatusBarHeight();
        if (statusBarHeight == 0) {
            statusBarHeight = (int) (25 * displayMetrics.scaledDensity + 0.5f);
        }
        height -= statusBarHeight;
        //还需要减去actionBar的高度
        margin_edge = 10;
    }

    private void resolveAttr (Context context,AttributeSet attrs){
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.DragFrameLayout);
        margin_edge = array.getDimensionPixelSize(R.styleable.DragFrameLayout_margin_edge,10);
        array.recycle();
    }

    @Override
    protected void onSizeChanged(int w,int h,int oldw,int oldh){
        super.onSizeChanged(w,h,oldw,oldh);
        viewHeight = getHeight();
        viewWidth = getWidth();
    }

    public int getStatusBarHeight() {
        if (statusBarHeight == 0) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object o = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = (Integer) field.get(o);
                statusBarHeight = getResources().getDimensionPixelSize(x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusBarHeight;
    }
}
