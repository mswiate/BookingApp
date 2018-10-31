package bookingsystem.agh.edu.bookingapp.controls;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import bookingsystem.agh.edu.bookingapp.R;

public class RestaurantEntity extends LinearLayout {

    private Context context;
    private TextView mRestaurantName;
    private TextView mRestaurantAddress;

    public RestaurantEntity(Context context) {
        super(context);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.all_restaurants_restaurant_entity, this);
        this.mRestaurantAddress = findViewById(R.id.restaurant_entity_restaurant_address);
        this.mRestaurantName = findViewById(R.id.restaurant_entity_restaurant_name);
    }

    public void setRestaurantName(String name){
        this.mRestaurantName.setText(name);
    }

    public void setRestaurantAddress(String address){
        this.mRestaurantName.setText(address);
    }


}
