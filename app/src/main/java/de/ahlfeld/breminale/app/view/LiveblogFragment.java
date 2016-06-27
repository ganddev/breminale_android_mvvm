package de.ahlfeld.breminale.app.view;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import de.ahlfeld.breminale.app.BreminaleApplication;
import de.ahlfeld.breminale.app.R;
import de.ahlfeld.breminale.app.databinding.FragmentLiveblogBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class LiveblogFragment extends Fragment {


    private FragmentLiveblogBinding binding;
    private Tracker tracker;

    public LiveblogFragment() {
        // Required empty public constructor
    }

    public static LiveblogFragment newInstance() {
        return new LiveblogFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_liveblog, container, false);
        setupWebview(binding.webview);
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BreminaleApplication application = (BreminaleApplication) getActivity().getApplication();
        tracker = application.getDefaultTracker();
    }

    @Override
    public void onResume() {
        super.onResume();
        tracker.setScreenName("LiveblogScreen");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public void setupWebview(WebView webview) {
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.loadUrl("file:///android_asset/liveblog.html");
    }
}
