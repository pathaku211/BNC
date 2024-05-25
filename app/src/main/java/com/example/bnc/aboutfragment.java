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
    TextView text, text1, text2, text3;

    public aboutfragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_aboutfragment, container, false);

        text = root.findViewById(R.id.text1);
        text1 = root.findViewById(R.id.text2);
        text2 = root.findViewById(R.id.text3);
        text3 = root.findViewById(R.id.text4);

        text.setOnClickListener(v -> {
            link("https://www.linkedin.com/in/umesh-pathak-07a73b192?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app");
        });

        text1.setOnClickListener(v -> {
            link("https://www.linkedin.com/in/karan-krishna-8185552a1?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app");
        });

        text2.setOnClickListener(v -> {
            link("https://www.linkedin.com/in/ashish-kumar-579b621a4?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app");
        });

        text3.setOnClickListener(v -> {
            link("https://www.linkedin.com/in/pragya-rai-2a696a309?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app");
        });

        return root;
    }

    private void link(String linkedinurl) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(linkedinurl));
        startActivity(intent);
    }
}