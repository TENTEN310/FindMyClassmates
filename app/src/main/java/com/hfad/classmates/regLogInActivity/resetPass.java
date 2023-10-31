package com.hfad.classmates.regLogInActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.hfad.classmates.R;

public class resetPass extends AppCompatActivity {
    Button reset;
    EditText email;
    TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
        reset = findViewById(R.id.resetBtn);
        email = findViewById(R.id.resetEmail);
        back = findViewById(R.id.Back);

        back.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        });

        reset.setOnClickListener(view -> {
            String email1 = email.getText().toString();
            if (email1.isEmpty()) {
                email.setError("Please enter email");
                email.requestFocus();
                return;
            }
            if (!email1.endsWith("@usc.edu")) {
                email.setError("Please enter a valid USC email");
                email.requestFocus();
            } else {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String emailAddress = email.getText().toString();

                auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(resetPass.this, "Reset Email sent.",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                startActivity(intent);
                                finish();
                            }
                        });
            }

        });
    }
}