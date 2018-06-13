package bookingsystem.agh.edu.bookingapp.api;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import bookingsystem.agh.edu.bookingapp.R;
import bookingsystem.agh.edu.bookingapp.activity.LoginActivity;

public class ApiConnection {

    private Context mContext;

    public ApiConnection(Context mContext) {
        this.mContext = mContext;
    }

    public Pair<Integer, JSONObject> get(String endpoint, JSONObject body, Pair<String, String>... headers) throws IOException {
        return doRequest("GET", endpoint, body, packHeaders(headers));
    }


    public Pair<Integer, JSONObject> authGet(String endpoint, JSONObject body, Pair<String, String>... headers) throws IOException {
        return doAuthRequest("GET", endpoint, body, packHeaders(headers));
    }

    public Pair<Integer, JSONObject> post(String endpoint, JSONObject body, Pair<String, String>... headers) throws IOException {
        return doRequest("POST", endpoint, body, packHeaders(headers));
    }

    public Pair<Integer, JSONObject> authPost(String endpoint, JSONObject body, Pair<String, String>... headers) throws IOException {
        return doAuthRequest("POST", endpoint, body, packHeaders(headers));
    }

    private Pair<Integer, JSONObject> doAuthRequest(String method, String endpoint, JSONObject body, List<Pair<String, String>> headers) throws IOException {
        AccountManager am = AccountManager.get(mContext);
        Account[] accounts = am.getAccountsByType(mContext.getString(R.string.account_type));
        Account account = accounts[0];
        String token = null;
        try {
            token = am.blockingGetAuthToken(account, "full_access", true);
        } catch (OperationCanceledException | AuthenticatorException e) {
            Log.e("apiConnection/authget", "Problem with accountManager", e);
            return null;
        }
        headers.add(new Pair<>("Authorization", "Bearer " + token));
        return doRequest(method, endpoint, body, headers);
    }

    private Pair<Integer, JSONObject> doRequest(String method, String endpoint, JSONObject body, List<Pair<String, String>> headers) throws IOException {
        HttpsURLConnection conn = openJSONConnection(endpoint);
        conn.setRequestMethod(method);
        if(body != null) {
            conn.setDoOutput(true);
            conn.getOutputStream().write(body.toString().getBytes());
        }
        JSONObject res = getResponseBody(conn.getResponseCode() >= 400? conn.getErrorStream(): conn.getInputStream());
        return new Pair<>(conn.getResponseCode(), res);
    }

    private JSONObject getResponseBody(InputStream stream) throws IOException {
        String json = "";
        try(BufferedReader r = new BufferedReader(new InputStreamReader(stream, "UTF-8"))) {
            for(String line; (line = r.readLine()) != null;)
                json += line;
        }
        try {
            return new JSONObject(json);
        } catch (JSONException e) {
            return null;
        }
    }

    private HttpsURLConnection openJSONConnection(String url, Pair<String, String>... headers) throws IOException {
        HttpsURLConnection conn = (HttpsURLConnection) new URL(url).openConnection();
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("charset", "utf-8");
        for(Pair<String, String> header: headers) {
            conn.setRequestProperty(header.first, header.second);
        }
        return conn;
    }


    private List<Pair<String,String>> packHeaders(Pair<String, String>[] headers) {
        List<Pair<String,String>> res = new ArrayList<>();
        for(Pair<String, String> header: headers)
            res.add(header);
        return res;
    }
}
