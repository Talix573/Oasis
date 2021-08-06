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



public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "EmailPassword" ;
    private FirebaseAuth mAuth;

    private EditText emailE;
    private EditText passwordE;
    private Button loginB;

    /*
        This activity will be launched if user is detected to be not logged in into any
        accounts from the MainActivity. This handles logins using Firebase Authentication.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        emailE = findViewById(R.id.et_email);
        passwordE = findViewById(R.id.et_password);
        loginB = findViewById(R.id.btn_login);
        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signInWithEmailAndPassword(emailE.getText().toString().trim(), passwordE.getText().toString().trim())
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    Intent signInToMainActivity = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(signInToMainActivity);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(getApplicationContext(), "Authentication failed. Please try again later.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent signInToMainActivity = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(signInToMainActivity);
            finish();
        }
    }

    /*
This is to link the textview above the Login button to the register activity once clicked
 */

    public void myMethod(View view) {

        Intent myIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        LoginActivity.this.startActivity(myIntent);

    }

}






