package com.example.bnc;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class Register extends AppCompatActivity {
    Button registerBtn;
    EditText registerNameInput, registerEmailInput, registerPasswordInput;
    Button loginBtn;

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

        loginBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Register.this, Login.class);
            startActivity(intent);
        });

        // Handle register button click
        registerBtn.setOnClickListener(v -> {
            // Get user input from EditText
            String name = registerNameInput.getText().toString().trim();
            String email = registerEmailInput.getText().toString().trim();
            String password = registerPasswordInput.getText().toString().trim();

            // Check if any field is empty
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(Register.this, "Fields can't be empty.!!", Toast.LENGTH_SHORT).show();
            } else if (!isValidEmail(email)) {
                Toast.makeText(Register.this, "Please enter a valid email address.", Toast.LENGTH_SHORT).show();
            } else if (password.length() < 6) {
                Toast.makeText(Register.this, "Password must be at least 6 characters long.", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    // Optionally, you can also use Firebase Authentication for user registration
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this, task -> {
                                if (task.isSuccessful()) {
                                    // Get the UID of the newly created user
                                    String userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

                                    // Create a User object with the provided data
                                    User user = new User(name, email, password, userId);

                                    // Add the user to the Firestore collection
                                    db.collection("users")
                                            .document(userId) // Use the UID as the document ID
                                            .set(user) // Set the user data in the document
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
                                    Toast.makeText(Register.this, "Already Registered", Toast.LENGTH_SHORT).show();
                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("error", String.valueOf(e));
                    // Handle the exception, show a toast, log, or perform any necessary action
                    Toast.makeText(Register.this, "An error occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isValidEmail(String email) {
        // Regular expression for validating an email address
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    public static class User {
        public String name;
        public String email;
        public String password;
        public String userId;

        // Constructors go here

        // Example constructor
        public User(String name, String email, String password, String userId) {
            this.name = name;
            this.email = email;
            this.password = password;
            this.userId = userId;
        }
    }
}



