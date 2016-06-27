package de.ahlfeld.breminale.app.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import de.ahlfeld.breminale.app.R;
import de.ahlfeld.breminale.app.core.repositories.realm.SortOptions;
import de.ahlfeld.breminale.app.databinding.ItemSortBinding;
import de.ahlfeld.breminale.app.viewmodel.ItemSortViewModel;

/**
 * Created by bjornahlfeld on 18.06.16.
 */
public class SortAdapter extends RecyclerView.Adapter<SortAdapter.SortItemViewHolder> {


    private final List<SortOptions> sortItems;
    private final ItemSortViewModel.OnItemClickListener listener;

    public SortAdapter(@NonNull List<SortOptions> sortItems,@NonNull ItemSortViewModel.OnItemClickListener listener){
        super();
        this.sortItems = sortItems;
        this.listener = listener;
    }

    @Override
    public SortItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemSortBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_sort,
                parent,
                false);
        return new SortItemViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(SortItemViewHolder holder, int position) {
        holder.bindSortItem(sortItems.get(position));
    }

    @Override
    public int getItemCount() {
        return this.sortItems.size();
    }

    public static class SortItemViewHolder extends RecyclerView.ViewHolder {

        private final ItemSortBinding binding;
        private final ItemSortViewModel.OnItemClickListener listener;

        public SortItemViewHolder(ItemSortBinding binding, ItemSortViewModel.OnItemClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
        }

        void bindSortItem(SortOptions sortItem) {
           if (binding.getViewModel() == null) {
                binding.setViewModel(new ItemSortViewModel(itemView.getContext(), sortItem, listener));
            } else {
                binding.getViewModel().setData(sortItem);
            }
        }
    }
}
