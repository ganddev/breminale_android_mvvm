package de.ahlfeld.breminale.view;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import de.ahlfeld.breminale.R;
import de.ahlfeld.breminale.databinding.FragmentInstagramBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class InstagramFragment extends Fragment {


    private FragmentInstagramBinding binding;

    public InstagramFragment() {
        // Required empty public constructor
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
