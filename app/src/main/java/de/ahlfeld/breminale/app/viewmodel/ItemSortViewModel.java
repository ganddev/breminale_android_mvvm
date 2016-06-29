package de.ahlfeld.breminale.app.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import de.ahlfeld.breminale.app.BreminaleApplication;
import de.ahlfeld.breminale.app.core.SortOrderManager;
import de.ahlfeld.breminale.app.core.repositories.realm.SortOptions;

/**
 * Created by bjornahlfeld on 18.06.16.
 */
public class ItemSortViewModel extends BaseObservable implements ViewModel {

    private OnItemClickListener listener;
    private Context context;
    private SortOptions sortOption;
    public ObservableInt isSelected;
    private Tracker tracker;

    public ItemSortViewModel(@NonNull Context context,@NonNull SortOptions sortOption,@NonNull OnItemClickListener listener) {
        this.context = context;
        this.sortOption = sortOption;
        isSelected = new ObservableInt(View.GONE);
        if(SortOrderManager.getSelectedSortOrder(context) == sortOption) {
            isSelected.set(View.VISIBLE);
        } else {
            isSelected.set(View.GONE);
        }
        this.listener = listener;
        tracker = ((BreminaleApplication)context.getApplicationContext()).getDefaultTracker();
    }

    public String getSortOrder() {
        return context.getString(sortOption.getName());
    }


    public void onItemClick(View view) {
        tracker.send(new HitBuilders.EventBuilder().setCategory("sortItem").setAction("onSortItemClick").build());
        isSelected.set(View.VISIBLE);
        SortOrderManager.saveSortOrder(view.getContext().getApplicationContext(),sortOption);
        if(listener != null) {
            listener.onItemClick();
        }
    }


    public void setData(@NonNull SortOptions sortOption) {
        this.sortOption = sortOption;
        if(SortOrderManager.getSelectedSortOrder(context) == sortOption) {
            isSelected.set(View.VISIBLE);
        } else {
            isSelected.set(View.GONE);
        }
        notifyChange();
    }

    @Override
    public void destroy() {
        listener = null;
        context = null;
    }

    public interface OnItemClickListener{
        void onItemClick();
    }
}
