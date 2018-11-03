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
                                           List<String> prices, String restaurantName) throws IOException {
        try {

            String endpoint = buildRequest(location, radius, tags, prices, restaurantName);

            Log.i("Current request: ", endpoint);
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

    private String buildRequest(LatLng location, double radius, List<String> tags,
                                List<String> prices, String restaurantName)  {

        String request = ApiEndpoints.GET_RESTAURANTS_LIST;

        String separator = "?";

        if(location !=null) {
            request += separator +  "lat=" + location.latitude;
            separator = "&";
            request += "&lon=" + location.longitude;
            request += "&radius=" + new Double(radius).intValue();
        }
        if(restaurantName != null) {
            request += separator + "name=" + restaurantName;
            separator = "&";
        }

        if(tags != null) {
            for (String tag : tags) {
                request += separator + "tags=" + tag;
                separator = "&";
            }
        }
        if(prices != null) {
            for (String price : prices) {
                request += separator + "price=" + price;
                separator = "&";
            }
        }

        return request;
    }

}
