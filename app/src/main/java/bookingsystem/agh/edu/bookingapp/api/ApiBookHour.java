package bookingsystem.agh.edu.bookingapp.api;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import bookingsystem.agh.edu.bookingapp.model.BookTime;

public class ApiBookHour {
    private final Integer restaurantId;
    private Context mContext;
    private String date;
    private String time;
    private Integer length;

    public ApiBookHour( Context mContext, Integer restaurantId, String date, String time, Integer length, Integer places) {
        this.restaurantId = restaurantId;
        this.mContext = mContext;
        this.date = date;
        this.time = time;
        this.length = length;
        this.places = places;
    }

    private Integer places;


    public boolean makeReservation() {

        try {
            JSONObject body = new JSONObject()
                    .put("date", String.valueOf(date + "_" + time))
                    .put("length", length.toString())
                    .put("places", places.toString())
                    .put("comment", "");

            String url = ApiEndpoints.POST_RESERVATION.replace("{id}", restaurantId.toString());
            Pair<Integer, JSONObject> res = new ApiConnection(mContext).authPost(url, body);

            if(res == null)
                return false;

            if(res.first != 201)
                return false;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
