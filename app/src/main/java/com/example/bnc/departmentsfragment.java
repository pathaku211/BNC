package com.example.bnc;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.MenuRes;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;


public class departmentsfragment extends Fragment {
    public departmentsfragment() {
        // Required empty public constructor
    }
    private View rootView;
    NavigationView navigationView;
    ConstraintLayout constraintLayout;
    FragmentManager fragmentManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         rootView = inflater.inflate(R.layout.fragment_departmentsfragment, container, false);

        GridLayout gridLayout = rootView.findViewById(R.id.gridLayout);
        TextView textview1 = rootView.findViewById(R.id.textview1);
        TextView textview2 = rootView.findViewById(R.id.textview2);
        TextView textview3 = rootView.findViewById(R.id.textview3);
        TextView textview4 = rootView.findViewById(R.id.textview4);
        constraintLayout = rootView.findViewById(R.id.constraintLayout);
        navigationView = rootView.findViewById(R.id.course_navigation);
        FrameLayout frameLayout = rootView.findViewById(R.id.frameLayout);
        fragmentManager = requireActivity().getSupportFragmentManager();


        textview1.setOnClickListener(v -> {
            hideOtherViews(gridLayout);
            setMenu(R.menu.science_course);
        });

        textview2.setOnClickListener(v -> {
            hideOtherViews(gridLayout);
            setMenu(R.menu.language_course);
        });
        textview3.setOnClickListener(v -> {
            hideOtherViews(gridLayout);
            setMenu(R.menu.social_science_course);
        });
        textview4.setOnClickListener(v -> {
            hideOtherViews(gridLayout);
            setMenu(R.menu.vocational_course);
        });
        frameLayout.setOnClickListener(v -> new Handler().postDelayed(() -> {
            showOtherViews(gridLayout);
        }, 100));

        navigationView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.menu_physics) {
                showOtherViews(gridLayout);
                Toast.makeText(rootView.getContext(), "Physics Clicked", Toast.LENGTH_SHORT).show();
                replaceFragment(new physicsfragment());
            }
            if(itemId == R.id.menu_chemistry){
                showOtherViews(gridLayout);
                Toast.makeText(rootView.getContext(), "Chemistry Clicked", Toast.LENGTH_SHORT).show();
                replaceFragment(new chemistryFragment());
            }
            if(itemId == R.id.menu_mathematics){
                showOtherViews(gridLayout);
                Toast.makeText(rootView.getContext(), "Mathematics Clicked", Toast.LENGTH_SHORT).show();
                replaceFragment(new mathematicsFragment());
            }
            return true;
        });
        return rootView;
    }

    private void showOtherViews(GridLayout gridLayout) {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            View child = gridLayout.getChildAt(i);
            if (child instanceof TextView) {
                child.setVisibility(View.VISIBLE);
            }
        }
        constraintLayout.setVisibility(View.GONE);

    }

    private void hideOtherViews(GridLayout gridLayout) {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            View child = gridLayout.getChildAt(i);
            if (child instanceof TextView) {
                child.setVisibility(View.GONE);
            }
        }
    }

    private void setMenu(@MenuRes int menuResId) {
        navigationView.getMenu().clear(); // Clear existing menu items
        navigationView.inflateMenu(menuResId); // Inflate new menu
        constraintLayout.setVisibility(View.VISIBLE);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Use the correct container ID where you want to replace the fragment
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}
