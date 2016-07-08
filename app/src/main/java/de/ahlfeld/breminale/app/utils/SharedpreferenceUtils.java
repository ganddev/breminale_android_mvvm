package de.ahlfeld.breminale.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

/**
 * Created by bjornahlfeld on 08.07.16.
 */
public class SharedpreferenceUtils {


    public static void toggleWifiState(@NonNull Context ctx, boolean wifiOnly) {
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(BreminaleConsts.DOWNLOAD_IMAGES_ONLY_ON_WIFI, wifiOnly);
        editor.commit();
    }

    public static boolean isWifiOnly(@NonNull Context ctx) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        return sharedPreferences.getBoolean(BreminaleConsts.DOWNLOAD_IMAGES_ONLY_ON_WIFI, true);
    }
}
