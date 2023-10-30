package com.hfad.classmates.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hfad.classmates.ClassDetail;
import com.hfad.classmates.R;
import com.hfad.classmates.objectClasses.Comments;

import java.util.ArrayList;

public class ShowReviewResult extends RecyclerView.Adapter<ShowReviewResult.MyViewHolder>{

    Context context;
    ArrayList<Comments> commentsArrayList;

    public ShowReviewResult(Context context, ArrayList<Comments> commentsArrayList) {
        this.context = context;
        this.commentsArrayList = commentsArrayList;
    }



    @NonNull
    @Override
    public ShowReviewResult.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.review_cover, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowReviewResult.MyViewHolder holder, int position) {

        Comments comment = commentsArrayList.get(position); // get the comment at the position
        holder.tvMessage.setText(comment.getMessage());
        holder.tvSender.setText(comment.getSenderId());
        holder.tvOverall.setText(String.valueOf(comment.getOverall()));
        holder.tvWorkload.setText(String.valueOf(comment.getWorkload()));
        holder.tvAttendance.setText(String.valueOf(comment.getAttendance()));
        holder.tvLate.setText(String.valueOf(comment.getLate()));

    }

    @Override
    public int getItemCount() {
        return commentsArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvMessage, tvSender, tvOverall, tvWorkload, tvAttendance, tvLate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.commentInfo);
            tvSender = itemView.findViewById(R.id.uname);
            tvOverall = itemView.findViewById(R.id.overallRating);
            tvWorkload = itemView.findViewById(R.id.workloadRating);
            tvAttendance = itemView.findViewById(R.id.attendanceRating);
            tvLate = itemView.findViewById(R.id.lateRating);
        }
    }

}
