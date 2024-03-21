package com.example.ninjacartinterview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class CustomProgressBar extends ProgressBar {
    private List<Integer> midpoints;
    private Paint strokePaint;
    private Paint fillPaint;


    public CustomProgressBar(Context context) {
        super(context);
        init();
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init(){
        midpoints = new ArrayList<>();
        midpoints.add(5000);
        midpoints.add(3000);
        midpoints.add(8000);
        strokePaint = new Paint();
        strokePaint.setAntiAlias(true);
        fillPaint = new Paint();
        fillPaint.setAntiAlias(true);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!midpoints.isEmpty()) {
            int width = getWidth();
            int height = getHeight();

            for (Integer midpoint : midpoints) {
                float x = (float) midpoint / getMax() * width;
                float y = height / 2f;

                strokePaint.setStyle(Paint.Style.STROKE);
                strokePaint.setStrokeWidth(2);
                if (midpoint <= getProgress())
                    strokePaint.setColor(getResources().getColor(R.color.white,getContext().getTheme()));
                else
                    strokePaint.setColor(getResources().getColor(R.color.disabled, getContext().getTheme()));

                canvas.drawCircle(x, y, 24, strokePaint);

                if (midpoint <= getProgress())
                    fillPaint.setColor(getResources().getColor(R.color.secondary, getContext().getTheme()));
                else
                    fillPaint.setColor(getResources().getColor(R.color.disabled, getContext().getTheme()));
                canvas.drawCircle(x, y, 24, fillPaint);


            }
        }
    }

    public List<Integer> getMidpoints(){
        return midpoints;
    }
}
