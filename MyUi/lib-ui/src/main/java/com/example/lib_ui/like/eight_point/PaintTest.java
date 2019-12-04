package com.example.lib_ui.like.eight_point;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.example.lib_ui.R;

import androidx.annotation.Nullable;

/**
 * Time:2019/11/30 0:05
 * Author: han1254
 * Email: 1254763408@qq.com
 * Function:
 */
public class PaintTest extends View {

    private Paint paintTest;
    private int x;
    private int y;
    private int width;
    private int height;
    private float positionX = 0f;
    private float positionY = 0f;
    private float radius = 30f;
    private float centerX = 0, centerY = 0;
    private ValueAnimator animator = new ValueAnimator();
    private ValueAnimator moveAnimator = new ValueAnimator();
    private ValueAnimator moveAnimatorY = new ValueAnimator();
    private ImageView imageView;
    private int dotCount = 8;
    private float singleAngle = 360 / 8;

    private AnimatorSet animatorSet;



    public PaintTest(Context context) {
        super(context);
        setWillNotDraw(false);
        initData();
    }

    public PaintTest(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        initData();
    }

    public PaintTest(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        initData();
    }


    private void initData() {
        paintTest = new Paint();
        paintTest.setColor(Color.TRANSPARENT);
        paintTest.setStyle(Paint.Style.FILL);
        animator = ValueAnimator.ofFloat(0f, 30f, 0f);
        moveAnimator = ValueAnimator.ofFloat(0, 30f);
        animatorSet = new AnimatorSet();
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < dotCount; i++) {
            float cX = (float) (positionX + (float)width/2 + width * (Math.cos(Math.PI * i * singleAngle / 180)));
            float cY = (float) (positionY + (float)height/2 - height * (Math.sin(Math.PI * i * singleAngle / 180)));
            canvas.drawCircle(cX, cY, radius, paintTest);
        }


    }


    public void show() {

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                paintTest.setColor(getResources().getColor(R.color.primary));
                radius = (float)valueAnimator.getAnimatedValue();
                invalidate();
            }
        });

        moveAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                centerX = (float)valueAnimator.getAnimatedValue();
                invalidate();
            }
        });

        moveAnimatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                centerY = (float)valueAnimator.getAnimatedValue();
                invalidate();
            }
        });

        animatorSet.playTogether(animator, moveAnimator, moveAnimatorY);
        animatorSet.setDuration(500);
        animatorSet.start();

//        animator.start();
    }



    public PaintTest setX(int x) {
        this.x = x;
        return this;
    }

    public PaintTest setY(int y) {
        this.y = y;
        return this;
    }

    public PaintTest setWidth(int width) {
        this.width = width;
        return this;
    }

    public PaintTest setHeight(int height) {
        this.height = height;
        return this;
    }

    public PaintTest setImag(ImageView imag) {
        this.imageView = imag;
        this.positionX = imageView.getX();
        this.positionY = imageView.getY();
        this.height = imag.getHeight();
        this.width = imag.getWidth();
        moveAnimator = ValueAnimator.ofFloat(positionX + (float)width / 2,  positionX + (float)width + 20f);
        moveAnimatorY = ValueAnimator.ofFloat(positionY + (float)height / 2, positionY - 20f);
        return this;
    }

    public PaintTest setSize(int width, int height) {
        this.height = height;
        this.width = width;
        moveAnimator = ValueAnimator.ofFloat((float)x, (float)width/2 + (float)x + 20f);
        return this;
    }

    public PaintTest setAnim(int x, int y, int width, int height) {
        this.height = height;
        this.width = width;
        this.centerX = x;
        this.centerY = y;
        moveAnimator = ValueAnimator.ofFloat((float)x, (float)width/2 + (float)x + 20f);
        moveAnimatorY = ValueAnimator.ofFloat((float)y, (float)y - (float)height/2 - 20f);
        return this;
    }

    public PaintTest setPosition(int x, int y) {
        this.centerX = x;
        this.centerY = y;
        return this;
    }

    public void setUp() {

    }


}
