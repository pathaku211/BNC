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
            } else {
                try {
                    // Create a User object with the provided data
                    User user = new User(name, email, password);

                    // Add the user to the Firestore collection

                    // Optionally, you can also use Firebase Authentication for user registration
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this, task -> {
                                if (task.isSuccessful()) {
                                    db.collection("users") // Use lowercase for collection name
                                            .add(user)
                                            .addOnSuccessListener(documentReference -> {
                                                Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                                // You can add additional logic here after a successful registration
                                            })
                                            .addOnFailureListener(e -> Toast.makeText(Register.this, "Registration Failed", Toast.LENGTH_SHORT).show());
                                    // Sign in success, update UI with the signed-in user's information
                                    Intent intent = new Intent(Register.this, Login.class);
                                    startActivity(intent);
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

    public static class User {
        public String name;
        public String email;
        public String password;

        // Constructors go here

        // Example constructor
        public User(String name, String email, String password) {
            this.name = name;
            this.email = email;
            this.password = password;
        }
    }
}



