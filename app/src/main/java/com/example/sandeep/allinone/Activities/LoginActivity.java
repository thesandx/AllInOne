package com.example.sandeep.allinone.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sandeep.allinone.R;
import com.example.sandeep.allinone.SharedPrefence;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button  loginButton;
    Button signUpButton;
    private CheckBox rememberMe;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.input_email);
        password = findViewById(R.id.input_password);
        loginButton = findViewById(R.id.btn_login);
        rememberMe = findViewById(R.id.remember_me);
        signUpButton = findViewById(R.id.btn_signupnew);

        dialog = new ProgressDialog(this);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


        if(isLoggedin()){
            Log.d("MainActivity", "already login starting homeActivity");
            startHomeActivity();
        }

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);

            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    dialog.show();
                    dialog.setMessage("Logging In...");
                    loginButton.setEnabled(false);
                    final String email1 = email.getText().toString();
                    final String password1 = password.getText().toString();

                    firebaseAuth.signInWithEmailAndPassword(email1, password1)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        if (rememberMe.isChecked()) {
                                            saveLoginDetails(email1, password1, true);
                                        }
                                        dialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "Successful login", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(i);
                                    } else {
                                        dialog.dismiss();
                                        if (!isNetworkAvailable()){
                                            Toast.makeText(getApplicationContext(), "Connect to Internet", Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                                        }
                                        loginButton.setEnabled(true);
                                    }

                                }
                            });
                }
            }
        });

    }


    private void startHomeActivity () {
        Log.d("MainActivity", "starting home Activity");


        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    private void saveLoginDetails(String email, String password,Boolean logged) {
        Log.d("MainActivity", "saving login details");

        new SharedPrefence(this).saveLoginDetails(email, password);

    }

    private boolean isLoggedin() {
        Log.d("Thesandx", "inside isloggedin");

        return new SharedPrefence(this).isLoggedIn();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }


    public boolean validate(){
        boolean valid = true;

        String emailStr = email.getText().toString();
        String passStr  = password.getText().toString();

        if (emailStr.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()) {
            email.setError("Enter a valid email address");
            valid = false;
        } else {
            email.setError(null);
        }

        if (passStr.isEmpty() ) {
            password.setError("Please enter password.");
            valid = false;
        } else {
            password.setError(null);
        }

        return valid;

    }
}
