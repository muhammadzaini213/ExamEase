package com.mzopensource.examease.main;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mzopensource.examease.R;
import com.mzopensource.examease.login.LoginActivity;
import com.mzopensource.examease.userdata.UserData;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivityAdmin extends AppCompatActivity {

    EditText copy_school_id, school_name_input, admin_name_input, uid_input, date_limit;
    private FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_profile_admin);

        SharedPreferences sharedPreferences = getSharedPreferences("EXAM_EASE", MODE_PRIVATE);

        FirebaseAuth auth = FirebaseAuth.getInstance();


        user = UserData.getUserData();
        String uid = user.getUid();

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        DocumentReference docRef = db.collection("users").document(uid);

        //handle exit button click
        findViewById(R.id.exit_profile).setOnClickListener(view -> {
            startActivity(new Intent(ProfileActivityAdmin.this, MainActivity.class));
            finish();
        });

        //handle logout button click
        findViewById(R.id.logout_btn).setOnClickListener(view -> {
            sharedPreferences.edit().putString("Username", "").apply();
            sharedPreferences.edit().putString("Password", "").apply();
            auth.signOut();

            startActivity(new Intent(ProfileActivityAdmin.this, LoginActivity.class));
            finish();
        });

        ConstraintLayout container = findViewById(R.id.container);
        school_name_input = container.findViewById(R.id.school_name_input);
        admin_name_input = container.findViewById(R.id.admin_name_input);

        uid_input = container.findViewById(R.id.uid_input);
        registerForContextMenu(uid_input); // register context menu for copy uid

        copy_school_id = container.findViewById(R.id.copy_school_id);
        registerForContextMenu(copy_school_id); // register context menu for copy school id


        Button save_btn = container.findViewById(R.id.save_btn);
        save_btn.setOnClickListener(view -> {
            String school_name = school_name_input.getText().toString();
            String admin_name = admin_name_input.getText().toString();

            Map<String, Object> userData = new HashMap<>();
            userData.put("School Name", school_name); // set school name
            userData.put("Name", admin_name); // set admin name
            //TODO: Create data with school_id
            
            // update user data in Firestore without affecting other fields
            docRef.update(userData)
                    .addOnSuccessListener(aVoid -> Toast.makeText(ProfileActivityAdmin.this, "Profile Updated", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> {
                    });

        });

        readUserData(); //read user data from firestore
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.copy_menu, menu); // get the menu
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                String textToCopy = copy_school_id.getText().toString();
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Copied Text", textToCopy);
                clipboard.setPrimaryClip(clip); // copy text to clipboard
                Toast.makeText(this, "Text copied to clipboard", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void readUserData() {
        String uid = user.getUid();

        // Reference to the user's document
        DocumentReference docRef = db.collection("users").document(uid);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    // Document exists, handle the retrieved data

                    String school_id = document.getString("School ID");
                    copy_school_id.setText(school_id);

                    //get school name
                    String school_name = document.getString("School Name");
                    school_name_input.setText(school_name);

                    //get admin name
                    String admin_name = document.getString("Name");
                    admin_name_input.setText(admin_name);

                    //get uid
                    uid_input.setText(uid);


                    // set the title info

                } else {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
