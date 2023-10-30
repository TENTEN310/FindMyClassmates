package com.hfad.classmates;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.hfad.classmates.objectClasses.Classes;
import com.hfad.classmates.regLogInActivity.Login;
import com.hfad.classmates.util.FirebaseUtil;
import com.hfad.classmates.util.ShowClassResult;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class ProfileFragment extends Fragment {
    Uri selectedImageUri = null;
    RecyclerView recyclerView;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //initialize the rootView
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        //initialize the variables
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        TextView nameText = rootView.findViewById(R.id.usernameText);
        TextView emailText = rootView.findViewById(R.id.emailText);
        recyclerView = rootView.findViewById(R.id.itemsList);

        ImageView profilePicture = rootView.findViewById(R.id.profile_image);

        TextView majorYearText = rootView.findViewById(R.id.majorYearText);
        Button logout = rootView.findViewById(R.id.logout);

        //set the name/major/year
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersCollection = db.collection("users");

        usersCollection.whereEqualTo("userID", user.getUid()).get()
            .addOnSuccessListener(queryDocumentSnapshots -> {
                if (!queryDocumentSnapshots.isEmpty()) {
                    DocumentSnapshot userDocument = queryDocumentSnapshots.getDocuments().get(0);
                    nameText.setText(userDocument.getString("username"));
                    majorYearText.setText(userDocument.getString("major") + " " + userDocument.getString("year"));
                }
            });

        //set the email
        emailText.setText(user.getEmail());

        //set original profile picture
        FirebaseUtil.getProfilePic(user.getUid()).getDownloadUrl()
            .addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Uri uri  = task.getResult();
                    Glide.
                        with(this).
                        load(uri).
                        apply(RequestOptions.circleCropTransform()).
                        into(profilePicture);
                }
            });

        //update username
        ImageView changeUsername = rootView.findViewById(R.id.changeUsername);
        changeUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Change Username:");

                final EditText usernameEditText = new EditText(getContext());
                usernameEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(usernameEditText);

                // save the updates
                builder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newUsername = usernameEditText.getText().toString().toLowerCase();
                        DocumentReference usersDocRef = db.collection("users").document(user.getUid());

                        usersDocRef.update("username", newUsername)
                            .addOnSuccessListener(new OnSuccessListener<Void>() { //success to change
                                @Override
                                public void onSuccess(Void aVoid) {
                                    nameText.setText(newUsername);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() { //fail to change, maybe duplicate?
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                }
                            });
                    }
                });

                // cancel the updates
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

        //update profile picture
        FloatingActionButton changePicture = rootView.findViewById(R.id.profilechangebtn);
        ActivityResultLauncher<Intent> imagePicker;
        imagePicker = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        if(data!=null && data.getData()!=null){
                            selectedImageUri = data.getData();
                            FirebaseUtil.getUserStorage().putFile(selectedImageUri);
                            Glide.with(this).load(selectedImageUri).apply(RequestOptions.circleCropTransform()).into(profilePicture);
                        }
                    }
                }
        );

        changePicture.setOnClickListener((v)->{
            ImagePicker.with(this).cropSquare().compress(512).maxResultSize(512,512)
                    .createIntent(new Function1<Intent, Unit>() {
                        @Override
                        public Unit invoke(Intent intent) {
                            imagePicker.launch(intent);
                            return null;
                        }
                    });
        });


        //log out feature
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(),Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        showClassUser();

        return rootView;
    }

    public void showClassUser() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Query classesQuery = db.collection("users").document(FirebaseUtil.getUserID()).collection("classList");
        FirestoreRecyclerOptions<Classes> options = new FirestoreRecyclerOptions.Builder<Classes>()
                .setQuery(classesQuery, Classes.class)
                .build();
        ShowClassResult ClassHistoryResult = new ShowClassResult(options, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(ClassHistoryResult);
        ClassHistoryResult.startListening();
    }
}