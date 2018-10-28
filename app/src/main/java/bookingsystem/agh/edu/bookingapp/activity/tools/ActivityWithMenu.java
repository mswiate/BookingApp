package bookingsystem.agh.edu.bookingapp.activity.tools;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import bookingsystem.agh.edu.bookingapp.R;
import bookingsystem.agh.edu.bookingapp.activity.LoginActivity;

public class ActivityWithMenu extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;

    protected void prepareNavigationMenu(int drawerId, int navId, Context context){
        mDrawerLayout = findViewById(drawerId);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.nav_menu_open, R.string.nav_menu_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        navigationView = findViewById(navId);
        navigationView.setNavigationItemSelectedListener(new LogoutListener(context));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class LogoutListener implements NavigationView.OnNavigationItemSelectedListener {

        Context mContext;

        public LogoutListener(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            if(item.getItemId() != R.id.nav_logout)
                return true;

            AccountManager am = AccountManager.get(mContext);
            Account account = am.getAccountsByType(mContext.getString(R.string.account_type))[0];
            am.removeAccount(account, null, null);

            startActivity(new Intent(ActivityWithMenu.this, LoginActivity.class));
            return true;
        }
    }
}
