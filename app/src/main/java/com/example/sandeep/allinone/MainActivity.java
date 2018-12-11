package com.example.sandeep.allinone;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    FragmentTransaction fragmentTransaction;
    NavigationView navigationView;
    //DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container,new Home());
        fragmentTransaction.commit();
        getSupportActionBar().setTitle("Home");
        navigationView=(NavigationView)findViewById(R.id.navigation_view);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){

                    case R.id.home_id:
                        fragmentTransaction=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container,new Home());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Home");
                        menuItem.setChecked(true);
                        //drawerLayout.closeDrawers();

                        break;

                    case R.id.fb_id:
                        fragmentTransaction=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container,new Facebook());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Facebook");
                        menuItem.setChecked(true);
                        //drawerLayout.closeDrawers();

                        break;


                    case R.id.tweeter_id:
                        fragmentTransaction=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container,new Twitter());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Twitter");
                        menuItem.setChecked(true);
                       // drawerLayout.closeDrawers();
                        break;

                    case R.id.insta_id:
                        fragmentTransaction=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container,new Instagram());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Instagram");
                        menuItem.setChecked(true);
                       // drawerLayout.closeDrawers();
                        break;

                    case R.id.timer_id:
                        fragmentTransaction=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container,new Timer());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Timer");
                        menuItem.setChecked(true);
                       // drawerLayout.closeDrawers();
                        break;

                    case R.id.About_id:
                        fragmentTransaction=getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container,new About());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("About");
                        menuItem.setChecked(true);
                        //drawerLayout.closeDrawers();

                        break;

                }
                return false;
            }
        });
    }
}
