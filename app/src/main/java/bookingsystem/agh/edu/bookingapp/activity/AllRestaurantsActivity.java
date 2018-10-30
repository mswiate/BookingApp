package bookingsystem.agh.edu.bookingapp.activity;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import bookingsystem.agh.edu.bookingapp.R;
import bookingsystem.agh.edu.bookingapp.controls.FiltersView;

public class AllRestaurantsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_restaurants);

        Button testButton = findViewById(R.id.all_restaurants_filters);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog dialog = new BottomSheetDialog(AllRestaurantsActivity.this);
                dialog.setContentView(new FiltersView(AllRestaurantsActivity.this));
                dialog.show();

            }
        });

    }
}
