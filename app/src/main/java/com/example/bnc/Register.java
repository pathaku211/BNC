package com.example.bnc;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Register extends AppCompatActivity {
    Button registerBtn;
    EditText registerNameInput, registerEmailInput, registerPasswordInput;
    Button loginBtn;
    ProgressBar progressBar;

    FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase components
        mAuth = FirebaseAuth.getInstance();

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(android.graphics.Color.TRANSPARENT);

        // Initialize views
        registerBtn = findViewById(R.id.register_btn);
        registerNameInput = findViewById(R.id.register_name_input);
        registerEmailInput = findViewById(R.id.register_email_input);
        registerPasswordInput = findViewById(R.id.register_password_input);
        loginBtn = findViewById(R.id.login_btn);
        progressBar = findViewById(R.id.progressBar);

        loginBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Register.this, Login.class);
            startActivity(intent);
        });

        // Handle register button click
        registerBtn.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE); // Show the progress bar when fetching begins
            // Get user input from EditText
            String name = registerNameInput.getText().toString().trim();
            String email = registerEmailInput.getText().toString().trim();
            String password = registerPasswordInput.getText().toString().trim();

            // Check if any field is empty
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(Register.this, "Fields can't be empty.!!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE); // Hide the progress bar
            } else if (!isValidEmail(email)) {
                Toast.makeText(Register.this, "Please enter a valid email address.", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE); // Hide the progress bar
            } else if (password.length() < 6) {
                Toast.makeText(Register.this, "Password must be at least 6 characters long.", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE); // Hide the progress bar
            } else {
                // Check if the email is registered
                mAuth.fetchSignInMethodsForEmail(email)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                List<String> signInMethods = task.getResult().getSignInMethods();
                                assert signInMethods != null;
                                if (signInMethods.contains("password")) {
                                    // User is already registered
                                    Toast.makeText(Register.this, "Already Registered", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE); // Hide the progress bar
                                } else {
                                    // Optionally, you can also use Firebase Authentication for user registration
                                    mAuth.createUserWithEmailAndPassword(email, password)
                                            .addOnCompleteListener(this, task1 -> {
                                                progressBar.setVisibility(View.GONE); // Hide the progress bar when fetching completes
                                                if (task1.isSuccessful()) {
                                                    // Get the UID of the newly created user
                                                    String userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

                                                    // Create a User object with the provided data
                                                    // Add the user to the Firestore collection with server timestamp
                                                    Map<String, Object> userMap = new HashMap<>();
                                                    userMap.put("name", name);
                                                    userMap.put("email", email);
                                                    userMap.put("password", password);
                                                    userMap.put("userId", userId);
                                                    userMap.put("timestamp", FieldValue.serverTimestamp());

                                                    // Add the user to the Firestore collection
                                                    db.collection("users")
                                                            .document(userId) // Use the UID as the document ID
                                                            .set(userMap) // Set the user data in the document
                                                            .addOnSuccessListener(documentReference -> {
                                                                Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                                                // You can add additional logic here after a successful registration

                                                                // Navigate to the login screen
                                                                Intent intent = new Intent(Register.this, Login.class);
                                                                startActivity(intent);
                                                            })
                                                            .addOnFailureListener(e -> {
                                                                Toast.makeText(Register.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                                                Log.e("Firestore", "Error adding document", e);
                                                            });
                                                } else {
                                                    // If sign-in fails, display a message to the user.
                                                    Toast.makeText(Register.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            } else {
                                // Error occurred while checking if user is registered
                                Toast.makeText(Register.this, "Error occurred while checking user registeration.", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE); // Hide the progress bar
                            }
                        });
            }
        });

    }

    private boolean isValidEmail(String email) {
        // Regular expression for validating an email address
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

//    public static class User {
//        public String name;
//        public String email;
//        public String password;
//        public String userId;
//
//        // Constructors go here
//
//        // Example constructor
//        public User(String name, String email, String password, String userId) {
//            this.name = name;
//            this.email = email;
//            this.password = password;
//            this.userId = userId;
//        }
//    }
}



