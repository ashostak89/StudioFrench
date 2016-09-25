package com.example.ashostak89.studiofrench;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by ashostak89 on 8/27/2016.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    public MyFirebaseInstanceIDService() {
    }
    @Override
    public void onTokenRefresh() {
// Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("mylog", "Refreshed token: " + refreshedToken);


// We will Send this refreshedToken to our app server, so app
// server can save it
// and can later use it for sending notification to app.

// sendRegistrationToServer(refreshedToken);
    }
}