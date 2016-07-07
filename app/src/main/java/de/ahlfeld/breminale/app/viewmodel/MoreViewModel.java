package de.ahlfeld.breminale.app.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;

import java.lang.ref.WeakReference;

import de.ahlfeld.breminale.app.core.DataManager;
import de.ahlfeld.breminale.app.view.ImprintActivity;
import de.ahlfeld.breminale.app.view.LicenseActivity;
import de.ahlfeld.breminale.app.view.PrivacyActivity;

/**
 * Created by bjornahlfeld on 18.06.16.
 */
public class MoreViewModel implements ViewModel {


    private WeakReference<Context> context;

    public MoreViewModel(@NonNull Context context) {
        this.context = new WeakReference<Context>(context);
    }


    public void onLicensesClick(View view) {
        Intent intent = new Intent(context.get(), LicenseActivity.class);
        context.get().startActivity(intent);
    }

    public void onImprintClick(View view) {
        Intent intent = new Intent(context.get(), ImprintActivity.class);
        context.get().startActivity(intent);
    }

    public void onPrivacyClick(View view) {
        Intent intent = new Intent(context.get(), PrivacyActivity.class);
        context.get().startActivity(intent);
    }

    public void onContactClick(View view) {
        String url = "http://www.breminale.de/Kontakt/_21";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        context.get().startActivity(i);
    }

    public void onLoadDataClick(View view) {
        DataManager dm = new DataManager(context.get().getApplicationContext());
        dm.loadLocations();
        dm.loadEvents();
    }

    @Override
    public void destroy() {
        this.context = null;
    }
}
