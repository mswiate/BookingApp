package bookingsystem.agh.edu.bookingapp.controls;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import bookingsystem.agh.edu.bookingapp.R;

public class FiltersView extends LinearLayout{

    private ArrayList<Integer> userTags = new ArrayList<>();
    private TextView tagsTextView;
    private Context mContext;


    public FiltersView(final Context context) {
        super(context);
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.filters_view, this);
        setTagButtonOnClickListener();
        setPriceButtonOnClickListener();


    }

    private void setTagButtonOnClickListener(){


        final String[] tags = new String[]{"pizza", "kebab"};
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
                        if(isChecked){
                            if(!userTags.contains(which)){
                                userTags.add(which);
                            } else {
                                userTags.remove(which);
                            }
                        }
                    }
                });
                mBuilder.setCancelable(true);
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StringBuilder item = new StringBuilder();
                        for(int i =0; i < userTags.size(); i++){
                            item.append(tags[userTags.get(i)]);
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

    private void setPriceButtonOnClickListener(){


        final String[] tags = new String[]{"pizza", "kebab"};
        final boolean[] checkedItems = new boolean[tags.length];

        Button tagsButton = findViewById(R.id.all_restaurants_filter_price_button);
        this.tagsTextView = findViewById(R.id.all_restaurants_filter_price_textview);
        tagsButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
                mBuilder.setTitle("Available tags");
                mBuilder.setMultiChoiceItems(tags, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if(isChecked){
                            if(!userTags.contains(which)){
                                userTags.add(which);
                            } else {
                                userTags.remove(which);
                            }
                        }
                    }
                });
                mBuilder.setCancelable(true);
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StringBuilder item = new StringBuilder();
                        for(int i =0; i < userTags.size(); i++){
                            item.append(tags[userTags.get(i)]);
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

}
