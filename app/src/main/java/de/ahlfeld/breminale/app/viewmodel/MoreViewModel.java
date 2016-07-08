package de.ahlfeld.breminale.app.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.SwitchCompat;
import android.view.View;

import de.ahlfeld.breminale.app.utils.BreminaleConsts;
import de.ahlfeld.breminale.app.view.ImprintActivity;
import de.ahlfeld.breminale.app.view.LicenseActivity;
import de.ahlfeld.breminale.app.view.PrivacyActivity;

/**
 * Created by bjornahlfeld on 18.06.16.
 */
public class MoreViewModel implements ViewModel {


    private static final String TAG = MoreViewModel.class.getSimpleName();
    private Context context;

    public MoreViewModel(@NonNull Context context) {
        this.context = context;
    }


    public void onOnlyWifiClick(View view) {
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        SwitchCompat wifiSwitch = (SwitchCompat)view;
        editor.putBoolean(BreminaleConsts.DOWNLOAD_IMAGES_ONLY_ON_WIFI, wifiSwitch.isChecked());
        editor.commit();
    }

    public void onLicensesClick(View view) {
        Intent intent = new Intent(context, LicenseActivity.class);
        context.startActivity(intent);
    }

    public void onImprintClick(View view) {
        Intent intent = new Intent(context, ImprintActivity.class);
        context.startActivity(intent);
    }

    public void onPrivacyClick(View view) {
        Intent intent = new Intent(context, PrivacyActivity.class);
        context.startActivity(intent);
    }

    public void onContactClick(View view) {
        String url = "http://www.breminale.de/Kontakt/_21";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        context.startActivity(i);
    }

    @Override
    public void destroy() {
        this.context = null;
    }
}
