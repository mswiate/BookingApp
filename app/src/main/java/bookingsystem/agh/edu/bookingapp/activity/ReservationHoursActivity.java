package bookingsystem.agh.edu.bookingapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bookingsystem.agh.edu.bookingapp.R;
import bookingsystem.agh.edu.bookingapp.adapter.ProposedHoursAdapter;
import bookingsystem.agh.edu.bookingapp.adapter.RestaurantsListAdapter;
import bookingsystem.agh.edu.bookingapp.api.ApiProposalHours;
import bookingsystem.agh.edu.bookingapp.model.BookTime;
import bookingsystem.agh.edu.bookingapp.model.Restaurant;

public class ReservationHoursActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<BookTime> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);
        mRecyclerView = findViewById(R.id.restaurants_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ProposedHoursAdapter(data, getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);

        Intent intent = getIntent();
        Integer restaurantId = intent.getIntExtra("id", -1);
        new GetProposalHourTask(data, mAdapter, getApplicationContext()).execute(restaurantId);
    }

    public class GetProposalHourTask extends AsyncTask<Integer, Void, Void> {


        private final List<BookTime> rl;
        private List<BookTime> list;
        private final RecyclerView.Adapter adapter;
        private Context mContext;
        boolean problemWithNet = false;

        GetProposalHourTask(List<BookTime> rl, RecyclerView.Adapter adapter, Context context) {
            mContext = context;
            this.adapter = adapter;
            this.rl = rl;
        }


        @Override
        protected Void doInBackground(Integer... values) {
            try {
                int restaurantId = values[0];
                list = new ApiProposalHours(mContext, restaurantId).getProposalBookingHours();
            } catch (IOException e) {
                problemWithNet = true;
                list = new ArrayList<>();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (problemWithNet)
                Toast.makeText(getApplicationContext(), "problem with net", Toast.LENGTH_SHORT);
            rl.clear();
            rl.addAll(list);
            adapter.notifyDataSetChanged();
        }
    }
}
