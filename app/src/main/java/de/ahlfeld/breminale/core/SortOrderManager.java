package de.ahlfeld.breminale.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import de.ahlfeld.breminale.core.repositories.realm.SortOptions;

/**
 * Created by bjornahlfeld on 18.06.16.
 */
public class SortOrderManager {

    private static final String SELECTED_SORT_OPTION = "selectedSortoption";

    public static void saveSortOrder(@NonNull Context context,@NonNull SortOptions sortOptions) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(SELECTED_SORT_OPTION, sortOptions.ordinal());
        editor.commit();
    }

    public static SortOptions getSelectedSortOrder(@NonNull Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        int sortOption = sp.getInt(SELECTED_SORT_OPTION, 0);
        return SortOptions.values()[sortOption];
    }
}
