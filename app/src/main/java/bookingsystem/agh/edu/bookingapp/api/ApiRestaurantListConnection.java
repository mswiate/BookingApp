package bookingsystem.agh.edu.bookingapp.api;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.util.Pair;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bookingsystem.agh.edu.bookingapp.model.Restaurant;

public class ApiRestaurantListConnection {

    private Context mContext;

    public ApiRestaurantListConnection(Context mContext) {
        this.mContext = mContext;
    }

    public List<Restaurant> getRestaurants(LatLng location, double radius, List<String> tags) throws IOException {
        try {

            String endpoint = buildRequest(ApiEndpoints.GET_RESTAURANTS_LIST, location, radius, tags);

            Pair<Integer, Object> res = new ApiConnection(mContext)
                    .authGet(endpoint, null);
            if(res == null)
                return null;

            if(res.first != 200)
                return null;
            JSONArray rl = ((JSONObject) res.second).getJSONArray("restaurants");

            List<Restaurant> restaurants = new ArrayList<>();
            for(int i = 0; i < rl.length(); i++) {
                restaurants.add(Restaurant.of(new JSONObject(rl.getString(i))));
            }
            return restaurants;
        } catch (JSONException e) {
            Log.e("ApiRestaurantConnection", "problem with json parsing", e);
            return null;
        }

    }

    private String buildRequest(String getRestaurantList, LatLng location, double radius, List<String> tags) {
        String request = getRestaurantList + "?" +
                "lat=" + location.latitude +
                "&lon=" + location.longitude +
                "&radius=" + new Double(radius).intValue();
        if(tags == null)
            return request;
        for (String tag: tags) {
            request += "&tags=" + tag;
        }
        return request;
    }

}
