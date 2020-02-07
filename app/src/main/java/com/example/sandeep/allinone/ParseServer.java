package com.example.sandeep.allinone;

import android.app.Application;

public class ParseServer extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

       /* // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("23033ab683ef8b042b5d3199b0372ab99c80708f")
                .server("http://34.221.98.208:80/parse")
                .build()
        );


        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

        ParseUser.enableAutomaticUser();
*/

       /* ParseObject score = new ParseObject("Score");
        score.put("PlayerName","thesandx");
        score.put("Score",340);
        score.saveInBackground();*/

    }
}
