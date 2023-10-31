package com.hfad.classmates;

import static com.hfad.classmates.util.FirebaseUtil.getUserID;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hfad.classmates.chatsActivity.Chats;
import com.hfad.classmates.util.FirebaseUtil;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView navigationView;
    ActionBar toolbar;
    ImageView profileAvatar;
    HomeFragment homeFragment = new HomeFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    ClassesFragment classesFragment = new ClassesFragment();
    Chats chatsFragment = new Chats();
    PostFragment postFragment = new PostFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // This needs to be at the top
        navigationView = findViewById(R.id.navigation); // Initialize after setContentView
        navigationView.setOnItemSelectedListener(selectedListener);

        initializeFragments();

        // Default to the home page upon opening the app
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.show(homeFragment);
        fragmentTransaction.commit();
    }


    public boolean onCreateOptionsMenu(Menu menu){
        profileAvatar = findViewById(R.id.tool_bar_profile_image);
        FirebaseUtil.getProfilePic(getUserID()).getDownloadUrl()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Uri uri  = task.getResult();
                        Glide.with(this).load(uri).apply(RequestOptions.circleCropTransform()).into(this.profileAvatar);
                    }
                });
        return super.onCreateOptionsMenu(menu);
    }


    private void initializeFragments() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.add(R.id.content, homeFragment, "HOME");
        fragmentTransaction.add(R.id.content, profileFragment, "PROFILE");
        fragmentTransaction.add(R.id.content, classesFragment, "CLASSES");
        fragmentTransaction.add(R.id.content, chatsFragment, "CHATS");
        fragmentTransaction.add(R.id.content, postFragment, "POST");

        fragmentTransaction.hide(profileFragment);
        fragmentTransaction.hide(classesFragment);
        fragmentTransaction.hide(chatsFragment);
        fragmentTransaction.hide(postFragment);

        fragmentTransaction.commit();
    }

    private final BottomNavigationView.OnItemSelectedListener selectedListener = new BottomNavigationView.OnItemSelectedListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            // Hide all fragments
            fragmentTransaction.hide(homeFragment);
            fragmentTransaction.hide(profileFragment);
            fragmentTransaction.hide(classesFragment);
            fragmentTransaction.hide(chatsFragment);
            fragmentTransaction.hide(postFragment);

            switch (menuItem.getItemId()) {
                case R.id.nav_home:
                    fragmentTransaction.show(homeFragment);
                    break;

                case R.id.nav_profile:
                    fragmentTransaction.show(profileFragment);
                    break;

                case R.id.nav_classes:
                    fragmentTransaction.show(classesFragment);
                    break;

                case R.id.nav_chats:
                    fragmentTransaction.show(chatsFragment);
                    break;

                case R.id.nav_post:
                    fragmentTransaction.show(postFragment);
                    break;

                default:
                    return false;
            }

            fragmentTransaction.commit();
            return true;
        }
    };

}



