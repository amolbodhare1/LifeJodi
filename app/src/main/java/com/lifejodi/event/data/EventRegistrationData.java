package com.lifejodi.event.data;

/**
 * Created by Administrator on 2/26/2018.
 */

public class EventRegistrationData {

    private static EventRegistrationData eventRegistrationData = new EventRegistrationData();

    public static EventRegistrationData getInstance()
    {
        return eventRegistrationData;
    }

    public static final String API = "api";
    public static final String DEVICE = "device";
    public static final String VERSION = "version";
    public static final String DATA = "data";

    public static final String USERID = "user_id";
    public static final String EVENTID = "event_id";
    public static final String FIRSTNAME = "first_name";
    public static final String LASTNAME = "last_name";
    public static final String MOBILENUM = "mobile_no";

    public static final String STATUS = "status";
    public static final String MESSAGE = "msg";

    public String eventRegStatus = "";
    public String getEventRegStatus() {
        return eventRegStatus;
    }

    public void setEventRegStatus(String eventRegStatus) {
        this.eventRegStatus = eventRegStatus;
    }

    public String eventRegMessage = "";
    public String getEventRegMessage() {
        return eventRegMessage;
    }

    public void setEventRegMessage(String eventRegMessage) {
        this.eventRegMessage = eventRegMessage;
    }

}
