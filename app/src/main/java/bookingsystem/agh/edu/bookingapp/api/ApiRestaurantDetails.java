package bookingsystem.agh.edu.bookingapp.api;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import bookingsystem.agh.edu.bookingapp.model.BookTime;
import bookingsystem.agh.edu.bookingapp.model.Restaurant;

public class ApiRestaurantDetails {

    private Context mContext;
    private Integer restaurantId;

    public ApiRestaurantDetails(Context mContext, Integer restaurantId) {
        this.mContext = mContext;
        this.restaurantId = restaurantId;
    }

    public Restaurant getRestaurantDetails(){
        try {
            String url = ApiEndpoints.GET_RESTAURANT_DETAILS
                    .replace("{id}", restaurantId.toString());

            Pair<Integer, JSONObject> res = new ApiConnection(mContext)
                    .authGet(url, null);
            if(res == null)
                return null;

            if(res.first != 200)
                return null;

            return Restaurant.of(res.second);
        } catch (JSONException e) {
            Log.e("ApiRestaurantConnection", "problem with json parsing", e);
            return null;
        } catch (IOException e) {
            Log.e("ApiRestaurantConnection", "problem with io connection", e);
            return null;
        }
    }
}
