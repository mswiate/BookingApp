package bookingsystem.agh.edu.bookingapp.service;


import android.content.Context;

import bookingsystem.agh.edu.bookingapp.dto.ProposedTimesAskDto;
import bookingsystem.agh.edu.bookingapp.task.GetProposalBookingHoursTask;

public class ReservationService {

    private ProposedTimesAskDto proposedHoursAskDto;
    private Context mContext;

    public ReservationService(ProposedTimesAskDto proposedHoursAskDto, Context mContext) {
        this.proposedHoursAskDto = proposedHoursAskDto;
        this.mContext = mContext;
    }

    public void submit() {
        new GetProposalBookingHoursTask(proposedHoursAskDto, mContext).execute();
    }

}
