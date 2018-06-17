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
    private Context mContext;
    private String date;
    private Integer restaurantId;

    public ApiBookHour(Context mContext, String date, int restaurantId) {
        this.mContext = mContext;
        this.date = date;
        this.restaurantId = restaurantId;
    }

    public boolean makeReservation() {

        try {
            String url = ApiEndpoints.POST_RESERVATION.replace("{id}", "2") +
                    "?date=" + date +
                    "&length=2" +
                    "&places=2" +
                    "&comment=blablabla";
            Pair<Integer, JSONObject> res = new ApiConnection(mContext).authPost(url, null);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
