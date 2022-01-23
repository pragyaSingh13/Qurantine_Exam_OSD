package com.example.qurantineexam;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class check_answer extends AppCompatActivity {
    RecyclerView recyclerView;
    TestAdapter adapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_answer);
        recyclerView=findViewById(R.id.rview);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent intent=getIntent();
        String reference=intent.getStringExtra("reference");
        FirebaseRecyclerOptions options=new FirebaseRecyclerOptions.Builder<Test>()
                .setQuery(FirebaseDatabase.getInstance().getReference(DashboardAct.keyn).child("Candidates").child(reference).child("Test"),Test.class)
                .build();
        adapter=new TestAdapter(options);
        recyclerView.setAdapter(adapter);

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
}