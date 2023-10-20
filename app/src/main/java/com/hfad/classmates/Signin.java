package com.hfad.classmates;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Signin extends AppCompatActivity {
    EditText email, password, confirm_password;
    Button register;
    TextView signin;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_signin);
        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextPassword);
        confirm_password = findViewById(R.id.editTextPassword1);
        register = findViewById(R.id.button);
        signin = findViewById(R.id.textView3);
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
                else if(password1.isEmpty()){
                    password.setError("Please enter password");
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
                mAuth.createUserWithEmailAndPassword(email1, password1)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
//                                    Log.d(TAG, "createUserWithEmail:success");
//                                    FirebaseUser user = mAuth.getCurrentUser();
                                    //updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(Signin.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }
                            }
                        });
            }
        });
    }

}