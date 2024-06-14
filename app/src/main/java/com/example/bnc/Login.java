package com.example.bnc;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class Login extends AppCompatActivity {
    Button loginBtn;
    Button registerBtn;
    EditText loginEmailInput, loginPasswordInput;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // ...
// Initialize Firebase Auth
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            currentUser.reload();
            updateUI();
        }
    }

    private void updateUI() {
//        FirebaseUser user = mAuth.getCurrentUser();
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        registerBtn = findViewById(R.id.register_btn);
        loginBtn = findViewById(R.id.login_btn);
        loginEmailInput = findViewById(R.id.login_email_input);
        loginPasswordInput = findViewById(R.id.login_password_input);
        // Find the "Forgot Password?" TextView
        TextView forgotPasswordTextView = findViewById(R.id.forgot_password_text);
        progressBar = findViewById(R.id.progressBar);

        // Set an OnClickListener for the text view
        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform the action when the text view is clicked
                // For example, open a new activity for resetting the password
                Intent intent = new Intent(Login.this, ResetPassword.class);
                startActivity(intent);
            }
        });


        registerBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
        });


        // Handle login button click
        loginBtn.setOnClickListener(v -> {
            // Check internet connectivity
            if (!isNetworkAvailable()) {
                Toast.makeText(Login.this, "No internet connection available.", Toast.LENGTH_SHORT).show();
                return; // Exit method if no internet connection
            }

            // Get user input from EditText
            String email = loginEmailInput.getText().toString().trim();
            String password = loginPasswordInput.getText().toString().trim();

            // Check if any field is empty
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(Login.this, "Email or Password can't be empty", Toast.LENGTH_SHORT).show();
                return; // Exit method if fields are empty
            } else if (!isValidEmail(email)) {
                Toast.makeText(Login.this, "Please enter a valid email address.", Toast.LENGTH_SHORT).show();
                return; // Exit method if email is invalid
            }

            // Show the progress bar when fetching begins
            progressBar.setVisibility(View.VISIBLE);

            // Check if the email is registered
            mAuth.fetchSignInMethodsForEmail(email)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<String> signInMethods = task.getResult().getSignInMethods();
                            assert signInMethods != null;
                            if (signInMethods.contains("password")) {
                                // User is already registered
                                mAuth.signInWithEmailAndPassword(email, password)
                                        .addOnCompleteListener(this, signInTask -> {
                                            // Hide the progress bar when sign-in attempt ends
                                            progressBar.setVisibility(View.GONE);

                                            if (signInTask.isSuccessful()) {
                                                // Sign in success, update UI with the signed-in user's information
                                                Toast.makeText(Login.this, "Login Successful.!!", Toast.LENGTH_SHORT).show();
                                                updateUI();
                                            } else {
                                                // If sign in fails, display a message to the user.
                                                Exception exception = signInTask.getException();
                                                if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                                                    // Incorrect password
                                                    Toast.makeText(Login.this, "Incorrect Password !!!", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    // Other errors
                                                    Toast.makeText(Login.this, "Login failed", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            } else {
                                // Email is not registered
                                Toast.makeText(Login.this, email + " is not registered.", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE); // Hide the progress bar
                            }
                        } else {
                            // Error occurred while checking if user is registered
                            Toast.makeText(Login.this, "Error occurred while checking user registeration.", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE); // Hide the progress bar
                        }
                    });
        });

    }

    private boolean isValidEmail(String email) {
        // Regular expression for validating an email address
        String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z]+\\.+[a-zA-Z]+";
        return email.matches(emailPattern);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }

}