package bookingsystem.agh.edu.bookingapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bookingsystem.agh.edu.bookingapp.R;
import bookingsystem.agh.edu.bookingapp.activity.ReservationHoursActivity;
import bookingsystem.agh.edu.bookingapp.api.ApiBookHour;
import bookingsystem.agh.edu.bookingapp.api.ApiRestaurantListConnection;
import bookingsystem.agh.edu.bookingapp.model.BookTime;
import bookingsystem.agh.edu.bookingapp.model.Restaurant;

public class ProposedHoursAdapter extends RecyclerView.Adapter<ProposedHoursAdapter.ProposedHoursViewHolder> {

    private List<BookTime> bookTimes;
    private Context context;
    private ProposedHoursAdapter proposedHoursAdapter;
    public ProposedHoursAdapter(List<BookTime> bookTimes, Context context) {
        this.bookTimes = bookTimes;
        this.context = context;
        proposedHoursAdapter = this;
    }

    @NonNull
    @Override
    public ProposedHoursViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.available_reservation_dates, parent,  false);
        return new ProposedHoursViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProposedHoursViewHolder holder, int position) {
        BookTime bookTime = bookTimes.get(position);
        holder.textViewResercationHour.setText("16.06.2018");
        String time = bookTime.getHour() + ":" + bookTime.getMinute();
        holder.textViewResercationDate.setText(time);
        holder.layout.setTag("16.06.2018" + time);
        holder.layout.setClickable(true);
        holder.layout.setOnClickListener(mProposalHourClickListener);
    }

    private View.OnClickListener mProposalHourClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            String time = (String) v.getTag();
            new DoReservaionTask(proposedHoursAdapter, v.getContext()).execute(time);
        }
    };

    @Override
    public int getItemCount() {
        return bookTimes.size();
    }

    public class ProposedHoursViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewResercationDate;
        public TextView textViewResercationHour;
        public RelativeLayout layout;

        public ProposedHoursViewHolder(View view) {
            super(view);
            textViewResercationDate = view.findViewById(R.id.reservation_date);
            textViewResercationHour = view.findViewById(R.id.reservation_hour);
            layout = view.findViewById(R.id.reservation_wrapper);
        }
    }

    public class DoReservaionTask extends AsyncTask<String, Void, Boolean> {


        private final RecyclerView.Adapter adapter;
        private Context mContext;
        boolean problemWithNet = false;

        DoReservaionTask(RecyclerView.Adapter adapter, Context context) {
            mContext = context;
            this.adapter = adapter;
        }


        @Override
        protected Boolean doInBackground(String... params) {
            String date = params[0];
            final boolean result = new ApiBookHour(context, date, 2).makeReservation();
//            Looper.prepare();
//            if(result){
//                Toast.makeText(context, "Made reservation successfully", Toast.LENGTH_SHORT).show();
//            }
//            else{
//                Toast.makeText(context, "Cannot make reservation", Toast.LENGTH_SHORT).show();
//            }

            return result;
        }
        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if(result){
                Toast.makeText(context, "Made reservation successfully", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(context, "Cannot make reservation", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
