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

import bookingsystem.agh.edu.bookingapp.model.BookTime;
import bookingsystem.agh.edu.bookingapp.model.Restaurant;

public class ApiProposalHours {

    private Context mContext;
    private Integer restaurantId;

    public ApiProposalHours(Context mContext, int restaurantId) {
        this.mContext = mContext;
        this.restaurantId = restaurantId;
    }

    public List<BookTime> getProposalBookingHours() throws IOException {
        try {
            String url = ApiEndpoints.GET_AVAILABLE_BOOKING_DATES.replace("{id}", restaurantId.toString()) +
                    "?date=2018-06-22" +
                    "&length=2" +
                    "&places=2";
            Pair<Integer, JSONObject> res = new ApiConnection(mContext).authGet(url, null);
            if(res == null)
                return null;

            if(res.first != 200)
                return null;
            JSONArray rl = res.second.getJSONArray("proposalHours");

            return BookTime.of(res.second);
        } catch (JSONException e) {
            Log.e("ApiRestaurantConnection", "problem with json parsing", e);
            return null;
        }

    }

}
