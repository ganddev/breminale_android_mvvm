package de.ahlfeld.breminale.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.view.View;

import de.ahlfeld.breminale.models.Event;
import de.ahlfeld.breminale.view.EventActivity;

/**
 * Created by bjornahlfeld on 04.04.16.
 */
public class ItemEventViewModel extends BaseObservable implements ViewModel {


    private final Context context;
    private Event event;

    public ItemEventViewModel(Context context, Event event) {
        this.context = context;
        this.event = event;
    }

    @Override
    public void destroy() {

    }

    public void onItemClick(View view) {
        context.startActivity(EventActivity.newIntent(context, event));
    }


    public void setEvent(Event event) {
        this.event = event;
    }
}
