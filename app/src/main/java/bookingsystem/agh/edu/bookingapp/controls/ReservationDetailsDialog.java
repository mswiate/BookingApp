package bookingsystem.agh.edu.bookingapp.controls;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import bookingsystem.agh.edu.bookingapp.R;
import bookingsystem.agh.edu.bookingapp.model.DoneReservation;
import bookingsystem.agh.edu.bookingapp.task.DeleteReservationTask;
import bookingsystem.agh.edu.bookingapp.task.GetMyReservationTask;
import bookingsystem.agh.edu.bookingapp.util.Constants;
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class ReservationDetailsDialog extends DialogFragment {

    private DoneReservation reservation;
    private CircularProgressButton cancelButton;
    private View dialog;
    private Context activityContext;


    public static ReservationDetailsDialog newInstance(DoneReservation reservation, Context context){
        ReservationDetailsDialog dialog = new ReservationDetailsDialog();
        Bundle args = new Bundle();
        args.putSerializable("reservation", reservation);
        dialog.setArguments(args);
        dialog.activityContext = context;
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this.reservation = (DoneReservation) getArguments().getSerializable("reservation");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View proposedHoursDialog = inflater.inflate(R.layout.dialog_reservation_details, null);
        this.dialog = proposedHoursDialog;
        this.displayRestaurantDetails(proposedHoursDialog);
        this.displayReservationDetails(proposedHoursDialog);
        this.setOnCancelButtonClickListener(proposedHoursDialog);
        this.setTheme(proposedHoursDialog);
        builder.setView(proposedHoursDialog)
                .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }

    private void setOnCancelButtonClickListener(View dialog) {
        this.cancelButton = dialog.findViewById(R.id.reservation_cancel_button);

        final CancellReservationCallbackInteface callback = new CancellReservationCallbackInteface() {
            @Override
            public void onCancelFinished(Boolean result) {
                if(!result){
                    Toast.makeText(ReservationDetailsDialog.this.getActivity(), "Cannot cancel this date", Toast.LENGTH_SHORT).show();
                    cancelButton.revertAnimation();
                }else {
                    Toast.makeText(ReservationDetailsDialog.this.getActivity(), "Date has been successfully cancelled", Toast.LENGTH_SHORT).show();
                    ReservationDetailsDialog.this.setCancelledTheme(ReservationDetailsDialog.this.dialog);
                    ((ViewGroup) cancelButton.getParent()).removeView(cancelButton);
                }
            }
        };

        this.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelButton.startAnimation();
                DeleteReservationTask task = new DeleteReservationTask(ReservationDetailsDialog.this.getActivity(),
                        ReservationDetailsDialog.this.reservation.getId(),
                        callback);
                task.execute();
                new GetMyReservationTask(getActivity()).execute();
            }
        });
    }

    private void displayReservationDetails(View dialog) {
        ((TextView)dialog.findViewById(R.id.reservation_duration)).setText(Integer.toString(reservation.getReservationLength()));
        ((TextView)dialog.findViewById(R.id.reservation_places)).setText(Integer.toString(reservation.getReservedPlaces()));
        ((TextView)dialog.findViewById(R.id.reservation_date)).setText(reservation.getFormatedDate());
        ((TextView)dialog.findViewById(R.id.reservation_reservation_time)).setText(reservation.getFormatedTime());
    }

    private void displayRestaurantDetails(View dialog){
        ((TextView)dialog.findViewById(R.id.reservation_restaurant_name)).setText(reservation.getRestaurant().getName());
        ((TextView)dialog.findViewById(R.id.reservation_restaurant_city)).setText("City: " + reservation.getRestaurant().getCity());
        ((TextView)dialog.findViewById(R.id.reservation_restaurant_street)).setText("Street: " + reservation.getRestaurant().getStreet());

        if(reservation.getRestaurant().getWebsite().equals("null")){
            TextView websiteTextView = dialog.findViewById(R.id.reservation_restaurant_website);
            ((ViewGroup) websiteTextView.getParent()).removeView(websiteTextView);
        }else {
            ((TextView)dialog.findViewById(R.id.reservation_restaurant_website)).setText("Website: " + reservation.getRestaurant().getWebsite());
        }

        if(reservation.getRestaurant().getPhoneNumber().equals("null")){
            TextView phoneNumberTextView = dialog.findViewById(R.id.reservation_restaurant_phone);
            ((ViewGroup) phoneNumberTextView.getParent()).removeView(phoneNumberTextView);
        }else {
            ((TextView)dialog.findViewById(R.id.reservation_restaurant_phone)).setText("Phone: " + reservation.getRestaurant().getPhoneNumber());
        }

        if(reservation.getRestaurant().getTags().isEmpty()){
            TextView tagsHeader = dialog.findViewById(R.id.reservation_restaurant_tags_header);
            ((ViewGroup) tagsHeader.getParent()).removeView(tagsHeader);

            TextView tagsTextView = dialog.findViewById(R.id.reservation_restaurant_tags);
            ((ViewGroup) tagsTextView.getParent()).removeView(tagsTextView);
        }else {
            StringBuilder builder = new StringBuilder();
            List<String> tags = reservation.getRestaurant().getTags();
            for(int i=0; i<tags.size(); i++){
                builder.append(tags.get(i));
                builder.append("  ");
            }
            ((TextView)dialog.findViewById(R.id.reservation_restaurant_tags)).setText(builder.toString());
        }

    }

    private void setTheme(View dialog) {
        if(reservation.isCancelled()){
            this.setCancelledTheme(dialog);
            View cancelButton = dialog.findViewById(R.id.reservation_cancel_button);
            ((ViewGroup) cancelButton.getParent()).removeView(cancelButton);
        }
        else if(reservation.isReservationOutdated()){
            this.setReservationOutdatedTheme(dialog);
            View cancelButton = dialog.findViewById(R.id.reservation_cancel_button);
            ((ViewGroup) cancelButton.getParent()).removeView(cancelButton);
        } else {
            this.setReservationInTimeTheme(dialog);
        }
    }

    private void setCancelledTheme(View dialog){
        TextView statusTextView = dialog.findViewById(R.id.reservation_status);
        statusTextView.setText(Constants.CANCELLED);
        statusTextView.setTextColor(getResources().getColor(R.color.status_cancelled));
        LinearLayout layout = dialog.findViewById(R.id.reservation_layout);
        layout.setBackgroundColor(getResources().getColor(R.color.backgroud_status_cancelled));

    }

    private void setReservationInTimeTheme(View dialog){
        TextView statusTextView = dialog.findViewById(R.id.reservation_status);
        statusTextView.setText(Constants.BOOKED);
        statusTextView.setTextColor(getResources().getColor(R.color.status_cancelled));
        LinearLayout layout = dialog.findViewById(R.id.reservation_layout);
        layout.setBackgroundColor(getResources().getColor(R.color.backgroud_status_booked));

    }

    private void setReservationOutdatedTheme(View dialog){
        TextView statusTextView = dialog.findViewById(R.id.reservation_status);
        statusTextView.setText(Constants.FINISHED);
        statusTextView.setTextColor(getResources().getColor(R.color.status_booked));
        LinearLayout layout = dialog.findViewById(R.id.reservation_layout);
        layout.setBackgroundColor(getResources().getColor(R.color.backgroud_status_finished));

    }

    public interface CancellReservationCallbackInteface {
        void onCancelFinished(Boolean result);
    }

}
