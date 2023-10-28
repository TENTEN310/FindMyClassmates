package com.hfad.classmates.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.hfad.classmates.objectClasses.Materials;
import com.hfad.classmates.R;

public class ShowMaterialsResult extends FirestoreRecyclerAdapter<Materials, ShowMaterialsResult.ClassView> {
    Context context;

    public ShowMaterialsResult(@NonNull FirestoreRecyclerOptions<Materials> options, Context context) {
        super(options);
        this.context = context;
    }

    @NonNull
    @Override
    public ClassView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.materials_cover, parent, false);
        return new ClassView(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ClassView holder, int position, @NonNull Materials model) {
        holder.materialType.setText(model.getMaterialName());
        holder.materialTerm.setText(model.getStorageLocation());
        holder.format.setText(model.getFileType());
    }

    class ClassView extends RecyclerView.ViewHolder {
        TextView materialType;
        TextView materialTerm;
        TextView format;

        public ClassView(@NonNull View itemView) {
            super(itemView);
            materialType = itemView.findViewById(R.id.materialType);
            materialTerm = itemView.findViewById(R.id.materialTerm);
            format = itemView.findViewById(R.id.format);
        }
    }
}
