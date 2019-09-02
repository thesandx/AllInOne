package com.example.sandeep.allinone;

import android.content.SharedPreferences;
import android.content.Context;

public class SharedPrefence {

    Context context;

    public SharedPrefence(Context context){
        this.context = context;
    }

    public void saveLoginDetails(String email,String password){
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Email",email);
        editor.putString("PassWord",password);
        editor.putBoolean("Logged",true);
        editor.apply();

    }

    public void logout(){

        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Logged",false);
        editor.apply();



    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("Logged",false);

    }

    public boolean isUserLogedOut() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        boolean isEmailEmpty = sharedPreferences.getString("Email", "").isEmpty();
        boolean isPasswordEmpty = sharedPreferences.getString("Password", "").isEmpty();
        return isEmailEmpty;
    }


}
