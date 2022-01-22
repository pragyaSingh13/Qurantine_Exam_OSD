package com.example.qurantineexam;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ExamAdapter extends FirebaseRecyclerAdapter<QuestionModel, com.example.hackt2.ExamAdapter.myviewholder> {

    public ExamAdapter(@NonNull FirebaseRecyclerOptions<QuestionModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull QuestionModel model) {
        holder.question.setText(model.getQuestion());
        holder.question.setText("M:"+model.getQuestion());
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.questionitem,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{
        TextView question,marks;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            question=itemView.findViewById(R.id.questionh);
            marks=itemView.findViewById(R.id.marksh);
        }
    }
}