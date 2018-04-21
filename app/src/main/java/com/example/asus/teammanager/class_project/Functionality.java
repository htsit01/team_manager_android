package com.example.asus.teammanager.class_project;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;

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
}
