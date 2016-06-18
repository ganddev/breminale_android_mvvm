package de.ahlfeld.breminale.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import java.util.Arrays;

import de.ahlfeld.breminale.R;
import de.ahlfeld.breminale.adapters.SortAdapter;
import de.ahlfeld.breminale.core.repositories.realm.SortOptions;
import de.ahlfeld.breminale.databinding.FragmentSortDialogBinding;
import de.ahlfeld.breminale.viewmodel.ItemSortViewModel;


/**
 * Created by bjornahlfeld on 18.06.16.
 */
public class SortDialog extends DialogFragment implements ItemSortViewModel.OnItemClickListener {

    private FragmentSortDialogBinding binding;

    public SortDialog() {}

    public static SortDialog newInstance() {return new SortDialog(); }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        binding = DataBindingUtil.inflate(getActivity().getLayoutInflater(), R.layout.fragment_sort_dialog, new LinearLayout(getContext()),false);
        setupRecyclerView(binding.rvSortList);
        builder.setView(binding.getRoot());
        return builder.create();
    }

    public void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SortAdapter(Arrays.asList(SortOptions.values()),this));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onItemClick() {
        dismiss();
    }
}
