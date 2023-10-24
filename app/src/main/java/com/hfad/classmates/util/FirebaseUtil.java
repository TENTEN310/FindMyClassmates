package com.hfad.classmates.util;

import android.content.Intent;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hfad.classmates.objectClasses.ProfileInfo;

import java.text.SimpleDateFormat;
import java.util.List;

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

    public static DocumentReference getChatroomReference(String chatroomId){
        return FirebaseFirestore.getInstance().collection("chatrooms").document(chatroomId);
    }

    public static CollectionReference getChatroomMessageReference(String chatroomId){
        return getChatroomReference(chatroomId).collection("chats");
    }


    public static String getChatroomID(String userId1,String userId2){
        if(userId1.hashCode()<userId2.hashCode()){
            return userId1+"_"+userId2;
        }else{
            return userId2+"_"+userId1;
        }
    }

    public static CollectionReference allChatroomCollectionReference(){
        return FirebaseFirestore.getInstance().collection("chatrooms");
    }

    public static DocumentReference getOtherUserFromChatroom(List<String> userIds){
        if(userIds.get(0).equals(FirebaseUtil.getUserID())){
            return FirebaseFirestore.getInstance().collection("users").document(userIds.get(1));
        }else{
            return FirebaseFirestore.getInstance().collection("users").document(userIds.get(0));
        }
    }

    public static String GetOtherUserID(List<String> userIDs){
        if(userIDs.get(0).equals(FirebaseUtil.getUserID())){
            return userIDs.get(1);
        }else{
            return userIDs.get(0);
        }
    }

    public static String reformateTime(Timestamp timestamp){
        return new SimpleDateFormat("HH:MM").format(timestamp.toDate());
    }

    public static StorageReference  getElseProfilePic(String otherUserId){
        return FirebaseStorage.getInstance().getReference().child("profile_pic")
                .child(otherUserId);
    }

    public static void passUserModelAsIntent(Intent intent, ProfileInfo model){
        intent.putExtra("username",model.getUsername());
        intent.putExtra("major",model.getMajor());
        intent.putExtra("userId",model.getUserID());
        intent.putExtra("year",model.getYear());
        intent.putExtra("school",model.getSchool());

    }

    public static ProfileInfo getUserModelFromIntent(Intent intent){
        ProfileInfo userProfile = new ProfileInfo();
        userProfile.setUsername(intent.getStringExtra("username"));
        userProfile.setMajor(intent.getStringExtra("major"));
        userProfile.setUserID(intent.getStringExtra("userId"));
        userProfile.setYear(intent.getStringExtra("year"));
        userProfile.setSchool(intent.getStringExtra("school"));
        return userProfile;
    }

    public static StorageReference  getProfilePic(String otherUserId){
        return FirebaseStorage.getInstance().getReference().child("profile_pic")
                .child(otherUserId);
    }

}
