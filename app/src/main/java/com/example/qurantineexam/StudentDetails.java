package com.example.qurantineexam;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class StudentDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        Intent intent = getIntent();
        String key = intent.getStringExtra("key");
        String password_ques  = intent.getStringExtra("pass");
    }
}