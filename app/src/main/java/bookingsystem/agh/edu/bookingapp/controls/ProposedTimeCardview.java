package bookingsystem.agh.edu.bookingapp.controls;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import bookingsystem.agh.edu.bookingapp.R;
import bookingsystem.agh.edu.bookingapp.dto.ProposedTimesAskDto;
import bookingsystem.agh.edu.bookingapp.task.MakeReservationTask;

public class ProposedTimeCardview extends CardView {

    private TextView timeTextView;
    private TextView dateTextView;
    private Context mContext;
    private ProposedTimesAskDto proposedHoursAskDto;
    private ProposedTimesDialog parentDialog;

    public ProposedTimeCardview(@NonNull Context context, ProposedTimesAskDto proposedHoursAskDto, ProposedTimesDialog parentDialog) {
        this(context, null, proposedHoursAskDto, parentDialog);
    }

    public ProposedTimeCardview(@NonNull Context context, @Nullable AttributeSet attrs, ProposedTimesAskDto proposedHoursAskDto, ProposedTimesDialog parentDialog) {
        super(context, attrs);
        this.mContext = context;
        this.proposedHoursAskDto = proposedHoursAskDto;
        this.parentDialog = parentDialog;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.proposed_hour_cardview, this);
        this.timeTextView = this.findViewById(R.id.proposed_hour_card_time);
        this.dateTextView = this.findViewById(R.id.proposed_hour_card_date);
        setMakeReservationButtonOnClickListener();
    }

    private void setMakeReservationButtonOnClickListener() {
        Button button = this.findViewById(R.id.make_booking_button);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Boolean isRequestSuccessful = new MakeReservationTask(ProposedTimeCardview.this.mContext, proposedHoursAskDto,
                            ProposedTimeCardview.this.timeTextView.getText().toString()).execute().get();

                    if(!isRequestSuccessful){
                        Toast.makeText(mContext, "Cannot book this date", Toast.LENGTH_SHORT).show();
                    }else {
                        SuccessfullyBookedDialog dialog = new SuccessfullyBookedDialog();
                        dialog.show(parentDialog.getActivity().getFragmentManager(), "succesful_reservation");

                    }

                } catch (InterruptedException | ExecutionException e) {
                    Toast.makeText(mContext, "Oops, some internal error occurred:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void setTime(int hour, int minute) {
        this.timeTextView.setText(String.format("%02d:%02d", hour, minute));
    }

    public void setDate(String date) {
        this.dateTextView.setText(date);
    }

    public String getTime() {
        return this.timeTextView.getText().toString();
    }

    public String getDate() {
        return this.dateTextView.getText().toString();
    }
}
