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

public class ApiRestaurantList {

    private Context mContext;

    public ApiRestaurantList(Context mContext) {
        this.mContext = mContext;
    }

    public List<Restaurant> getRestaurants(LatLng location, double radius, List<String> tags,
                                           String price, String restaurantName) throws IOException {
        try {

            String endpoint = buildRequest(location, radius, tags, price, restaurantName);

            Pair<Integer, JSONObject> res = new ApiConnection(mContext)
                    .authGet(endpoint, null);
            if(res == null)
                return null;

            if(res.first != 200)
                return null;
            JSONArray rl = res.second.getJSONArray("restaurants");

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

    private String buildRequest(LatLng location, double radius, List<String> tags,
                                String price, String restaurantName)  {

        String request = ApiEndpoints.GET_RESTAURANTS_LIST + "?" +
                "lat=" + location.latitude +
                "&lon=" + location.longitude +
                "&radius=" + new Double(radius).intValue() +
                "&price=" + price +
                "&name=" + restaurantName;
        if(tags == null)
            return request;
        for (String tag: tags) {
            request += "&tags=" + tag;
        }
        return request;
    }

}
