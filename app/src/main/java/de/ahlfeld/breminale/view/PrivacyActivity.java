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
import de.ahlfeld.breminale.databinding.ActivityPrivacyBinding;

public class PrivacyActivity extends AppCompatActivity {

    private Tracker tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPrivacyBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_privacy);
        setSupportActionBar(binding.toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupWebview(binding.webview);

        BreminaleApplication application = (BreminaleApplication) getApplication();
        tracker = application.getDefaultTracker();
    }

    public void setupWebview(@NonNull WebView webview) {
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(false);
        webview.loadUrl("file:///android_asset/privacy.html");
    }

    @Override
    public void onResume() {
        super.onResume();
        tracker.setScreenName("PrivacyScreen");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

}
