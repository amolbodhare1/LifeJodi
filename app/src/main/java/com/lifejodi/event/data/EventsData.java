package com.lifejodi.event.data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2/27/2018.
 */

public class EventsData {

    private static EventsData eventsData = new EventsData();

    public static EventsData getInstance()
    {
        return eventsData;
    }

    public static final String API = "api";
    public static final String DEVICE = "device";
    public static final String VERSION = "version";

    public static final String STATUS = "status";
    public static final String MESSAGE = "msg";
    public static final String DATA = "data";

    public static final String ID = "id";
    public static final String EVENTNAME = "event_name";
    public static final String EVENTDATE = "event_date";
    public static final String EVENTINFORMATION = "event_information";
    public static final String ADDRESS = "address";
    public static final String ADDBY = "add_by";
    public static final String ADDDATE = "add_date";
    public static final String DELETE = "delete";
    public static final String ACTIVE = "active";

    ArrayList<HashMap<String,String>> eventsList = new ArrayList<>();
    public ArrayList<HashMap<String, String>> getEventsList() {
        return eventsList;
    }

    public void setEventsList(ArrayList<HashMap<String, String>> eventsList) {
        this.eventsList = eventsList;
    }


}
