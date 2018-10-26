package bookingsystem.agh.edu.bookingapp.activity;

import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import bookingsystem.agh.edu.bookingapp.R;

public class OverviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CardView showMapButton = findViewById(R.id.showMapButton);
        showMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MapsActivity.class);
                view.getContext().startActivity(intent);
            }
        });

//        CardView showRestaurantListButton = findViewById(R.id.showMRestaurantListButton);
//        showRestaurantListButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(view.getContext(), RestaurantListActivity.class);
//                view.getContext().startActivity(intent);
//            }
//        });

        CardView showMyReservations = findViewById(R.id.show_my_reservations);
        showMyReservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MyReservationsActivity.class);
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        AccountManager am = AccountManager.get(this);
        if(am.getAccountsByType(getString(R.string.account_type)).length == 0) {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}