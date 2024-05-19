package com.mzopensource.examease.main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mzopensource.examease.R;
import com.mzopensource.examease.userdata.UserData;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class MainTitle {
    private FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseUser user;
    Context context;

    TextView main_title_info_school, main_title_info_identity;

    private static String generateRandomNumericString() {
        StringBuilder sb = new StringBuilder(20);
        for (int i = 0; i < 20; i++) {
            SecureRandom random = new SecureRandom();
            int randomDigit = random.nextInt(10); // Generate a random digit between 0 and 9
            sb.append(randomDigit);
        }
        return sb.toString();
    }

    private void inputSchoolNameDialog(DocumentReference docRef) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_enter_school_name);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        final EditText input = dialog.findViewById(R.id.school_input);

        Button okButton = dialog.findViewById(R.id.enter_button);
        okButton.setOnClickListener(v -> {
            String email = user.getEmail();
            String school_id = generateRandomNumericString();
            String inputText = input.getText().toString();
            Map<String, Object> userData = new HashMap<>();
            userData.put("Email", email); //generate email
            userData.put("School Name", inputText); // generate school name
            userData.put("Name", "Admin"); // generate name for admin
            userData.put("School ID", school_id); // generate random digit for school ID

            // if exists add user data to database
            docRef.set(userData)
                    .addOnSuccessListener(aVoid -> readUserData())
                    .addOnFailureListener(e -> {
                    });

            setSchoolData(school_id);
            dialog.dismiss();
        });
        dialog.show();
    }

    private void setSchoolData(String school_id){
        //create database for school ID
        DocumentReference schoolDocRef = FirebaseFirestore.getInstance().collection("school_id").document(school_id);
        Map<String, Object> schoolData = new HashMap<>();
        schoolData.put("Hello World", "Why are you here? Get out please, or i call the police"); // generate website url
        // if exists add user data to database
        schoolDocRef.set(schoolData)
                .addOnSuccessListener(aVoid -> readUserData())
                .addOnFailureListener(e -> {
                });
    }

    public void init(ConstraintLayout main_title, Context context, Activity activity) {
        this.context = context;
        TextView main_title_titletext = main_title.findViewById(R.id.main_title_titletext);
        ImageView main_title_profile = main_title.findViewById(R.id.main_title_profile);


        LinearLayout main_title_info = main_title.findViewById(R.id.main_title_info);
        main_title_info_school = main_title_info.findViewById(R.id.main_title_info_school);
        main_title_info_identity = main_title_info.findViewById(R.id.main_title_info_identity);

        main_title_info_school.setText("Loading...");
        main_title_info_identity.setText("\nLoading...\n\n");

        user = UserData.getUserData();
        String uid = user.getUid();

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        DocumentReference usersDocRef = db.collection("users").document(uid);
        usersDocRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    // Document exists, read the data
                    readUserData();
                } else {
                    // Document does not exist, create a new one
                    inputSchoolNameDialog(usersDocRef);
                }
            } else {
                Toast.makeText(context, "Please verify your email to access this feature.", Toast.LENGTH_SHORT).show();

            }
        });

        //handle profile button
        main_title_profile.setOnClickListener(view -> {
            context.startActivity(new Intent(context, ProfileActivityAdmin.class));
            activity.finish();
        });
    }

    private void readUserData() {
        String uid = user.getUid();

        // Reference to the user's document
        DocumentReference userdDocRef = db.collection("users").document(uid);
        userdDocRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    // Document exists, handle the retrieved data
                    String schoolId = document.getString("School ID");
                    String schoolName = document.getString("School Name");

                    // set the title info
                    main_title_info_school.setText(schoolName);
                    main_title_info_identity.setText("School ID: " + schoolId);
                    main_title_info_identity.append("\n");
                    main_title_info_identity.append("Name: " + schoolName);
                    main_title_info_identity.append("\n");
                    main_title_info_identity.append("Status: Admin");
                    main_title_info_identity.append("\n");
                    main_title_info_identity.append("Account: " + "Premium");

                } else {
                    Toast.makeText(context, "No user data found.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Failed to retrieve data.", Toast.LENGTH_SHORT).show();
            }
        });
    }


}

