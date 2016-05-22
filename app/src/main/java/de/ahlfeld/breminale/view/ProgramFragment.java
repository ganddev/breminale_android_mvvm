package de.ahlfeld.breminale.view;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.ahlfeld.breminale.R;
import de.ahlfeld.breminale.adapters.ProgramPageAdapter;
import de.ahlfeld.breminale.databinding.FragmentProgramBinding;
import de.ahlfeld.breminale.viewmodel.ProgramViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProgramFragment extends Fragment implements ViewPager.OnPageChangeListener {

    private FragmentProgramBinding binding;

    private ProgramViewModel viewModel;
    public static ProgramFragment newInstance() {
        return new ProgramFragment();
    }

    public ProgramFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_program,container,false);
        ProgramPageAdapter adapter = new ProgramPageAdapter(getChildFragmentManager());
        viewModel = new ProgramViewModel(getContext(),adapter);
        binding.setViewModel(viewModel);
        binding.viewpager.setAdapter(adapter);
        binding.viewpager.addOnPageChangeListener(this);
        binding.circles.setViewPager(binding.viewpager);
        return binding.getRoot();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        viewModel.onPageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
