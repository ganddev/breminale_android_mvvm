package de.ahlfeld.breminale.dataaccessobjects;

import de.ahlfeld.breminale.models.Event;

/**
 * Created by bjornahlfeld on 06.04.16.
 */
public interface IEvent {

    Event getEvent(Integer eventId);
}
