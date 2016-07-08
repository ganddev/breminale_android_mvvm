package de.ahlfeld.breminale.app.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import de.ahlfeld.breminale.app.BreminaleApplication;
import de.ahlfeld.breminale.app.R;
import de.ahlfeld.breminale.app.utils.SharedpreferenceUtils;

/**
 * Created by bjornahlfeld on 08.07.16.
 */
public class WifiOnlyDialog extends DialogFragment {


    private Tracker tracker;

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
        builder.setTitle(R.string.download_image_only_wifi).setMessage(R.string.download_images_only_if_connected_to_wifi);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedpreferenceUtils.toggleWifiState(getContext().getApplicationContext(),true);
            }
        });
        builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedpreferenceUtils.toggleWifiState(getContext().getApplicationContext(), false);
            }
        });
        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
        tracker.setScreenName("WifiOnlyDialog");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

}
