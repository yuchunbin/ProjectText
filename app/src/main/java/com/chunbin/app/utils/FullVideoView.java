package com.chunbin.app.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

public class FullVideoView extends VideoView {
    public FullVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSpec = MeasureSpec.getSize(widthMeasureSpec);
        int hightSpec = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
    }
}
