package com.hfad.classmates;


import static androidx.fragment.app.FragmentManager.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Transaction;
import com.hfad.classmates.objectClasses.Classes;
import com.hfad.classmates.objectClasses.ProfileInfo;
import com.hfad.classmates.util.FirebaseUtil;

import java.util.HashMap;
import java.util.Map;


public class ClassDetail extends AppCompatActivity {
    TextView classFullName, professor, classAbv, description, empty1, empty2;
    Classes classes;

    ImageButton back, add, remove;

    Button roster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_detail_activity);
        classes = FirebaseUtil.getClassesModelFromIntent(getIntent());
        classFullName = findViewById(R.id.fullname);
        professor = findViewById(R.id.Professor_name);
        classAbv = findViewById(R.id.class_name);
        back = findViewById(R.id.back_btn);
        roster = findViewById(R.id.button);
        add = findViewById(R.id.imageButton2);
        remove = findViewById(R.id.imageButton4);
        remove.setVisibility(Button.GONE);
        empty1 = findViewById(R.id.empty_view1);
        empty2 = findViewById(R.id.empty_view2);
        description = findViewById(R.id.description_class);
        classFullName.setText(classes.getName() + "\n(" + classes.getUnits() + " units)");
        professor.setText(classes.getProfessor());
        classAbv.setText(classes.getAbv());
        description.setText(classes.getDescription());
        back.setOnClickListener(v -> finish());
        //add class to user's class list

        add.setOnClickListener(v -> addClassToUserAndUserToClass(classes, add, remove));
        roster.setOnClickListener(
                v -> {
                    Intent intent = new Intent(getApplicationContext(), Roster.class);
                    intent.putExtra("classID", classes.getAbv());
                    startActivity(intent);
                }
        );
        checkIfUserIsInClass(classes.getAbv(), FirebaseUtil.getUserID(), add, remove);
        remove.setOnClickListener(v -> removeClassFromUserAndUserFromClass(classes, add, remove));

    }

    private void checkIfUserIsInClass(String classID, String userID, ImageButton add, ImageButton remove) {
        DocumentReference classUserRef = FirebaseFirestore.getInstance()
                .collection("departments")
                .document(FirebaseUtil.GetDeptFromClassID(classID))
                .collection("classes")
                .document(classID)
                .collection("userList")
                .document(userID);

        classUserRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    // User is already in the class
                    add.setVisibility(Button.GONE);
                    remove.setVisibility(Button.VISIBLE);
                } else {
                    // User is not in the class
                    add.setVisibility(Button.VISIBLE);
                    remove.setVisibility(Button.GONE);
                }
            }
        });
    }


    private void addClassToUserAndUserToClass(Classes currentClass, ImageButton add, ImageButton remove) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Reference to the user's class list collection
        DocumentReference userClassRef = db.collection("users")
                .document(userId)
                .collection("classList")
                .document(currentClass.getAbv());

        // Reference to the class's user list collection
        DocumentReference classUserRef = db.collection("departments")
                .document(FirebaseUtil.GetDeptFromClassID(currentClass.getAbv()))
                .collection("classes")
                .document(currentClass.getAbv())
                .collection("userList")
                .document(userId);

        // Retrieve the user's profile details first
        db.collection("users").document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        ProfileInfo userProfile = documentSnapshot.toObject(ProfileInfo.class);

                        // Run transaction with the userProfile data
                        db.runTransaction((Transaction.Function<Void>) transaction -> {
                                    // Check if the class already exists in user's list
                                    DocumentSnapshot userClassSnapshot = transaction.get(userClassRef);
                                    if (userClassSnapshot.exists()) {
                                        try {
                                            throw new Exception("Class already in your list!");
                                        } catch (Exception e) {
                                            throw new RuntimeException(e);
                                        }
                                    }

                                    // Add class to user's list
                                    transaction.set(userClassRef, currentClass);

                                    // Add user to class's user list
                                    if (userProfile != null) {
                                        transaction.set(classUserRef, userProfile);
                                    }

                                    return null;
                                })
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(getApplicationContext(), "Class added successfully!", Toast.LENGTH_SHORT).show();
                                    add.setVisibility(Button.GONE);
                                    remove.setVisibility(Button.VISIBLE);
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                });

                    } else {
                        Toast.makeText(getApplicationContext(), "User profile not found!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(), "Error fetching user profile: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }


    private void removeClassFromUserAndUserFromClass(Classes currentClass, ImageButton add, ImageButton remove) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Reference to the user's class list collection
        DocumentReference userClassRef = db.collection("users")
                .document(userId)
                .collection("classList")
                .document(currentClass.getAbv());

        // Reference to the class's user list collection
        DocumentReference classUserRef = db.collection("departments")
                .document(FirebaseUtil.GetDeptFromClassID(currentClass.getAbv()))
                .collection("classes")
                .document(currentClass.getAbv())
                .collection("userList")
                .document(userId);

        // Run transaction
        db.runTransaction((Transaction.Function<Void>) transaction -> {
                    // Check if the class exists in the user's list
                    DocumentSnapshot userClassSnapshot = transaction.get(userClassRef);
                    if (!userClassSnapshot.exists()) {
                        try {
                            throw new Exception("Class not found in your list!");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }

                    // Remove class from user's list
                    transaction.delete(userClassRef);

                    // Remove user from class's user list
                    transaction.delete(classUserRef);

                    return null;
                })
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getApplicationContext(), "Class removed successfully!", Toast.LENGTH_SHORT).show();
                    add.setVisibility(Button.VISIBLE);
                    remove.setVisibility(Button.GONE);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }



}

