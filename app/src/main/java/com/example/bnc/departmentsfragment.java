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
//            textview1.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//            property(textview1);
//            setTextViewProperties(textview1);
//            property3(textview1);
//            setTextViewMargins(textview1, 0, 0);
            setMenu(R.menu.science_course);
        });

        textview2.setOnClickListener(v -> {
            hideOtherViews(gridLayout);
//            textview2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//            property(textview2);
//            setTextViewProperties(textview2);
//            property3(textview2);
//            setTextViewMargins(textview2, 0, 0);
            setMenu(R.menu.language_course);
        });
        textview3.setOnClickListener(v -> {
            hideOtherViews(gridLayout);
//            textview3.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//            property(textview3);
//            setTextViewProperties(textview3);
//            property2(textview3);
//            setTextViewMargins(textview3, 0, 600);
            setMenu(R.menu.social_science_course);
        });
        textview4.setOnClickListener(v -> {
            hideOtherViews(gridLayout);
//            textview4.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//            property(textview4);
//            setTextViewProperties(textview4);
//            property2(textview4);
//            setTextViewMargins(textview4, 0, 600);
            setMenu(R.menu.vocational_course);
        });
        frameLayout.setOnClickListener(v -> new Handler().postDelayed(() -> {
//            gridLayout.setVisibility(View.VISIBLE);
//            resetTextViewProperties(textview1);
//            resetTextViewProperties(textview2);
//            resetTextViewProperties(textview3);
//            resetTextViewProperties(textview4);
//            textview1.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.science_drawable, 0, 0); // Left, Top, Right, Bottom
//            textview3.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.social_science_drawable, 0, 0);
//            textview4.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.vocational_drawable, 0, 0);
            showOtherViews(gridLayout);
        }, 100));

        navigationView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.menu_physics) {
                showOtherViews(gridLayout);
                Toast.makeText(rootView.getContext(), "Physics Clicked", Toast.LENGTH_SHORT).show();
                replaceFragment(new physicsfragment());
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

    //    public void property(View text) {
//        ViewGroup.LayoutParams layoutParams = text.getLayoutParams();
//        layoutParams.width = 1200;
//        layoutParams.height = 1400;
//        text.setLayoutParams(layoutParams);
//        GridLayout.LayoutParams Params = (GridLayout.LayoutParams) text.getLayoutParams();
//        Params.setGravity(Gravity.CENTER);
//        text.setLayoutParams(Params);
//    }

//    private void setTextViewProperties(TextView textView) {
//        textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//        ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
//        layoutParams.width = 1200;
//        layoutParams.height = 1400;
//        textView.setLayoutParams(layoutParams);
////        GridLayout.LayoutParams params = (GridLayout.LayoutParams) textView.getLayoutParams();
////        params.setGravity(Gravity.CENTER);
////        textView.setLayoutParams(params);
//    }

//    public void property2(View text) {
//        ViewGroup.MarginLayoutParams layoutParams1 = (ViewGroup.MarginLayoutParams) text.getLayoutParams();
//        layoutParams1.setMargins(0, 0, 0, 600);
//        text.setLayoutParams(layoutParams1);
//    }

    //    public void property3(View text) {
//        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) text.getLayoutParams();
//        layoutParams.setMargins(0, 100, 0, 0);
//        text.setLayoutParams(layoutParams);
//    }
//    private void setTextViewMargins(TextView textView, int top, int bottom) {
//        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) textView.getLayoutParams();
//        int screenWidth = getResources().getDisplayMetrics().widthPixels;
//        int textViewWidth = 1200; // Width specified in dp
//        int horizontalMargin = (screenWidth - textViewWidth) / 2;
//        layoutParams.setMargins(horizontalMargin / 4, top, horizontalMargin, bottom);
//        textView.setLayoutParams(layoutParams);
//    }


//    public void revert(View text_view) {
//
//        ViewGroup.LayoutParams layoutParams = text_view.getLayoutParams();
//        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
//        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//        text_view.setLayoutParams(layoutParams);
//        ViewGroup.MarginLayoutParams layoutParams1 = (ViewGroup.MarginLayoutParams) text_view.getLayoutParams();
//        layoutParams1.setMargins(0, 0, 0, 0);
//        text_view.setLayoutParams(layoutParams1);
//    }

//    private void resetTextViewProperties(TextView textView) {
//        ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
//        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
//        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//        textView.setLayoutParams(layoutParams);
//        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) textView.getLayoutParams();
//        marginLayoutParams.setMargins(0, 0, 0, 0);
//        textView.setLayoutParams(marginLayoutParams);
//    }

    private void hideOtherViews(GridLayout gridLayout) {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            View child = gridLayout.getChildAt(i);
            if (child instanceof TextView) {
                child.setVisibility(View.GONE);
            }
        }
//        setTextViewProperties(textViewToShow);
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
