package de.ahlfeld.breminale.viewmodel;

import android.content.Context;

import de.ahlfeld.breminale.view.EventActivity;

/**
 * Created by bjornahlfeld on 05.04.16.
 */
public class EventViewModel implements ViewModel{


    private final Context context;

    public EventViewModel(Context context) {
        this.context = context;
    }

    @Override
    public void destroy() {

    }
}
