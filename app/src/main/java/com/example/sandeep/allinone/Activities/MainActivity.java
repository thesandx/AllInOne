package com.example.sandeep.allinone.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;


import com.example.sandeep.allinone.ConnectionLiveData;
import com.example.sandeep.allinone.Models.ConnectionModel;
import com.example.sandeep.allinone.Models.HistoryModel;
import com.example.sandeep.allinone.R;
import com.example.sandeep.allinone.SharedPrefence;
import com.example.sandeep.allinone.Utils.DateUtils;
import com.example.sandeep.allinone.fragments.HistoryFragment;
import com.example.sandeep.allinone.fragments.Home;
import com.example.sandeep.allinone.fragments.Timer;
import com.example.sandeep.allinone.fragments.WebviewUrl;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    String Currentdate = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date());
    int date = Integer.parseInt(Currentdate);

    int date1= date;

    int totalTime = 15885; //in miliseconds

    boolean isUsedToday = false;

    private EditText mEditTextInput;
    private TextView mTextViewCountDown;
    private Button mButtonSet;
    private Button mButtonStartPause;
    private Button mButtonReset;

    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;

    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;
    private long mEndTime;

    boolean doubleBackToExitPressedOnce = false;


    AlertDialog.Builder  alertBuilder;

    ProgressDialog progressDialog;


    Toolbar toolbar;
    FragmentTransaction fragmentTransaction;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    FirebaseAuth auth;

    private DatabaseReference database;


    public static final int MobileData = 2;
    public static final int WifiData = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar =  findViewById(R.id.toolbar);
        drawerLayout=findViewById(R.id.drawerlayout);
        setSupportActionBar(toolbar);

        database = FirebaseDatabase.getInstance().getReference();

        alertBuilder = new AlertDialog.Builder(this);

        progressDialog = new ProgressDialog(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container, new Home());
        fragmentTransaction.commit();
        getSupportActionBar().setTitle("Home");
        navigationView =  findViewById(R.id.navigation_view);

        auth = FirebaseAuth.getInstance();


        Toast.makeText(MainActivity.this,"Please set the time for this session",Toast.LENGTH_SHORT).show();

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
                if (connection.getIsConnected()){

                    switch (connection.getType()){

                        case WifiData:
                            Toast.makeText(getApplicationContext(),"wifi on", Toast.LENGTH_SHORT).show();                            break;
                        case MobileData:
                            Toast.makeText(getApplicationContext(),"mobile on", Toast.LENGTH_SHORT).show();                            break;

                    }

                }
                else {
                    Toast.makeText(getApplicationContext(),"Connection turned OFF", Toast.LENGTH_SHORT).show();
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
                        fragmentTransaction.replace(R.id.main_container, new WebviewUrl(MainActivity.this,"https://m.facebook.com/"));
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Facebook");
                        menuItem.setChecked(true);
                        navigationView.setCheckedItem(menuItem.getItemId());
                        drawerLayout.closeDrawers();

                        break;


                    case R.id.tweeter_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new WebviewUrl(MainActivity.this,"https://www.twitter.com/"));
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Twitter");
                        menuItem.setChecked(true);
                        navigationView.setCheckedItem(menuItem.getItemId());
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.insta_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new WebviewUrl(MainActivity.this,"https://www.instagram.com/"));
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Instagram");
                        navigationView.setCheckedItem(menuItem.getItemId());
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.linkedin_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new WebviewUrl(MainActivity.this,"https://www.linkedin.com/"));
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("LinkedIn");
                        navigationView.setCheckedItem(menuItem.getItemId());
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.timer_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new Timer());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Website");
                        menuItem.setChecked(true);
                        navigationView.setCheckedItem(menuItem.getItemId());
                        drawerLayout.closeDrawers();
                        break;



                    case R.id.history_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new HistoryFragment());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("History");
                        menuItem.setChecked(true);
                        navigationView.setCheckedItem(menuItem.getItemId());
                        drawerLayout.closeDrawers();

                        break;


                    case R.id.Logout_id:

                                  drawerLayout.closeDrawers();


                        alertBuilder.setMessage("Are you sure you want to logout?").setTitle("Alert")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {


                                        progressDialog.show();
                                        progressDialog.setMessage("Logging Out");
                                        progressDialog.setCancelable(false);
                                        auth.signOut();
                                        new SharedPrefence(getApplicationContext()).logout();
                                        Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                                        progressDialog.dismiss();
                                        startActivity(i);
                                        finish();

                                    }
                                })

                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        dialog.cancel();

                                    }
                                });



                        AlertDialog alert = alertBuilder.create();
                        alert.show();






                        break;

                }
                return false;
            }
        });



        mEditTextInput = findViewById(R.id.edit_text_input);
        mTextViewCountDown = findViewById(R.id.text_view_countdown);

        mButtonSet = findViewById(R.id.button_set);
       // mButtonStartPause = findViewById(R.id.button_start_pause);
       // mButtonReset = findViewById(R.id.button_reset);

        mTimerRunning=false;

        mButtonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = mEditTextInput.getText().toString();
                if (input.length() == 0) {
                    Toast.makeText(MainActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                long millisInput = Long.parseLong(input) * 60000;
                if (millisInput == 0 || millisInput > 30*60*1000) {
                    Toast.makeText(MainActivity.this, "Please enter number between 1 and 30", Toast.LENGTH_SHORT).show();
                    return;
                }

                setTime(millisInput);
                saveHistory(input);
                //millisinput
                mEditTextInput.setText("");
                startTimer();
              //
             //   mButtonStartPause.setEnabled(false);
               // mButtonReset.setEnabled(false);
            }
        });

    }

    private void saveHistory(String timeLimit){
        //create a child in root
        //assign value to the child
        DatabaseReference historyDb = database.child("history");
        DatabaseReference userDb = historyDb.child("sandeep").push();

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        String dateStr = DateUtils.DateToString(date,"dd/MM/yyyy");
        String timeStr = DateUtils.DateToString(date,"HH:mm");

        HistoryModel historyModel = new HistoryModel(dateStr,timeStr,timeLimit);

        userDb.setValue(historyModel);

    }

    private void setTime(long milliseconds) {
        mStartTimeInMillis = milliseconds;
        mTimeLeftInMillis = milliseconds;
       // resetTimer();
        closeKeyboard();
    }


    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                if(mTimeLeftInMillis<59000){
                   Toast.makeText(MainActivity.this, "You have only "+mTimeLeftInMillis/1000+" s left", Toast.LENGTH_SHORT).show();
                    //pauseTimer();
                }
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                updateWatchInterface();
                Toast.makeText(MainActivity.this, "Time is Up!!!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }.start();



        mTimerRunning = true;
        updateWatchInterface();
    }


    private void updateCountDownText() {
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }

        mTextViewCountDown.setText(timeLeftFormatted);
    }

    private void updateWatchInterface() {
        if (mTimerRunning) {
            mEditTextInput.setVisibility(View.INVISIBLE);
            mButtonSet.setVisibility(View.INVISIBLE);

        } else {
            mEditTextInput.setVisibility(View.VISIBLE);
            mButtonSet.setVisibility(View.VISIBLE);

        }
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) this.getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mCountDownTimer!=null) {
            mCountDownTimer.cancel();
        }

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
