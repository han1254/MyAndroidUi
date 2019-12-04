package com.example.lib_ui.clock.neuq_clock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Time:2019/12/4 8:53
 * Author: han1254
 * Email: 1254763408@qq.com
 * Function:
 */
public class NEUQClock extends View {

    //背景画笔
    private Paint circlePaint;
    private Paint minuteScalePaint;
    private Paint hourScalePaint;
    private Paint pointPaint;
    private Paint minuteHandPaint;
    private Paint hourHandPaint;

    private int circleColor = Color.parseColor("#FFF68F");
    //设置钟表的边缘颜色（用于选择STROKE或者FI_AND_ST的情况）
    private int strokeColor = Color.parseColor("#0A0A0A");
    //设置时刻度颜色
    private int hourScaleColor = Color.parseColor("#0A0A0A");
    //设置分刻度颜色
    private int minuteScaleColor = Color.parseColor("#0A0A0A");
    //设置中点颜色
    private int centerPointColor = Color.parseColor("#0A0A0A");
    //设置分针颜色
    private int minuteHandColor = Color.parseColor("#0A0A0A");
    //设置时针颜色
    private int hourHandColor = Color.parseColor("#0A0A0A");
    //设置是否镂空
    private boolean isHollowOut = false;
    //暂时没用
    private float strokeSize = 5f;

    //分刻度的宽度
    private float minuteScaleWidth = 5f;
    //分刻度长
    private float minuteScaleLengthShort = 10f;
    //分刻度短
    private float minuteScaleLengthLong = 20f;
    //分针的长度
    private float minuteHandLength = 50f;
    //时针的长度
    private float hourHandLength = 30f;

    private float minuteHandWidth = 5f;

    private float hourHandWidth = 5f;

    //时
    private int hour = 0;
    //分
    private int minute = 0;



    public NEUQClock(Context context) {
        super(context);
        initValue();
    }

    public NEUQClock(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initValue();
    }

    public NEUQClock(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initValue();
    }

    private void initValue() {
        circlePaint = new Paint();
        minuteScalePaint = new Paint();
        hourScalePaint = new Paint();
        pointPaint = new Paint();
        minuteHandPaint = new Paint();
        hourHandPaint = new Paint();
        circlePaint.setColor(circleColor);
        minuteScalePaint.setColor(minuteScaleColor);
        hourScalePaint.setColor(hourScaleColor);
        //test
        minuteHandPaint.setColor(minuteHandColor);
        //test
        hourHandPaint.setColor(minuteHandColor);
        //test
        minuteHandPaint.setStrokeWidth(minuteHandWidth);
        //test
        hourHandPaint.setStrokeWidth(hourHandWidth);
        pointPaint.setColor(centerPointColor);
        circlePaint.setStyle(Paint.Style.FILL);
//        minuteHandLength = (getMeasuredWidth() >> 1) - 25f;
//        hourHandLength = (getMeasuredHeight() >> 1) - 32f;

    }

    public NEUQClock setStyle(ClockStyle style) {
        switch (style) {
            case STROKE: circlePaint.setStyle(Paint.Style.STROKE);
                isHollowOut = true;
                break;
            case FI_AND_SCALE: circlePaint.setStyle(Paint.Style.FILL_AND_STROKE);
                isHollowOut = false;
                break;
            default: circlePaint.setStyle(Paint.Style.FILL);
                isHollowOut = false;
                break;
        }

        return this;
    }

    public NEUQClock setHourScaleColor(String color) {
        this.hourScaleColor = Color.parseColor(color);
        hourScalePaint.setColor(hourScaleColor);
        return this;
    }

    public NEUQClock setMinuteScaleColor(String color) {
        this.minuteScaleColor = Color.parseColor(color);
        minuteScalePaint.setColor(minuteScaleColor);
        return this;
    }

    public NEUQClock setHourHandColor(String color) {
        this.hourHandColor = Color.parseColor(color);
        hourHandPaint.setColor(hourHandColor);
        return this;
    }

    public NEUQClock setMinuteHandColor(String color) {
        this.minuteHandColor = Color.parseColor(color);
        minuteHandPaint.setColor(minuteHandColor);
        return this;
    }

    public NEUQClock setTime(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
        return this;
    }

    public NEUQClock setHourHandLength(float length) {
        this.hourHandLength = length;
        return this;
    }

    public NEUQClock setMinuteHandLength(float length) {
        this.minuteHandLength = length;
        return this;
    }

    public void setUp() {
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getX() + (getWidth() >> 1), getY() + (getHeight() >> 1), getWidth() / 3, circlePaint);
        drawScale(canvas);
        drawMinuteHand(canvas);
        drawHourHand(canvas);
    }

    /**
     * 绘制刻度
     * @param canvas
     */
    private void drawScale(Canvas canvas) {
        canvas.save();
        minuteScalePaint.setStrokeWidth(minuteScaleWidth);
        for (int i = 0; i < 60; i++) {
            if (i % 5 == 0) {
                canvas.drawLine(getMeasuredWidth() / 2, strokeSize, getMeasuredWidth() / 2, strokeSize + minuteScaleLengthLong, hourScalePaint);
            } else {
                canvas.drawLine(getMeasuredWidth() / 2, strokeSize, getMeasuredWidth() / 2, strokeSize + minuteScaleLengthShort, minuteScalePaint);
            }
            canvas.rotate(360f / 60, getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        }

        canvas.restore();
    }

    /**
     * 绘制表针
     * 时针的转动度数为 hour * 30 + minute * 0.5
     * @param canvas
     */
    private void drawHourHand(Canvas canvas) {
        canvas.save();
        canvas.rotate(30f * hour + minute / 2f, getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        canvas.drawLine(getMeasuredWidth() / 2, getMeasuredHeight() >> 1, getMeasuredWidth() / 2, (getMeasuredHeight() >> 1) - hourHandLength, hourHandPaint);
        canvas.restore();
    }

    /**
     * 绘制分针
     * 分针的转动度数为 minute * 6
     * @param canvas
     */
    private void drawMinuteHand(Canvas canvas) {
        canvas.save();
        canvas.rotate(6f * minute, getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        canvas.drawLine(getMeasuredWidth() / 2, getMeasuredHeight() >> 1, getMeasuredWidth() / 2, (getMeasuredHeight() >> 1) - minuteHandLength, minuteHandPaint);
        canvas.restore();
    }


}
