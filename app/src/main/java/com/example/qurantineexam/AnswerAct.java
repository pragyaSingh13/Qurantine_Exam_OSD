package com.example.qurantineexam;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class AnswerAct extends AppCompatActivity {
    TextView Question;
    EditText Answer;
    LinearLayout upload;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ImageView mic;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        Question=findViewById(R.id.textquestion);
        Answer=findViewById(R.id.textanswer);
        upload=findViewById(R.id.uploadans);
        mic = findViewById(R.id.micforans);

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference(DashboardAct.Keyr);

        Intent intent= getIntent();
        String ques= intent.getStringExtra("Question");
        Question.setText(ques);

        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent
                        = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                        Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");

                try {
                    startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
                } catch (Exception e) {
                    Toast.makeText(AnswerAct.this, " " + e.getMessage(),
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Answer = findViewById(R.id.textanswer);

        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS);
                Answer.setText(
                        Objects.requireNonNull(result).get(0));
            }
        }
    }
}