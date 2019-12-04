package com.example.lib_ui.like.scale_circle_dots;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.provider.CalendarContract;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationSet;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * Time:2019/11/29 18:43
 * Author: han1254
 * Email: 1254763408@qq.com
 * Function:
 */
public class Dots extends View {

    //在需要的View周围，将360度分成DOT_COUNT份，围绕
    //着View的一周绘制圆形
    private int bigDotsCount = 7;
    private int smallDotsCount = 7;
    private float bigDotsAngle = 360 / bigDotsCount;
    private float smallDotsAngle = 360 / smallDotsCount;
    private float MAX_BIG_DOT_R = 6f;
    private float MAX_SMALL_DOT_R = 3f;

    private Paint paintBigDot;
    private Paint paintSmallDot;

    private int viewWidth = 0;
    private int viewHeight = 0;
    private int centerX = 0;
    private int centerY = 0;
    private int colorBigDot = Color.parseColor("#48CFC2");
    private int colorSmallDot = Color.parseColor("#5BA2E9");
    private float radiusBigDot = MAX_BIG_DOT_R;
    private float radiusSmallDot = MAX_SMALL_DOT_R;
    private int durationBigDot = 300;
    private int durationSmallDot = 300;

    //动画集合，批量控制动画
    private AnimatorSet animatorSet;

    public Dots(Context context) {
        super(context);
        setWillNotDraw(false);
        initData();
    }

    public Dots(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        initData();
    }

    public Dots(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        initData();
    }


    private void initData() {
        animatorSet = new AnimatorSet();
        paintBigDot = new Paint();
        paintSmallDot = new Paint();
        paintBigDot.setColor(Color.TRANSPARENT);
        paintSmallDot.setColor(Color.TRANSPARENT);
        //防锯齿
        paintBigDot.setAntiAlias(true);
        paintSmallDot.setAntiAlias(true);
        paintBigDot.setStyle(Paint.Style.FILL);
        paintSmallDot.setStyle(Paint.Style.FILL);
    }

    /**
     * 设置大点的个数
     * @param num
     */
    public void setBigDotNum(int num) {
        this.bigDotsAngle = num;
        invalidate();
    }

    public void setSmallDotsCount(int num) {
        this.smallDotsCount = num;
        invalidate();
    }

    /**
     * 设置点的颜色
     * @param colorBig
     * @param colorSmall
     */
    public void setDotsColor(int colorBig, int colorSmall) {
        this.colorBigDot = colorBig;
        this.colorSmallDot = colorSmall;
        //重绘View树
        invalidate();
    }

    /**
     * 设置点所在的View的大小
     * @param width
     * @param height
     */
    public void setViewSize(int width, int height) {
        this.viewWidth = width;
        this.viewHeight = height;
        invalidate();
    }

    /**
     * 设置点的持续时间
     * @param bigDuration
     * @param smallDuration
     */
    public void setDuration(int bigDuration, int smallDuration) {
        this.durationBigDot = bigDuration;
        this.durationSmallDot = smallDuration;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (viewWidth != 0 && viewHeight != 0) {
            setMeasuredDimension(viewWidth, viewHeight);
            //设置中点坐标
            centerX = viewWidth / 2;
            centerY = viewHeight / 2;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //(i * smallDotsAngle) * Math.PI / 180是获得角度的弧度值

        for (int i = 0; i < bigDotsCount; i++) {
            double cX = centerX + (centerX - 10) * Math.cos((i * bigDotsAngle) * Math.PI / 180);
            double cY = centerY + (centerY - 10) * Math.sin((i * bigDotsAngle) * Math.PI / 180);
            canvas.drawCircle(594f, 1005f, 30, paintBigDot);
        }

        for (int i = 0; i < smallDotsCount; i++) {
            double cX = centerX + (centerX - 20) * Math.cos((i * smallDotsAngle) * Math.PI / 180);
            double cY = centerY + (centerY - 20) * Math.sin((i * smallDotsAngle) * Math.PI / 180);
        }
    }

    public void dismiss() {
        //让点的半径缩回0
        ValueAnimator animatorBigDot = ValueAnimator.ofFloat(radiusBigDot, 0f);
        ValueAnimator animatorSmallDot = ValueAnimator.ofFloat(radiusSmallDot, 0f);
        animatorBigDot.setDuration(durationBigDot);
        animatorSmallDot.setDuration(durationSmallDot);
        animatorBigDot.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                radiusBigDot = (float)valueAnimator.getAnimatedValue();
                invalidate();
            }
        });

        animatorSmallDot.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                radiusSmallDot = (float)valueAnimator.getAnimatedValue();
                invalidate();
            }
        });

        animatorSet.playTogether(animatorBigDot, animatorSmallDot);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                paintBigDot.setColor(Color.TRANSPARENT);
                paintSmallDot.setColor(Color.TRANSPARENT);

                radiusBigDot = MAX_BIG_DOT_R;
                radiusSmallDot = MAX_SMALL_DOT_R;
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                //动画结束后还原
                paintBigDot.setColor(Color.TRANSPARENT);
                paintSmallDot.setColor(Color.TRANSPARENT);

                radiusBigDot = MAX_BIG_DOT_R;
                radiusSmallDot = MAX_SMALL_DOT_R;
                invalidate();
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animatorSet.start();
    }

    public void cancelAnim() {
        animatorSet.cancel();
    }

    public void show() {
        paintBigDot.setColor(colorBigDot);
        paintSmallDot.setColor(colorSmallDot);
        animatorSet.start();
        invalidate();
    }
}
