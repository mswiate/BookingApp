package bookingsystem.agh.edu.bookingapp.api;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import bookingsystem.agh.edu.bookingapp.api.ApiConnection;
import bookingsystem.agh.edu.bookingapp.api.ApiEndpoints;

public class ApiAuthConnection {

    private Context mContext;

    public ApiAuthConnection(Context mContext) {
        this.mContext = mContext;
    }

    public String getToken(String username, String password) throws IOException {
        try {
            JSONObject auth = new JSONObject()
                        .put("username", username)
                        .put("password", password);
            Pair<Integer, Object> res = new ApiConnection(mContext).post(ApiEndpoints.GET_TOKEN, auth);

            if(res.first == 200)
                return ((JSONObject) res.second).getString("token");

        } catch (JSONException e) {
            Log.e("getToken", "problem with json", e);
        }
        return null;
    }


    public boolean register(String username, String password) throws IOException {
        try {
            JSONObject auth = new JSONObject()
                    .put("username", username)
                    .put("password", password);
            Pair<Integer, Object> res = new ApiConnection(mContext).post(ApiEndpoints.REGISTER_USER, auth);
            return res.first == 204;
        } catch (JSONException e) {
            Log.e("register", "problem with json", e);
        }
        return false;
    }

}
