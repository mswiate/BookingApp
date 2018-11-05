package bookingsystem.agh.edu.bookingapp.util;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import bookingsystem.agh.edu.bookingapp.model.Restaurant;

public class MarkerDrawer {

    private static float RANGE_MIN = 1.0f;
    private static float RANGE_MAX = 120.0f;

    public static MarkerOptions getMarker(Restaurant restaurant){
        float markerColor = getMarkerColor(restaurant);
        return new MarkerOptions().position(new LatLng(
                restaurant.getLatitude(),
                restaurant.getLongitude()))
                .icon(BitmapDescriptorFactory
                        .defaultMarker(markerColor));
    }

    private static float getMarkerColor(Restaurant restaurant) {
        double priority = restaurant.getPriority();
        return (float) ((RANGE_MAX - RANGE_MIN) * priority);
    }

}
