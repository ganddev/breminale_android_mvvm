package de.ahlfeld.breminale.app.view;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.leakcanary.RefWatcher;

import de.ahlfeld.breminale.app.BreminaleApplication;
import de.ahlfeld.breminale.app.R;
import de.ahlfeld.breminale.app.adapters.ProgramPageAdapter;
import de.ahlfeld.breminale.app.databinding.FragmentProgramBinding;
import de.ahlfeld.breminale.app.viewmodel.ProgramViewModel;




/**
 * A simple {@link Fragment} subclass.
 */
public class ProgramFragment extends Fragment implements ViewPager.OnPageChangeListener, ProgramViewModel.OnPageChangeListener, ProgramViewModel.SortClickListener {

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
        viewModel = new ProgramViewModel(getContext().getApplicationContext(),adapter,this, this);
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

    @Override
    public void pageChanged(int currentPage) {
        if(binding.viewpager != null) {
            binding.viewpager.setCurrentItem(currentPage, true);
        }
    }

    @Override
    public void onSortClick() {
        SortDialog sortDialog = SortDialog.newInstance();
        sortDialog.show(getFragmentManager(), "sortdialog");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(viewModel != null) {
            viewModel.destroy();
        }
        RefWatcher refWatcher = BreminaleApplication.getRefWatcher(getContext());
        refWatcher.watch(this);
    }
}
