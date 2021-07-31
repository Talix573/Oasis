package com.talix573.oasis.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
<<<<<<< Updated upstream:app/src/main/java/com/talix573/oasis/main/LoginActivity.java

import com.talix573.oasis.R;
=======
>>>>>>> Stashed changes:app/src/main/java/com/talix573/oasis/LoginActivity.java

public class LoginActivity extends AppCompatActivity {

    /*
        This activity will be launched if user is detected to be not logged in into any
        accounts from the MainActivity. This handles logins using Firebase Authentication.
     */

    private TextView testTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        testTextView = this.findViewById(R.id.TestTextView);
        testTextView.setText("Login");
    }

}

    public LoginActivity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

}