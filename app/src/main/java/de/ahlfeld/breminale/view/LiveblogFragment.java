package de.ahlfeld.breminale.view;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import de.ahlfeld.breminale.R;
import de.ahlfeld.breminale.databinding.FragmentLiveblogBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class LiveblogFragment extends Fragment {


    private FragmentLiveblogBinding binding;

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

    public void setupWebview(WebView webview) {
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.loadUrl("file:///android_asset/liveblog.html");
    }
}
