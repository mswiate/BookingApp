package bookingsystem.agh.edu.bookingapp.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DoneReservation implements Serializable {
    private int id;
    private int reservedPlaces;
    private int reservationLength;
    private int restaurantId;
    private boolean cancelled;
    private Date dateReservation;
    private String comment;
    private Restaurant restaurant;

    public DoneReservation(int id, int reservedPlaces, int reservationLength, int restaurantId, boolean cancelled, Date dateReservation, String comment) {
        this.id = id;
        this.reservedPlaces = reservedPlaces;
        this.reservationLength = reservationLength;
        this.restaurantId = restaurantId;
        this.cancelled = cancelled;
        this.dateReservation = dateReservation;
        this.comment = comment;
    }

    public static List<DoneReservation> of(JSONObject jsonObject) throws JSONException, ParseException {
        JSONArray jsonArray = jsonObject.getJSONArray("reservations");
        if(jsonArray == null){
            return null;
        }
        List<DoneReservation> doneReservations = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); ++i){
            JSONObject json = new JSONObject(jsonArray.getString(i));
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm", Locale.getDefault());
            Date date = dateFormat.parse(json.getString("dateReservation"));
            doneReservations.add(new DoneReservation(
                    json.getInt("id"),
                    json.getInt("reservedPlaces"),
                    json.getInt("reservationLength"),
                    json.getInt("restaurantId"),
                    json.getBoolean("cancelled"),
                    date,
                    json.getString("comment")));
        }
        return doneReservations;
    }

    public int getId() {
        return id;
    }

    public int getReservedPlaces() {
        return reservedPlaces;
    }

    public int getReservationLength() {
        return reservationLength;
    }

    public Date getDateReservation() {
        return dateReservation;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public String getComment() {
        return comment;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public boolean isReservationOutdated(){
        return this.getDateReservation().getTime() < Calendar.getInstance().getTime().getTime();
    }

    public String getFormatedTime(){
        final long ONE_MINUTE_IN_MILLIS=60000;
        Date date = this.getDateReservation();

        long t = date.getTime();
        Date toTimeDate = new Date(t + (ONE_MINUTE_IN_MILLIS * this.getReservationLength()));
        String timeFormat = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat, Locale.getDefault());
        String fromTimeText = sdf.format(date);
        String toTimeText = sdf.format(toTimeDate);

        return fromTimeText + " - " + toTimeText;
    }

    public String getFormatedDate(){
        String dateFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.getDefault());
        return sdf.format(this.getDateReservation());
    }
}
