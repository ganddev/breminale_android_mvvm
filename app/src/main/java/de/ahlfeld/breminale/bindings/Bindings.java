package de.ahlfeld.breminale.bindings;

import android.databinding.BindingAdapter;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.ahlfeld.breminale.animations.ResizeAnimation;
import de.ahlfeld.breminale.caches.FontCache;

/**
 * Created by bjornahlfeld on 12.04.16.
 */
public class Bindings {

    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();

    @BindingAdapter({"bind:font"})
    public static void setFont(TextView textView, String fontName) {
        textView.setTypeface(FontCache.getInstance().get(fontName));
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.with(view.getContext())
                .load(imageUrl)
                .centerCrop()
                .into(view);
    }

    @BindingAdapter("android:layout_height")
    public static void setLayoutHeight(TextView view, float height) {
        ResizeAnimation resizeAnimation = new ResizeAnimation(view, (int)height, view.getHeight());
        resizeAnimation.setDuration(1200);
        view.startAnimation(resizeAnimation);
    }

}
