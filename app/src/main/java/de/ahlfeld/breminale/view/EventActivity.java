package de.ahlfeld.breminale.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import de.ahlfeld.breminale.R;
import de.ahlfeld.breminale.databinding.ActivityEventBinding;
import de.ahlfeld.breminale.models.Event;
import de.ahlfeld.breminale.viewmodel.EventViewModel;

/**
 * Created by bjornahlfeld on 05.04.16.
 */
public class EventActivity extends AppCompatActivity {


    private static final String EXTRA_EVENT = "EXTRA_EVENT";

    private EventViewModel eventViewModel;
    private ActivityEventBinding binding;

    public static Intent newIntent(Context context, Event event) {
        Intent intent = new Intent(context, EventActivity.class);
        intent.putExtra(EXTRA_EVENT, event);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_event);

        Event event = getIntent().getParcelableExtra(EXTRA_EVENT);

        eventViewModel = new EventViewModel(this);

    }
}
