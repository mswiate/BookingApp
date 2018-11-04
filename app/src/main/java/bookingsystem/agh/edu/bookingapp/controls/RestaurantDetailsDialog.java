package bookingsystem.agh.edu.bookingapp.controls;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import bookingsystem.agh.edu.bookingapp.R;
import bookingsystem.agh.edu.bookingapp.model.Restaurant;

public class RestaurantDetailsDialog extends DialogFragment {

    private Restaurant restaurant;

    public static RestaurantDetailsDialog newInstance(Restaurant restaurant){
        RestaurantDetailsDialog dialog = new RestaurantDetailsDialog();
        Bundle args = new Bundle();
        args.putSerializable("restaurant", restaurant);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this.restaurant = (Restaurant) getArguments().getSerializable("restaurant");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View restaurantDetailsDialog = inflater.inflate(R.layout.restaurant_details, null);
        this.displayRestaurantDetails(restaurantDetailsDialog);
        builder.setView(restaurantDetailsDialog)
                .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }


    private void displayRestaurantDetails(View dialog){
        ((TextView)dialog.findViewById(R.id.reservation_restaurant_name)).setText(this.restaurant.getName());
        String city = "City: " + this.restaurant.getCity();
        ((TextView)dialog.findViewById(R.id.reservation_restaurant_city)).setText(city);
        String street = "Street: " + this.restaurant.getStreet();
        ((TextView)dialog.findViewById(R.id.reservation_restaurant_street)).setText(street);

        if(this.restaurant.getWebsite().equals("null")){
            TextView websiteTextView = dialog.findViewById(R.id.reservation_restaurant_website);
            ((ViewGroup) websiteTextView.getParent()).removeView(websiteTextView);
        }else {
            String text = "Website: " + this.restaurant.getWebsite();
            ((TextView)dialog.findViewById(R.id.reservation_restaurant_website)).setText(text);
        }

        if(this.restaurant.getPhoneNumber().equals("null")){
            TextView phoneNumberTextView = dialog.findViewById(R.id.reservation_restaurant_phone);
            ((ViewGroup) phoneNumberTextView.getParent()).removeView(phoneNumberTextView);
        }else {
            String text = "Phone: " + this.restaurant.getPhoneNumber();
            ((TextView)dialog.findViewById(R.id.reservation_restaurant_phone)).setText(text);
        }

        if(this.restaurant.getTags().isEmpty()){
            TextView tagsHeader = dialog.findViewById(R.id.reservation_restaurant_tags_header);
            ((ViewGroup) tagsHeader.getParent()).removeView(tagsHeader);

            TextView tagsTextView = dialog.findViewById(R.id.reservation_restaurant_tags);
            ((ViewGroup) tagsTextView.getParent()).removeView(tagsTextView);
        }else {
            StringBuilder builder = new StringBuilder();
            List<String> tags = this.restaurant.getTags();
            for(int i=0; i<tags.size(); i++){
                builder.append(tags.get(i));
                builder.append("  ");
            }
            ((TextView)dialog.findViewById(R.id.reservation_restaurant_tags)).setText(builder.toString());
        }

    }
}
