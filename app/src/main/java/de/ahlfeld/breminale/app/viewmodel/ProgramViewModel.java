package de.ahlfeld.breminale.app.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.ObservableField;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import de.ahlfeld.breminale.app.R;
import de.ahlfeld.breminale.app.adapters.ProgramPageAdapter;
import de.ahlfeld.breminale.app.core.SortOrderManager;

/**
 * Created by bjornahlfeld on 22.05.16.
 */
public class ProgramViewModel implements ViewModel, SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = ProgramViewModel.class.getSimpleName();
    private final ProgramPageAdapter programPageAdapter;
    private Context context;

    private int currentPage;

    public ObservableField<String> date;

    public ObservableField<String> day;

    public ObservableField<String> sortOrder;


    private OnPageChangeListener mListener;
    private SortClickListener mSortListener;

    public ProgramViewModel(Context context, ProgramPageAdapter programPageAdapter, OnPageChangeListener listener, SortClickListener sortClickListener) {
        this.context = context;
        this.programPageAdapter = programPageAdapter;
        date = new ObservableField<>();
        day = new ObservableField<>();
        updateHeaders();
        mListener = listener;
        mSortListener = sortClickListener;
        sortOrder = new ObservableField<>();
        createSortOrderString(sortOrder);
        registerPreferenceChangeListener(context);
    }

    private void createSortOrderString(ObservableField<String> sortOrder) {
        sortOrder.set(String.format(context.getString(R.string.sort_by),context.getString(SortOrderManager.getSelectedSortOrder(context).getName())));
    }

    private void registerPreferenceChangeListener(@NonNull Context context) {
        PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void destroy() {
        Log.d(TAG, "destroy");
        PreferenceManager.getDefaultSharedPreferences(context).unregisterOnSharedPreferenceChangeListener(this);
        context = null;
        mListener = null;
        mSortListener = null;
    }

    public void onPageSelected(int position) {
        currentPage = position;
        updateHeaders();
    }

    private void updateHeaders() {
        day.set(String.format(context.getString(R.string.day_number),currentPage+1));
        date.set(programPageAdapter.getPageTitle(currentPage).toString());
    }

    public void nextPage(View v) {
        if(currentPage < programPageAdapter.getCount()) {
            currentPage++;
        }
        if(mListener != null) {
            mListener.pageChanged(currentPage);
        }
    }

    public void prevPage(View v) {
        if(currentPage > 0) {
            currentPage--;
        }
        if(mListener != null) {
            mListener.pageChanged(currentPage);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        createSortOrderString(this.sortOrder);
    }

    public interface OnPageChangeListener {
        void pageChanged(int currentPage);
    }

    public void onSortByClick(View view) {
        if(mSortListener != null) {
            mSortListener.onSortClick();
        }
    }

    public interface SortClickListener {
        void onSortClick();
    }
}
