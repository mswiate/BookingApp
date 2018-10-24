package bookingsystem.agh.edu.bookingapp.task;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

import bookingsystem.agh.edu.bookingapp.R;
import bookingsystem.agh.edu.bookingapp.activity.MyReservationsActivity;
import bookingsystem.agh.edu.bookingapp.api.ApiGetReservations;
import bookingsystem.agh.edu.bookingapp.api.ApiRestaurantDetails;
import bookingsystem.agh.edu.bookingapp.controls.ReservationCardView;
import bookingsystem.agh.edu.bookingapp.controls.ReservationDetailsDialog;
import bookingsystem.agh.edu.bookingapp.model.DoneReservation;
import bookingsystem.agh.edu.bookingapp.util.MyReservationDrawer;

public class GetMyReservationTask extends AsyncTask<Void, Void, List<DoneReservation>> {

    private Context mContext;

    public GetMyReservationTask(Context mContext) {
        this.mContext = mContext;
    }


    @Override
    protected List<DoneReservation> doInBackground(Void... voids) {

        List<DoneReservation> myReservations = new ApiGetReservations(mContext)
                .getMyReservations();

        for(int i=0; i < myReservations.size(); i++){
            myReservations.get(i)
                    .setRestaurant(
                            new ApiRestaurantDetails(mContext, myReservations.get(i).getRestaurantId())
                                    .getRestaurantDetails());
        }
        return myReservations;
    }

    @Override
    protected void onPostExecute(final List<DoneReservation> doneReservations) {
        MyReservationsActivity activity = (MyReservationsActivity) mContext;
        LinearLayout linearLayout = activity.findViewById(R.id.my_reservation_layout);
        MyReservationDrawer drawer = new MyReservationDrawer(mContext);
        for (int i = 0; i < doneReservations.size(); i++){
            final ReservationCardView reservationCardView = drawer.drawReservationCardView(doneReservations.get(i));
            final int finalI = i;
            reservationCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ReservationDetailsDialog dialog = ReservationDetailsDialog.newInstance(doneReservations.get(finalI));
                    dialog.show(((MyReservationsActivity) mContext).getFragmentManager(), "reservationdetails");
                }
            });
            linearLayout.addView(reservationCardView);
        }
    }
}
