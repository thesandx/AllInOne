package com.thesandx.sandeep.allinone.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import com.google.android.material.navigation.NavigationView;
import com.thesandx.sandeep.allinone.BuildConfig;
import com.thesandx.sandeep.allinone.ConnectionLiveData;
import com.thesandx.sandeep.allinone.Models.ConnectionModel;
import com.thesandx.sandeep.allinone.R;
import com.thesandx.sandeep.allinone.fragments.Home;
import com.thesandx.sandeep.allinone.fragments.WebviewUrl;


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
        navigationView.setItemIconTintList(null);



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
                            // Toast.makeText(getApplicationContext(), "wifi on", Toast.LENGTH_SHORT).show();
                            break;
                        case MobileData:
                            // Toast.makeText(getApplicationContext(), "mobile data on", Toast.LENGTH_SHORT).show();
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
                        openFragment("https://www.facebook.com/");
                        getSupportActionBar().setTitle("Facebook");
                        menuItem.setChecked(true);
                        navigationView.setCheckedItem(menuItem.getItemId());
                        drawerLayout.closeDrawers();

                        break;


                    case R.id.tweeter_id:
                        openFragment("https://www.twitter.com/");
                        getSupportActionBar().setTitle("Twitter");
                        menuItem.setChecked(true);
                        navigationView.setCheckedItem(menuItem.getItemId());
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.insta_id:
                        openFragment("https://www.instagram.com/");
                        getSupportActionBar().setTitle("Instagram");
                        navigationView.setCheckedItem(menuItem.getItemId());
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.linkedin_id:
                        openFragment("https://www.linkedin.com/");
                        getSupportActionBar().setTitle("LinkedIn");
                        navigationView.setCheckedItem(menuItem.getItemId());
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.tik_tok_id:
                        openFragment("https://www.tiktok.com/trending/?lang=en");
                        getSupportActionBar().setTitle("TikTok");
                        navigationView.setCheckedItem(menuItem.getItemId());
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.youtube_id:
                        openFragment("https://m.youtube.com/");
                        getSupportActionBar().setTitle("Youtube");
                        navigationView.setCheckedItem(menuItem.getItemId());
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;


                    case R.id.share:
                        try {
                            Intent shareIntent = new Intent(Intent.ACTION_SEND);
                            shareIntent.setType("text/plain");
                            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "AllinOne Social Media");
                            String shareMessage = "\nTry this super fast App to use all social media apps .\nIt is only 1MB in size.\nClick on below link to download the app.\n";
                            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
                            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                            startActivity(Intent.createChooser(shareIntent, "choose one"));
                        } catch (Exception e) {
                            //e.toString();
                        }
                        drawerLayout.closeDrawers();
                        break;

                }


                return false;
            }
        });


    }

    public void openFragment(String url) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        WebviewUrl webviewUrl = new WebviewUrl();
        Bundle args = new Bundle();
        args.putString("url", url);
        webviewUrl.setArguments(args);
        fragmentTransaction.replace(R.id.main_container, webviewUrl);
        fragmentTransaction.commit();


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
