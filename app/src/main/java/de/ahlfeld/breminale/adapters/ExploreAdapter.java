package de.ahlfeld.breminale.adapters;

import android.support.v7.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import de.ahlfeld.breminale.models.Location;

/**
 * Created by bjornahlfeld on 31.03.16.
 */
public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.ExploreViewHolder> {

    List<Location> locations;

    public ExploreAdapter() {
        this.locations = Collections.emptyList();
    }

    public ExploreAdapter(List<Location> locations) {
        this.locations = locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public static class ExploreViewHolder.RecyclerView.ViewHolder {

        public ExploreViewHolder() {
            super();
        }

        void bindLocation(Location location) {
        if()
    }
    }
}
