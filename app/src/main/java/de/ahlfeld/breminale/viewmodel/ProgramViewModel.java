package de.ahlfeld.breminale.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;

import de.ahlfeld.breminale.R;
import de.ahlfeld.breminale.adapters.ProgramPageAdapter;

/**
 * Created by bjornahlfeld on 22.05.16.
 */
public class ProgramViewModel implements ViewModel {

    private final ProgramPageAdapter programPageAdapter;
    private Context context;

    private int currentPage;

    public ObservableField<String> date;

    public ObservableField<String> day;

    public ProgramViewModel(Context context, ProgramPageAdapter programPageAdapter) {
        this.context = context;
        this.programPageAdapter = programPageAdapter;
        date = new ObservableField<>();
        day = new ObservableField<>();
        updateHeaders();
    }

    @Override
    public void destroy() {
        context = null;
    }

    public void onPageSelected(int position) {
        currentPage = position;
        updateHeaders();
    }

    private void updateHeaders() {
        day.set(String.format(context.getString(R.string.day_number),currentPage+1));
        date.set(programPageAdapter.getPageTitle(currentPage).toString());
    }
}
