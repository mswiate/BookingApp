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
    private Integer places;
    private String date;
    private String fromTime;
    private Integer length;

    public ApiProposalHours(Context mContext, int restaurantId, String date, String fromTime, int length, int places) {
        this.mContext = mContext;
        this.restaurantId = restaurantId;
        this.length = length;
        this.date = date;
        this.fromTime = fromTime;
        this.places = places;
    }

    public List<BookTime> getProposalBookingHours() {
        try {
            String url = ApiEndpoints.GET_AVAILABLE_BOOKING_DATES +
                    "?date={date}_{fromTime}" +
                    "&length={length}" +
                    "&places={places}";

            url = url.replace("{id}", restaurantId.toString())
                    .replace("{date}", date)
                    .replace("{fromTime}", fromTime)
                    .replace("{length}", length.toString())
                    .replace("{places}", places.toString());

            Pair<Integer, JSONObject> res = new ApiConnection(mContext)
                    .authGet(url, null);
            if(res == null)
                return null;

            if(res.first != 200)
                return null;
            JSONArray rl = res.second.getJSONArray("proposalHours");

            return BookTime.of(res.second);
        } catch (JSONException e) {
            Log.e("ApiRestaurantConnection", "problem with json parsing", e);
            return null;
        } catch (IOException e) {
            Log.e("ApiRestaurantConnection", "problem with io connection", e);
            return null;
        }

    }

}
