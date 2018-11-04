package bookingsystem.agh.edu.bookingapp.task;

import android.content.Context;
import android.os.AsyncTask;

import bookingsystem.agh.edu.bookingapp.activity.ReservationActivity;
import bookingsystem.agh.edu.bookingapp.api.ApiRestaurantDetails;
import bookingsystem.agh.edu.bookingapp.model.Restaurant;

public class GetRestaurantDetailsTask  extends AsyncTask<Integer, Void, Restaurant> {

    private Context mContext;
    private ReservationActivity.GetRestaurantDetailsCallback callback;

    public GetRestaurantDetailsTask(Context mContext, ReservationActivity.GetRestaurantDetailsCallback callback) {
        this.mContext = mContext;
        this.callback = callback;
    }


    @Override
    protected Restaurant doInBackground(Integer... integers) {
        int restaurantId = integers[0];

        return new ApiRestaurantDetails(mContext, restaurantId).getRestaurantDetails();
    }

    @Override
    protected void onPostExecute(Restaurant restaurant) {
        callback.onRequestDone(restaurant);
    }
}
