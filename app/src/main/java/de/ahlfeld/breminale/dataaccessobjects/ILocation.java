package de.ahlfeld.breminale.dataaccessobjects;

import de.ahlfeld.breminale.models.Location;
import rx.Observable;

/**
 * Created by bjornahlfeld on 06.04.16.
 */
public interface ILocation {
    Observable<Location> getLocation(Integer locationId);
}
