package com.example.asus.teammanager.class_project;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class Functionality {
    public static String getMacAddress(){
        try {
            List<NetworkInterface> networkInterfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for(NetworkInterface networkInterface : networkInterfaces){
                if(!networkInterface.getName().equalsIgnoreCase("wlan0")){
                    continue;
                }

                byte[] macBytes = networkInterface.getHardwareAddress();
                if(macBytes == null){
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for(byte b: macBytes){
//                    res1.append(Integer.toHexString(b & 0xFF).concat(":"));
                    res1.append(String.format("%02X:",b));
                }

                if(res1.length()>0){
                    res1.deleteCharAt(res1.length()-1);
                }
                return res1.toString();
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "02:00:00:00:00:00";
    }
    public static String formatDate(String from_format, String to_format, String value){
        SimpleDateFormat from = new SimpleDateFormat(from_format, new Locale("id","ID"));
        SimpleDateFormat to = new SimpleDateFormat(to_format, new Locale("id","ID"));

        try {
            Date date = from.parse(value);
            return to.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }


    //function to calculate distance between 2 lat&lng
    //source::https://www.geodatasource.com/developers/java
    //Info------------------------------------------------
    /*::  Definitions:                                                           :*/
    /*::    South latitudes are negative, east longitudes are positive           :*/
    /*::                                                                         :*/
    /*::  Passed to function:                                                    :*/
    /*::    lat1, lon1 = Latitude and Longitude of point 1 (in decimal degrees)  :*/
    /*::    lat2, lon2 = Latitude and Longitude of point 2 (in decimal degrees)  :*/
    /*::    unit = the unit you desire for results                               :*/
    /*::           where: 'M' is statute miles (default)                         :*/
    /*::                  'K' is kilometers                                      :*/
    /*::                  'N' is nautical miles                                  :*/

    public static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit.equals("K")) {
            dist = dist * 1.609344;
        } else if (unit.equals("N")) {
            dist = dist * 0.8684;
        }
        else if (unit.equals("m")){
            dist = dist * 1.609344 *1000;
        }

        return round(dist, 2);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::	This function converts decimal degrees to radians			:*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::	This function converts radians to decimal degrees		    :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    public static String formatDatetoString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("id","ID"));
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
        return sdf.format(date);
    }

    public static double round(double value, int places){
        if(places>=0){
            long factor = (long) Math.pow(10, places);
            value = value * factor;
            long tmp = Math.round(value);
            return (double) tmp/factor;
        }
        return 0;
    }
}
