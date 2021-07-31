package com.talix573.oasis.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.talix573.oasis.R;

public class RegisterActivity extends AppCompatActivity {

    public RegisterActivity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

}