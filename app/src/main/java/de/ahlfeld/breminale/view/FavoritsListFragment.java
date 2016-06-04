package de.ahlfeld.breminale.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import de.ahlfeld.breminale.R;
import de.ahlfeld.breminale.adapters.EventAdapter;
import de.ahlfeld.breminale.core.domain.domain.Event;
import de.ahlfeld.breminale.databinding.FragmentFavoritsListBinding;
import de.ahlfeld.breminale.viewmodel.FavoritsListViewModel;



/**
 * Created by bjornahlfeld on 03.06.16.
 */
public class FavoritsListFragment extends Fragment implements FavoritsListViewModel.DataListener, FavoritsListViewModel.Navigation {

    private static final String TAG = FavoritsListFragment.class.getSimpleName();
    private FavoritsListViewModel viewModel;
    private FragmentFavoritsListBinding binding;


    public static FavoritsListFragment newInstance() {
        return new FavoritsListFragment();
    }
    public FavoritsListFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorits_list, container, false);

        setupRecyclerView(binding.eventsRecyclerView);

        viewModel = new FavoritsListViewModel(this.getContext(),this, this);

        return binding.getRoot();
    }

    @Override
    public void onFavoritsChanged(List<Event> events) {
        Log.i(TAG, "onFavoritsChanged");
        EventAdapter adapter = (EventAdapter) binding.eventsRecyclerView.getAdapter();
        adapter.setEvents(events);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (viewModel != null) {
            viewModel.destroy();
        }
    }

    public void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setAdapter(new EventAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onProgramClick() {
        Log.d(TAG, "onProgramClick");
        ((MainActivity)getActivity()).showProgram();
    }
}
