package bookingsystem.agh.edu.bookingapp.task;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bookingsystem.agh.edu.bookingapp.activity.AllRestaurantsActivity;
import bookingsystem.agh.edu.bookingapp.api.ApiRestaurantList;
import bookingsystem.agh.edu.bookingapp.model.Restaurant;

public class GetRestaurantsTask extends AsyncTask<Void, Void, List<Restaurant>> {

    private Context mContext;
    private AllRestaurantsActivity.GetRestaurantsCallback callback;
    private boolean problemWithNet;

    public GetRestaurantsTask(Context mContext, AllRestaurantsActivity.GetRestaurantsCallback callback) {
        this.mContext = mContext;
        this.callback = callback;
    }

    @Override
    protected List<Restaurant> doInBackground(Void... voids) {
        try {
            return new ApiRestaurantList(mContext)
                    .getRestaurants(new LatLng(30, 30), 100, new ArrayList<String>(), "HIGH", "name");
        } catch (IOException e) {
            problemWithNet = true;
            return new ArrayList<>();
        }
    }

    @Override
    protected void onPostExecute(List<Restaurant> restaurants) {
        if(problemWithNet){
            Toast.makeText(mContext, "problem with net", Toast.LENGTH_SHORT).show();
            return;
        }
        callback.onRequestDone(restaurants);
    }

}
