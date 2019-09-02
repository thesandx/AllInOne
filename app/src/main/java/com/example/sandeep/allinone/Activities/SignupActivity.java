package com.example.sandeep.allinone.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sandeep.allinone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    EditText email1;
    EditText password1;
    Button signupButton;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email1 = findViewById(R.id.input_email1);
        password1 = findViewById(R.id.input_password1);
        signupButton= findViewById(R.id.btn_signup);

        firebaseAuth = FirebaseAuth.getInstance();

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    signupButton.setEnabled(false);
                    String email = email1.getText().toString();
                    String password = password1.getText().toString();

                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Successful created,Now Login..", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(i);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                                        signupButton.setEnabled(true);
                                    }

                                }
                            });
                }
            }
        });





    }

    public boolean validate(){
        boolean valid = true;

        String emailStr = email1.getText().toString();
        String passStr  = password1.getText().toString();

        if (emailStr.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()) {
            email1.setError("Enter a valid email address");
            valid = false;
        } else {
            email1.setError(null);
        }

        if (passStr.isEmpty() ) {
            password1.setError("Please enter password.");
            valid = false;
        } else {
            password1.setError(null);
        }

        return valid;

    }

}
