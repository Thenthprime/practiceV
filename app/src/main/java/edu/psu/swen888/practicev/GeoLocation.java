//package edu.psu.swen888.practicev;
//
//import android.content.Context;
//import android.location.Geocoder;
//import android.os.Handler;
//
//import java.util.List;
//import java.util.Locale;
//
//public class GeoLocation {
//    public static void getAddress(String locationAddress, Context context, Handler handler){
//        Thread thread = new Thread(){
//            @Override
//            public void run(){
//                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
//                String result = null;
//                List addressList = geocoder.getFromLocationName(locationAddress, 1);
//
//            }
//        }
//    }
//}
