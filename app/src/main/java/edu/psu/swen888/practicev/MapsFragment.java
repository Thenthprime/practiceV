package edu.psu.swen888.practicev;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsFragment extends Fragment{
    private ArrayList<Event> eventsList = new ArrayList<>();
    private SearchView mSearchView;
    private FloatingActionButton mFloatingActionButton;
    private TextView textViewOption1, textViewOption2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //Initialize view
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        //initialize UI
        mSearchView = view.findViewById(R.id.idSearchView);
        mFloatingActionButton = view.findViewById(R.id.fab);

        //Initialize map fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        //Async map
        supportMapFragment.getMapAsync(new OnMapReadyCallback(){
            public void onMapReady(GoogleMap googleMap) {

                //retrieve events from database
                DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
                eventsList = dataBaseHelper.getAllEvents();
                //iterate through each event to retrieve the address
                    for(Event event : eventsList){
                        String eventLocation = event.getAddress();
                        //initialize a list of addresses
                        List<Address> addressList = null;
                        if(eventLocation != null || eventLocation.equals("")){
                            //Initialize the geocoder
                            Geocoder geocoder = new Geocoder(getContext());
                            try {
                                //this is to get only one result (if there are multiple cities with the same name)
                                addressList = geocoder.getFromLocationName(eventLocation, 1);
                            }
                            catch (IOException e) {
                                //exception handling
                                e.printStackTrace();
                            }
                            //get the address
                            Address address = addressList.get(0);
                            //retrieve the latitude and longitude and add the marker to the map
                            MarkerOptions markerOptions = new MarkerOptions();
                            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                            markerOptions.position(latLng);
                            googleMap.addMarker(markerOptions).setTitle(event.getName());
                        }
            }

                //implement search view functionality
                mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
                    @Override
                    public boolean onQueryTextSubmit(String query){
                        //Get the location name from searchView
                        String locationName = mSearchView.getQuery().toString();
                        //create a list of addresses to store locations
                        List<Address> addressList = null;
                        //check of location is null
                        if(locationName != null || locationName.equals("")){
                            //Initialize the geocoder
                            Geocoder geocoder = new Geocoder(getContext());
                            try {
                                //this is to get only one result (if there are multiple cities with the same name)
                                addressList = geocoder.getFromLocationName(locationName, 1);
                            }
                            catch (IOException e) {
                                //exception handling
                                e.printStackTrace();
                            }
                            //get the address
                            Address address = addressList.get(0);
                            //retrieve the latitude and longitude and add the marker to the map
                            MarkerOptions markerOptions = new MarkerOptions();
                            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                            markerOptions.position(latLng);
                            //animating to zoom the marker
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                            googleMap.addMarker(markerOptions).setTitle(query);
                        }
                        return false;
                    }
                    @Override
                    public boolean onQueryTextChange(String newText) {
                        //method isn't necessary for my intended functionality
                        return false;
                    }
                });

                mFloatingActionButton.setOnClickListener(new FloatingActionButton.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        showOptionsMenu();
                        }
                });
                textViewOption1 = getView().findViewById(R.id.option_clear_map);
                textViewOption2 = getView().findViewById(R.id.option_add_marker);

                textViewOption1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        googleMap.clear();
                    }
                });
                textViewOption2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String locationName = "Penn State - Great Valley";
                        //Manual addition of PSU
                        LatLng pennStateGV = new LatLng(40.0429, -75.5133);
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(pennStateGV);
                        //animating to zoom the marker
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pennStateGV, 10));
                        googleMap.addMarker(markerOptions).setTitle(locationName);
                    }
                });
            }
        });
        //Return view
        return view;
    }
    //method for floating action bar menu
    private void showOptionsMenu() {
        ConstraintLayout optionsMenu = getView().findViewById(R.id.options_menu);
        if (optionsMenu.getVisibility() == View.VISIBLE) {
            //Hide the options menu
            optionsMenu.setVisibility(View.GONE);
        } else {
            //Show the options menu
            optionsMenu.setVisibility(View.VISIBLE);
        }
    }
}