package com.example.sandeep.allinone.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button  loginButton;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.input_email);
        password = findViewById(R.id.input_password);
        loginButton = findViewById(R.id.btn_login);


        firebaseAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(),"Sucessful created",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                                }

                            }
                        });



            }
        });

    }

    public void validate(){
        if (email.getText().toString()=="" && email.getText()==null){
            email.setError("enter email address");
            return;
        }
        if (password.getText().toString().equalsIgnoreCase("") && password.getText()!=null){
            password.setError("enter password");
            return;
        }


    }
}
