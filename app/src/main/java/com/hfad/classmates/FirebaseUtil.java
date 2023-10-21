package com.hfad.classmates;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseUtil {
    public static String user_id;
    public static DocumentReference userdetails(){
        return FirebaseFirestore.getInstance().collection("users").document(user_id);
    }


}
