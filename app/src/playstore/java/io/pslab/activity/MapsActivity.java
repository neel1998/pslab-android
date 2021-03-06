package io.pslab.activity;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.pslab.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (getIntent().getExtras() != null && getIntent().getExtras().getBoolean("hasMarkers")) {
            try {
                JSONArray markers = new JSONArray(getIntent().getExtras().getString("markers"));
                addMarkers(markers);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, R.string.no_location_specified, Toast.LENGTH_SHORT).show();
        }
    }

    private void addMarkers(JSONArray markers) throws JSONException {
        for (int i = 0; i < markers.length(); i++) {
            JSONObject marker = markers.getJSONObject(i);
            LatLng location = new LatLng(marker.getDouble("lat"),marker.getDouble("lon"));
            mMap.addMarker(new MarkerOptions().position(location).title("Location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        }
    }
}
