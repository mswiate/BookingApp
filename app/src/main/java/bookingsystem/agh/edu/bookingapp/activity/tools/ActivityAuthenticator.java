package bookingsystem.agh.edu.bookingapp.activity.tools;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import bookingsystem.agh.edu.bookingapp.R;
import bookingsystem.agh.edu.bookingapp.activity.LoginActivity;
import bookingsystem.agh.edu.bookingapp.api.ApiValidate;

public class ActivityAuthenticator extends AsyncTask<Void, Void, Boolean> {

    private final Context mContext;

    public ActivityAuthenticator(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        AccountManager am = AccountManager.get(mContext);
        if(am.getAccountsByType(mContext.getString(R.string.account_type)).length == 0) {
            return false;
        }
        try {
            boolean isValid = new ApiValidate(mContext).validate();
            if(isValid)
                return true;

            Account account = am.getAccountsByType(mContext.getString(R.string.account_type))[0];
            am.removeAccount(account, null, null);

        } catch (IOException e) {
            Log.e("Authentication", "Problem with Net", e);
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean authenticated) {
        Intent intent = new Intent(mContext, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(!authenticated)
            mContext.startActivity(intent);
    }
}
