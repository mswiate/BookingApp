package bookingsystem.agh.edu.bookingapp.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import bookingsystem.agh.edu.bookingapp.R;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DrawerLayout mDrawerLayour;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        prepareNavigationMenu();
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
                //return;
            }
            else{

                googleMap.setMyLocationEnabled(true);
//                Location location;
//                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//                if (locationManager == null) {
//                    return;
//                }
//                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                LatLng latLng = new LatLng(location.getLatitude(),
//                        location.getLongitude());
//                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//
//                googleMap.addMarker(new MarkerOptions().position(latLng).title("I am here"));
//
//                // For zooming automatically to the location of the marker
//                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//                googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            }
        }
    }

// @Override
//public void onRequestPermissionsResult(int requestCode,
//                                       @NonNull String permissions[],
//                                       @NonNull int[] grantResults) {
//    mLocationPermissionGranted = false;
//    switch (requestCode) {
//        case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
//            // If request is cancelled, the result arrays are empty.
//            if (grantResults.length > 0
//                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                mLocationPermissionGranted = true;
//            }
//        }
//    }
//    updateLocationUI();
//}

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
