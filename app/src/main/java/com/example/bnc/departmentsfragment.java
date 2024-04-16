package com.example.bnc;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.checkerframework.checker.nullness.qual.NonNull;


public class departmentsfragment extends Fragment {
    public departmentsfragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_departmentsfragment, container, false);

        GridLayout gridLayout = rootView.findViewById(R.id.gridLayout);
        TextView textview1 = rootView.findViewById(R.id.textview1);
        TextView textview2 = rootView.findViewById(R.id.textview2);
        TextView textview3 = rootView.findViewById(R.id.textview3);
        TextView textview4 = rootView.findViewById(R.id.textview4);
        FrameLayout frameLayout = rootView.findViewById(R.id.frameLayout);



        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            View childView = gridLayout.getChildAt(0);
            if (childView instanceof TextView) {
                textview1.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.science, 0, 0);
            }
        }
        textview1.setOnClickListener(v -> {
            for (int i = 0; i < gridLayout.getChildCount(); i++) {
                View child = gridLayout.getChildAt(i);
                if (child instanceof TextView && child != textview1) {
                    child.setVisibility(View.GONE);
                }
            }
            textview1.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            property(textview1);
            property3(textview1);
        });

        textview2.setOnClickListener(v -> {
            for (int i = 0; i < gridLayout.getChildCount(); i++) {
                View child = gridLayout.getChildAt(i);
                if (child instanceof TextView && child != textview2) {
                    child.setVisibility(View.GONE);
                }
            }
              property(textview2);
            property3(textview2);
//            textview2.setCompoundDrawablesWithIntrinsicBounds(0,0, 0, 0);
        });
        textview3.setOnClickListener(v -> {
            for (int i = 0; i < gridLayout.getChildCount(); i++) {
                View child = gridLayout.getChildAt(i);
                if (child instanceof TextView && child != textview3) {
                    child.setVisibility(View.GONE);
                }
            }
            property(textview3);
            property2(textview3);
//            textview3.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        });
        textview4.setOnClickListener(v -> {
                    for (int i = 0; i < gridLayout.getChildCount(); i++) {
                        View child = gridLayout.getChildAt(i);
                        if (child instanceof TextView && child != textview4) {
                            child.setVisibility(View.GONE);
                        }
                    }
                    property(textview4);
                    property2(textview4);
//            textview4.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                });
        frameLayout.setOnClickListener(v -> new Handler().postDelayed(() -> {
            gridLayout.setVisibility(View.VISIBLE);
            for (int i = 0; i < gridLayout.getChildCount(); i++) {
                View child = gridLayout.getChildAt(i);
                if(child instanceof TextView){
                    revert(textview1);
                    textview1.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.science, 0, 0); // Left, Top, Right, Bottom
                    revert(textview2);
                    revert(textview3);
                    revert(textview4);
                    child.setVisibility(View.VISIBLE);
                }
         }}, 100));
            return rootView;
        }
    public void property(View textv){
        ViewGroup.LayoutParams layoutParams = textv.getLayoutParams();
            layoutParams.width = 1200;
            layoutParams.height = 1400;
            textv.setLayoutParams(layoutParams);
            GridLayout.LayoutParams Params = (GridLayout.LayoutParams) textv.getLayoutParams();
            Params.setGravity(Gravity.CENTER);
            textv.setLayoutParams(Params);

    }
    public void property2(View text){
        ViewGroup.MarginLayoutParams layoutParams1 = (ViewGroup.MarginLayoutParams) text.getLayoutParams();
        layoutParams1.setMargins(0, 0, 0, 600);
        text.setLayoutParams(layoutParams1);
    }
    public void property3(View text){
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) text.getLayoutParams();
        layoutParams.setMargins(0,100,0,0);
        text.setLayoutParams(layoutParams);
    }
    public void revert(View text_view){

        ViewGroup.LayoutParams layoutParams = text_view.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        text_view.setLayoutParams(layoutParams);
        ViewGroup.MarginLayoutParams layoutParams1 = (ViewGroup.MarginLayoutParams) text_view.getLayoutParams();
        layoutParams1.setMargins(0, 0, 0, 0);
        text_view.setLayoutParams(layoutParams1);
    }
}
