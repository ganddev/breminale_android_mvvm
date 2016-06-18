package de.ahlfeld.breminale.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;

import de.ahlfeld.breminale.view.ImprintActivity;
import de.ahlfeld.breminale.view.LicenseActivity;
import de.ahlfeld.breminale.view.PrivacyActivity;

/**
 * Created by bjornahlfeld on 18.06.16.
 */
public class MoreViewModel implements ViewModel {


    private Context context;

    public MoreViewModel(@NonNull Context context) {
        this.context = context;
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
