package de.ahlfeld.breminale.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * Created by bjornahlfeld on 18.06.16.
 */
public class MoreViewModel implements ViewModel {


    private Context context;

    public MoreViewModel(@NonNull Context context) {
        this.context = context;
    }


    public void onLicensesClick(View view) {

    }

    public void onImprintClick(View view) {

    }

    public void onPrivacyClick(View view) {

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
