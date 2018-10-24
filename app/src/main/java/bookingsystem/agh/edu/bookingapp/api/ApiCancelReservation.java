package bookingsystem.agh.edu.bookingapp.api;

import android.content.Context;
import android.util.Pair;

import org.json.JSONObject;

import java.io.IOException;

public class ApiCancelReservation {

    private Integer reservationId;
    private Context mContext;

    public ApiCancelReservation( Context mContext, Integer reservationId) {
        this.mContext = mContext;
        this.reservationId = reservationId;
    }

    public boolean cancelReservation() {

        try {

            String url = ApiEndpoints.DELETE_RESERVATION.replace("{id}", reservationId.toString());
            Pair<Integer, JSONObject> res = new ApiConnection(mContext).authPost(url, null);

            if(res == null)
                return false;

            if(res.first != 200)
                return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}