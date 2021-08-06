package com.talix573.oasis.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.talix573.oasis.R;



public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "EmailPassword" ;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private EditText fullNameE;
    private EditText emailE;
    private EditText passwordE;
    private EditText passwordReE;
    private Button registerB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        fullNameE = findViewById(R.id.et_register_name);
        emailE = findViewById(R.id.et_register_email);
        passwordE = findViewById(R.id.et_register_password);
        passwordReE = findViewById(R.id.et_register_repassword);
        registerB = findViewById(R.id.btn_register);
        registerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("PASSWORD:", passwordE.getText().toString());
                Log.i("PASSWORD:", passwordReE.getText().toString());
                mAuth.createUserWithEmailAndPassword(emailE.getText().toString().trim(), passwordE.getText().toString().trim())
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "createUserWithEmail:success");
                                    Intent signupSuccess = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(signupSuccess);
                                    finish();
                                } else {
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }

    private void updateUI(FirebaseUser user) {
        this.user = user;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent signInToMainActivity = new Intent(this, MainActivity.class);
            startActivity(signInToMainActivity);
            finish();
        }
    }

}





