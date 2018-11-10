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
        Pair<Integer, Object> res = new ApiConnection(mContext).authGet(ApiEndpoints.VALIDATE, null);

        return res.first == 200;
    }

}
