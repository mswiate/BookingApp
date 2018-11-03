package bookingsystem.agh.edu.bookingapp.activity;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import bookingsystem.agh.edu.bookingapp.R;
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
                dialog.setContentView(new FiltersView(AllRestaurantsActivity.this));
                dialog.show();

            }
        });

        new GetRestaurantsTask.Builder()
                .callback(new GetRestaurantsTask.GetRestaurantsCallback() {
                    @Override
                    public void onRequestDone(List<Restaurant> restaurantList) {
                        AllRestaurantsAdapter adapter = new AllRestaurantsAdapter(AllRestaurantsActivity.this, restaurantList);
                        AllRestaurantsActivity.this.listView.setAdapter(adapter);
                    }
                })
                .context(this)
                .build()
                .execute();

        prepareNavigationMenu(R.id.restaurantsDrawerLayout, R.id.restaruants_nav_view, this);
    }



}
