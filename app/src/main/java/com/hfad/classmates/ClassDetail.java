package com.hfad.classmates;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hfad.classmates.objectClasses.Classes;
import com.hfad.classmates.objectClasses.ProfileInfo;
import com.hfad.classmates.util.FirebaseUtil;

public class ClassDetail extends AppCompatActivity {
    TextView classFullName, professor, classAbv, description, empty1, empty2;
    Classes classes;
    ImageButton back, add, remove;
    Button roster, uploadMaterial;
    private Uri fileUri;

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
        uploadMaterial = findViewById(R.id.uploadMaterialbtn);
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

        // add a file to the class materials
        uploadMaterial.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*"); // all files are allowed
            startActivityForResult(intent, 1);
        });
    }

    // add the file to the database storage
    private void uploadFileWithCustomName(Uri fileUri, String customFileName) {
        if (fileUri != null) {
            String fileName = classes.getAbv() + "/" + customFileName;

            // create the location for this file
            StorageReference storageRef = FirebaseStorage.getInstance().getReference()
                    .child("materials")
                    .child(classes.getAbv())
                    .child(customFileName);

            // store the file here
            storageRef.putFile(fileUri)
                    // upload success
                    .addOnSuccessListener(taskSnapshot -> {
                        Toast.makeText(getApplicationContext(), "File uploaded successfully", Toast.LENGTH_SHORT).show();
                    })
                    // upload failure
                    .addOnFailureListener(e -> {
                        Toast.makeText(getApplicationContext(), "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(getApplicationContext(), "Please select a file", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            fileUri = data.getData();

            // set the name for the file
            final EditText customFileNameEditText = new EditText(this);
            customFileNameEditText.setHint("Enter file name");

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Custom File Name");
            alertDialog.setView(customFileNameEditText);
            alertDialog.setPositiveButton("Upload", (dialog, which) -> {
                String customFileName = customFileNameEditText.getText().toString().trim();
                if (!customFileName.isEmpty() && fileUri != null) {
                    uploadFileWithCustomName(fileUri, customFileName);
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter a custom file name", Toast.LENGTH_SHORT).show();
                }
            });
            alertDialog.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
            alertDialog.show();
        }
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