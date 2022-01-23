package com.example.qurantineexam;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.w3c.dom.Text;

public class StudAdapter extends FirebaseRecyclerAdapter<Smodel,StudAdapter.myviewholder> {
    public static String reference;

    public StudAdapter(@NonNull FirebaseRecyclerOptions<Smodel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Smodel model) {
        holder.Rollno.setText(model.getRollno());
        holder.name.setText(model.getName());
        Glide.with(holder.profileimg.getContext()).load(model.getImageuri()).into(holder.profileimg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference=getRef(position).getKey();
                Intent intent=new Intent(holder.Rollno.getContext(),check_answer.class);

                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.student_detail_layout,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{
        ImageView profileimg;
        TextView name,Rollno;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            profileimg=itemView.findViewById(R.id.imager);
            name=itemView.findViewById(R.id.studnam);
            Rollno=itemView.findViewById(R.id.studrol);
        }
    }
}
