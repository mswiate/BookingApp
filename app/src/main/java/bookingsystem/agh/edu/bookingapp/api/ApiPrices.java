package bookingsystem.agh.edu.bookingapp.api;

import android.content.Context;
import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ApiPrices {

    private Context mContext;

    public ApiPrices(Context mContext) {
        this.mContext = mContext;
    }

    public List<String> getPrices(){

        try {

            String url = ApiEndpoints.PRICES;
            Pair<Integer, Object> res = new ApiConnection(mContext).get(url, null);

            if(res == null)
                return null;

            if(res.first != 200)
                return null;
            JSONArray list = (JSONArray) res.second;
            List<String> prices = new ArrayList<>();

            for (int i = 0; i < list.length(); i++) {
                prices.add(list.getString(i));
            }

            return prices;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
