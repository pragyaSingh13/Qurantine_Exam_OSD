package com.example.qurantineexam;

import android.content.pm.PackageManager;
import android.app.Dialog;
import android.content.Intent;
import android.media.MediaTimestamp;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import static android.Manifest.permission.CAMERA;



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
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, PackageManager.PERMISSION_GRANTED);
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
                ImageView camera = d.findViewById(R.id.camera);
                EditText Question = d.findViewById(R.id.rET);
                EditText Marks = d.findViewById(R.id.markstext);
                Button upload = d.findViewById(R.id.uploadBtnk);
                upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String ques = Question.getText().toString();
                        String mark = Marks.getText().toString();
                        QuestionModel obj = new QuestionModel(ques, mark);
                        databaseReference.child("Questions").child("ques" + new Random().nextInt(100000)).setValue(obj);
                    }
                });

                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(AddQuesact.this, scancamera.class));
                    }
                });

                micimg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                d.show();
                d.dismiss();
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

