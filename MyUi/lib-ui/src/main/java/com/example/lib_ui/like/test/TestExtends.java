package com.example.lib_ui.like.test;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.view.View;
import android.widget.ImageView;

/**
 * Time:2019/12/1 0:12
 * Author: han1254
 * Email: 1254763408@qq.com
 * Function: 为图片设置各种动画效果的叠加
 */
 public class TestExtends {
    private ImageView imageView;
    private AnimatorSet animatorSet;
    public TestExtends(Builder builder) {
        this.animatorSet = builder.animatorSet;
    }
    public void startAnim() {
        animatorSet.start();
    }

    public static final class Builder {
        private ImageView imageView;
        private AnimatorSet animatorSet;

        public Builder() {
            animatorSet = new AnimatorSet();
        }

        /**
         * 设置放缩效果
         * @param scaleImageView
         * @param isScaleX
         * @param isScaleY
         * @param duration
         * @param scaleAnimValue
         * @return
         */
        public Builder setScaleAnim(ImageView scaleImageView, boolean isScaleX, boolean isScaleY, int duration, float...scaleAnimValue) {
            if (isScaleX) {
                ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(scaleImageView, "scaleX", scaleAnimValue);
                objectAnimatorX.setDuration(duration);
//                objectAnimatorX.setInterpolator(new BounceInterpolator());
                animatorSet.playTogether(objectAnimatorX);
            }

            if (isScaleY) {
                ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(scaleImageView, "scaleY", scaleAnimValue);
                objectAnimatorY.setDuration(duration);
//                objectAnimatorY.setInterpolator(new BounceInterpolator());
                animatorSet.playTogether(objectAnimatorY);
            }
            return this;
        }

        /**
         *
         * @param rotationImageView
         * @param duration
         * @param rotationAnimValue
         * @return
         */
        public Builder setRotationAnim(ImageView rotationImageView, int duration, float...rotationAnimValue) {
            ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(rotationImageView, "rotation", rotationAnimValue);
            rotationAnimator.setDuration(duration);
//            rotationAnimator.setInterpolator(new BounceInterpolator());
            animatorSet.playTogether(rotationAnimator);
            return this;
        }

        public Builder setScaleAnim(View scaleImageView, boolean isScaleX, boolean isScaleY, int duration, float...scaleAnimValue) {
            if (isScaleX) {
                ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(scaleImageView, "scaleX", scaleAnimValue);
                objectAnimatorX.setDuration(duration);
//                objectAnimatorX.setInterpolator(new BounceInterpolator());
//                objectAnimatorX.setInterpolator(new OvershootInterpolator());
                animatorSet.playTogether(objectAnimatorX);
            }

            if (isScaleY) {
                ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(scaleImageView, "scaleY", scaleAnimValue);
                objectAnimatorY.setDuration(duration);
//                objectAnimatorY.setInterpolator(new BounceInterpolator());
//                objectAnimatorY.setInterpolator(new OvershootInterpolator());
                animatorSet.playTogether(objectAnimatorY);
            }
            return this;
        }

        /**
         * 设置旋转效果
         * @param rotationImageView
         * @param duration
         * @param rotationAnimValue
         * @return
         */
        public Builder setRotationAnim(View rotationImageView, int duration, float...rotationAnimValue) {
            ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(rotationImageView, "rotation", rotationAnimValue);
            rotationAnimator.setDuration(duration);
//            rotationAnimator.setInterpolator(new BounceInterpolator());
            animatorSet.playTogether(rotationAnimator);
            return this;
        }

        public Builder setXTransation(View targetView, int duration, float...transXValue) {
            ObjectAnimator transXAnimator = ObjectAnimator.ofFloat(targetView, "translationX", transXValue);
            transXAnimator.setDuration(duration);
            animatorSet.playTogether(transXAnimator);
            return this;
        }
        public Builder setYTransation(View targetView, int duration, float...transYValue) {
            ObjectAnimator transYAnimator = ObjectAnimator.ofFloat(targetView, "translationY", transYValue);
            transYAnimator.setDuration(duration);
            animatorSet.playTogether(transYAnimator);
            return this;
        }

        public Builder setCirclePath(View targetView, int duration, float centerX, float centerY, float radius) {

            Path path = new android.graphics.Path();
            path.addCircle(centerX, centerY, radius, Path.Direction.CCW);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                ObjectAnimator circlePathAnimator = ObjectAnimator.ofFloat(targetView,View.X, View.Y, path);
                circlePathAnimator.setDuration(duration);
                animatorSet.playTogether(circlePathAnimator);
            }
            return this;
        }


        public TestExtends build() {
//            animatorSet.setInterpolator(new AccelerateInterpolator());
//            animatorSet.setInterpolator(new BounceInterpolator());
            return new TestExtends(this);
        }
   }
}
