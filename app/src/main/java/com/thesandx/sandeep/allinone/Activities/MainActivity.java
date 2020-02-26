package com.thesandx.sandeep.allinone.Activities;

import android.app.ProgressDialog;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;

import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;


import com.thesandx.sandeep.allinone.ConnectionLiveData;
import com.thesandx.sandeep.allinone.Models.ConnectionModel;
import com.thesandx.sandeep.allinone.R;
import com.thesandx.sandeep.allinone.fragments.Home;
import com.thesandx.sandeep.allinone.fragments.WebviewUrl;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {



    boolean doubleBackToExitPressedOnce = false;


    AlertDialog.Builder  alertBuilder;

    ProgressDialog progressDialog;


    Toolbar toolbar;
    FragmentTransaction fragmentTransaction;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;




    public static final int MobileData = 2;
    public static final int WifiData = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerlayout);
        setSupportActionBar(toolbar);



        alertBuilder = new AlertDialog.Builder(this);

        progressDialog = new ProgressDialog(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container, new Home());
        fragmentTransaction.commit();
        getSupportActionBar().setTitle("Home");
        navigationView = findViewById(R.id.navigation_view);



       // Toast.makeText(MainActivity.this, "Please set the time for this session", Toast.LENGTH_SHORT).show();

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        /**    SharedPreferences prefs = this.getSharedPreferences("prefs", MODE_PRIVATE);
         SharedPreferences.Editor editor = prefs.edit();

         editor.putInt("currentDate",date);
         editor.putInt("totalTime",totalTime);

         editor.apply();

         Toast.makeText(MainActivity.this,"Date is "+date+" and time is "+totalTime/60000,Toast.LENGTH_LONG).show();
         **/


        ConnectionLiveData connectionLiveData = new ConnectionLiveData(getApplicationContext());

        connectionLiveData.observe(this, new Observer<ConnectionModel>() {
            @Override
            public void onChanged(ConnectionModel connection) {
                if (connection.getIsConnected()) {

                    switch (connection.getType()) {

                        case WifiData:
                            Toast.makeText(getApplicationContext(), "wifi on", Toast.LENGTH_SHORT).show();
                            break;
                        case MobileData:
                            Toast.makeText(getApplicationContext(), "mobile data on", Toast.LENGTH_SHORT).show();
                            break;

                    }

                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {


                    case R.id.home_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new Home());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Home");
                        menuItem.setChecked(true);
                        navigationView.setCheckedItem(menuItem.getItemId());
                        drawerLayout.closeDrawers();

                        break;

                    case R.id.fb_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new WebviewUrl(MainActivity.this, "https://www.facebook.com/"));
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Facebook");
                        menuItem.setChecked(true);
                        navigationView.setCheckedItem(menuItem.getItemId());
                        drawerLayout.closeDrawers();

                        break;


                    case R.id.tweeter_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new WebviewUrl(MainActivity.this, "https://www.twitter.com/"));
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Twitter");
                        menuItem.setChecked(true);
                        navigationView.setCheckedItem(menuItem.getItemId());
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.insta_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new WebviewUrl(MainActivity.this, "https://www.instagram.com/"));
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Instagram");
                        navigationView.setCheckedItem(menuItem.getItemId());
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.linkedin_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new WebviewUrl(MainActivity.this, "https://www.linkedin.com/"));
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("LinkedIn");
                        navigationView.setCheckedItem(menuItem.getItemId());
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;



                }
                return false;
            }
        });


    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();


    }



    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
       // pauseTimer();
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        //i.e after 2 second the code inside this will be executed.
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


}
