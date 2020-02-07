package com.example.sandeep.allinone.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sandeep.allinone.Dialogs.ProgressDialog;
import com.example.sandeep.allinone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends Fragment {

    EditText email;
    Button forgot_btn;
    ProgressDialog dialog;


    public ForgotPassword() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set toolbar back button etc here
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.frogot_password,container,false);

        dialog = new ProgressDialog(getActivity());

        email  = view.findViewById(R.id.input_forgot_email);
        forgot_btn = view.findViewById(R.id.btn_forgot);




        forgot_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate()) {
                    forgot_btn.setEnabled(false);
                    dialog.show();
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getActivity(), "Email Sent", Toast.LENGTH_SHORT).show();
                                        getActivity().onBackPressed();
                                    }
                                    else {
                                        forgot_btn.setEnabled(true);
                                        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                                    }
                                    dialog.dismiss();
                                }
                            });

                }

            }
        });


        return view;

    }


    public boolean validate(){
        String email1 =  email.getText().toString();
        if (!email1.isEmpty()){
            return true;
        }
        else {
            return false;
        }
    }





}
