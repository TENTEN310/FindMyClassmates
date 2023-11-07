package com.hfad.classmates.regLogInActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.hfad.classmates.R;

import java.util.Objects;

public class Register extends AppCompatActivity {
    EditText email, password, confirm_password;
    Button register;
    TextView signin;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.RegisterPass);
        confirm_password = findViewById(R.id.ConfirmPass);
        register = findViewById(R.id.RegisterButton);
        signin = findViewById(R.id.SigninText);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email1 = email.getText().toString();
                String password1 = password.getText().toString();
                String confirm_password1 = confirm_password.getText().toString();
                if(email1.isEmpty()){
                    email.setError("Please enter email");
                    email.requestFocus();
                    return;
                }
                if(!email1.endsWith("@usc.edu")){
                    email.setError("Please enter an USC email");
                    email.requestFocus();
                    return;
                }
                else if(password1.isEmpty()){
                    password.setError("Please enter password");
                    password.requestFocus();
                    return;
                }
                else if(password1.length() < 6){
                    password.setError("Password should be at least 6 characters");
                    password.requestFocus();
                    return;
                }
                else if(confirm_password1.isEmpty()){
                    confirm_password.setError("Please enter confirm password");
                    confirm_password.requestFocus();
                    return;
                }
                else if(!password1.equals(confirm_password1)){
                    confirm_password.setError("Password and confirm password should be same");
                    confirm_password.requestFocus();
                    return;
                }
                //----------------debug for skipping auth------------------/
//                Intent intent = new Intent(getApplicationContext(), UserInit.class);
//                startActivity(intent);
//                finish();
                //-----------------debug for skipping auth------------------/
                progressBar.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(email1, password1)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
//                                    Log.d(TAG, "createUserWithEmail:success");
//                                    FirebaseUser user = mAuth.getCurrentUser();
                                    progressBar.setVisibility(View.GONE);
                                    Intent intent = new Intent(getApplicationContext(), UserInit.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Register.this, Objects.requireNonNull(task.getException()).toString(),
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }
                            }
                        });
            }
        });
    }
}