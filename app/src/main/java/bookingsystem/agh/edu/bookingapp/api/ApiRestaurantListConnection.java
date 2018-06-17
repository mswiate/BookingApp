package bookingsystem.agh.edu.bookingapp.api;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

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

    public List<Restaurant> getRestaurants() throws IOException {
        try {
            Pair<Integer, JSONObject> res = new ApiConnection(mContext)
                    .authGet(ApiEndpoints.GET_RESTAURANT_LIST, null);
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

}
