package com.example.bnc;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    private EditText editTextEmail;
    private Button buttonResetPassword;
    private ImageView backButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        editTextEmail = findViewById(R.id.editTextEmail);
        buttonResetPassword = findViewById(R.id.buttonResetPassword);
        backButton = findViewById(R.id.backButton);

        mAuth = FirebaseAuth.getInstance();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(ResetPassword.this, Login.class);
                    startActivity(intent);
            }
        });

        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the email entered by the user
                String email = editTextEmail.getText().toString().trim();

                // Check if the email is empty
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(ResetPassword.this, "Please enter your email address.", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(email)) {
                    Toast.makeText(ResetPassword.this, "Please enter a valid email address.", Toast.LENGTH_SHORT).show();
                } else {
                    // Send reset password email to the provided email address
                    mAuth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    // Reset password email sent successfully
                                    Toast.makeText(ResetPassword.this, "Reset password email sent to " + email, Toast.LENGTH_SHORT).show();
                                } else {
                                    // Failed to send reset password email
                                    Toast.makeText(ResetPassword.this, "Failed to send reset password email.", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

    }

    private boolean isValidEmail(String email) {
        // Regular expression for validating an email address
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }
}
