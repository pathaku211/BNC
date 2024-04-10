package com.example.bnc;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageButton buttonDrawerToggle;
    NavigationView navigationView;

    ImageButton logout_btn;

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

        logout_btn.setOnClickListener(v -> {
            Toast.makeText(this, "Logged Out!!", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        });

        buttonDrawerToggle.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

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

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Use the correct container ID where you want to replace the fragment
        fragmentTransaction.replace(R.id.frameLayout, fragment);

        fragmentTransaction.commit();
    }
}


