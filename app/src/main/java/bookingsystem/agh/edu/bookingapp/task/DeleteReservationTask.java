package bookingsystem.agh.edu.bookingapp.task;

import android.content.Context;
import android.os.AsyncTask;

import bookingsystem.agh.edu.bookingapp.api.ApiCancelReservation;
import bookingsystem.agh.edu.bookingapp.controls.ReservationDetailsDialog;

public class DeleteReservationTask extends AsyncTask<Void, Void, Boolean> {

    private Context mContext;
    private Integer reservationId;
    private ReservationDetailsDialog.CancellReservationCallbackInteface callback;

    public DeleteReservationTask(Context context, Integer reservationId, ReservationDetailsDialog.CancellReservationCallbackInteface callback) {
        this.reservationId = reservationId;
        this.mContext = context;
        this.callback = callback;
    }


    @Override
    protected Boolean doInBackground(Void... voids) {
        return new ApiCancelReservation(mContext, reservationId).cancelReservation();
    }

    @Override
    protected void onPostExecute(Boolean result) {
        callback.onCancelFinished(result);
    }
}
