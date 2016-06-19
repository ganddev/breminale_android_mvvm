package de.ahlfeld.breminale.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import de.ahlfeld.breminale.BreminaleApplication;
import de.ahlfeld.breminale.R;
import de.ahlfeld.breminale.databinding.ActivityImprintBinding;

public class ImprintActivity extends AppCompatActivity {

    private Tracker tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityImprintBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_imprint);
        setSupportActionBar(binding.toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupWebview(binding.webview);

        BreminaleApplication application = (BreminaleApplication) getApplication();
        tracker = application.getDefaultTracker();
    }


    @Override
    protected void onResume() {
        super.onResume();
        tracker.setScreenName("ImprintScreen");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public void setupWebview(@NonNull WebView webview) {
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(false);
        webview.loadUrl("file:///android_asset/imprint.html");
    }
}
