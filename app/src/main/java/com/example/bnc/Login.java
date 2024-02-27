package com.example.bnc;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Login extends AppCompatActivity {
    Button loginBtn;
    Button registerBtn;
    EditText loginEmailInput, loginPasswordInput;

    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // ...
// Initialize Firebase Auth
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
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


        registerBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
        });

        mAuth = FirebaseAuth.getInstance();
        // Handle login button click
        loginBtn.setOnClickListener(v -> {
            // Get user input from EditText
            String email = loginEmailInput.getText().toString().trim();
            String password = loginPasswordInput.getText().toString().trim();

            // Check if any field is empty
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(Login.this, "Email or Password can't be empty", Toast.LENGTH_SHORT).show();
            } else {
                // Perform login logic here
                // For now, let's just show a Toast message
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(Login.this, "Login Successful.!!",
                                        Toast.LENGTH_SHORT).show();
                                updateUI();
                            } else {
                                // If sign in fails, display a message to the user.

                                Toast.makeText(Login.this, "Wrong Password or not Registered!!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}