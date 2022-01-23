package com.example.qurantineexam;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class QuesAdapter extends FirebaseRecyclerAdapter<QuestionModel, com.example.qurantineexam.QuesAdapter.myviewholder> {


    public QuesAdapter(@NonNull FirebaseRecyclerOptions<QuestionModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull QuestionModel model) {
        holder.question.setText(model.getQuestion());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog=new Dialog(holder.question.getContext());
                dialog.setContentView(R.layout.dialog_layout_add__ques);
                dialog.setCanceledOnTouchOutside(true);
                EditText Question=dialog.findViewById(R.id.rET);
                EditText Marks=dialog.findViewById(R.id.markstext);
                Button upload=dialog.findViewById(R.id.uploadBtnk);

                Question.setText(model.getQuestion());
                Marks.setText(model.getMarks());
                dialog.show();
                upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("question",Question.getText().toString());
                        map.put("marks",Marks.getText().toString());
                        FirebaseDatabase.getInstance().getReference(DashboardAct.Keys).child("Questions").child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        dialog.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                dialog.dismiss();
                            }
                        });
                    }
                });
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.question.getContext())
                        .setTitle("Delete Question")
                        .setMessage("Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseDatabase.getInstance().getReference(DashboardAct.Keys).child("Questions").child(getRef(position).getKey()).removeValue();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                builder.show();
            }
        });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{
        TextView question;
        ImageView edit,delete;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            question=itemView.findViewById(R.id.questionh);
            delete=itemView.findViewById(R.id.delete_icons);
            edit=itemView.findViewById(R.id.edit_icons);
        }
    }
}
