package com.example.qurantineexam;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class DashboardAct extends AppCompatActivity {
    ConstraintLayout createExam,AttemptExam;
    public static String Keys,passwords;
    EditText Key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        createExam=findViewById(R.id.layoutforcreateExam);
        AttemptExam=findViewById(R.id.layoutattemptcreateExam);
        AttemptExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog=new Dialog(DashboardAct.this);
                dialog.setContentView(R.layout.key_to_attempt_examdialog);
                dialog.setCanceledOnTouchOutside(true);
                EditText keyr=dialog.findViewById(R.id.editkey);
                Button next=dialog.findViewById(R.id.uploadBtn);
                dialog.show();
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }
        });
        createExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog=new Dialog(com.example.qurantineexam.DashboardAct.this);
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
//                                startActivity(new Intent(DashboardAct.this,AddQuesact.class));
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