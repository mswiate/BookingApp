package bookingsystem.agh.edu.bookingapp.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BookTime implements Serializable{

    private int hour;
    private int minute;


    public BookTime(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public static List<BookTime> of(JSONObject json) throws JSONException {
        JSONArray jsonArray = json.getJSONArray("proposalHours");
        if(jsonArray == null){
            return null;
        }
        List<BookTime> bookTimes = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); ++i){
            String time = jsonArray.getString(i);
            String[] hourAndMinute = time.split(":");
            bookTimes.add(new BookTime(Integer.parseInt(hourAndMinute[0]), Integer.parseInt(hourAndMinute[1])));
        }
        return bookTimes;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
