package bookingsystem.agh.edu.bookingapp.controls;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

import bookingsystem.agh.edu.bookingapp.R;
import bookingsystem.agh.edu.bookingapp.activity.ReservationActivity;
import bookingsystem.agh.edu.bookingapp.dto.ProposedHoursAskDto;
import bookingsystem.agh.edu.bookingapp.model.BookTime;

@SuppressLint("ValidFragment")
public class ProposalHoursDialog extends DialogFragment {

    private List<BookTime> proposedHours;
    private ProposedHoursAskDto proposedHoursAskDto;
    private LinearLayout proposedHoursLayout;

    @SuppressLint("ValidFragment")
    public ProposalHoursDialog(List<BookTime> proposedHours, ProposedHoursAskDto proposedHoursAskDto) {
        this.proposedHours = proposedHours;
        this.proposedHoursAskDto = proposedHoursAskDto;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View proposedHoursDialog = inflater.inflate(R.layout.dialog_proposed_hours, null);
        this.proposedHoursLayout = proposedHoursDialog.findViewById(R.id.proposed_hours_menu);

        this.setProposedHours();

        builder.setView(proposedHoursDialog)
                .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ReservationActivity activity = (ReservationActivity) ProposalHoursDialog.this.getActivity();
                        activity.stopSubmitButtonAnimation();
                        dialog.cancel();
                    }
                });

        return builder.create();
    }
    private void setProposedHours(){

        if(this.proposedHours.size() == 0){
            setNoAvailableDates();
        }

        for(int i = 0; i < this.proposedHours.size(); i++){
            BookTime bookTime = this.proposedHours.get(i);
            ProposedHourCardview proposedHourCardview = new ProposedHourCardview(this.getActivity().getApplicationContext(),
                    this.proposedHoursAskDto);
            proposedHourCardview.setTime(String.valueOf(bookTime.getHour()) + ":" + bookTime.getMinute());
            proposedHourCardview.setDate(this.proposedHoursAskDto.getDate());
            this.proposedHoursLayout.addView(proposedHourCardview);
        }

    }

    private void setNoAvailableDates() {
        NoAvailableDatesDialog dialog = new NoAvailableDatesDialog();
        dialog.show(getFragmentManager(), "noavailabledates");
        this.dismiss();
    }

//    public void updateProposedHours(List<BookTime> proposedHours){
//        this.proposedHoursLayout.removeAllViews();
//        for(int i = 0; i < proposedHours.size(); i++) {
//            BookTime bookTime = proposedHours.get(i);
//            ProposedHourCardview proposedHourCardview = new ProposedHourCardview(this.getActivity().getApplicationContext(),
//                    this.proposedHoursAskDto);
//            proposedHourCardview.setTime(String.valueOf(bookTime.getHour()) + ":" + bookTime.getMinute());
//            proposedHourCardview.setDate(this.proposedHoursAskDto.getDate());
//            this.proposedHoursLayout.addView(proposedHourCardview);
//        }
//    }
}
