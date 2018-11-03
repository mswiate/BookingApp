package bookingsystem.agh.edu.bookingapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import bookingsystem.agh.edu.bookingapp.R;
import bookingsystem.agh.edu.bookingapp.activity.AllRestaurantsActivity;
import bookingsystem.agh.edu.bookingapp.model.Restaurant;

public class AllRestaurantsAdapter extends BaseAdapter {

    private List<Restaurant> restaurantList;
    private Context mContext;

    public AllRestaurantsAdapter(Context context, List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return restaurantList.size();
    }

    @Override
    public Object getItem(int position) {
        return restaurantList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Restaurant currentRestaurant = this.restaurantList.get(position);

        convertView = ((AllRestaurantsActivity)mContext).getLayoutInflater().inflate(R.layout.all_restaurants_restaurant_entity, null);
        TextView restaurantName = convertView.findViewById(R.id.restaurant_entity_restaurant_name);
        TextView restaurantAddress = convertView.findViewById(R.id.restaurant_entity_restaurant_address);
        restaurantName.setText(currentRestaurant.getName());
        String address = currentRestaurant.getCity() + ", " + currentRestaurant.getStreet();
        restaurantAddress.setText(address);

        return convertView;
    }
}
