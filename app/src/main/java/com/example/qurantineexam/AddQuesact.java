package com.example.qurantineexam;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class AddQuesact extends AppCompatActivity {
    FloatingActionButton addquesfloat;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerView quesrecycles;
    QuesAdapter adapter;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;
    int i = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_quesact);
        addquesfloat = findViewById(R.id.addquesfloat);
        quesrecycles = findViewById(R.id.quesrecycle);
        firebaseDatabase = FirebaseDatabase.getInstance();


        databaseReference = firebaseDatabase.getReference(DashboardAct.Keys);
//        databaseReference.setValue("heyjadlk");


        quesrecycles.setHasFixedSize(true);
        quesrecycles.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<QuestionModel> options = new FirebaseRecyclerOptions.Builder<QuestionModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference(DashboardAct.Keys).child("Questions"), QuestionModel.class)
                .build();
        adapter = new QuesAdapter(options);
        quesrecycles.setAdapter(adapter);

        addquesfloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog d = new Dialog(AddQuesact.this);
                d.setContentView(R.layout.dialog_layout_add__ques);
                d.setCanceledOnTouchOutside(false);
                ImageView micimg = d.findViewById(R.id.mic);
                EditText Question = d.findViewById(R.id.rET);
                EditText Marks = d.findViewById(R.id.markstext);
                Button upload = d.findViewById(R.id.uploadBtn);
                upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String ques = Question.getText().toString();
                        String mark = Marks.getText().toString();
                        QuestionModel obj = new QuestionModel(ques, mark);
                        databaseReference.child("Questions").child("ques" + new Random().nextInt(100000)).setValue(obj);
                    }
                });

                micimg.setOnClickListener(new View.OnClickListener() {
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
                            Toast.makeText(AddQuesact.this, " " + e.getMessage(),
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
                d.show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.startListening();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        final LayoutInflater factory = getLayoutInflater();
        final View textEntryView = factory.inflate(R.layout.dialog_layout_add__ques, null);
        EditText edittext = textEntryView.findViewById(R.id.mic);

        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS);
                edittext.setText(
                        Objects.requireNonNull(result).get(0));
            }
        }
    }
}