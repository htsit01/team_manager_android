package com.example.asus.teammanager.model.response;

import com.example.asus.teammanager.model.api_model.Location;
import com.example.asus.teammanager.model.api_model.UserLocation;

import java.util.ArrayList;

public class LocationHistoryResponse {
    private ArrayList<Location> locations;
    private ArrayList<Location> checkins;
    private ArrayList<Location> checkouts;

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
    }

    public ArrayList<Location> getCheckins() {
        return checkins;
    }

    public void setCheckins(ArrayList<Location> checkins) {
        this.checkins = checkins;
    }

    public ArrayList<Location> getCheckouts() {
        return checkouts;
    }

    public void setCheckouts(ArrayList<Location> checkouts) {
        this.checkouts = checkouts;
    }
}
