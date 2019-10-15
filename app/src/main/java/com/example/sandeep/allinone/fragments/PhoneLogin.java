package com.example.sandeep.allinone.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sandeep.allinone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneLogin extends Fragment {

    EditText phoneNumber;
    EditText otp;
    Button  getOTPBtn;
    Button verifyOTPBtn;
    FirebaseAuth firebaseAuth;

    String codeSent;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.phone_login,container,false);

        phoneNumber = view.findViewById(R.id.input_phone);
        otp = view.findViewById(R.id.input_otp);
        getOTPBtn = view.findViewById(R.id.btn_otp);
        verifyOTPBtn = view.findViewById(R.id.btn_otp_verify);

        firebaseAuth = FirebaseAuth.getInstance();

        verifyOTPBtn.setEnabled(false);


        getOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    getOTPBtn.setEnabled(false);
                    String phoneNum = phoneNumber.getText().toString();
                  //  sendOTP(phoneNum);


                }
            }
        });


        verifyOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                verifyOTP();

            }
        });


        return view;

    }


    public void verifyOTP(){

        if (otp.getText()!=null && otp.getText().toString().length()>0){

            String otpInput = otp.getText().toString();
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, otpInput);
            //signInWithPhoneAuthCredential(credential);

        }
        else {
            Toast.makeText(getActivity(),"Please Enter OTP",Toast.LENGTH_LONG).show();
            return ;
        }

    }




   /* private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                        } else {
                            // Sign in failed, display a message and update the UI

                        }
                    }
                });
    }
*/


  /*  public void sendOTP(String phoneNumberverify){

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumberverify,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacksPhoneAuthActivity.java

    }
*/


    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeSent = s;

        }
    };


    public boolean validate(){
        if (phoneNumber!=null && !phoneNumber.getText().toString().trim().equals("")) {
            String phoneNum = phoneNumber.getText().toString();
            if (phoneNum.length()!=10){
                Toast.makeText(getActivity(),"Please Enter 10 digit mobile number",Toast.LENGTH_LONG).show();
                return false;
            }
            else {
                return true;
            }
        }else {
            return false;
        }
    }

}
