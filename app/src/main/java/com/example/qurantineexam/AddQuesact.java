package com.example.qurantineexam;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class AddQuesact extends AppCompatActivity {
    FloatingActionButton addquesfloat;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerView quesrecycles;
    QuesAdapter adapter;
    int i=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_quesact);
        addquesfloat=findViewById(R.id.addquesfloat);
        quesrecycles=findViewById(R.id.quesrecycle);
        firebaseDatabase=FirebaseDatabase.getInstance();

        databaseReference=firebaseDatabase.getReference(DashboardAct.Keys);
//        databaseReference.setValue("heyjadlk");


        quesrecycles.setHasFixedSize(true);
        quesrecycles.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<QuestionModel> options= new FirebaseRecyclerOptions.Builder<QuestionModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference(DashboardAct.Keys).child("Questions"),QuestionModel.class)
                .build();
        adapter=new QuesAdapter(options);
        quesrecycles.setAdapter(adapter);

        addquesfloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog d= new Dialog(AddQuesact.this);
                d.setContentView(R.layout.dialog_layout_add__ques);
                d.setCanceledOnTouchOutside(false);
                EditText Question=d.findViewById(R.id.rET);
                EditText Marks=d.findViewById(R.id.markstext);
                Button upload=d.findViewById(R.id.uploadBtn);
                upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String ques=Question.getText().toString();
                        String mark=Marks.getText().toString();
                        QuestionModel obj=new QuestionModel(ques,mark);
                        databaseReference.child("Questions").child("ques"+new Random().nextInt(100000)).setValue(obj);


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
}