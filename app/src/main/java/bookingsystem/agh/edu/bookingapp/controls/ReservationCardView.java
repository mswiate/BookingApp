package bookingsystem.agh.edu.bookingapp.controls;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import bookingsystem.agh.edu.bookingapp.R;
import bookingsystem.agh.edu.bookingapp.util.Constants;

public class ReservationCardView extends CardView {

    private Context mContext;
    private LinearLayout cardView;
    private TextView dateTextView;
    private TextView timeTextView;
    private TextView restaurantNameTextView;
    private TextView reservationLengthTextView;
    private TextView placesTextView;
    private TextView statusTextView;

    public ReservationCardView(@NonNull Context context) {
        this(context, null);
    }

    public ReservationCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.my_reservation_cardview, this);
        this.cardView = this.findViewById(R.id.my_reservation_card);
        this.dateTextView = this.findViewById(R.id.my_reservation_card_date);
        this.timeTextView = this.findViewById(R.id.my_reservation_card_reservation_time);
        this.restaurantNameTextView = this.findViewById(R.id.my_reservation_card_restaurant_name);
        this.reservationLengthTextView = this.findViewById(R.id.my_reservation_card_duration);
        this.placesTextView = this.findViewById(R.id.my_reservation_card_places);
        this.statusTextView = this.findViewById(R.id.my_reservation_card_status);
    }

    public void setDate(String date){
        this.dateTextView.setText(date);
    }

    public void setTime(String time){
        this.timeTextView.setText(time);
    }

    public void setRestaurantName(String name){
        this.restaurantNameTextView.setText(name);
    }

    public void setReservationLength(String length){
        this.reservationLengthTextView.setText(length);
    }

    public void setReservedPlaces(String places){
        this.placesTextView.setText(places);
    }

    public void setCancelledStatus(){
        this.statusTextView.setText(Constants.CANCELLED);
        this.statusTextView.setTextColor(getResources().getColor(R.color.status_cancelled));
        this.cardView.setBackgroundColor(getResources().getColor(R.color.backgroud_status_cancelled));
    }

    public void setFinishedStatus(){
        this.statusTextView.setText(Constants.FINISHED);
        this.statusTextView.setTextColor(getResources().getColor(R.color.status_finished));
        this.cardView.setBackgroundColor(getResources().getColor(R.color.backgroud_status_finished));
    }

    public void setBookedStatus(){
        this.statusTextView.setText(Constants.BOOKED);
        this.statusTextView.setTextColor(getResources().getColor(R.color.status_booked));
        this.cardView.setBackgroundColor(getResources().getColor(R.color.backgroud_status_booked));
    }


}
