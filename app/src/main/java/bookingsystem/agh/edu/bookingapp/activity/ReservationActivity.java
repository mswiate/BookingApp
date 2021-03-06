package bookingsystem.agh.edu.bookingapp.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.shawnlin.numberpicker.NumberPicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import bookingsystem.agh.edu.bookingapp.R;
import bookingsystem.agh.edu.bookingapp.activity.tools.ActivityAuthenticator;
import bookingsystem.agh.edu.bookingapp.activity.tools.ActivityWithMenu;
import bookingsystem.agh.edu.bookingapp.controls.RestaurantDetailsDialog;
import bookingsystem.agh.edu.bookingapp.dto.ProposedTimesAskDto;
import bookingsystem.agh.edu.bookingapp.exception.BadReservationRequestDataException;
import bookingsystem.agh.edu.bookingapp.model.Restaurant;
import bookingsystem.agh.edu.bookingapp.service.ReservationService;
import bookingsystem.agh.edu.bookingapp.task.GetRestaurantDetailsTask;
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class ReservationActivity extends ActivityWithMenu {

    private int restaurantId;
    private CircularProgressButton submitButton;
    private Button restaurantDetailsButton;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.restaurantId = this.getIntent().getIntExtra("restaurantId", -1);
        String restaurantName = this.getIntent().getStringExtra("restaurantName");
        setContentView(R.layout.activity_reservation);
        this.setRestaurantName(restaurantName);
        this.submitButton = findViewById(R.id.info_window_submit_button);
        this.restaurantDetailsButton = findViewById(R.id.restaurant_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setDatePicker();
        setTimePicker((Button) findViewById(R.id.info_window_set_to_time_button),
                (TextView) findViewById(R.id.info_window_set_to_time_textview));

        setTimePicker((Button) findViewById(R.id.info_window_set_from_time_button),
                (TextView) findViewById(R.id.info_window_set_from_time_textview));


        this.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    submitButton.startAnimation();
                    final ProposedTimesAskDto proposedHoursAskDto = fetchDataFromActivity();
                    validateFormData(proposedHoursAskDto);
                    new ReservationService(proposedHoursAskDto,ReservationActivity.this).submit();
                }
                catch (BadReservationRequestDataException e){
                    Toast.makeText(ReservationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    submitButton.revertAnimation();
                }
                catch (Exception e) {
                    Toast.makeText(ReservationActivity.this, "Oops, some internal error occurred:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    submitButton.revertAnimation();
                }
            }
        });

        this.restaurantDetailsButton.setOnClickListener(new View.OnClickListener() {

            GetRestaurantDetailsCallback callback = new GetRestaurantDetailsCallback() {
                @Override
                public void onRequestDone(Restaurant restaurant) {
                    RestaurantDetailsDialog dialog = RestaurantDetailsDialog.newInstance(restaurant);
                    dialog.show(ReservationActivity.this.getFragmentManager(), "restaurantdetails");
                }
            };

            @Override
            public void onClick(View v) {
                 new GetRestaurantDetailsTask(ReservationActivity.this, callback).execute(restaurantId);
            }
        });

        prepareNavigationMenu(R.id.reservationDrawerLayout, R.id.reservation_nav_view, getApplicationContext());
    }

    private void setRestaurantName(String restaurantName) {
        TextView restaurantNameTextView = findViewById(R.id.info_window_restaurant_name);
        restaurantNameTextView.setText(restaurantName);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new ActivityAuthenticator(getApplicationContext()).execute();
    }


    private void validateFormData(ProposedTimesAskDto data) throws ParseException, BadReservationRequestDataException {
        validateDate(data.getDate());
        validateTime(data.getFromTime(), data.getToTime());
        validateReservationLength(data.getLength());
    }

    private void validateReservationLength(int length) throws BadReservationRequestDataException {
        if(length < 30){
            throw new BadReservationRequestDataException("Reservation period is too short. (30min at least)");
        }
    }

    private void validateTime(String fromTime, String toTime) throws ParseException, BadReservationRequestDataException {
        String timeFormat = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat, Locale.getDefault());
        Date parsedFromTime = sdf.parse(fromTime);
        Date parsedToTime = sdf.parse(toTime);
        if(parsedFromTime.getTime() >= parsedToTime.getTime()){
            throw new BadReservationRequestDataException("From time cannot be later than to time");
        }
    }

    private void validateDate(String date) throws ParseException, BadReservationRequestDataException {
        String dateFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.getDefault());
        Date parsedDate = sdf.parse(date);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        if(parsedDate.getTime() < cal.getTime().getTime()){
            throw new BadReservationRequestDataException("Invalid date value");
        }
    }

    private ProposedTimesAskDto fetchDataFromActivity() throws ParseException {
        String date = ((TextView)findViewById(R.id.info_window_set_date_textview)).getText().toString();
        String fromTime = ((TextView)findViewById(R.id.info_window_set_from_time_textview)).getText().toString();
        String toTime = ((TextView)findViewById(R.id.info_window_set_to_time_textview)).getText().toString();
        Integer numberOfGuests = ((NumberPicker)findViewById(R.id.guest_number_picker)).getValue();

        return new ProposedTimesAskDto(restaurantId, numberOfGuests, date, fromTime, toTime);
    }

    private void setTimePicker(Button button, final TextView textView) {
        final Calendar calendar = Calendar.getInstance();

        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                updateTimeLabel(textView, calendar);

            }
        };

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(ReservationActivity.this,time, calendar
                        .get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                        true).show();
            }
        });

    }



    private void setDatePicker(){
        final Calendar calendar = Calendar.getInstance();

        Button setDateButton = findViewById(R.id.info_window_set_date_button);
        final TextView setDateTextView= findViewById(R.id.info_window_set_date_textview);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateDateLabel(setDateTextView, calendar);
            }
        };
        setDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ReservationActivity.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }
    private void updateDateLabel(TextView textView, Calendar calendar){
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        textView.setText(sdf.format(calendar.getTime()));
    }

    private void updateTimeLabel(TextView textView, Calendar calendar) {
        String myFormat = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        textView.setText(sdf.format(calendar.getTime()));

    }

    public void stopSubmitButtonAnimation(){
        this.submitButton.revertAnimation();
    }

    public interface GetRestaurantDetailsCallback {
        void onRequestDone(Restaurant restaurant);
    }

}
