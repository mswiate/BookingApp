package bookingsystem.agh.edu.bookingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import bookingsystem.agh.edu.bookingapp.R;
import bookingsystem.agh.edu.bookingapp.activity.tools.ActivityAuthenticator;
import bookingsystem.agh.edu.bookingapp.activity.tools.ActivityWithMenu;
import bookingsystem.agh.edu.bookingapp.adapter.AllRestaurantsAdapter;
import bookingsystem.agh.edu.bookingapp.controls.FiltersView;
import bookingsystem.agh.edu.bookingapp.model.Restaurant;
import bookingsystem.agh.edu.bookingapp.task.GetRestaurantsTask;

public class AllRestaurantsActivity extends ActivityWithMenu {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_restaurants);

        this.listView = findViewById(R.id.all_restaurants_listview);

        Button filterButton = findViewById(R.id.all_restaurants_filters);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog dialog = new BottomSheetDialog(AllRestaurantsActivity.this);
                dialog.setContentView(new FiltersView(AllRestaurantsActivity.this, dialog, createRestaurantTaskBuilder()));
                dialog.show();
            }
        });

        createRestaurantTaskBuilder().build().execute();

        listView.setOnItemClickListener(new RestaurantsListener());

        prepareNavigationMenu(R.id.restaurantsDrawerLayout, R.id.restaruants_nav_view, this);
    }

    private GetRestaurantsTask.Builder createRestaurantTaskBuilder() {
        return new GetRestaurantsTask.Builder(this,
            new GetRestaurantsTask.GetRestaurantsCallback() {
                @Override
                public void onRequestDone(List<Restaurant> restaurantList) {
                    AllRestaurantsAdapter adapter = new AllRestaurantsAdapter(AllRestaurantsActivity.this, restaurantList);
                    AllRestaurantsActivity.this.listView.setAdapter(adapter);
                }
            });
    }

    @Override
    protected void onResume() {
        super.onResume();
        new ActivityAuthenticator(getApplicationContext()).execute();
    }


    private class RestaurantsListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Restaurant selectedRestaurant = (Restaurant) parent.getItemAtPosition(position);

            Intent intent = new Intent(AllRestaurantsActivity.this, ReservationActivity.class);
            int restaurantId = selectedRestaurant.getId();
            String restaurantName = selectedRestaurant.getName();
            intent.putExtra("restaurantId", restaurantId);
            intent.putExtra("restaurantName", restaurantName);
            startActivity(intent);
        }
    }
}
