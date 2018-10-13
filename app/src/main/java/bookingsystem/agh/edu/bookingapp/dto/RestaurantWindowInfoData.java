package bookingsystem.agh.edu.bookingapp.dto;

public class RestaurantWindowInfoData {

    private int restaurantId;

    public RestaurantWindowInfoData(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }
}
