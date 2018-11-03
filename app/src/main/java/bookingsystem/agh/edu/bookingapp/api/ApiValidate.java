package bookingsystem.agh.edu.bookingapp.api;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ApiValidate {

    private Context mContext;

    public ApiValidate(Context mContext) {
        this.mContext = mContext;
    }

    public boolean validate() throws IOException {
        try {
            Pair<Integer, Object> res = new ApiConnection(mContext).authGet(ApiEndpoints.VALIDATE, null);

            if(res.first == 200)
                return "Correct validation".equalsIgnoreCase(((JSONObject)res.second).getString("desc"));

        } catch (JSONException e) {
            Log.e("ApiValidate", "problem with json", e);
        }
        return false;
    }

}
