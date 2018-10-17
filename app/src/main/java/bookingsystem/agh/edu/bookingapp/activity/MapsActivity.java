package bookingsystem.agh.edu.bookingapp.activity;

import android.Manifest;
import android.accounts.AccountManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnSuccessListener;

import bookingsystem.agh.edu.bookingapp.R;
import bookingsystem.agh.edu.bookingapp.adapter.googlemaps.RestaurantInfoWindowGoogleMap;
import bookingsystem.agh.edu.bookingapp.dto.RestaurantWindowInfoData;
import bookingsystem.agh.edu.bookingapp.task.GetRestaurantMarkersTask;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnCameraIdleListener {

    private final LatLng CRACOW_TOWN_SQUARE_LAT_LNG = new LatLng(50.0618971, 19.9345672);
    private GoogleMap mMap;
    private DrawerLayout mDrawerLayour;
    private ActionBarDrawerToggle mToggle;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        prepareNavigationMenu();

    }

    @Override
    protected void onResume() {
        super.onResume();
        AccountManager am = AccountManager.get(this);
        if(am.getAccountsByType(getString(R.string.account_type)).length == 0) {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    private void prepareNavigationMenu(){
        mDrawerLayour = findViewById(R.id.drawerLayour);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayour, R.string.nav_menu_open, R.string.nav_menu_close);
        mDrawerLayour.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        this.mMap = googleMap;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission
                    (this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    &&
                    ActivityCompat.checkSelfPermission
                            (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                }, 1);
            }
        }
        focusMapOnCurrentLocation();

        RestaurantInfoWindowGoogleMap restaurantInfoWindow = new RestaurantInfoWindowGoogleMap(this);
        googleMap.setInfoWindowAdapter(restaurantInfoWindow);
        mMap.setOnCameraIdleListener(this);

        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Toast.makeText(MapsActivity.this, "dadada", Toast.LENGTH_SHORT).show();
            }
        });

        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(MapsActivity.this, ReservationActivity.class);
                int restaurantId = ((RestaurantWindowInfoData) marker.getTag()).getRestaurantId();
                intent.putExtra("restaurantId", restaurantId);
                startActivity(intent);
            }
        });

    }

    private void focusMapOnCurrentLocation() {
        if (mMap == null) {
            return;
        }
        try {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mFusedLocationProviderClient.getLastLocation().
                        addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location == null) {
                                    mMap.setMyLocationEnabled(false);
                                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                            CRACOW_TOWN_SQUARE_LAT_LNG,15));
                                    return;
                                }
                                mMap.setMyLocationEnabled(true);
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(location.getLatitude(), location.getLongitude()),13));
                            }
                        });

            } else {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        CRACOW_TOWN_SQUARE_LAT_LNG,13));
                mMap.setMyLocationEnabled(false);
            }

        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void markRestaurants() {
        new GetRestaurantMarkersTask(getApplicationContext(), mMap, mMap.getCameraPosition(), mMap.getProjection().getVisibleRegion()).execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCameraIdle() {
        markRestaurants();
    }
}
