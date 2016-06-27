package de.ahlfeld.breminale.app.view;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.Date;
import java.util.List;

import de.ahlfeld.breminale.app.BreminaleApplication;
import de.ahlfeld.breminale.app.R;
import de.ahlfeld.breminale.app.adapters.EventAdapter;
import de.ahlfeld.breminale.app.core.domain.domain.Event;
import de.ahlfeld.breminale.app.databinding.FragmentEventListBinding;
import de.ahlfeld.breminale.app.viewmodel.EventListViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventListFragment extends Fragment implements EventListViewModel.DataListener {


    private static final String TAG = EventListFragment.class.getSimpleName();
    private static final String DATE_FROM = "datefrom";
    private static final String DATE_TO = "dateto";
    private FragmentEventListBinding binding;
    private EventListViewModel viewModel;
    private Tracker tracker;

    public EventListFragment() {
        // Required empty public constructor
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
        tracker.setScreenName("EventListScreen");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_event_list, container, false);

        Bundle args = getArguments();

        setupRecyclerView(binding.eventsRecyclerView);

        viewModel = new EventListViewModel(this.getContext(), this, new Date(args.getLong(DATE_FROM)), new Date(args.getLong(DATE_TO)));
        binding.setViewModel(viewModel);
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

    public static EventListFragment newInstance(Date from, Date to) {
        EventListFragment fragment = new EventListFragment();
        Bundle args = new Bundle();
        args.putLong(DATE_FROM, from.getTime());
        args.putLong(DATE_TO, to.getTime());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onEventsChanged(@NonNull List<Event> events) {
        Log.i(TAG, "onEventsChanged size of events: " +events.size());
        EventAdapter adapter = (EventAdapter) binding.eventsRecyclerView.getAdapter();
        adapter.setEvents(events);
        adapter.notifyDataSetChanged();
    }
}
