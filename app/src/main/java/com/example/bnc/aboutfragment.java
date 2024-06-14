package com.example.bnc;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class aboutfragment extends Fragment {

    View root;
    TextView linkedin4, linkedin1, linkedin2, linkedin3, github1, github2, github3, github4;

    public aboutfragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_aboutfragment, container, false);


        linkedin1 = root.findViewById(R.id.linkedin1);
        linkedin2 = root.findViewById(R.id.linkedin2);
        linkedin3 = root.findViewById(R.id.linkedin3);
        linkedin4 = root.findViewById(R.id.linkedin4);
        github1 = root.findViewById(R.id.github1);
        github2 = root.findViewById(R.id.github2);
        github3 = root.findViewById(R.id.github3);
        github4 = root.findViewById(R.id.github4);


        linkedin1.setOnClickListener(v -> {
            link("https://www.linkedin.com/in/karan-krishna-8185552a1?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app");
        });

        linkedin2.setOnClickListener(v -> {
            link("https://www.linkedin.com/in/ashish-kumar-579b621a4?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app");
        });

        linkedin3.setOnClickListener(v -> {
            link("https://www.linkedin.com/in/umesh-pathak-07a73b192?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app");
        });

        linkedin4.setOnClickListener(v -> {
            link("https://www.linkedin.com/in/pragya-rai-2a696a309?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app");
        });

        github1.setOnClickListener(v -> {
            link("https://github.com/KaranKrishna1");
        });

        github2.setOnClickListener(v -> {
            link("https://github.com/ashishkumar5338");
        });

        github3.setOnClickListener(v -> {
            link("https://github.com/pathaku211/BNC");
        });

        github4.setOnClickListener(v -> {
            link("https://github.com/prgyya");
        });

        return root;
    }

    private void link(String linkedinurl) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(linkedinurl));
        startActivity(intent);
    }
}
