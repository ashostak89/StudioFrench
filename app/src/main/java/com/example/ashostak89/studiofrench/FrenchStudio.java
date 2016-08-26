package com.example.ashostak89.studiofrench;

import com.firebase.client.Firebase;

/**
 * Created by ashostak89 on 8/23/2016.
 */
public class FrenchStudio extends android.app.Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);

    }
}
