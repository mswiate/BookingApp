package bookingsystem.agh.edu.bookingapp.task;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bookingsystem.agh.edu.bookingapp.api.ApiRestaurantListConnection;
import bookingsystem.agh.edu.bookingapp.dto.RestaurantWindowInfoData;
import bookingsystem.agh.edu.bookingapp.model.Restaurant;
import bookingsystem.agh.edu.bookingapp.model.RestaurantMarker;

public class GetRestaurantMarkersTask  extends AsyncTask<Void, Void, List<Restaurant>> {

    private final Context mContext;
    private final CameraPosition cameraPosition;
    private final VisibleRegion visibleRegion;
    private GoogleMap googleMap;
    private boolean problemWithNet;

    public GetRestaurantMarkersTask(Context context, GoogleMap googleMap, CameraPosition cameraPosition, VisibleRegion visibleRegion) {
        this.mContext = context;
        this.cameraPosition = cameraPosition;
        this.visibleRegion = visibleRegion;
        this.googleMap = googleMap;
    }

    @Override
    protected List<Restaurant> doInBackground(Void... voids) {
        try {
            LatLng location = cameraPosition.target;
            float radius = getRadius();

            List<Restaurant> restaurants = new ApiRestaurantListConnection(mContext)
                    .getRestaurants(location, radius, null);

            return restaurants == null ? new ArrayList<Restaurant>(): restaurants;
        } catch (IOException e) {
            problemWithNet = true;
            return new ArrayList<>();
        }
    }

    @Override
    protected void onPostExecute(List<Restaurant> restaurants) {
        for (Restaurant restaurant: restaurants) {
            RestaurantWindowInfoData tag = new RestaurantWindowInfoData(restaurant.getId());
            Marker marker = googleMap.addMarker(
                    new MarkerOptions().position(new LatLng(
                            restaurant.getLatitude(),
                            restaurant.getLongitude())));

            marker.setTag(tag);
        }
        if(problemWithNet)
            Toast.makeText(mContext, "problem with net", Toast.LENGTH_SHORT).show();
    }


    private float getRadius() {
        LatLngBounds region = visibleRegion.latLngBounds;
        LatLng ne = region.northeast;
        LatLng sw = region.southwest;
        float[] result = new float[1];
        Location.distanceBetween(ne.latitude, ne.longitude, sw.latitude, sw.longitude, result);
        return result[0] / 2.0f;
    }

}
