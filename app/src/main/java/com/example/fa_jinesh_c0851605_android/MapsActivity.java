package com.example.fa_jinesh_c0851605_android;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.fa_jinesh_c0851605_android.databinding.ActivityMapsBinding;

import java.util.ArrayList;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMapLongClickListener,GoogleMap.OnMarkerClickListener{

    GoogleMap mMap;
    ActivityMapsBinding binding;
    dbHalper db;

    LatLng Place = new LatLng(53.544388, -113.490929);
    List<Marker> markerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        db = new dbHalper(this);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapLongClickListener(this);

        markerList = new ArrayList<>();
        List<Place> placeList = db.getAllPlaces();

        for(Place p: placeList){
            String myInfo = "ID: " + p.getId() + " Latitude: "+ p.getPlatitude() + "Longitude"
                    + p.getPlongitude() + " Title: " + p.getTitle();
            Log.d("myInfo", myInfo);

            markerList.add(mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(p.getPlatitude())
                            ,Double.parseDouble(p.getPlongitude()))).title(p.getTitle())
                    .zIndex( p.getId() ).snippet("By ME")     ));
        }

        markerList.add(mMap.addMarker(new MarkerOptions()
                .position(Place).title("Edmonton")));

        for(Marker m : markerList){

            LatLng latLng = new LatLng(m.getPosition().latitude, m.getPosition().longitude);
            mMap.addMarker(new MarkerOptions().position(latLng) );
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,12));
        }


    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {

        Intent intent = new Intent(MapsActivity.this, AddActivity.class);
        intent.putExtra("latitude" , latLng.latitude);
        intent.putExtra("longitude" , latLng.longitude);

        startActivity(intent);

    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {

        Intent intent = new Intent(MapsActivity.this, ShowActivity.class);
        intent.putExtra("latitude" , marker.getPosition().latitude);
        intent.putExtra("longitude" , marker.getPosition().longitude);
        intent.putExtra("title" , marker.getTitle());
        intent.putExtra("id" ,  marker.getZIndex()) ;
        startActivity(intent);
        return false;

    }
}