package com.example.lib_ui.like.test;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.widget.ImageView;

/**
 * Time:2019/11/29 23:47
 * Author: han1254
 * Email: 1254763408@qq.com
 * Function: 为图片设置放缩效果的测试类
 */
public class Test {

    private ImageView imageView;

    AnimatorSet set;

    public Test(ImageView imageView) {
        this.imageView = imageView;
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(imageView, "scaleX", 2f, 1f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(imageView, "scaleY", 2f, 1f);
        set = new AnimatorSet();
        set.playTogether(animatorX, animatorY);
    }

    public void scale() {

        int x = imageView.getWidth();
        int y = imageView.getHeight();

        set.start();
    }
}
