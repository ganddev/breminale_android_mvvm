package de.ahlfeld.breminale.view;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import de.ahlfeld.breminale.R;
import de.ahlfeld.breminale.adapters.EventAdapter;
import de.ahlfeld.breminale.databinding.FragmentEventListBinding;
import de.ahlfeld.breminale.models.Event;
import de.ahlfeld.breminale.viewmodel.EventListViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventListFragment extends Fragment implements EventListViewModel.DataListener {


    private FragmentEventListBinding binding;
    private EventListViewModel viewModel;

    public EventListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_event_list,container,false);
        viewModel = new EventListViewModel(this.getContext(), this);
        binding.setViewModel(viewModel);
        setupRecyclerView(binding.eventsRecyclerView);
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.destroy();
    }

    @Override
    public void onEventsChanged(List<Event> events) {

    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setAdapter(new EventAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
