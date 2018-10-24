package bookingsystem.agh.edu.bookingapp.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Restaurant implements Serializable{
    private int id;
    private String name;
    private String city;
    private String street;
    private String website;
    private String phoneNumber;
    private List<String> tags;
    private double latitude;
    private double longitude;

    public Restaurant() {
    }

    public static Restaurant of(JSONObject json) throws JSONException {
        JSONArray jsonTags = json.getJSONArray("tags");
        List<String> tags = new ArrayList<>();
        for(int i = 0; i < jsonTags.length(); ++i)
            tags.add(jsonTags.getString(i));
        return new Restaurant(json.getInt("id"),
                json.getString("name"),
                json.getString("city"),
                json.getString("street"),
                json.getString("website"),
                json.getString("phoneNumber"),
                tags,
                json.getDouble("latitude"),
                json.getDouble("longitude"));
    }

    public Restaurant(int id, String name, String city, String street, String website, String phoneNumber, List<String> tags, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.street = street;
        this.website = website;
        this.phoneNumber = phoneNumber;
        this.tags = tags;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWebsite() {
        return website;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
