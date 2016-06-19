package de.ahlfeld.breminale.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import de.ahlfeld.breminale.BreminaleApplication;
import de.ahlfeld.breminale.R;
import de.ahlfeld.breminale.databinding.FragmentMoreBinding;
import de.ahlfeld.breminale.viewmodel.MoreViewModel;

/**
 * Created by bjornahlfeld on 18.06.16.
 */
public class MoreFragment extends Fragment {

    private FragmentMoreBinding binding;

    private MoreViewModel viewModel;
    private Tracker tracker;

    public MoreFragment() {
    }

    public static MoreFragment newInstance() {
        return new MoreFragment();
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
        tracker.setScreenName("MoreScreen");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_more, container, false);

        viewModel = new MoreViewModel(getContext());

        binding.setViewModel(viewModel);

        return binding.getRoot();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.destroy();
    }
}
