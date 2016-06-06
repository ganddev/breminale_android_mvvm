package de.ahlfeld.breminale.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;

import de.ahlfeld.breminale.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TwitterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TwitterFragment extends ListFragment {

    public TwitterFragment() {
        // Required empty public constructor
    }

    public static TwitterFragment newInstance() {
        return new TwitterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final SearchTimeline searchTimeline = new SearchTimeline.Builder().query("#breminale").maxItemsPerRequest(50).build();
        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter(getContext(),searchTimeline);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_twitter, container, false);
    }

}
