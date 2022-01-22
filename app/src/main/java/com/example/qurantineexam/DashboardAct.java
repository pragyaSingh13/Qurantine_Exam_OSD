package com.example.qurantineexam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class DashboardAct extends AppCompatActivity {
    ConstraintLayout createExam,AttemptExam;
    public static String Keys,Keyr,passwords;
    EditText Key;
    public static int random;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser curUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        createExam=findViewById(R.id.layoutforcreateExam);
        AttemptExam=findViewById(R.id.layoutattemptcreateExam);
        firebaseDatabase=FirebaseDatabase.getInstance();

        AttemptExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference dataref = FirebaseDatabase.getInstance().getReference().child("users").child(curUser.getUid()).child("imgUrl");
                Dialog dialog=new Dialog(DashboardAct.this);
                dialog.setContentView(R.layout.key_to_attempt_examdialog);
                dialog.setCanceledOnTouchOutside(true);
                EditText keyr=dialog.findViewById(R.id.editkey);
                Button next=dialog.findViewById(R.id.uploadBtn);
                dialog.show();
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Keyr=keyr.getText().toString();
//                        startActivity(new Intent(DashboardAct.this,ExamAct.class));
                        Dialog dialog1=new Dialog(DashboardAct.this);
                        dialog1.setContentView(R.layout.student_det);
                        dialog1.setCanceledOnTouchOutside(true);
                        EditText Name=dialog1.findViewById(R.id.studnames);
                        EditText Roll=dialog1.findViewById(R.id.studrollnos);
                        Button next2=dialog1.findViewById(R.id.uploadBtn5);
                        dialog1.show();
                        next2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //fetch image url
                                dataref.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                                        String imageUrl = (String) snapshot.getValue();
                                        random=new Random().nextInt(100000);
                                        Student obj=new Student(Name.getText().toString(),Roll.getText().toString(),imageUrl);
                                        firebaseDatabase.getReference(Keyr).child("Candidates").child("student"+random).setValue(obj);
                                        startActivity(new Intent(DashboardAct.this,ExamAct.class));
                                    }
                                    @Override
                                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
        createExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog=new Dialog(DashboardAct.this);
                dialog.setContentView(R.layout.dialog_create_dialog_1);
                dialog.setCanceledOnTouchOutside(true);
                Button next=dialog.findViewById(R.id.uploadBtn);
                Key=dialog.findViewById(R.id.editkey);

                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Dialog dialog1=new Dialog(DashboardAct.this);
                        dialog1.setContentView(R.layout.dialog_layout_dialog_2);
                        dialog1.setCanceledOnTouchOutside(true);
                        Button next2=dialog1.findViewById(R.id.uploadBtn2);
                        EditText passwrd=dialog1.findViewById(R.id.enterpassword);
                        next2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Keys=Key.getText().toString();
                                passwords=passwrd.getText().toString();
                                Intent intent=new Intent(DashboardAct.this,AddQuesact.class);
                                startActivity(intent);
                                //startActivity(new Intent(DashboardAct.this,AddQuesact.class));
                            }
                        });
                        dialog1.show();
                    }
                });
                dialog.show();
            }
        });
    }
}