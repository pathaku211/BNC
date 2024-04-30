package com.example.bnc;

import static android.Manifest.permission.POST_NOTIFICATIONS;
import static android.content.ContentValues.TAG;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;


public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageButton buttonDrawerToggle;
    NavigationView navigationView;
    TextView username, useremail;
    ImageView userimage;
    ImageButton back;
    ImageButton logout_btn;
    private static final String MSG_TOKEN_FMT = "Token: %s";
//    Button logout_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        replaceFragment(new homefragment());
        drawerLayout = findViewById(R.id.drawerlayout);
        buttonDrawerToggle = findViewById(R.id.buttondrawertoggle);
        navigationView = findViewById(R.id.navigationview);
        logout_btn = findViewById(R.id.logout_btn);
        back = findViewById(R.id.back_btn);
        View headerView = navigationView.getHeaderView(0);
        username = headerView.findViewById(R.id.user_name);
        useremail = headerView.findViewById(R.id.user_email);
        userimage = headerView.findViewById(R.id.userimage);

        userimage.setImageResource(R.drawable.image1);

        checkNotificationPermission();


//        FirebaseMessaging.getInstance().getToken()
//                .addOnCompleteListener(new OnCompleteListener<String>() {
//                    @Override
//                    public void onComplete(@NonNull Task<String> task) {
//                        if (!task.isSuccessful()) {
//                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
//                            return;
//                        }
//
//                        // Get new FCM registration token
//                        String token = task.getResult();
//
//                        // Log and toast
//                        String msg = String.format(MSG_TOKEN_FMT, token);
//                        Log.d(TAG, msg);
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
//                    }
//                });

//        FirebaseMessaging.getInstance().subscribeToTopic("notice")
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        String msg = "Subscribed";
//                        if (!task.isSuccessful()) {
//                            msg = "Subscribe failed";
//                        }
//                        Log.d(TAG, msg);
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
//                    }
//                });

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (currentUser != null) {
            // Retrieve user data from Firestore
            String userId = currentUser.getUid();
            db.collection("users").document(userId).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            // Get user data from Firestore document
                            String displayName = documentSnapshot.getString("name");
                            String email = documentSnapshot.getString("email");
                            // Set display name and email to TextViews
                            if (displayName != null && !displayName.isEmpty()) {
                                username.setText(displayName);
                            }
                            if (email != null && !email.isEmpty()) {
                                useremail.setText(email);
                            }
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.e("Firestore", "Error getting user document", e);
                    });
        }
        // Set an applyInsetsListener to add top margin equal to the height of the notification bar
        navigationView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                setMarginTop(v, insets.getSystemWindowInsetTop());
                return insets;
            }
        });


        logout_btn.setOnClickListener(v -> {
            Toast.makeText(this, "Logged Out!!", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        });

        buttonDrawerToggle.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        back.setOnClickListener(v ->
                onBackPressed()
        );

        navigationView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.menu_home) {
                Toast.makeText(MainActivity.this, "Home Clicked", Toast.LENGTH_SHORT).show();
                replaceFragment(new homefragment());
            }
            if (itemId == R.id.menu_about) {
                Toast.makeText(MainActivity.this, "About Clicked", Toast.LENGTH_SHORT).show();
                replaceFragment(new aboutfragment());
            }
            if (itemId == R.id.menu_profile) {
                Toast.makeText(MainActivity.this, "Profile Clicked", Toast.LENGTH_SHORT).show();
                replaceFragment(new profilefragment());
            }
            if (itemId == R.id.menu_departments) {
                Toast.makeText(MainActivity.this, "Departments Clicked", Toast.LENGTH_SHORT).show();
                replaceFragment(new departmentsfragment());
            }
            if (itemId == R.id.menu_admission) {
                Toast.makeText(MainActivity.this, "Admission Clicked", Toast.LENGTH_SHORT).show();
                replaceFragment(new admissionfragment());
            }
            if (itemId == R.id.menu_logout) {
                Toast.makeText(this, "Logged Out!!", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, Login.class);
                startActivity(intent);
                finish();
            }

            // Close the drawer when an item is selected
            drawerLayout.closeDrawer(GravityCompat.START);

            return true;
        });

    }

    @Override
    public void onBackPressed() {
        // Check if there are any fragments in the back stack
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            // Pop the back stack to navigate to the previous fragment
            button_visibility();
            fragmentManager.popBackStack();
        } else {
            // If there are no fragments in the back stack, proceed with the default behavior
            button_visibility();
            super.onBackPressed();
        }
    }

    // Function to set top margin
    private void setMarginTop(View view, int topMargin) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        params.topMargin = topMargin;
        view.setLayoutParams(params);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Use the correct container ID where you want to replace the fragment
        fragmentTransaction.replace(R.id.frameLayout, fragment);

        fragmentTransaction.commit();
    }

    private void checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int permissionNotif = ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS);
            if (permissionNotif != PackageManager.PERMISSION_GRANTED) {
                String[] NOTIF_PERM = {Manifest.permission.POST_NOTIFICATIONS};
                ActivityCompat.requestPermissions(this, NOTIF_PERM, 100);
            }
        }
    }

    private void button_visibility() {
        ImageButton logout = findViewById(R.id.logout_btn);
        ImageButton toggle = findViewById(R.id.buttondrawertoggle);
        ImageButton back = findViewById(R.id.back_btn);
        if (logout != null) logout.setVisibility(View.VISIBLE);
        if (toggle != null) {
            toggle.setVisibility(View.VISIBLE);
            back.setVisibility(View.GONE);
        }
    }


}


