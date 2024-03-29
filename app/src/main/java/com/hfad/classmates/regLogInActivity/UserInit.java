package com.hfad.classmates.regLogInActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hfad.classmates.util.FirebaseUtil;
import com.hfad.classmates.R;
import com.hfad.classmates.objectClasses.ProfileInfo;

public class UserInit extends AppCompatActivity {
    Button submit;
    EditText year, username, USCID;
    Spinner school, major;
    ProgressBar bar;
    TextView resend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        setContentView(R.layout.activity_user_init);
        submit = findViewById(R.id.init_submit_btn);
        year = findViewById(R.id.init_year_input);
        major = findViewById(R.id.init_major_input);
        school = findViewById(R.id.init_school_spinner);
        username = findViewById(R.id.init_username_input);
        school = findViewById(R.id.init_school_spinner);
        USCID = findViewById(R.id.init_USCID_input);
        bar = findViewById(R.id.progressBar4);
        resend = findViewById(R.id.resentText);
        ArrayAdapter<CharSequence> school_adapter = ArrayAdapter.createFromResource(this,
                R.array.usc_schools, android.R.layout.simple_spinner_item);
        school_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        school.setAdapter(school_adapter);
        ArrayAdapter<CharSequence> major_adapter = ArrayAdapter.createFromResource(this,
                R.array.usc_majors, android.R.layout.simple_spinner_item);
        major_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        major.setAdapter(major_adapter);
        bar.setVisibility(View.GONE);
        if(user!= null){
            user.sendEmailVerification();
            Toast.makeText(UserInit.this, "Verification Email sent.",
                    Toast.LENGTH_LONG).show();
        }
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user!= null){
                    user.sendEmailVerification();
                    Toast.makeText(UserInit.this, "Verification Email sent.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String year1 = year.getText().toString();
                String major1 = major.getSelectedItem().toString();
                String username1 = username.getText().toString().toLowerCase();
                String school1 = school.getSelectedItem().toString();
                String USCID1 = USCID.getText().toString();
                if(username1.isEmpty()) username.setError("Please enter username");
                else if(username1.length()<3) username.setError("Username length should be at least 3 chars");
                else if(year1.isEmpty()) year.setError("Please enter year");
                else if(USCID1.isEmpty()) year.setError("Please enter USCID");
                else if(major1.isEmpty()) ((TextView)major.getSelectedView()).setError("Please enter major");
                else if(school1.isEmpty()) ((TextView)school.getSelectedView()).setError("Please select school");
                else{
                    submit.setVisibility(View.GONE);
                    bar.setVisibility(View.VISIBLE);
                    ProfileInfo profileInfo = new ProfileInfo(username1, year1, major1, school1, USCID1, Timestamp.now(), FirebaseUtil.getUserID());

                    user.reload().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                // Check if the user's email is verified
                                if (user.isEmailVerified()) {
                                    // The user's email is verified, proceed to the next screen
                                    FirebaseUtil.getUserDetails().set(profileInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Intent intent = new Intent(getApplicationContext(), Init_avatar.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                            else{
                                                submit.setVisibility(View.VISIBLE);
                                                bar.setVisibility(View.GONE);
                                                Toast.makeText(UserInit.this, "Failed to save user details.",
                                                        Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                } else {
                                    submit.setVisibility(View.VISIBLE);
                                    bar.setVisibility(View.GONE);
                                    // The user's email is not verified yet
                                    Toast.makeText(UserInit.this, "Please verify your email before the next step.",
                                            Toast.LENGTH_LONG).show();
                                }
                            } else {
                                submit.setVisibility(View.VISIBLE);
                                bar.setVisibility(View.GONE);
                                // Handle failure to reload user
                                Toast.makeText(UserInit.this, "Failed to reload user.",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
}