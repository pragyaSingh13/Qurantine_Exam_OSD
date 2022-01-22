package com.example.qurantineexam;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class AnswerAct extends AppCompatActivity {
    TextView Question;
    EditText Answer;
    LinearLayout upload;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        Question=findViewById(R.id.textquestion);
        Answer=findViewById(R.id.textanswer);
        upload=findViewById(R.id.uploadans);

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference(DashboardAct.Keyr);

        Intent intent= getIntent();
        String ques= intent.getStringExtra("Question");
        Question.setText(ques);
//        Student obj=new Student(ExamAct.studentn,ExamAct.studentr);
////        int random=new Random().nextInt(100000);
//        databaseReference.child("Candidates").child("student"+random).setValue(obj);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ans=Answer.getText().toString();
                Test obj2=new Test(ques,ans);
                databaseReference.child("Candidates").child("student"+DashboardAct.random).child("Test").child("q"+new Random().nextInt(10000)).setValue(obj2);
                startActivity(new Intent(AnswerAct.this,ExamAct.class));
                finish();
            }
        });

    }
}