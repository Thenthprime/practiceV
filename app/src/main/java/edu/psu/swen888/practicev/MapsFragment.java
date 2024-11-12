package edu.psu.swen888.practicev;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.SearchView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.SquareCap;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener  {

    private GoogleMap mMap;
    private TextView textViewOption1, textViewOption2, textViewOption3, textViewOption4;
    private SearchView mSearchView;
    public ArrayList<Event> eventsList = new ArrayList<>();

    private FloatingActionButton mFloatingActionButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //initialize the view
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        //Initialize map fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map);

        //Async map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                //When map is loaded
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        //When clicked on map
                        //Initialize marker options
                        MarkerOptions markerOptions = new MarkerOptions();
                        //Set position of marker
                        markerOptions.position(latLng);
                        //Set title of marker
                        markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                        //Remove all marker
                        googleMap.clear();
                        //animating to zoom the marker
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                latLng, 10
                        ));
                        //Add marker on map
                        googleMap.addMarker(markerOptions);
                    }
                });
            }
        });

        mSearchView = view.findViewById(R.id.idSearchView);

        mSearchView = view.findViewById(R.id.idSearchView);

        /** Instantiate the UI Elements */
        //instantiateUIElements();
        /** Include a listener to the searchView */
        createSearchViewListener();
        return view;
    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.option_clear_map: mMap.clear(); break;
            case R.id.option_add_marker: createMarkerOnLocation(); break;
            case R.id.option_create_polygons: createPolygonsOnMap(); break;
            case R.id.option_create_polylines: createPolylinesOnMap(); break;
            default: break;
        }
    }

    private void createSearchViewListener() {
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                /** Getting the location name from the searchView */
                String locationName = mSearchView.getQuery().toString();
                /** Create list of address where we will store the locations found **/
                List<Address> addressList = null;
                    /** Check if the location is null */
                    if (locationName != null || locationName.equals("")) {
                        /** Initializing the geocode */
                        Geocoder geocoder = new Geocoder(getContext());
                        try {
                            addressList = geocoder.getFromLocationName(locationName, 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        /** Getting the location in the first position */
                        Address address = addressList.get(0);
                        /** Creating the LatLng object to store the address coordinates */
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        /** Add a marker */
                        mMap.addMarker(new MarkerOptions().position(latLng).title(locationName));
                    }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //method unnessecary because we are not using autocomplete
                return false;
            }
        });
    }

    private void createPolylinesOnMap(){
        //Define coordinates
        /** Define the Coordinates */
        LatLng sydney = new LatLng(-34, 151);
        LatLng tokyo = new LatLng(35.67, 139.65);
        LatLng singapore = new LatLng(1.35, 103.81);
        /** Add markers to specific location */
        mMap.addMarker(new MarkerOptions().position(sydney).title("Sydney, Australia"));
        mMap.addMarker(new MarkerOptions().position(singapore).title("Singapore, Singapore"));
        mMap.addMarker(new MarkerOptions().position(tokyo).title("Tokyo, Japan"));

        /** Configure the PolylineOptions to be displayed in map */
        PolylineOptions polylineOptions = new PolylineOptions()
                .width(15)
                .color(Color.RED)
                .startCap(new SquareCap())
                .endCap(new SquareCap());
        /** Add the coordinates to be included in the Polyline **/
        polylineOptions.add(sydney, tokyo, singapore);
        /** Add the polyline to the map */
        mMap.addPolyline(polylineOptions);
        /** Call the move camera method to the new coordinate, and adjust the zoom */
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tokyo, 2));
    }

    private void createPolygonsOnMap(){
        /** Define the Coordinates  for Polygon A*/
        LatLng columbus = new LatLng(39.96712, -82.9988);
        LatLng newYork = new LatLng(40.7128, -74.0060);
        LatLng philadelphia = new LatLng(39.9526, -75.1652);
        LatLng nashville = new LatLng(36.1627, -86.7816);

        /** Configuring polygon A */
        Polygon polygonA = mMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(columbus, nashville, philadelphia, newYork));
        polygonA.setTag("Polygon A");

        /** Define Coordinates for Polygon B */
        LatLng miami = new LatLng(25.7617,-80.1918);
        LatLng orlando = new LatLng(28.5383, -81.3792);
        LatLng jacksonville = new LatLng(30.3322, -81.6557);
        LatLng tampa = new LatLng(27.9506, -82.4572);

        /** Configuring polygon B */
        Polygon polygonB = mMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(miami, orlando, jacksonville, tampa));
        polygonB.setTag("Polygon B");

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(jacksonville, 4));
    }

    private void addMarker (String location, LatLng coordinates){
        mMap.addMarker(new MarkerOptions().position(coordinates).title(location));
        //move the camera to a specific location without changin the zoom of the map
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 10));
    }

//    private void instantiateUIElements(){
//        mFloatingActionButton = getView().findViewById(R.id.fab);
//        mFloatingActionButton.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//                showOptionsMenu();
//            }
//        });
//
//        textViewOption1 = getView().findViewById(R.id.option_clear_map);
//        textViewOption2 = getView().findViewById(R.id.option_add_marker);
//        textViewOption3 = getView().findViewById(R.id.option_create_polylines);
//        textViewOption4 = getView().findViewById(R.id.option_create_polygons);
//
////        textViewOption1.setOnClickListener(MapsFragment);
////        textViewOption2.setOnClickListener(this);
////        textViewOption3.setOnClickListener(this);
////        textViewOption4.setOnClickListener(this);
//    }

    private void showOptionsMenu(){
        ConstraintLayout optionsMenu = getView().findViewById(R.id.options_menu);
        if(optionsMenu.getVisibility() == View.VISIBLE){
            //Hide the options menu
            optionsMenu.setVisibility(View.GONE);
        }
        else{
            //show the options menu
            optionsMenu.setVisibility(View.VISIBLE);
        }
    }

    private void createMarkerOnLocation(){
        String locationName = "Penn State - Great Valley";
        /** Represent location we need to use LatLng */
        LatLng pennStateGV = new LatLng(40.0429, -75.5133);
        addMarker(locationName, pennStateGV);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

    }
}