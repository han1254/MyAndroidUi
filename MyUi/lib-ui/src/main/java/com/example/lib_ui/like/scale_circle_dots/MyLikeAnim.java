package com.example.lib_ui.like.scale_circle_dots;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.example.lib_ui.R;

/**
 * Time:2019/11/29 18:47
 * Author: han1254
 * Email: 1254763408@qq.com
 * Function:
 */
public class MyLikeAnim extends PopupWindow {

    private ImageView imageView;
    private Dots dots;

    private int imgRes;
    private ImageView targetView;
    private int viewWith = 0;
    private int viewHeight = 0;
    private int bigColor = Color.parseColor("#48CFC2");
    private int smallColor = Color.parseColor("#5BA2E9");
    private int bigCount = 0;
    private int smallCount = 0;
    private View contentView;
    private Builder builder;

    private ObjectAnimator imgScalXAnimator;
    private ObjectAnimator imgScalYAnimator;
    private AnimatorSet animatorSet;

    private int SCALE_FACTOR = 2;
    private int ANIM_DURATION = 600;

    private MyLikeAnim(Builder builder, Context context) {
        this.builder = builder;
        contentView = LayoutInflater.from(context).inflate(R.layout.circle_dots_layout, null);
        this.viewWith = builder.viewWith;
        this.viewHeight = builder.viewHeight;
        this.bigColor = builder.bigColor;
        this.smallColor = builder.smallColor;
        this.bigCount = builder.bigCount;
        this.smallCount = builder.smallCount;
        dots = contentView.findViewById(R.id.dots_around);
        imageView = contentView.findViewById(R.id.img_like);
        initData();
    }

    private void initData() {
        this.animatorSet = new AnimatorSet();
        //设置图片动画为从小到大到小
        imgScalXAnimator = ObjectAnimator.ofFloat(imageView, "scaleX", 1.5f, 1f);
        imgScalYAnimator = ObjectAnimator.ofFloat(imageView, "scaleY", 1.5f, 1f);
//        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //
        builder.targetView.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams params = imageView.getLayoutParams();
                //如果没有设置padding, 则imageView宽高设置给params
                if (builder.targetView.getPaddingBottom() == 0
                        && builder.targetView.getPaddingTop() == 0
                        && builder.targetView.getPaddingLeft() == 0
                        && builder.targetView.getPaddingRight() == 0) {
                    params.width = builder.viewWith;
                    params.height = builder.viewHeight;
                } else {
                    //否则，获取图片的原始大小
                    params.width = builder.targetView.getDrawable().getBounds().width();
                    params.height = builder.targetView.getDrawable().getBounds().height();
                }

                imageView.setImageResource(builder.imgRes);
                imageView.setLayoutParams(params);

                viewWith = params.width * SCALE_FACTOR;
                viewHeight = params.height * SCALE_FACTOR;

                Log.e("Color", "setting color");
                dots.setDotsColor(bigColor, smallColor);
                dots.setViewSize(viewWith, viewHeight);

                animatorSet.setDuration(ANIM_DURATION);
                //???????
                animatorSet.setInterpolator(new BounceInterpolator());
                animatorSet.playTogether(imgScalXAnimator, imgScalYAnimator);
                animatorSet.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        dots.show();
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        dots.dismiss();
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {
                        dismiss();
                    }
                });
            }
        });

    }

    public void show() {
        int[] locations = new int[2];
        builder.targetView.getLocationInWindow(locations);
        int x = locations[0] + builder.viewWith / 2 - viewWith / 2;
        int y = locations[1] + builder.viewHeight / 2 - viewHeight / 2;
        showAtLocation(imageView, Gravity.NO_GRAVITY, x, y);
        dots.show();
        animatorSet.start();
    }


    public static final class Builder {
        private int imgRes;
        private ImageView targetView;
        private int viewWith = 0;
        private int viewHeight = 0;
        private int bigColor = Color.parseColor("#48CFC2");
        private int smallColor = Color.parseColor("#5BA2E9");
        private int bigCount = 0;
        private int smallCount = 0;

        public Builder setTargetImg(ImageView img) {
            this.targetView = img;
//            viewWith = img.getWidth();
//            viewHeight = img.getHeight();
            viewWith = 300;
            viewHeight = 300;
            return this;
        }

        public Builder setReplaceImg(int imgRes) {
            this.imgRes = imgRes;
            return this;
        }

        public Builder setColors(String bigColor, String smallColor) {
            setColors(Color.parseColor(bigColor), Color.parseColor(smallColor));
            return this;
        }

        public Builder setColors(int bigColor, int smallColor) {
            this.bigColor = bigColor;
            this.smallColor = smallColor;
            return this;
        }

        public Builder setDotsCount(int bigCount, int smallCount) {
            this.bigCount = bigCount;
            this.smallCount = smallCount;
            return this;
        }

        public MyLikeAnim build() {
            return new MyLikeAnim(this, targetView.getContext());
        }
    }
}
