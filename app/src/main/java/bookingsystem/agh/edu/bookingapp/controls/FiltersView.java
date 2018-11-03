package bookingsystem.agh.edu.bookingapp.controls;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.xw.repo.BubbleSeekBar;

import java.util.ArrayList;
import java.util.List;

import bookingsystem.agh.edu.bookingapp.R;
import bookingsystem.agh.edu.bookingapp.task.GetRestaurantTagsTask;
import bookingsystem.agh.edu.bookingapp.task.GetRestaurantsTask;

import static android.content.Context.LOCATION_SERVICE;

public class FiltersView extends LinearLayout {

    private List<String> userTags = new ArrayList<>();
    private List<String> userPrices = new ArrayList<>();
    private TextView tagsTextView;
    private Context mContext;
    private TextView pricesTextView;
    private EditText restaurantName;
    private CheckBox localization;
    private BubbleSeekBar radius;
    private Button applyFilters;
    private LatLng mLocation;

    public FiltersView(Context context, final BottomSheetDialog dialog, GetRestaurantsTask.Builder restaurantTaskBuilder) {
        super(context);
        mContext = context;
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View thisView = inflater.inflate(R.layout.filters_view, this);

        new GetRestaurantTagsTask(mContext, new GetRestaurantTagsTask.GetRestaurantTagsCallback() {
            @Override
            public void onRequestDone(Pair<List<String>, List<String>> restaurantInfo) {
                List<String> prices = restaurantInfo.first;
                List<String> tags = restaurantInfo.second;

                setPriceButtonOnClickListener(prices.toArray(new String[prices.size()]));
                setTagButtonOnClickListener(tags.toArray(new String[tags.size()]));
            }
        }).execute();

        restaurantName = findViewById(R.id.all_restaurants_filter_name_edittextview);
        restaurantName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        localization = findViewById(R.id.localization);
        radius = findViewById(R.id.radius);
        applyFilters = findViewById(R.id.btnGetMoreResults);
        applyFilters.setOnClickListener(new ApplyFiltersListener(dialog, restaurantTaskBuilder));

        accessCurrentLocation();

    }

    private void accessCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        LocationManager locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
        Location location;

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            location = locationManager.getLastKnownLocation( LocationManager.GPS_PROVIDER);
            if(location != null) {
                mLocation = new LatLng(location.getLatitude(), location.getLongitude());
                return;
            }
        }
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            location = locationManager.getLastKnownLocation( LocationManager.NETWORK_PROVIDER);
            if(location != null) {
                mLocation = new LatLng(location.getLatitude(), location.getLongitude());
                return;
            }
        }
        Toast.makeText(mContext, "no location available", Toast.LENGTH_LONG);
    }

    private void setTagButtonOnClickListener(String[] restaurantTags){

        final String[] tags = restaurantTags;
        final boolean[] checkedItems = new boolean[tags.length];

        Button tagsButton = findViewById(R.id.all_restaurants_filter_tags_button);
        this.tagsTextView = findViewById(R.id.all_restaurants_filter_tags_textview);
        tagsButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
                mBuilder.setTitle("Available tags");
                mBuilder.setMultiChoiceItems(tags, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if(isChecked && !userTags.contains(tags[which])){
                            userTags.add(tags[which]);
                        } else {
                            userTags.remove(userTags.indexOf(tags[which]));
                        }
                    }
                });
                mBuilder.setCancelable(true);
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StringBuilder item = new StringBuilder();
                        for(int i =0; i < userTags.size(); i++){
                            item.append(userTags.get(i));
                            if(i != userTags.size() -1){
                                item.append(", ");
                            }

                        }
                        FiltersView.this.tagsTextView.setText(item);
                    }
                });
                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                mBuilder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for(int i = 0; i < checkedItems.length; i++){
                            checkedItems[i] = false;
                            userTags.clear();
                            FiltersView.this.tagsTextView.setText("");
                        }
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
    }

    private void setPriceButtonOnClickListener(String[] restaurantPrices){


        final String[] prices = restaurantPrices;
        final boolean[] checkedItems = new boolean[prices.length];

        Button pricesButton = findViewById(R.id.all_restaurants_filter_price_button);
        this.pricesTextView = findViewById(R.id.all_restaurants_filter_price_textview);
        pricesButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
                mBuilder.setTitle("Available tags");
                mBuilder.setMultiChoiceItems(prices, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if(isChecked && !userPrices.contains(prices[which])){
                            userPrices.add(prices[which]);
                        } else {
                            userPrices.remove(userPrices.indexOf(prices[which]));
                        }
                    }
                });
                mBuilder.setCancelable(true);
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StringBuilder item = new StringBuilder();
                        for(int i =0; i < userPrices.size(); i++){
                            item.append(userPrices.get(i));
                            if(i != userPrices.size() -1){
                                item.append(", ");
                            }

                        }
                        FiltersView.this.pricesTextView.setText(item);
                    }
                });
                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                mBuilder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for(int i = 0; i < checkedItems.length; i++){
                            checkedItems[i] = false;
                            userPrices.clear();
                            FiltersView.this.pricesTextView.setText("");
                        }
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
    }

    private class ApplyFiltersListener implements OnClickListener {
        private final BottomSheetDialog dialog;
        private GetRestaurantsTask.Builder restaurantTaskBuilder;

        public ApplyFiltersListener(BottomSheetDialog dialog, GetRestaurantsTask.Builder restaurantTaskBuilder) {
            this.dialog = dialog;
            this.restaurantTaskBuilder = restaurantTaskBuilder;
        }

        @Override
        public void onClick(View v) {
            restaurantTaskBuilder.tags(userTags);
            restaurantTaskBuilder.prices(userPrices);
            String name = restaurantName.getText().toString();
            if(name != null && !name.isEmpty()) {
                restaurantTaskBuilder.name(name);
            }
            if(localization.isChecked() && mLocation != null) {
                restaurantTaskBuilder.location(mLocation, radius.getProgressFloat());
            }
            if(mLocation == null)
                Toast.makeText(mContext, "no location available", Toast.LENGTH_LONG);
            restaurantTaskBuilder.build().execute();
            dialog.cancel();
        }
    }
}
