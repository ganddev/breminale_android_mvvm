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
        AlertDialog dialog = new AlertDialog.Builder(getContext()).setCancelable(false)
                .setTitle(R.string.download_image_only_wifi)
                .setMessage(R.string.download_images_only_if_connected_to_wifi)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tracker.send(new HitBuilders.EventBuilder().setCategory("WifiOnly").setAction("onYesClicked").build());
                        SharedpreferenceUtils.toggleFirstStart(getContext(), false);
                        SharedpreferenceUtils.toggleWifiState(getContext().getApplicationContext(), true);
                        dismiss();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tracker.send(new HitBuilders.EventBuilder().setCategory("WifiOnly").setAction("onNoClicked").build());
                        SharedpreferenceUtils.toggleFirstStart(getContext(), false);
                        SharedpreferenceUtils.toggleWifiState(getContext().getApplicationContext(), false);
                        dismiss();
                    }
                }).create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                dialog.getButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(android.R.color.black));
            }
        });

        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        tracker.setScreenName("WifiOnlyDialog");
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

}
