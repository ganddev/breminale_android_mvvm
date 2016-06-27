package de.ahlfeld.breminale.app.bindings;

import android.databinding.BindingAdapter;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.ahlfeld.breminale.app.R;
import de.ahlfeld.breminale.app.animations.ResizeAnimation;
import de.ahlfeld.breminale.app.caches.FontCache;

/**
 * Created by bjornahlfeld on 12.04.16.
 */
public class Bindings {

    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
    private static final String TAG = Bindings.class.getSimpleName();

    @BindingAdapter({"font"})
    public static void setFont(TextView textView, String fontName) {
        textView.setTypeface(FontCache.getInstance().get(fontName));
    }

    @BindingAdapter({"font"})
    public static void setFont(Button button, String fontName) {
        button.setTypeface(FontCache.getInstance().get(fontName));
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        if(!imageUrl.isEmpty()) {
            Glide.with(view.getContext())
                    .load(imageUrl)
                    .centerCrop()
                    .into(view);
        } else {
            view.setImageResource(R.mipmap.empty_image);
        }
    }

    @BindingAdapter({"isFavorit"})
    public static void isFavorit(ImageView iv, boolean isFavorit) {
        if(isFavorit) {
            iv.setImageResource(R.mipmap.favorit_selected);
        } else {
            iv.setImageResource(R.mipmap.favorit_menu);
        }
    }

    @BindingAdapter({"favoritList"})
    public static void favoritList(ImageView iv, boolean isFavorit) {
        if(isFavorit) {
            iv.setImageResource(R.drawable.is_favorit_list);
        } else {
            iv.setImageResource(R.mipmap.favorit_menu);
        }
    }

    @BindingAdapter("android:layout_height")
    public static void setLayoutHeight(TextView view, float height) {
        ResizeAnimation resizeAnimation = new ResizeAnimation(view, (int)height, view.getHeight());
        resizeAnimation.setDuration(1200);
        view.startAnimation(resizeAnimation);
    }

}
