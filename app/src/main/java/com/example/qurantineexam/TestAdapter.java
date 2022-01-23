package com.example.qurantineexam;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class TestAdapter extends FirebaseRecyclerAdapter<Test,TestAdapter.myviewholder> {


    public TestAdapter(@NonNull FirebaseRecyclerOptions<Test> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Test model) {
        holder.question.setText(model.getQuestion());
        holder.answer.setText(model.getAnswer());
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout_2,parent,false);
        return  new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{
        EditText question,answer;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            question=itemView.findViewById(R.id.cquestion);
            answer=itemView.findViewById(R.id.canswer);
        }
    }
}
