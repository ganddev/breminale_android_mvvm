package de.ahlfeld.breminale.animations;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by bjornahlfeld on 03.05.16.
 */
public class ResizeAnimation extends Animation {

    private static final String TAG = ResizeAnimation.class.getSimpleName();
    final int targetHeight;
    View view;
    int startHeight;

    public ResizeAnimation(View view, int targetHeight, int startHeight) {
        this.view = view;
        this.targetHeight = targetHeight;
        this.startHeight = startHeight;
    }


    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        int newHeight = 0;
        if(startHeight < targetHeight) {
            newHeight = (int) (startHeight + targetHeight * interpolatedTime);
        } else {
            newHeight = (int) (startHeight - targetHeight * interpolatedTime);
        }
        view.getLayoutParams().height = newHeight;
        view.requestLayout();
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
    }

    @Override
    public boolean willChangeBounds() {
        return super.willChangeBounds();
    }
}
