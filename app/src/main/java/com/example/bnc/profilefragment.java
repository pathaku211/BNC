package com.example.bnc;

import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.content.ClipboardManager;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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
    TextView roll_input, roll_name, roll_registeration, roll_dob, roll_aadhaar, roll_mob, roll_fname, roll_mname, roll_gender, roll_address, roll_sem1, roll_sem2, roll_sem3, roll_sem4, roll_sem5, roll_sem6;
    LinearLayout roll_details;
    ProgressBar progressBar;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profilefragment, container, false);

        show = v.findViewById(R.id.show);
        roll_details = v.findViewById(R.id.roll_details);
        roll_input = v.findViewById(R.id.roll_input);
        roll_name = v.findViewById(R.id.roll_name);
        roll_registeration = v.findViewById(R.id.roll_registeration);
        roll_aadhaar = v.findViewById(R.id.roll_aadhaar);
        roll_mob = v.findViewById(R.id.roll_mob);
        roll_sem1 = v.findViewById(R.id.roll_sem1);
        roll_sem2 = v.findViewById(R.id.roll_sem2);
        roll_sem3 = v.findViewById(R.id.roll_sem3);
        roll_sem4 = v.findViewById(R.id.roll_sem4);
        roll_sem5 = v.findViewById(R.id.roll_sem5);
        roll_sem6 = v.findViewById(R.id.roll_sem6);
        roll_fname = v.findViewById(R.id.roll_fname);
        roll_mname = v.findViewById(R.id.roll_mname);
        roll_gender = v.findViewById(R.id.roll_gender);
        roll_dob = v.findViewById(R.id.roll_dob);
        roll_address = v.findViewById(R.id.roll_address);
        progressBar = v.findViewById(R.id.progressBar);


        show.setOnClickListener(v1 -> {
            progressBar.setVisibility(View.VISIBLE); // Show the progress bar when fetching begins
            String roll = roll_input.getText().toString();
            // Check if the first character is '0'
            if (roll.startsWith("0")) {
                // Remove the first character
                roll = roll.substring(1);
            }

            // Add timestamp and roll to "fetchrecords" collection
            Map<String, Object> recordData = new HashMap<>();
            recordData.put("uid", currentUser.getUid());
            recordData.put("user_email", currentUser.getEmail());
            recordData.put("searched_roll", roll);
            recordData.put("timestamp", FieldValue.serverTimestamp());

            // Inside your code block where you set the document ID
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
            String documentId = sdf.format(new Date());

            FirebaseFirestore.getInstance().collection("fetchrecords")
                    .document(documentId) // Use formatted timestamp as document ID
                    .set(recordData)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("FetchRecords", "Record added with timestamp as ID");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("FetchRecords", "Error adding record", e);
                        }
                    });


            //  Feching data from firestore using roll
            db.collection("students")
                    .whereEqualTo("ROLL", roll)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            progressBar.setVisibility(View.GONE); // Hide the progress bar when fetching completes
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
//                                    Log.d("name", document.getId() + " => " + document.getString("NAME") + " => " + document.getData());
                                    roll_details.setVisibility(View.VISIBLE);
                                    roll_name.setText(document.getString("NAME"));
                                    roll_registeration.setText(document.getString("REGISTRATION"));
                                    String aadhaarNumber = document.getString("AADHAAR");
                                    // Add spaces between every four digits
                                    StringBuilder formattedAadhaar = new StringBuilder();
                                    for (int i = 0; i < aadhaarNumber.length(); i++) {
                                        formattedAadhaar.append(aadhaarNumber.charAt(i));
                                        if ((i + 1) % 4 == 0 && (i + 1) != aadhaarNumber.length()) {
                                            formattedAadhaar.append(" ");
                                        }
                                    }
                                    roll_aadhaar.setText(formattedAadhaar.toString());
                                    roll_mob.setText(document.getString("MOB"));
                                    roll_sem1.setText(document.getString("TERM1"));
                                    roll_sem2.setText(document.getString("TERM2"));
                                    roll_sem3.setText(document.getString("TERM3"));
                                    roll_sem4.setText(document.getString("TERM4"));
                                    roll_sem5.setText(document.getString("TERM5"));
                                    roll_sem6.setText(document.getString("TERM6"));
                                    roll_fname.setText(document.getString("FNAME"));
                                    roll_mname.setText(document.getString("MNAME"));
                                    roll_gender.setText(document.getString("GENDER"));
                                    String dobString = document.getString("DOB");
                                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd"); // Assuming the original format is YYYY-MM-DD
                                    SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");

                                    try {
                                        Date dobDate = inputFormat.parse(dobString);
                                        String formattedDob = outputFormat.format(dobDate);
                                        roll_dob.setText(formattedDob);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                        // Handle parsing exception
                                    }
                                    roll_address.setText(document.getString("ADDRESS"));

                                    setCopyTextView(roll_registeration);
                                    setCopyTextView(roll_aadhaar);
                                    setCopyTextView(roll_mob);
                                    setCopyTextView(roll_sem1);
                                    setCopyTextView(roll_sem2);
                                    setCopyTextView(roll_sem3);
                                    setCopyTextView(roll_sem4);
                                    setCopyTextView(roll_sem5);
                                    setCopyTextView(roll_sem6);
                                    setCopyTextView(roll_address);

                                }
                            } else {
                                Toast.makeText(getContext(), "Fail to Fetch data", Toast.LENGTH_SHORT).show();
                                Log.d("DBerror", "Error getting documents: ", task.getException());
                            }
                        }
                    });

        });


        return v;
    }

    // Method to set compound drawables for TextView
    private void setCopyTextView(TextView textView) {
        textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_content_copy, 0);
        textView.setOnClickListener(copyClickListener);
    }

    // OnClickListener for copy icons
    View.OnClickListener copyClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView textView = (TextView) v;
            String textToCopy = textView.getText().toString();
            ClipboardManager clipboard = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Copied Text", textToCopy);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getContext(), textToCopy + " copied to clipboard", Toast.LENGTH_SHORT).show();
        }
    };


}