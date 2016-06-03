package de.ahlfeld.breminale.view;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;
import java.util.List;

import de.ahlfeld.breminale.R;
import de.ahlfeld.breminale.adapters.EventAdapter;
import de.ahlfeld.breminale.core.domain.domain.Event;
import de.ahlfeld.breminale.databinding.FragmentEventListBinding;
import de.ahlfeld.breminale.viewmodel.EventListViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventListFragment extends Fragment implements EventListViewModel.DataListener {


    private static final String TAG = EventListFragment.class.getSimpleName();
    private static final String DATE_FROM = "datefrom";
    private static final String DATE_TO = "dateto";
    private static final String LOAD_FAVORITS = "loadfavorits";
    private FragmentEventListBinding binding;
    private EventListViewModel viewModel;

    public EventListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_event_list, container, false);

        Bundle args = getArguments();

        viewModel = new EventListViewModel(this.getContext(), this, new Date(args.getLong(DATE_FROM)), new Date(args.getLong(DATE_FROM)), args.getBoolean(LOAD_FAVORITS));
        binding.setViewModel(viewModel);
        setupRecyclerView(binding.eventsRecyclerView);
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (viewModel != null) {
            viewModel.destroy();
        }
    }


    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setAdapter(new EventAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public static EventListFragment newInstance(Date from, Date to, boolean loadFavorits) {
        EventListFragment fragment = new EventListFragment();
        Bundle args = new Bundle();
        args.putLong(DATE_FROM, from.getTime());
        args.putLong(DATE_TO, to.getTime());
        args.putBoolean(LOAD_FAVORITS, loadFavorits);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onEventsChanged(List<Event> events) {
        Log.i(TAG, "onEventsChanged");
        EventAdapter adapter = (EventAdapter) binding.eventsRecyclerView.getAdapter();
        adapter.setEvents(events);
        adapter.notifyDataSetChanged();
    }
}
