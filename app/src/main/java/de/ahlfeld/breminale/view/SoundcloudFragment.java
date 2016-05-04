package de.ahlfeld.breminale.view;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.ahlfeld.breminale.R;
import de.ahlfeld.breminale.databinding.FragmentSoundcloudBinding;
import de.ahlfeld.breminale.viewmodel.SoundcloudViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class SoundcloudFragment extends Fragment {


    private SoundcloudViewModel viewModel;
    private FragmentSoundcloudBinding binding;

    public SoundcloudFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_soundcloud,container,false);
        viewModel = new SoundcloudViewModel(getContext(), 3207);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }


    @Override
    public void onDestroy() {
        viewModel.destroy();
        super.onDestroy();
    }
}
