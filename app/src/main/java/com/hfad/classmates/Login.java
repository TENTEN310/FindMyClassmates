package com.hfad.classmates;

import static java.sql.DriverManager.println;

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
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    EditText email, password;
    Button login;
    TextView signup, reset;
    FirebaseAuth  mAuth;
    ProgressBar progressBar;
    Boolean firstTime = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.SigninEmail);
        password = findViewById(R.id.SigninPass);
        login = findViewById(R.id.loginButton);
        signup = findViewById(R.id.signUpText);
        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.GONE);
        reset = findViewById(R.id.resetText);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), resetPass.class);
                startActivity(intent);
                finish();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email1 = email.getText().toString();
                String password1 = password.getText().toString();
                if(email1.isEmpty()){
                    email.setError("Please enter email");
                    email.requestFocus();
                    return;
                }
                else if(password1.isEmpty()){
                    password.setError("Please enter password");
                    password.requestFocus();
                    return;
                }
                login.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(email1, password1)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    progressBar.setVisibility(View.GONE);
                                    // Sign in success, update UI with the signed-in user's information
                                    mAuth.getCurrentUser().reload();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    println(user.toString());
                                    if(!user.isEmailVerified()){
                                        firstTime = true;
                                        progressBar.setVisibility(View.GONE);
                                        login.setVisibility(View.VISIBLE);
                                        Toast.makeText(Login.this, "Please verify your email.",
                                                Toast.LENGTH_SHORT).show();
                                        user.sendEmailVerification();
                                        Toast.makeText(Login.this, "Verification Email sent.",
                                                Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        Toast.makeText(Login.this, "Authentication success.",
                                                Toast.LENGTH_SHORT).show();
                                        if(firstTime){
                                            Intent intent = new Intent(getApplicationContext(), UserInit.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else{
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    login.setVisibility(View.VISIBLE);
                                    Toast.makeText(Login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }
}