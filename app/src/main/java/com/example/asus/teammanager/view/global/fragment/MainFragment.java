package com.example.asus.teammanager.view.global.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.asus.teammanager.R;
import com.example.asus.teammanager.model.api_model.UserLocation;
import com.example.asus.teammanager.model.session_manager.SessionManager;
import com.example.asus.teammanager.presenter.GlobalPresenter;
import com.example.asus.teammanager.presenter.location_presenter.GetAllLocationsPresenter;
import com.example.asus.teammanager.view.global.adapter.UserLocationAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    private SupportMapFragment mapFragment;
    private OnMapReadyCallback mapReadyCallback;
    private GoogleMap map;
    private RecyclerView rv_user_locations;
    private ArrayList<UserLocation> user_locations = new ArrayList<>();
    private UserLocationAdapter adapter;
    private SessionManager sm;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
//        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        rv_user_locations = view.findViewById(R.id.rv_users);

        sm = new SessionManager(getContext());

        adapter = new UserLocationAdapter(user_locations);
        rv_user_locations.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_user_locations.setAdapter(adapter);

//        mapReadyCallback = new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(GoogleMap googleMap) {
//                map = googleMap;
//                map.clear();
//                for(UserLocation userLocation : user_locations){
//                    map.addMarker(new MarkerOptions().position(new LatLng(userLocation.getLast_location().getLat(),userLocation.getLast_location().getLng()))
//                            .anchor(0.5f,1).title(userLocation.getLast_location().getAddress()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
//                            .showInfoWindow();
//                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(3.594276,98.687040),18f));
//                }
//            }
//        };

        GetAllLocationsPresenter getAllLocationsPresenter = new GetAllLocationsPresenter(new GlobalPresenter() {
            @Override
            public void onSuccess(Object object) {
                ArrayList<UserLocation> response = (ArrayList<UserLocation>) object;

                user_locations.clear();
                user_locations.addAll(response);
                adapter.notifyDataSetChanged();

//                mapFragment.getMapAsync(mapReadyCallback);
            }

            @Override
            public void onError(int code, String message) {
                Toast.makeText(getContext(), "Sorry, something went wrong. Error Code: ".concat(String.valueOf(code)), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(String message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        getAllLocationsPresenter.getAllLocations(sm.getToken().getAccess_token());

        return view;
    }

}
