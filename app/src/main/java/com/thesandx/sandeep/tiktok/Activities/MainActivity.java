package com.thesandx.sandeep.tiktok.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import com.thesandx.sandeep.tiktok.ConnectionLiveData;
import com.thesandx.sandeep.tiktok.Models.ConnectionModel;
import com.thesandx.sandeep.tiktok.R;
import com.thesandx.sandeep.tiktok.fragments.WebviewUrl;


public class MainActivity extends AppCompatActivity {



    boolean doubleBackToExitPressedOnce = false;


    AlertDialog.Builder  alertBuilder;

    ProgressDialog progressDialog;


    Toolbar toolbar;
    FragmentTransaction fragmentTransaction;





    public static final int MobileData = 2;
    public static final int WifiData = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        alertBuilder = new AlertDialog.Builder(this);

        progressDialog = new ProgressDialog(this);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        openFragment("https://www.tiktok.com/trending/?lang=en");



       // Toast.makeText(MainActivity.this, "Please set the time for this session", Toast.LENGTH_SHORT).show();


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
