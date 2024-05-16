package com.example.bnc;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;

public class functionalenglishFragment extends Fragment {
    View root;
    TextView text,text1;
    LinearLayout linearLayout,linelayout;
    ImageSlider image;

    public functionalenglishFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_functionalenglish, container, false);
        text = root.findViewById(R.id.text);
        text1 = root.findViewById(R.id.text1);
        linelayout = root.findViewById(R.id.line_layout);
        linearLayout = root.findViewById(R.id.line);

        Button textLoad = root.findViewById(R.id.text_load);
        textLoad.setOnClickListener(v -> {
            // Define the URL you want to open
            String url = "https://www.bncollegepatna.com/department_login/files/#";

            // Create an Intent with the ACTION_VIEW action and the URL as the data
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

            // Check if there is an Activity available to handle the Intent
            if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
                // Start the activity to open the URL
                startActivity(intent);
            } else {
                // If no Activity is available to handle the Intent, show a toast or perform any other action
                Toast.makeText(requireContext(), "No app found to open the URL", Toast.LENGTH_SHORT).show();
            }
        });

        image = root.findViewById(R.id.image_slider);

        ArrayList<SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel(R.drawable.funceng_depart, ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.bn_college6, ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.bn_college7, ScaleTypes.CENTER_CROP));
        image.setImageList(imageList);

        text.setOnClickListener(v -> {
            load_content(text, linelayout, text1);
        });
        text1.setOnClickListener(v -> {
            load_content(text1, linearLayout, text);
        });
        return root;
    }
    boolean slide=true;
    private void load_content(View textview , View text_view ,View another_textview) {
        if (slide) {
            slide = false;
            textview.animate().scaleX(0.2f).setDuration(10).start();
            textview.animate().translationX(-text.getWidth() / 2).setDuration(400).start();
            another_textview.setVisibility(View.GONE);
            text_view.setVisibility(View.VISIBLE);
            text_view.setTranslationX(text_view.getWidth());
            text_view.animate().translationX(140).setDuration(400).start();
        } else {
            slide = true;
            textview.animate().scaleX(1f).setDuration(400).start();
            textview.animate().translationX(0).setDuration(400).start();
            another_textview.setVisibility(View.VISIBLE);
            text_view.setVisibility(View.GONE);
        }
    }
}