package bookingsystem.agh.edu.bookingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import bookingsystem.agh.edu.bookingapp.R;
import bookingsystem.agh.edu.bookingapp.activity.LoginActivity;
import bookingsystem.agh.edu.bookingapp.activity.ReservationHoursActivity;
import bookingsystem.agh.edu.bookingapp.activity.RestaurantListActivity;
import bookingsystem.agh.edu.bookingapp.model.Restaurant;

public class RestaurantsListAdapter extends RecyclerView.Adapter<RestaurantsListAdapter.MyViewHolder> {

    private final List<Restaurant> restaurantList;

    public RestaurantsListAdapter(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout layout;
        public TextView mName;
        public TextView mLoc;
        public TextView mTags;

        public MyViewHolder(View view) {
            super(view);
            mName = (TextView) view.findViewById(R.id.restaurant_name);
            mLoc = (TextView) view.findViewById(R.id.localization);
            mTags = (TextView) view.findViewById(R.id.tags);
            layout = view.findViewById(R.id.restaurant_wrapper);
        }

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Restaurant r = restaurantList.get(position);
        holder.mName.setText(r.getName());

        holder.mLoc.setText(r.getCity() + ", " + r.getStreet());
        List<String> tagsList = r.getTags();
        String tags = "";
        for(int i = 0; (i < 3) && (i < r.getTags().size()); ++i) {
            tags += (i == 0? tagsList.get(i): ", " + tagsList.get(i));
        }
        holder.mTags.setText(tags);
        holder.layout.setClickable(true);
        holder.layout.setTag(r.getId());

        holder.layout.setOnClickListener(mMyButtonClickListener);
    }

    private View.OnClickListener mMyButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int pos = (int) v.getTag();
            Intent intent = new Intent(v.getContext(), ReservationHoursActivity.class);
            intent.putExtra("id", pos);

            v.getContext().startActivity(intent);
        }
    };

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_row_item, parent, false);

        return new MyViewHolder(v);
    }


}
