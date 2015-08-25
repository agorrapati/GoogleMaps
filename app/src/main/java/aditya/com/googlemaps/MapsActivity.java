package aditya.com.googlemaps;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity {

    Button search;// Might be null if Google Play services APK is not available.
    private GoogleMap gMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        search = (Button) findViewById(R.id.LSearch);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (gMaps == null) {
            // Try to obtain the map from the SupportMapFragment.
            gMaps = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (gMaps != null) {

            }
        }
    }


    private void setUpMap(LatLng latLng) {
        gMaps.addMarker(new MarkerOptions().position(latLng).title("Marker"));
        gMaps.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                View view = getLayoutInflater().inflate(R.layout.marker, null);
                LatLng latLng1 = marker.getPosition();
                TextView tvLat = (TextView) view.findViewById(R.id.markerlat);
                TextView tvLng = (TextView) view.findViewById(R.id.markerlng);
                tvLat.setText("Latitude: " + latLng1.latitude);
                tvLng.setText("Longitude: " + latLng1.longitude);


                return view;
            }
        });

        gMaps.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                gMaps.clear();
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                gMaps.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                Marker marker = gMaps.addMarker(markerOptions);
                marker.showInfoWindow();
            }
        });
        gMaps.animateCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    public void onSearch(View view) {

        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.actv);
        String location = autoCompleteTextView.getText().toString();
        List<Address> addressList = null;
        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            LatLng latlng = new LatLng(address.getLatitude(), address.getLongitude());
            setUpMap(latlng);
            /*
            gMaps.addMarker(new MarkerOptions().position(latlng).title("Marker"));
            gMaps.animateCamera(CameraUpdateFactory.newLatLng(latlng));*/
        }

    }
}