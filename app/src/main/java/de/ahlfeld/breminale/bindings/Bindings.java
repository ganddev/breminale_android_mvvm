package de.ahlfeld.breminale.bindings;

import android.databinding.BindingAdapter;
import android.widget.TextView;

import de.ahlfeld.breminale.caches.FontCache;

/**
 * Created by bjornahlfeld on 12.04.16.
 */
public class Bindings {

    @BindingAdapter({"bind:font"})
    public static void setFont(TextView textView, String fontName) {
        textView.setTypeface(FontCache.getInstance().get(fontName));
    }
}
