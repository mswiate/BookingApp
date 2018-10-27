package bookingsystem.agh.edu.bookingapp.activity;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import bookingsystem.agh.edu.bookingapp.R;

public class ActivityWithMenu extends AppCompatActivity{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    protected void prepareNavigationMenu(int drawerId){
        mDrawerLayout = findViewById(drawerId);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.nav_menu_open, R.string.nav_menu_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
