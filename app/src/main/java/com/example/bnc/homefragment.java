package com.example.bnc;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.Window;

import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;

public class homefragment extends Fragment {

    public homefragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_homefragment, container, false);

        // Get references to UI components
        ImageSlider imageSlider = rootView.findViewById(R.id.image_slider);

        // Create image list for the slider
        ArrayList<SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel(R.drawable.bn_college, ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.bn_college2, ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.bn_college1, ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.bn_college9, ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.bn_college13, ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.bn_college3, ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(imageList);


        return rootView;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //checking whether theme is in dark mode or light
        int mode = getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;

        //if it is in dark mode color will change
        if(mode==android.content.res.Configuration.UI_MODE_NIGHT_YES){
            requireActivity().getWindow().getDecorView().setBackgroundColor(Color.parseColor("#D0D0E3"));
        }
    }
}
