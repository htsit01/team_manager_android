package com.example.asus.teammanager.model.api_model;

public class UserLocation extends User {

    private Location last_location;

    public UserLocation(int id, String name, String email, String mac_address, String code, String phone, int role_id, int group_id, int customer_area_id, Location last_location) {
        super(id, name, email, mac_address, code, phone, role_id, group_id, customer_area_id);
        this.setLast_location(last_location);
    }

    public Location getLast_location() {
        return last_location;
    }

    public void setLast_location(Location last_location) {
        this.last_location = last_location;
    }
}
