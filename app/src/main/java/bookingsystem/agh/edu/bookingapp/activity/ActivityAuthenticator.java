package bookingsystem.agh.edu.bookingapp.activity;

import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import bookingsystem.agh.edu.bookingapp.R;
import bookingsystem.agh.edu.bookingapp.api.ApiValidate;

public class ActivityAuthenticator {

    private final Context mContext;

    public ActivityAuthenticator(Context mContext) {
        this.mContext = mContext;
    }

    public boolean authenticate() {
        AccountManager am = AccountManager.get(mContext);
        if(am.getAccountsByType(mContext.getString(R.string.account_type)).length == 0) {
            return false;
        }
        try {
            return new ValidateTask(mContext).execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    public class ValidateTask extends AsyncTask<Void, Void, Boolean> {

        private Context mContext;

        public ValidateTask(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                return new ApiValidate(mContext).validate();
            } catch (IOException e) {
                Log.e("Authentication", "Problem with Net", e);
                return false;
            }
        }
    }
}
