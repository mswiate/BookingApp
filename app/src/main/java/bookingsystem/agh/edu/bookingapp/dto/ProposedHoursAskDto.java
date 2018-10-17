package bookingsystem.agh.edu.bookingapp.dto;

public class ProposedHoursAskDto {
    private int restaurantId;
    private int nubmerOfGuests;
    private String date;
    private String fromTime;
    private String toTime;

    public ProposedHoursAskDto(int restaurantId, int nubmerOfGuests, String date, String fromTime, String toTime) {
        this.restaurantId = restaurantId;
        this.nubmerOfGuests = nubmerOfGuests;
        this.date = date;
        this.fromTime = fromTime;
        this.toTime = toTime;
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

}
