package com.hfad.classmates;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserInit extends AppCompatActivity {
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        getSupportActionBar().hide();
        setContentView(R.layout.activity_user_init);
        submit = findViewById(R.id.button2);
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.usc_schools, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        if(user!= null){
            user.sendEmailVerification();
            Toast.makeText(UserInit.this, "Verification Email sent.",
                    Toast.LENGTH_LONG).show();
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user != null) {

                    user.reload().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                // Check if the user's email is verified
                                if (user.isEmailVerified()) {
                                    // The user's email is verified, proceed to the next screen
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // The user's email is not verified yet
                                    Toast.makeText(UserInit.this, "Please verify your email before the next step.",
                                            Toast.LENGTH_LONG).show();
                                }
                            } else {
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