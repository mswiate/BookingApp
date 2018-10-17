package bookingsystem.agh.edu.bookingapp.service;


import android.content.Context;

import bookingsystem.agh.edu.bookingapp.dto.ProposedHoursAskDto;
import bookingsystem.agh.edu.bookingapp.task.GetProposalBookingHoursTask;

public class ReservationService {

    private ProposedHoursAskDto proposedHoursAskDto;
    private Context mContext;

    public ReservationService(ProposedHoursAskDto proposedHoursAskDto, Context mContext) {
        this.proposedHoursAskDto = proposedHoursAskDto;
        this.mContext = mContext;
    }

    public void submit() {
        new GetProposalBookingHoursTask(proposedHoursAskDto, mContext).execute();
    }

}
