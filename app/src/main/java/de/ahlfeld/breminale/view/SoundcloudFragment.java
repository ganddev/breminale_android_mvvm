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


    private static final String SOUNDCLOUD_USER_ID = "soundclouduserid";
    private SoundcloudViewModel viewModel;

    private FragmentSoundcloudBinding binding;

    public SoundcloudFragment() {
        // Required empty public constructor
    }

    public static SoundcloudFragment newInstance(long soundcloudUsersId) {
        SoundcloudFragment sFm = new SoundcloudFragment();
        Bundle args = new Bundle();
        args.putLong(SOUNDCLOUD_USER_ID, soundcloudUsersId);
        sFm.setArguments(args);
        return sFm;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_soundcloud,container,false);
        Bundle args  = getArguments();
        if(!args.containsKey(SOUNDCLOUD_USER_ID)) {
            throw new IllegalStateException("soundcloud user id is not set");
        }
        long soundcloudUserId = args.getLong(SOUNDCLOUD_USER_ID);
        viewModel = new SoundcloudViewModel(getContext(), soundcloudUserId);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }


    @Override
    public void onDestroy() {
        viewModel.destroy();
        super.onDestroy();
    }
}
