package bookingsystem.agh.edu.bookingapp.api;

public class ApiEndpoints {

    public static final String REGISTER_USER = "https://restaurant-booking-app.herokuapp.com/api/client/auth/register";
    public static final String GET_TOKEN = "https://restaurant-booking-app.herokuapp.com/api/client/auth";
    public static final String GET_RESTAURANT_LIST = "https://restaurant-booking-app.herokuapp.com/api/client/surroundingRestaurants";
    public static final String GET_AVAILABLE_BOOKING_DATES = "https://restaurant-booking-app.herokuapp.com/api/restaurant{id}/freeDates";
    public static final String POST_RESERVATION = "https://restaurant-booking-app.herokuapp.com/api/restaurant{id}/reservation/add/";
}
