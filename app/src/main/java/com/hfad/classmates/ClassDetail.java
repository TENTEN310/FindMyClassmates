package com.hfad.classmates;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hfad.classmates.objectClasses.Classes;
import com.hfad.classmates.objectClasses.Comments;
import com.hfad.classmates.objectClasses.Materials;
import com.hfad.classmates.objectClasses.ProfileInfo;
import com.hfad.classmates.util.FirebaseUtil;
import com.hfad.classmates.util.ShowMaterialsResult;
import com.hfad.classmates.util.ShowReviewResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class ClassDetail extends AppCompatActivity {
    RecyclerView recyclerView;
    ShowReviewResult reviewHistoryResult;
    TextView classFullName, professor, classAbv, description;
    Classes classes;
    ImageButton back, add, remove;
    Button roster, comment, uploadMaterial;
    String storagePath;
    Query db;
    private Uri fileUri;
    private RecyclerView materialsList;
    private ShowMaterialsResult materialsAdapter;

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
        comment = findViewById(R.id.commentbtn);
        add = findViewById(R.id.imageButton2);
        remove = findViewById(R.id.imageButton4);
        remove.setVisibility(Button.GONE);
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

        // get our current class and storage path
        String classAbv = classes.getAbv();
        storagePath = "materials/" + classAbv;

        // add a file to the class materials
        uploadMaterial.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*"); // all files are allowed
            startActivityForResult(intent, 1);
        });

        // list our materials out
        materialsList = findViewById(R.id.materialsList);
        materialsList.setLayoutManager(new LinearLayoutManager(this));
        materialsAdapter = new ShowMaterialsResult(this, new ArrayList<>());
        materialsList.setAdapter(materialsAdapter);

        // update the materials list
        retrieveMaterialsFromFirebase(storagePath, findViewById(R.id.emptyMaterial));

        // check if there are any reviews if there are don't show first to add review message
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String path = "/departments/" + classes.getAbv().substring(0, 4) + "/classes/" + classes.getAbv()+ "/reviews";
        CollectionReference postsCollection = db.collection(path);
        postsCollection.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        TextView emptyReview = findViewById(R.id.emptyReview);
                        emptyReview.setVisibility(View.GONE);
                    }
                });
        comment.setOnClickListener(v -> {
            // after clicking on comment button show review_popup
            showReviewPop();
        });
        showReview();
        // update the rating when we open up the class (refreshes)
        calculateOverallRatingSumForAllUsersFromFirestore();
    }

    // update our materials list
    private void retrieveMaterialsFromFirebase(String storagePath, TextView emptyMaterial) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child(storagePath);
        List<Materials> materialsListFromFirebase = new ArrayList<>();

        storageRef.listAll()
                .addOnSuccessListener(listResult -> {
                    int totalItems = listResult.getItems().size();
                    AtomicInteger itemsProcessed = new AtomicInteger();

                    for (StorageReference item : listResult.getItems()) {
                        item.getMetadata()
                                .addOnSuccessListener(storageMetadata -> {
                                    // get our name and file type and add it to our list
                                    String fileType = getFileType(storageMetadata.getContentType());
                                    materialsListFromFirebase.add(new Materials(item.getName(), fileType, item.getPath()));
                                    itemsProcessed.getAndIncrement();

                                    // check if all items have been processed
                                    if (itemsProcessed.get() == totalItems) {
                                        // update our list
                                        materialsAdapter.setMaterialsList(materialsListFromFirebase);
                                        materialsAdapter.setEmptyView(emptyMaterial);
                                    }
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(getApplicationContext(), "Failed to retrieve metadata: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    itemsProcessed.getAndIncrement();
                                });
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(), "Failed to retrieve materials: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    // get the file type based on the material name
    private String getFileType(String contentType) {
        if (contentType != null) {
            String[] parts = contentType.split("/");
            if (parts.length > 1) {
                return parts[parts.length-1].toUpperCase();
            }
        }

        // if there is no associated file type
        return "Unknown";
    }

    // add the file to the database storage
    private void uploadFileWithCustomName(Uri fileUri, String customFileName, TextView emptyMaterial) {
        if (fileUri != null) {
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

                        // update the materials list
                        retrieveMaterialsFromFirebase(storagePath, emptyMaterial);
                    })
                    // upload failure
                    .addOnFailureListener(e -> {
                        Toast.makeText(getApplicationContext(), "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(getApplicationContext(), "Please select a file", Toast.LENGTH_SHORT).show();
        }
    }
    // give the file a custom name
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
                    uploadFileWithCustomName(fileUri, customFileName, findViewById(R.id.emptyMaterial));
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter a custom file name", Toast.LENGTH_SHORT).show();
                }
            });
            alertDialog.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
            alertDialog.show();
        }
    }

    public void showReview() {
        recyclerView = findViewById(R.id.reviewRecycleView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance().collection("departments").document(FirebaseUtil.GetDeptFromClassID(classes.getAbv()))
                .collection("classes").document(classes.getAbv()).collection("reviews")
                .orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Comments> options = new FirestoreRecyclerOptions.Builder<Comments>()
                .setQuery(db, Comments.class).build();

        reviewHistoryResult = new ShowReviewResult(options, ClassDetail.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(ClassDetail.this));
        recyclerView.setAdapter(reviewHistoryResult);
        reviewHistoryResult.startListening();
    }


    @Override
    public void onStart() {
        super.onStart();
        if(reviewHistoryResult!=null)
            reviewHistoryResult.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(reviewHistoryResult!=null)
            reviewHistoryResult.stopListening();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(reviewHistoryResult!=null)
            reviewHistoryResult.notifyDataSetChanged();
    }
    private void addComments(Classes currentClass, String comment, double overall, double workload, boolean attendance, boolean late){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Timestamp timestamp = Timestamp.now();

        CollectionReference usersCollection = db.collection("users");
        usersCollection.whereEqualTo("userID",userId).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentSnapshot userDocument = queryDocumentSnapshots.getDocuments().get(0);
                        String category = currentClass.getAbv().substring(0, 4);
                        String path = "/departments/" + category + "/classes/" + currentClass.getAbv()+ "/reviews";
                        CollectionReference postsCollection = db.collection(path);
                        Comments finalComment = new Comments(comment, userDocument.getString("username"), timestamp, currentClass.getAbv(), overall, workload, attendance, late, 0, 0, userId);
                        postsCollection.add(finalComment)
                                .addOnSuccessListener(documentReference -> {
                                    Toast.makeText(getApplicationContext(), "Review submitted", Toast.LENGTH_LONG).show();
                                     // update the review value
                                    calculateOverallRatingSumForAllUsersFromFirestore();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                                });
                    }
                });
    }

    // calculate the total rating from all of the reviews on the class
    private void calculateOverallRatingSumForAllUsersFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String path = "/departments/" + classes.getAbv().substring(0, 4) + "/classes/" + classes.getAbv() + "/reviews";
        CollectionReference reviewsCollection = db.collection(path);

        reviewsCollection.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    double overallRatingSum = 0.0;
                    int numberOfReviews = 0;

                    // calculate all the reviews from each user
                    for (QueryDocumentSnapshot reviewSnapshot : queryDocumentSnapshots) {
                        Double overallRating = reviewSnapshot.getDouble("overall");

                        if (overallRating != null) {
                            overallRatingSum += overallRating;
                            numberOfReviews++;
                        }
                    }

                    // if there are reviews, update the rating
                    if (numberOfReviews > 0) {
                        double averageRating = overallRatingSum / numberOfReviews;

                        updateClassRatingInFirebase(averageRating);
                    }
                })
                .addOnFailureListener(e -> {
                    System.out.println("Error: " + e.getMessage());
                });
    }

    private void updateClassRatingInFirebase(double newRating) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String path = "/departments/" + classes.getAbv().substring(0, 4) + "/classes/" + classes.getAbv();
        DocumentReference classRef = db.document(path);

        // update the rating for our class object
        classes.setRating(Math.floor(newRating * 100) / 100);

        // update the rating in our firebase database
        classRef.update("rating", newRating)
                .addOnSuccessListener(aVoid -> {
                    System.out.println("Class rating updated successfully!");
                })
                .addOnFailureListener(e -> {
                    System.out.println("Error updating class rating: " + e.getMessage());
                });
    }

    private void showReviewPop() {
        // Inflate the custom popup layout
        View popupView = getLayoutInflater().inflate(R.layout.review_popup, null);

        // Create a Dialog to display the popup
        Dialog dialog = new Dialog(this);
        dialog.setContentView(popupView);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // Show the dialog
        dialog.show();
        // Capture the user's input when needed (e.g., when they click a "Submit" button)
        Button submitButton = popupView.findViewById(R.id.Message);
        submitButton.setOnClickListener(view -> {
            // Get the user's comment input
            EditText review = popupView.findViewById(R.id.Review);
            String comment = review.getText().toString();
            RatingBar overall = popupView.findViewById(R.id.overallRating);
            double overallRating = overall.getRating();
            RatingBar workload = popupView.findViewById(R.id.workloadRating);
            double workloadRating = workload.getRating();
            CheckBox attendance = popupView.findViewById(R.id.attendanceSwitch);
            boolean attendanceRating = attendance.isChecked();
            CheckBox late = popupView.findViewById(R.id.lateSwitch);
            boolean lateRating = late.isChecked();
            if (comment.isEmpty()) {
                review.setError("Comment can't be empty");
            } else {
                // Add the comment to the database
                addComments(classes, comment, overallRating, workloadRating, attendanceRating, lateRating);
                // Close the popup
                dialog.dismiss();
                TextView emptyReview = findViewById(R.id.emptyReview);
                emptyReview.setVisibility(View.GONE);
            }
        });
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