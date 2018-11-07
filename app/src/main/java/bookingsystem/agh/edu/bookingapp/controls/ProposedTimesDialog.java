package bookingsystem.agh.edu.bookingapp.controls;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import bookingsystem.agh.edu.bookingapp.R;
import bookingsystem.agh.edu.bookingapp.activity.ReservationActivity;
import bookingsystem.agh.edu.bookingapp.dto.ProposedTimesAskDto;
import bookingsystem.agh.edu.bookingapp.model.BookTime;

public class ProposedTimesDialog extends DialogFragment {

    private List<BookTime> proposedHours;
    private ProposedTimesAskDto proposedHoursAskDto;
    private LinearLayout proposedHoursLayout;

    public static ProposedTimesDialog newInstance(List<BookTime> proposedHours, ProposedTimesAskDto proposedHoursAskDto){
        ProposedTimesDialog dialog = new ProposedTimesDialog();
        ProposedHoursWrapper wrapper = new ProposedHoursWrapper();
        wrapper.setProposedHours(proposedHours);
        Bundle args = new Bundle();
        args.putSerializable("proposedHours", wrapper);
        args.putSerializable("proposedHoursAskDto", proposedHoursAskDto);
        dialog.setArguments(args);
        return dialog;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this.proposedHours = ((ProposedHoursWrapper) Objects.requireNonNull(getArguments().getSerializable("proposedHours"))).getProposedHours();
        this.proposedHoursAskDto = (ProposedTimesAskDto) getArguments().getSerializable("proposedHoursAskDto");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View proposedHoursDialog = inflater.inflate(R.layout.dialog_proposed_hours, null);
        this.proposedHoursLayout = proposedHoursDialog.findViewById(R.id.proposed_hours_menu);

        this.setProposedHours();

        builder.setView(proposedHoursDialog)
                .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ReservationActivity activity = (ReservationActivity) ProposedTimesDialog.this.getActivity();
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
            ProposedTimeCardview proposedHourCardview = new ProposedTimeCardview(this.getActivity().getApplicationContext(),
                    this.proposedHoursAskDto,
                    this);
            proposedHourCardview.setTime(bookTime.getHour(), bookTime.getMinute());
            proposedHourCardview.setDate(this.proposedHoursAskDto.getDate());
            this.proposedHoursLayout.addView(proposedHourCardview);
        }

    }

    private void setNoAvailableDates() {
        NoAvailableDatesDialog dialog = new NoAvailableDatesDialog();
        dialog.show(getFragmentManager(), "noavailabledates");
        this.dismiss();
    }

    static class ProposedHoursWrapper implements Serializable{
        private List<BookTime> proposedHours;

        public List<BookTime> getProposedHours() {
            return proposedHours;
        }

        public void setProposedHours(List<BookTime> proposedHours) {
            this.proposedHours = proposedHours;
        }

    }
}
