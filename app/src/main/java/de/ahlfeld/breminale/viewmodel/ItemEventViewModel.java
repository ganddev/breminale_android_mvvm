package de.ahlfeld.breminale.viewmodel;

import android.content.Context;

import de.ahlfeld.breminale.models.Event;

/**
 * Created by bjornahlfeld on 04.04.16.
 */
public class ItemEventViewModel implements ViewModel {


    private Event event;

    public ItemEventViewModel(Context context, Event event) {

    }

    @Override
    public void destroy() {

    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
