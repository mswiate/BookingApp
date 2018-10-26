package bookingsystem.agh.edu.bookingapp.api;

public class ApiEndpoints {

    public static final String REGISTER_USER = "https://restaurant-booking-app.herokuapp.com/api/client/auth/register";
    public static final String GET_TOKEN = "https://restaurant-booking-app.herokuapp.com/api/client/auth";
    public static final String GET_RESERVATIONS = "https://restaurant-booking-app.herokuapp.com/api/client/reservations";
    public static final String GET_RESTAURANT_LIST = "https://restaurant-booking-app.herokuapp.com/api/client/surroundingRestaurants";
    public static final String GET_RESTAURANT_DETAILS = "https://restaurant-booking-app.herokuapp.com/api/client/restaurant{id}";
    public static final String GET_AVAILABLE_BOOKING_DATES = "https://restaurant-booking-app.herokuapp.com/api/client/restaurant{id}/freeDates";
    public static final String POST_RESERVATION = "https://restaurant-booking-app.herokuapp.com/api/client/restaurant{id}/reservation/add/";
    public static final String DELETE_RESERVATION = "https://restaurant-booking-app.herokuapp.com/api/client/reservation{id}/cancel";
    public static final String VALIDATE = "https://restaurant-booking-app.herokuapp.com/api/client/validate";
}
