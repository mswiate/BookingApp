package bookingsystem.agh.edu.bookingapp.adapter.googlemaps;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import bookingsystem.agh.edu.bookingapp.R;
import bookingsystem.agh.edu.bookingapp.dto.RestaurantWindowInfoData;

public class RestaurantInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter{

    private Context context;

    public RestaurantInfoWindowGoogleMap(Context context) {
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)context).getLayoutInflater()
                .inflate(R.layout.marker_restaurant_info, null);
        TextView restaurantNameTextView = view.findViewById(R.id.info_window_restaurant_name);
        RestaurantWindowInfoData restaurantWindowInfoData = (RestaurantWindowInfoData) marker.getTag();
        restaurantNameTextView.setText(Integer.toString(restaurantWindowInfoData.getRestaurantId()));
        return view;
    }
}
