package com.example.linkdatingapp;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

//         Register your parse models
        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("tbYbz7xlFzb9KjEBGgx5GqP1lFWXWI75dG2JWjSF") // should correspond to Application Id env variable
                .clientKey("HFZXpqu4Sx7UgVuFWTWR0hAE2PpRrjduj7RNm5nJ")  // should correspond to Client key env variable
                .server("https://parseapi.back4app.com").build());
    }
}
