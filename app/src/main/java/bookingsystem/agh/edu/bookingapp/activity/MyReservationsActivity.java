package bookingsystem.agh.edu.bookingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import bookingsystem.agh.edu.bookingapp.R;
import bookingsystem.agh.edu.bookingapp.task.GetMyReservationTask;

public class MyReservationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reservations);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new GetMyReservationTask(MyReservationsActivity.this).execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean authenticated = new ActivityAuthenticator(getApplicationContext()).authenticate();
        if(!authenticated)
            startActivity(new Intent(this, LoginActivity.class));
    }
}
