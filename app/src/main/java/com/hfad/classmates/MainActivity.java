package com.hfad.classmates;

import static com.hfad.classmates.util.FirebaseUtil.getUserID;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hfad.classmates.chatsActivity.Chats;
import com.hfad.classmates.util.FirebaseUtil;

import de.hdodenhof.circleimageview.CircleImageView;

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

        Toolbar my_toolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(my_toolbar);
        toolbar = getSupportActionBar();

        navigationView = findViewById(R.id.navigation); // Initialize after setContentView
        navigationView.setOnItemSelectedListener(selectedListener);
        toolbar.setTitle("FindMyClassmates");

        initializeFragments();

        // Default to the home page upon opening the app
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.show(homeFragment);
        fragmentTransaction.commit();
    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.top, menu);
        MenuItem menuItem = menu.findItem(R.id.profile_image);
        profileAvatar = (ImageView) findViewById(R.id.tool_bar_profile_image);
        FirebaseUtil.getProfilePic(getUserID()).getDownloadUrl()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Uri uri  = task.getResult();
                        Glide.with(this).load(uri).apply(RequestOptions.circleCropTransform()).into(this.profileAvatar);
                    }
                });
        profileAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this, "profile clicked", Toast.LENGTH_SHORT).show();
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

    private BottomNavigationView.OnItemSelectedListener selectedListener = new BottomNavigationView.OnItemSelectedListener() {
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
                    toolbar.setTitle("FindMyClassmates");
                    fragmentTransaction.show(homeFragment);
                    break;

                case R.id.nav_profile:
                    toolbar.setTitle("FindMyClassmates");
                    fragmentTransaction.show(profileFragment);
                    break;

                case R.id.nav_classes:
                    toolbar.setTitle("FindMyClassmates");
                    fragmentTransaction.show(classesFragment);
                    break;

                case R.id.nav_chats:
                    toolbar.setTitle("FindMyClassmates");
                    fragmentTransaction.show(chatsFragment);
                    break;

                case R.id.nav_post:
                    toolbar.setTitle("FindMyClassmates");
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



