package com.talix573.oasis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    /*
        This activity will be launched if user is detected to be not logged in into any
        accounts from the MainActivity. This handles logins using Firebase Authentication.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}