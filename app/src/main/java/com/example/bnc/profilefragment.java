package com.example.bnc;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link profilefragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class profilefragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public profilefragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profilefragment.
     */
    // TODO: Rename and change types and number of parameters
    public static profilefragment newInstance(String param1, String param2) {
        profilefragment fragment = new profilefragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    Button show;
    TextView roll_input, roll_name, roll_registeration, roll_dob;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profilefragment, container, false);

        show = v.findViewById(R.id.show);
        roll_input = v.findViewById(R.id.roll_input);
        roll_name = v.findViewById(R.id.roll_name);
        roll_registeration = v.findViewById(R.id.roll_registeration);
        roll_dob = v.findViewById(R.id.roll_dob);

        show.setOnClickListener(v1 -> {
            String roll = roll_input.getText().toString();
            // Check if the first character is '0'
            if (roll.startsWith("0")) {
                // Remove the first character
                roll = roll.substring(1);
            }

            //  Feching data from firestore using roll
            db.collection("students")
                    .whereEqualTo("ROLL", roll)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
//                                    Log.d("name", document.getId() + " => " + document.getString("NAME") + " => " + document.getData());
                                    roll_name.setText(document.getString("NAME"));
                                    roll_registeration.setText(document.getString("REGISTRATION"));
                                    roll_dob.setText(document.getString("DOB"));
                                }
                            } else {
                                Log.d("DBerror", "Error getting documents: ", task.getException());
                            }
                        }
                    });

        });


        return v;
    }
}