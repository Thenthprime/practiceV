package edu.psu.swen888.practicev;

import java.io.Serializable;

public class Event implements Serializable {
    private int eventID;
    private String name;
    private String address;

    public Event(int eventID, String name, String address) {
        this.eventID = eventID;
        this.name = name;
        this.address = address;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
