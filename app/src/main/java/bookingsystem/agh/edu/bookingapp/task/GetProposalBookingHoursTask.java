package bookingsystem.agh.edu.bookingapp.task;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import bookingsystem.agh.edu.bookingapp.activity.ReservationActivity;
import bookingsystem.agh.edu.bookingapp.api.ApiProposalHours;
import bookingsystem.agh.edu.bookingapp.controls.ProposedTimesDialog;
import bookingsystem.agh.edu.bookingapp.dto.ProposedTimesAskDto;
import bookingsystem.agh.edu.bookingapp.model.BookTime;

public class GetProposalBookingHoursTask extends AsyncTask<Void, Void, List<BookTime>> {

    private boolean problemWithNet = false;
    private ProposedTimesAskDto proposedHoursAskDto;
    private Context mContext;


    public GetProposalBookingHoursTask(ProposedTimesAskDto proposedHoursAskDto, Context mContext) {
        this.proposedHoursAskDto = proposedHoursAskDto;
        this.mContext = mContext;
    }

    @Override
    protected List<BookTime> doInBackground(Void... values) {

        List<BookTime> list = new ApiProposalHours(mContext, proposedHoursAskDto.getRestaurantId(), proposedHoursAskDto.getDate(),
                proposedHoursAskDto.getFromTime(), proposedHoursAskDto.getLength(), proposedHoursAskDto.getNubmerOfGuests())
                .getProposalBookingHours();
        if(problemWithNet){
            problemWithNet = true;
            list = new ArrayList<>();
        }

        return list;
    }

    @Override
    protected void onPostExecute(List<BookTime> bookTimes) {
        ReservationActivity parentActivity = (ReservationActivity) mContext;
        ProposedTimesDialog proposalHoursDialog = ProposedTimesDialog.newInstance(bookTimes, proposedHoursAskDto);
        proposalHoursDialog.show(parentActivity.getFragmentManager(), "proposedhours");
        parentActivity.stopSubmitButtonAnimation();
    }
}
