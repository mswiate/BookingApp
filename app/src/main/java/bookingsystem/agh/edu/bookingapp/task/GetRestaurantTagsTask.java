package bookingsystem.agh.edu.bookingapp.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;
import android.widget.Toast;

import java.util.List;

import bookingsystem.agh.edu.bookingapp.api.ApiPrices;
import bookingsystem.agh.edu.bookingapp.api.ApiTags;

public class GetRestaurantTagsTask  extends AsyncTask<Void, Void, Pair<List<String>, List<String>>> {

    private GetRestaurantTagsCallback callback;
    private boolean problemWithNet;
    private Context mContext;

    public GetRestaurantTagsTask(Context mContext, GetRestaurantTagsCallback callback) {
        this.mContext = mContext;
        this.callback = callback;
    }

    @Override
    protected Pair<List<String>, List<String>> doInBackground(Void... voids) {
        List<String> prices = new ApiPrices(mContext).getPrices();
        List<String> tags = new ApiTags(mContext).getTags();
        return new Pair<>(prices, tags);
    }

    @Override
    protected void onPostExecute(Pair<List<String>, List<String>> restaurantInfo) {
        if(problemWithNet){
            Toast.makeText(mContext, "problem with net", Toast.LENGTH_SHORT).show();
            return;
        }
        callback.onRequestDone(restaurantInfo);
    }


    public interface GetRestaurantTagsCallback{
        void onRequestDone(Pair<List<String>, List<String>> restaurantInfo);
    }
}
