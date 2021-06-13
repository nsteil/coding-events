package org.launchcode.codingevents.data;

import org.launchcode.codingevents.models.Event;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EventData {

    // need a place to put events
    private static final Map<Integer, Event> events = new HashMap<>();

    // be able to get all events
    public static Collection<Event> getAll() {
        return events.values();
    }

    // get a single event
    public static Event getById(int id){
       return events.get(id);
    }

    // add an event
    public static void add(Event event){
        events.put(event.getId(),event);
    }

    // remove an event
    public static void remove(int id){
        events.remove(id);
    }

    public static void edit(int id, String newName, String newDescription, String newEmail,
                            String newLocation, boolean newRequired, int newAttendees){
        getById(id).setName(newName);
        getById(id).setDescription(newDescription);
        getById(id).setContactEmail(newEmail);
        getById(id).setLocation(newLocation);
        getById(id).setRegistrationRequired(newRequired);
        getById(id).setNumberOfAttendees(newAttendees);
    }
}
