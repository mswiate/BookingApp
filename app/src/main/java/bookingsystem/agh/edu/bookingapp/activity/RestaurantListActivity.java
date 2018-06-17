package bookingsystem.agh.edu.bookingapp.activity;

import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bookingsystem.agh.edu.bookingapp.R;
import bookingsystem.agh.edu.bookingapp.adapter.RestaurantsListAdapter;
import bookingsystem.agh.edu.bookingapp.api.ApiRestaurantListConnection;
import bookingsystem.agh.edu.bookingapp.model.Restaurant;

public class RestaurantListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Restaurant> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.restaurants_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RestaurantsListAdapter(data);
        mRecyclerView.setAdapter(mAdapter);


    }

    @Override
    protected void onResume() {
        super.onResume();
        AccountManager am = AccountManager.get(this);
        if(am.getAccountsByType(getString(R.string.account_type)).length == 0) {
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            new GetDataTask(data, mAdapter, getApplicationContext()).execute();
        }
    }

    public class GetDataTask extends AsyncTask<Void, Void, Void> {


        private final List<Restaurant> rl;
        private List<Restaurant> list;
        private final RecyclerView.Adapter adapter;
        private Context mContext;
        boolean problemWithNet = false;

        GetDataTask(List<Restaurant> rl, RecyclerView.Adapter adapter, Context context) {
            mContext = context;
            this.adapter = adapter;
            this.rl = rl;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            try {
                list = new ApiRestaurantListConnection(mContext).getRestaurants();
            } catch (IOException e) {
                problemWithNet = true;
                list = new ArrayList<>();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(problemWithNet)
                Toast.makeText(getApplicationContext(), "problem with net", Toast.LENGTH_SHORT);
            rl.clear();
            rl.addAll(list);
            adapter.notifyDataSetChanged();
        }
    }

}