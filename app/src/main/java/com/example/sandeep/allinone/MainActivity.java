package com.example.sandeep.allinone;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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


    Toolbar toolbar;
    FragmentTransaction fragmentTransaction;
    NavigationView navigationView;


    //DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container, new Home());
        fragmentTransaction.commit();
        getSupportActionBar().setTitle("Home");
        navigationView =  findViewById(R.id.navigation_view);


        Toast.makeText(MainActivity.this,"Please set the time for this session",Toast.LENGTH_SHORT).show();


    /**    SharedPreferences prefs = this.getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt("currentDate",date);
        editor.putInt("totalTime",totalTime);

        editor.apply();

        Toast.makeText(MainActivity.this,"Date is "+date+" and time is "+totalTime/60000,Toast.LENGTH_LONG).show();

**/



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
                        //drawerLayout.closeDrawers();

                        break;

                    case R.id.fb_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new Facebook());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Facebook");
                        menuItem.setChecked(true);
                        //drawerLayout.closeDrawers();

                        break;


                    case R.id.tweeter_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new Twitter());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Twitter");
                        menuItem.setChecked(true);
                        // drawerLayout.closeDrawers();
                        break;

                    case R.id.insta_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new Instagram());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Instagram");
                        menuItem.setChecked(true);
                        // drawerLayout.closeDrawers();
                        break;

                    case R.id.timer_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new Timer());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Website");
                        menuItem.setChecked(true);
                        // drawerLayout.closeDrawers();
                        break;

                    case R.id.About_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new About());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("About");
                        menuItem.setChecked(true);
                        //drawerLayout.closeDrawers();

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
                if (millisInput == 0) {
                    Toast.makeText(MainActivity.this, "Please enter a positive number", Toast.LENGTH_SHORT).show();
                    return;
                }

                setTime(millisInput);
                //millisinput
                mEditTextInput.setText("");
                startTimer();
              //
             //   mButtonStartPause.setEnabled(false);
               // mButtonReset.setEnabled(false);
            }
        });

     /**   mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }

            }
        });


        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });  **/
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
               // isUsedToday = true;
               // date1 = date+1;

               // if(date == date1 && isUsedToday){
                    finish();
               // }


              //  SharedPreferences prefs = this.getSharedPreferences("prefs", MODE_PRIVATE);


                // setTime(1*60*1000);

                // finish();

              /**  int id = android.os.Process.myPid();
                android.os.Process.killProcess(id);**/
                //here you can draw a poster to cover the application till next day
            }
        }.start();

        mTimerRunning = true;
        updateWatchInterface();
    }

  /**  private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        updateWatchInterface();
    }

    private void resetTimer() {
        mTimeLeftInMillis = mStartTimeInMillis;
        updateCountDownText();
        updateWatchInterface();
    }  **/


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
           // mButtonStartPause.setVisibility(View.INVISIBLE);
           // mButtonStartPause.setText("Pause");
          //  mButtonStartPause.setEnabled(false);


        } else {
            mEditTextInput.setVisibility(View.VISIBLE);
            mButtonSet.setVisibility(View.VISIBLE);
           // mButtonStartPause.setVisibility(View.INVISIBLE);
          //  mButtonStartPause.setText("Start");
          //  mButtonReset.setEnabled(false);

         /**   if (mTimeLeftInMillis < 1000) {
                mButtonStartPause.setVisibility(View.INVISIBLE);
            } else {
                mButtonStartPause.setVisibility(View.VISIBLE);
            }

            if (mTimeLeftInMillis < mStartTimeInMillis) {
                mButtonReset.setVisibility(View.VISIBLE);
            } else {
                mButtonReset.setVisibility(View.INVISIBLE);
            }
          **/

        }
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) this.getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

  /**  @Override
    public void onPause() {
        super.onPause();

        SharedPreferences prefs = this.getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt("currentDate",date);
        editor.putInt("totalTime",totalTime);
        editor.putLong("startTimeInMillis", mStartTimeInMillis);
        editor.putLong("millisLeft", mTimeLeftInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);

        editor.apply();

        Toast.makeText(this, "In the onPause() event",Toast.LENGTH_SHORT).show();


        if (mCountDownTimer != null) {
           // mCountDownTimer.cancel();
           // pauseTimer();
            //mCountDownTimer.cancel();
            Toast.makeText(this, "Time Left is "+mTimeLeftInMillis/1000+" seconds",Toast.LENGTH_SHORT).show();

        }
    }   **/


  /**  public void onResume()
    {
        super.onResume();

        Toast.makeText(this, "In the onResume() event",Toast.LENGTH_SHORT).show();
        SharedPreferences prefs = this.getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("startTimeInMillis", mStartTimeInMillis);
        editor.putLong("millisLeft", mTimeLeftInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);




        int currentdate = prefs.getInt("currentDate",0);
        int totaltime = prefs.getInt("totalTime",0);
        Long timeLeft =prefs.getLong("millisLeft",0);

        Toast.makeText(MainActivity.this,"Date is "+currentdate+" and time is "+totaltime/60000,Toast.LENGTH_LONG).show();

        if(date == currentdate){
            if(isUsedToday == true){
                finish();
            }
        }
        else{


            editor.putInt("currentDate",date);
            editor.apply();

        }

        editor.apply();

        startTimer();

        if (mCountDownTimer != null) {
           // mCountDownTimer.start();

        }
    }   **/

    /**   @Override
   public void onStart() {
        super.onStart();


        Toast.makeText(MainActivity.this,"In onStart",Toast.LENGTH_SHORT).show();


        SharedPreferences prefs = this.getSharedPreferences("prefs", MODE_PRIVATE);

        mStartTimeInMillis = prefs.getLong("startTimeInMillis", 600000);
        mTimeLeftInMillis = prefs.getLong("millisLeft", mStartTimeInMillis);
        mTimerRunning = prefs.getBoolean("timerRunning", false);

        int currentdate = prefs.getInt("currentDate",0);
        int totaltime = prefs.getInt("totalTime",0);
        long timeleft = prefs.getLong("millisLeft",0);

        if(date == currentdate){
            if(isUsedToday == true){
                finish();
            }
        }
        else{
            SharedPreferences.Editor editor = prefs.edit();

            editor.putInt("currentDate",date);
            editor.apply();

        }




        Toast.makeText(MainActivity.this,"Date is "+currentdate+" and time is "+totaltime+" time left is "+timeleft,Toast.LENGTH_LONG).show();




        updateCountDownText();
        updateWatchInterface();

        if (mTimerRunning) {
            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();


            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0;
                mTimerRunning = false;
                updateCountDownText();
                updateWatchInterface();
            } else {
                startTimer();
            }
        }

    }
**/


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
       // pauseTimer();
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}
