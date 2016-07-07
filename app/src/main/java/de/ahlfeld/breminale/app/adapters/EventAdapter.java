package de.ahlfeld.breminale.app.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import de.ahlfeld.breminale.app.R;
import de.ahlfeld.breminale.app.core.domain.domain.Event;
import de.ahlfeld.breminale.app.viewmodel.ItemEventViewModel;
import de.ahlfeld.breminale.app.databinding.ItemEventBinding;

/**
 * Created by bjornahlfeld on 05.04.16.
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {


    private List<Event> events;

    public EventAdapter() {
        super();
        this.events = Collections.emptyList();
    }

    public EventAdapter(List<Event> events) {
        super();
        this.events = events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }


    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemEventBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_event,
                parent,
                false);
        return new EventViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        holder.bindEvent(events.get(position));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        final ItemEventBinding binding;

        public EventViewHolder(ItemEventBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindEvent(Event event) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new ItemEventViewModel(itemView.getContext(), event));
            } else {
                binding.getViewModel().destroy();
                binding.getViewModel().setContext(itemView.getContext());
                binding.getViewModel().setEvent(event);
            }
        }
    }
}
