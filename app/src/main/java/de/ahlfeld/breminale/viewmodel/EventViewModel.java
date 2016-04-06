package de.ahlfeld.breminale.viewmodel;

import android.content.Context;

import de.ahlfeld.breminale.models.Event;

/**
 * Created by bjornahlfeld on 05.04.16.
 */
public class EventViewModel implements ViewModel{


    private final Context context;

    private Event event;

    public EventViewModel(Context context, Event event) {
        this.context = context;
        this.event = event;
    }

    @Override
    public void destroy() {

    }
}
