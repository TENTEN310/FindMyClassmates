package com.hfad.classmates.util;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.hfad.classmates.ShowClass;
import com.hfad.classmates.R;
import com.hfad.classmates.objectClasses.Dept;


public class ShowDeptResult extends FirestoreRecyclerAdapter<Dept, ShowDeptResult.DeptView> {
    Context context;
    public ShowDeptResult(@NonNull FirestoreRecyclerOptions<Dept> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull DeptView holder, int position, @NonNull Dept model) {
        holder.deptName.setText(model.getAbv());
        holder.fullName.setText(model.getDepartmentName());
        holder.extra.setVisibility(View.GONE);
        holder.divider.setVisibility(View.GONE);
        holder.close.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ShowClass.class);
            intent.putExtra("dept",model.getAbv());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

    }

    @NonNull
    @Override
    public DeptView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.class_cover,parent,false);
        return new DeptView(view);
    }

    class DeptView extends RecyclerView.ViewHolder{
        TextView deptName;
        TextView fullName, divider, extra;
        ImageButton close;


        public DeptView(@NonNull View itemView) {
            super(itemView);
            deptName = itemView.findViewById(R.id.user_name_text);
            fullName = itemView.findViewById(R.id.contact_user_info);
            divider = itemView.findViewById(R.id.Divider);
            extra = itemView.findViewById(R.id.extra);
            close = itemView.findViewById(R.id.imageButton3);
        }
    }
    @Override
    public void onDataChanged() {
        super.onDataChanged();
        notifyDataSetChanged();
    }
}