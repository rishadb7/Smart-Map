package com.teamhexcode.smartmap;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    List<LocationPoints> locationPointsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        locationPointsList= new ArrayList<>();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);




        mapFragment.getMapAsync(this);



        getLocationPoints();




      //  Toast.makeText(getApplicationContext(),locationPointsList.get(2).getLocationName(),Toast.LENGTH_LONG).show();

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

      /*  // Add a marker in Sydney and move the camera

        for (int i=0; i<locationPointsList.size();i++)
        {
            Toast.makeText(getApplicationContext(),locationPointsList.get(i).getLocationName(),Toast.LENGTH_LONG).show();

            Double lat=Double.valueOf(locationPointsList.get(i).getLatitude());
            Double lon=Double.valueOf(locationPointsList.get(i).getLongitude());
            String locationName=locationPointsList.get(i).getLocationName();

            LatLng sydney = new LatLng(lat, lon);
            mMap.addMarker(new MarkerOptions().position(sydney).title(locationName));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        }*/

       /* LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }


    public void getLocationPoints()
    {
        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl("https://sireen1195.000webhostapp.com/")

                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service = retrofit2.create(ApiService.class);
        Call<List<LocationPoints>> call = service.getLocationPoints("12345");

        call.enqueue(new Callback<List<LocationPoints>>() {
            @Override
            public void onResponse(Call<List<LocationPoints>> call, Response<List<LocationPoints>> response) {

                List<LocationPoints> list = response.body();
                LocationPoints locationPoints = null;

                try {
                    PolylineOptions rectOptions = new PolylineOptions();
                    rectOptions.color(Color.argb(255, 255, 0, 0));
                    LatLng startLatLng = null;
                    LatLng endLatLng = null;



                    for (int i = 0; i < list.size(); i++) {
                        locationPoints = new LocationPoints();


                        String dbId = list.get(i).getDbId();
                        String locationName = list.get(i).getLocationName();
                        String latitude =list.get(i).getLatitude();
                        String logitude =list.get(i).getLongitude();



                     //   Toast.makeText(getApplicationContext(),locationName,Toast.LENGTH_LONG).show();

                        Double lat=Double.valueOf(latitude);
                        Double lon=Double.valueOf(logitude);




                        LatLng sydney = new LatLng(lat, lon);

                        if (i == 0) {
                            startLatLng = sydney;
                        }
                        if (i == list.size() - 1) {
                            endLatLng = sydney;
                        }
                        rectOptions.add(sydney);


                        mMap.addMarker(new MarkerOptions().position(sydney).title(locationName));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

                        locationPoints.setDbId(dbId);
                        locationPoints.setLocationName(locationName);
                        locationPoints.setLatitude(latitude);
                        locationPoints.setLongitude(logitude);
                        locationPointsList.add(locationPoints);


                    }

                    mMap.addPolyline(rectOptions);









                } catch (Exception e) {
                    e.printStackTrace();
                    //    Toast.makeText(getApplicationContext(),"No items available",Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onFailure(Call<List<LocationPoints>> call, Throwable t) {

            }
        });


    }











}
