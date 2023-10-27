package com.hfad.classmates;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class ClassDetail extends AppCompatActivity {
    TextView classFullName, professor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_detail_activity);


    }
}

