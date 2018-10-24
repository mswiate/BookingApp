package bookingsystem.agh.edu.bookingapp.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ProposedHoursAskDto {
    private int restaurantId;
    private int nubmerOfGuests;
    private String date;
    private String fromTime;
    private String toTime;
    private int length;

    public ProposedHoursAskDto(int restaurantId, int nubmerOfGuests, String date, String fromTime, String toTime) throws ParseException {
        this.restaurantId = restaurantId;
        this.nubmerOfGuests = nubmerOfGuests;
        this.date = date;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.length = getReservationLength();
    }

    public int getLength() {
        return length;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public int getNubmerOfGuests() {
        return nubmerOfGuests;
    }

    public String getDate() {
        return date;
    }

    public String getFromTime() {
        return fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    private int getReservationLength() throws ParseException {

        String timeFormat = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat, Locale.getDefault());
        Date parsedFromTime = sdf.parse(fromTime);
        Date parsedToTime = sdf.parse(toTime);
        long l = parsedToTime.getTime() - parsedFromTime.getTime();
        return (int) (l / (1000 * 60));
    }

}
