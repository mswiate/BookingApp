package bookingsystem.agh.edu.bookingapp.task;

import android.content.Context;
import android.os.AsyncTask;

import bookingsystem.agh.edu.bookingapp.api.ApiBookHour;
import bookingsystem.agh.edu.bookingapp.dto.ProposedTimesAskDto;

public class MakeReservationTask extends AsyncTask<Void, Void, Boolean> {


    private Context mContext;
    private ProposedTimesAskDto proposedHoursAskDto;
    private String chosenTime;

    public MakeReservationTask(Context context, ProposedTimesAskDto proposedHoursAskDto, String chosenTime) {
        this.proposedHoursAskDto = proposedHoursAskDto;
        this.chosenTime = chosenTime;
        this.mContext = context;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        return new ApiBookHour(mContext, proposedHoursAskDto.getRestaurantId(), proposedHoursAskDto.getDate(),
                chosenTime,60, proposedHoursAskDto.getNubmerOfGuests()).makeReservation();
    }
}
