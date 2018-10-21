package bookingsystem.agh.edu.bookingapp.task;

import android.content.Context;
import android.os.AsyncTask;

import bookingsystem.agh.edu.bookingapp.api.ApiRestaurantDetails;
import bookingsystem.agh.edu.bookingapp.model.Restaurant;

public class GetRestaurantDetailsTask  extends AsyncTask<Integer, Void, Restaurant> {

    private Context mContext;

    public GetRestaurantDetailsTask(Context mContext) {
        this.mContext = mContext;
    }


    @Override
    protected Restaurant doInBackground(Integer... integers) {
        int restaurantId = integers[0];

        return new ApiRestaurantDetails(mContext, restaurantId).getRestaurantDetails();
    }

}
