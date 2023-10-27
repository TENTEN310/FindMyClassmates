package com.hfad.classmates;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hfad.classmates.objectClasses.Classes;
import com.hfad.classmates.util.FirebaseUtil;


public class ClassDetail extends AppCompatActivity {
    TextView classFullName, professor, classAbv, description, empty1, empty2;
    Classes classes;

    ImageButton back, add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_detail_activity);
        classes = FirebaseUtil.getClassesModelFromIntent(getIntent());
        classFullName = findViewById(R.id.fullname);
        professor = findViewById(R.id.Professor_name);
        classAbv = findViewById(R.id.class_name);
        back = findViewById(R.id.back_btn);
        add = findViewById(R.id.imageButton2);
        empty1 = findViewById(R.id.empty_view1);
        empty2 = findViewById(R.id.empty_view2);
        description = findViewById(R.id.description_class);
        classFullName.setText(classes.getName() + "\n(" + classes.getUnits() + " units)");
        professor.setText(classes.getProfessor());
        classAbv.setText(classes.getAbv());
        description.setText(classes.getDescription());
        back.setOnClickListener(v -> finish());
        //add class to user's class list

    }
}

