package bookingsystem.agh.edu.bookingapp.dto;

import android.support.annotation.NonNull;

public class RestaurantWindowInfoData {

    private int restaurantId;
    private String restaurantName;

    public RestaurantWindowInfoData(int restaurantId, @NonNull String restaurantName) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }
}
