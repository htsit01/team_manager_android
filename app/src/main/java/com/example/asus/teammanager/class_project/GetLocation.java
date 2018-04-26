package com.example.asus.teammanager.class_project;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v4.app.ActivityCompat;


public class GetLocation {
    private double lat, lng;
    private boolean isGPSEnabled = false;
    private boolean isNetworkEnabled = false;
    private LocationManager location_manager;
    private Context context;
    private LocationListener listener;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES =100; //100 meter
    private static final long MIN_TIME_BW_UPDATES = 1000 *10;
    private Location location;


    public GetLocation(Context context, LocationListener listener){
        this.context = context;
        this.listener = listener;
    }

    public boolean canGetLocation(){
        location_manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        isGPSEnabled = location_manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = location_manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if(!isGPSEnabled && !isNetworkEnabled){
            return false;
        }

        return true;
    }

    public void findLocation(){
        if(isGPSEnabled){
            if(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                return;
            }

            location_manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, listener);
            if(location_manager!=null){
                location = location_manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if(location!=null){
                    lat = location.getLatitude();
                    lng = location.getLongitude();
                }
            }
        }
        if(isNetworkEnabled){
            if(location==null){
                location_manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, listener);
                if(location_manager!=null){
                    location = location_manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if(location!=null){
                        lat = location.getLatitude();
                        lng = location.getLongitude();
                    }
                }
            }
        }
    }

    public void stopUsingGPS(){
        if(location_manager!=null){
            location_manager.removeUpdates(listener);
        }
    }

    public double getLng() {
        if(location!=null) {
            return location.getLongitude();
        }
        return 0.0;
    }

    public double getLat() {
        if(location!=null){
            return location.getLatitude();
        }
        return 0.0;
    }

    public Location getLocation(){
        return location;
    }
}
