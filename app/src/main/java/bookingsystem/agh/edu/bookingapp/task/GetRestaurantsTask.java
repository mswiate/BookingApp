package bookingsystem.agh.edu.bookingapp.task;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bookingsystem.agh.edu.bookingapp.api.ApiRestaurantList;
import bookingsystem.agh.edu.bookingapp.model.Restaurant;

public class GetRestaurantsTask extends AsyncTask<Void, Void, List<Restaurant>> {

    private LatLng location;
    private double radius;
    private List<String> tags;
    private List<String> prices;
    private String name;
    private Context mContext;
    private GetRestaurantsCallback callback;
    private boolean problemWithNet;

    @Override
    protected List<Restaurant> doInBackground(Void... voids) {
        try {
            return new ApiRestaurantList(mContext)
                    .getRestaurants(location, radius, tags, prices, name);
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

    public static class Builder {

        private LatLng location;
        private double radius;
        private List<String> tags;
        private List<String> prices;
        private String name;
        private Context context;
        private GetRestaurantsCallback callback;

        public Builder(Context context, GetRestaurantsCallback callback) {
            this.context = context;
            this.callback = callback;
        }

        public Builder location(LatLng location, double radius) {
            this.location = location;
            this.radius = radius;
            return this;
        }

        public Builder tags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        public Builder prices(List<String> prices) {
            this.prices = prices;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public GetRestaurantsTask build() {
            return new GetRestaurantsTask(this);
        }
    }

    private GetRestaurantsTask(Builder builder) {
        this.location = builder.location;
        this.radius = builder.radius;
        this.tags = builder.tags;
        this.prices = builder.prices;
        this.name = builder.name;
        this.mContext = builder.context;
        this.callback = builder.callback;
    }

    public interface GetRestaurantsCallback{
        void onRequestDone(List<Restaurant> restaurantList);
    }

}
