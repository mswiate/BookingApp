package bookingsystem.agh.edu.bookingapp.api;

import android.content.Context;
import android.util.Pair;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import bookingsystem.agh.edu.bookingapp.model.DoneReservation;

public class ApiGetReservations {

    private Context mContext;

    public ApiGetReservations(Context mContext) {
        this.mContext = mContext;
    }

    public List<DoneReservation> getMyReservations(){

        try {

            String url = ApiEndpoints.GET_RESERVATIONS;
            Pair<Integer, JSONObject> res = new ApiConnection(mContext).authGet(url, null);

            if(res == null)
                return null;

            if(res.first != 200)
                return null;

            return DoneReservation.of(res.second);
        } catch (IOException | ParseException |  JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
