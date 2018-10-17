package bookingsystem.agh.edu.bookingapp.task;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import bookingsystem.agh.edu.bookingapp.activity.ReservationActivity;
import bookingsystem.agh.edu.bookingapp.api.ApiProposalHours;
import bookingsystem.agh.edu.bookingapp.controls.ProposalHoursDialog;
import bookingsystem.agh.edu.bookingapp.dto.ProposedHoursAskDto;
import bookingsystem.agh.edu.bookingapp.model.BookTime;

public class GetProposalBookingHoursTask extends AsyncTask<Void, Void, List<BookTime>> {

    private boolean problemWithNet = false;
    private ProposedHoursAskDto proposedHoursAskDto;
    private Context mContext;


    public GetProposalBookingHoursTask(ProposedHoursAskDto proposedHoursAskDto, Context mContext) {
        this.proposedHoursAskDto = proposedHoursAskDto;
        this.mContext = mContext;
    }

    @Override
    protected List<BookTime> doInBackground(Void... values) {

        List<BookTime> list = new ApiProposalHours(mContext, proposedHoursAskDto.getRestaurantId(), proposedHoursAskDto.getDate(),
                proposedHoursAskDto.getFromTime(), 60, proposedHoursAskDto.getNubmerOfGuests())
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
        ProposalHoursDialog proposalHoursDialog = new ProposalHoursDialog(bookTimes, proposedHoursAskDto);
        proposalHoursDialog.show(parentActivity.getFragmentManager(), "proposedhours");
        parentActivity.stopSubmitButtonAnimation();
    }
}
