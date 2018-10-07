package bookingsystem.agh.edu.bookingapp.task;

import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import bookingsystem.agh.edu.bookingapp.dto.RestaurantWindowInfoData;
import bookingsystem.agh.edu.bookingapp.model.RestaurantMarker;

public class GetRestaurantMarkersTask  extends AsyncTask<Void, Void, List<RestaurantMarker>> {

    private GoogleMap googleMap;

    public GetRestaurantMarkersTask(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    @Override
    protected List<RestaurantMarker> doInBackground(Void... voids) {
        List<RestaurantMarker> restaurantMarkers = new ArrayList<>();
        restaurantMarkers.add(new RestaurantMarker(50.060478, 19.937356));
        restaurantMarkers.add(new RestaurantMarker(50.060991, 19.936133));
        return restaurantMarkers;
    }

    @Override
    protected void onPostExecute(List<RestaurantMarker> restaurantMarkers) {
        for(int i = 0; i < restaurantMarkers.size(); i++){
            RestaurantWindowInfoData restaurantWindowInfoData = new RestaurantWindowInfoData();
            restaurantWindowInfoData.setRestaurantId(i);
            Marker marker = googleMap.addMarker(new MarkerOptions().position(restaurantMarkers.get(i).getLatLng()));
            marker.setTag(restaurantWindowInfoData);
        }
    }
}
