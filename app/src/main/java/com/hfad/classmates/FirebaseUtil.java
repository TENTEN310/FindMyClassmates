package com.hfad.classmates;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseUtil {
    public static String getUserID(){
        return FirebaseAuth.getInstance().getUid();
    }
    public static DocumentReference getUserDetails(){
        return FirebaseFirestore.getInstance().collection("users").document(getUserID());
    }

    public static StorageReference getUserStorage(){
        return FirebaseStorage.getInstance().getReference().child("profile_pic")
                .child(FirebaseUtil.getUserID());
    }


}
