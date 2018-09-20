package bookingsystem.agh.edu.bookingapp.model;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

public class RestaurantMarker {

    private LatLng latLng;

    public RestaurantMarker(double latitude, double longitude) {
        this.latLng = new LatLng(latitude, longitude);
    }

    public static RestaurantMarker of(JSONObject jsonObject) throws JSONException {
        return new RestaurantMarker(jsonObject.getDouble("latitude"), jsonObject.getDouble("longitude"));
    }

    public LatLng getLatLng() {
        return latLng;
    }
}
