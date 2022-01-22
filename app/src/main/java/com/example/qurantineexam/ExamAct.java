package com.example.qurantineexam;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ExamAct extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ExamAdapter adapter;
    EditText StudName, StudRoll;
    public static String studentn,studentr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        recyclerView=findViewById(R.id.quesrecycle);
        StudName=findViewById(R.id.namestud);
        StudRoll=findViewById(R.id.rollstud);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference(DashboardAct.Keyr);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<QuestionModel> options=new FirebaseRecyclerOptions.Builder<QuestionModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference(DashboardAct.Keyr).child("Questions"),QuestionModel.class)
                .build();
        adapter=new ExamAdapter(options);
        recyclerView.setAdapter(adapter);
        studentn=StudName.getText().toString();
        studentr=StudRoll.getText().toString();


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
}