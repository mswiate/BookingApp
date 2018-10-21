package bookingsystem.agh.edu.bookingapp.util;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import bookingsystem.agh.edu.bookingapp.controls.ReservationCardView;
import bookingsystem.agh.edu.bookingapp.model.DoneReservation;

public class MyReservationDrawer {

    private Context mContext;

    public MyReservationDrawer(Context mContext) {
        this.mContext = mContext;
    }

    public ReservationCardView drawReservationCardView(DoneReservation doneReservation){
        if(doneReservation.isCancelled()){
            return drawCancelledReservationCardView(doneReservation);
        }
        if(doneReservation.getDateReservation().getTime() < Calendar.getInstance().getTime().getTime()){
            return drawFinishedReservationCardView(doneReservation);
        }
        return drawBookedReservationCardView(doneReservation);
    }

    private ReservationCardView drawBookedReservationCardView(DoneReservation doneReservation) {
        ReservationCardView reservationCardView = new ReservationCardView(mContext);
        this.setConstData(reservationCardView, doneReservation);
        reservationCardView.setBookedStatus();
        return reservationCardView;
    }

    private ReservationCardView drawFinishedReservationCardView(DoneReservation doneReservation) {
        ReservationCardView reservationCardView = new ReservationCardView(mContext);
        this.setConstData(reservationCardView, doneReservation);
        reservationCardView.setFinishedStatus();
        return reservationCardView;
    }

    private ReservationCardView drawCancelledReservationCardView(DoneReservation doneReservation) {
        ReservationCardView reservationCardView = new ReservationCardView(mContext);
        this.setConstData(reservationCardView, doneReservation);
        reservationCardView.setCancelledStatus();
        return reservationCardView;
    }

    private void setConstData(ReservationCardView reservationCardView, DoneReservation doneReservation) {

        this.setTimeAndDate(reservationCardView, doneReservation);
        reservationCardView.setRestaurantName(doneReservation.getRestaurant().getName());
        reservationCardView.setReservationLength(Integer.toString(doneReservation.getReservationLength()));
        reservationCardView.setReservedPlaces(Integer.toString(doneReservation.getReservedPlaces()));
    }

    private void setTimeAndDate(ReservationCardView reservationCardView, DoneReservation doneReservation) {
        final long ONE_MINUTE_IN_MILLIS=60000;

        Date date = doneReservation.getDateReservation();

        long t = date.getTime();
        Date toTimeDate = new Date(t + (ONE_MINUTE_IN_MILLIS * doneReservation.getReservationLength()));
        String timeFormat = "HH:mm";
        String dateFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat, Locale.getDefault());
        String fromTimeText = sdf.format(date);
        String toTimeText = sdf.format(toTimeDate);

        String timeText = fromTimeText + " - " + toTimeText;

        sdf = new SimpleDateFormat(dateFormat, Locale.getDefault());
        String dateText = sdf.format(date);

        reservationCardView.setDate(dateText);
        reservationCardView.setTime(timeText);

    }
}
