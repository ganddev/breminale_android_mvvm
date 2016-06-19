package de.ahlfeld.breminale.view;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import de.ahlfeld.breminale.BreminaleApplication;
import de.ahlfeld.breminale.R;
import de.ahlfeld.breminale.databinding.FragmentInstagramBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class InstagramFragment extends Fragment {


    private FragmentInstagramBinding binding;
    private Tracker tracker;

    public InstagramFragment() {
        // Required empty public constructor
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
        tracker.setScreenName("InstagramScreen");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public static InstagramFragment newInstance() {
        return new InstagramFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_instagram, container, false);
        setupWebview(binding.webview);
        return binding.getRoot();
    }

    public void setupWebview(@NonNull WebView webview) {
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.loadUrl("file:///android_asset/instagram.html");
    }
}
