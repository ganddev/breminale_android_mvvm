package de.ahlfeld.breminale.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.Arrays;

import de.ahlfeld.breminale.BreminaleApplication;
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
    private Tracker tracker;

    public SortDialog() {}

    public static SortDialog newInstance() {return new SortDialog(); }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BreminaleApplication application = (BreminaleApplication) getActivity().getApplication();
        tracker = application.getDefaultTracker();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        binding = DataBindingUtil.inflate(getActivity().getLayoutInflater(), R.layout.fragment_sort_dialog, new LinearLayout(getContext()),false);
        setupRecyclerView(binding.rvSortList);
        builder.setView(binding.getRoot());
        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
        tracker.setScreenName("SortScreen");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
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
