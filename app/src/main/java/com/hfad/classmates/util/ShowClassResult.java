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
import com.hfad.classmates.ClassDetail;
import com.hfad.classmates.R;
import com.hfad.classmates.objectClasses.Classes;

public class ShowClassResult extends FirestoreRecyclerAdapter<Classes, ShowClassResult.ClassView> {
    Context context;
    public ShowClassResult(@NonNull FirestoreRecyclerOptions<Classes> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ClassView holder, int position, @NonNull Classes model) {
        holder.classname.setText(model.getAbv());
        holder.term.setText(model.getTerm());
        holder.professor.setText(model.getProfessor());
        holder.close.setVisibility(View.GONE);
        holder.divider.setText("/");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ClassDetail.class);
                FirebaseUtil.passClassesModelAsIntent(intent,model);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public ClassView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.class_cover,parent,false);
        return new ClassView(view);
    }

    class ClassView extends RecyclerView.ViewHolder{
        TextView classname;
        TextView term, professor, divider;
        ImageButton close;

        public ClassView(@NonNull View itemView) {
            super(itemView);
            classname = itemView.findViewById(R.id.user_name_text);
            term = itemView.findViewById(R.id.contact_user_info);
            professor = itemView.findViewById(R.id.extra);
            close = itemView.findViewById(R.id.imageButton3);
            divider = itemView.findViewById(R.id.Divider);
        }
    }
    @Override
    public void onDataChanged() {
        super.onDataChanged();
        notifyDataSetChanged();
    }
}