package edu.psu.swen888.practicev;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


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

public class MapsFragment extends Fragment {

    private GoogleMap mMap;
    private TextView textViewOption1, textViewOption2, textViewOption3, textViewOption4;
    private SearchView mSearchView;
    public ArrayList<Event> eventsList;

    private FloatingActionButton mFloatingActionButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        //initialize the view
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        //Initialize map fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map);

        //Async map
        supportMapFragment.getMapAsync(new OnMapReadyCallback(){
            @Override
            public void onMapReady(GoogleMap googleMap){
                //When map is loaded
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
                    @Override
                    public void onMapClick(LatLng latLng){
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

        //Include a listener to the searchView
        createSearchViewListener();

        return view;
    }

    private void createSearchViewListener(){
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                /** Getting the location name from the searchView */
                String locationName = mSearchView.getQuery().toString();
                /** Create list of address where we will store the locations found **/
                List<Address> addressList = null;
                for(int i = 0; i < eventsList.size(); i++){
                    String address = eventsList.get(i).getAddress();
                    //parse address to get city and state

                }
                /** Check if the location is null */
                if (locationName != null || locationName.equals("")){
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
                    /** Animate the camera */
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8));
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
}