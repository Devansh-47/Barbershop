package com.example.barberr;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsFragment extends Fragment {

    String Shop_id;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {


            FirebaseDatabase.getInstance().getReference("Shops").child(Shop_id).child("shop_details").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Double latitude=Double.parseDouble(snapshot.child("location").child("latitude").getValue(String.class));
                    Double longitude=Double.parseDouble(snapshot.child("location").child("longitude").getValue(String.class));
                    LatLng loc = new LatLng(latitude, longitude);
                    googleMap.addMarker(new MarkerOptions().position(loc).title(snapshot.child("shop_name").getValue(String.class)));
                   // googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(current_Location.getLatitude(), current_Location.getLongitude()), 35f));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc,16f));

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


    }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Shop_id=getArguments().getString("Shop_id");
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}