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
import bookingsystem.agh.edu.bookingapp.dto.ProposedHoursAskDto;
import bookingsystem.agh.edu.bookingapp.task.MakeReservationTask;

public class ProposedHourCardview extends CardView {

    private TextView timeTextView;
    private TextView dateTextView;
    private Context mContext;
    private ProposedHoursAskDto proposedHoursAskDto;

    public ProposedHourCardview(@NonNull Context context, ProposedHoursAskDto proposedHoursAskDto) {
        this(context, null, proposedHoursAskDto);
    }

    public ProposedHourCardview(@NonNull Context context, @Nullable AttributeSet attrs, ProposedHoursAskDto proposedHoursAskDto) {
        super(context, attrs);
        this.mContext = context;
        this.proposedHoursAskDto = proposedHoursAskDto;
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
                    Boolean isRequestSuccessful = new MakeReservationTask(ProposedHourCardview.this.mContext, proposedHoursAskDto,
                            ProposedHourCardview.this.timeTextView.getText().toString()).execute().get();

                    if(!isRequestSuccessful){
                        Toast.makeText(mContext, "Cannot book this date", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(mContext, "Date has been successfully booked", Toast.LENGTH_SHORT).show();
                    }

                } catch (InterruptedException | ExecutionException e) {
                    Toast.makeText(mContext, "Oops, some internal error occurred:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setTime(String time) {
        this.timeTextView.setText(time);
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
