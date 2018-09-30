package uis.festival;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import uis.DataBase.FestivalInformationVO;

public class  MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Geocoder geocoder;
    private FestivalInformationVO data;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        geocoder = new Geocoder(this);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions mOptions = new MarkerOptions();

                Double latitude = latLng.latitude;
                Double longitude = latLng.longitude;

                mOptions.snippet(latitude.toString() + ", " + longitude.toString());
                mOptions.position(new LatLng(latitude,longitude));

            }
        });

        ///
        List<Address> addressList = null;
        Intent intent = getIntent();
        data = (FestivalInformationVO) intent.getExtras().get("data");
        char tmp[];

        String place = data.getGCode();
        place += " " + data.getPlace();

        tmp = place.toCharArray();

        for(int i=0; i<tmp.length; i++){
            if(tmp[i]=='('){
                for(int j=i; j<tmp.length; j++) {
                    if (tmp[j] == ')') {
                        tmp[j] = ' ';
                        break;
                    }
                    tmp[j]=' ';
                }
            }
        }
        place = String.valueOf(tmp);
        place.trim();

        System.out.println(place);
        try{
            addressList = geocoder.getFromLocationName(place,10);
        }
        catch(IOException e){
            e.printStackTrace();
        }

        try{
            String []splitStr = addressList.get(0).toString().split(",");
            String addr = splitStr[0].substring(splitStr[0].indexOf("\"") + 1,splitStr[0].length() -2);

            String latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1);
            String longitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1);


            LatLng point = new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));

            MarkerOptions mOptions2 = new MarkerOptions();
            mOptions2.title(""+place+"");
            mOptions2.snippet(addr);
            mOptions2.position(point);
            ///


            mMap.addMarker(mOptions2);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point,15));

        }
        catch(IndexOutOfBoundsException e){
            LatLng SejongUniv = new LatLng(37.5502596,127.0709503);

            MarkerOptions markerOptions = new MarkerOptions();

            markerOptions.position(SejongUniv);

            markerOptions.title("세종대학교");

            markerOptions.snippet("세종대학교");

            mMap.addMarker(markerOptions);

            mMap.moveCamera(CameraUpdateFactory.newLatLng(SejongUniv));

            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));


        }
    }
}