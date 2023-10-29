package com.hfad.classmates.util;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hfad.classmates.R;
import com.hfad.classmates.objectClasses.Materials;

public class ShowMaterialsResult extends FirestoreRecyclerAdapter<Materials, ShowMaterialsResult.MaterialViewHolder> {
    Context context;
    String classAbv;

    public ShowMaterialsResult(@NonNull FirestoreRecyclerOptions<Materials> options, Context context, String classAbv) {
        super(options);
        this.context = context;
        this.classAbv = classAbv;
    }

    @NonNull
    @Override
    public MaterialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.materials_cover, parent, false);
        return new MaterialViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull MaterialViewHolder holder, int position, @NonNull Materials model) {
        // retrieve the materials from storage
        StorageReference storageRef = FirebaseStorage.getInstance().getReference()
                .child("materials")
                .child(classAbv)
                .child(model.getMaterialName());

        // set the material name text
        holder.materialName.setText(model.getMaterialName());

        // set the material format text
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String materialUrl = uri.toString();
                holder.format.setText(getFileFormat(materialUrl));
            }
        });
    }

    // setting the format in the view
    private String getFileFormat(String fileUrl) {
        if (fileUrl.endsWith(".pdf")) {
            return "PDF";
        } else if (fileUrl.endsWith(".docx")) {
            return "DOCX";
        } else if (fileUrl.endsWith(".jpg")) {
            return "JPG";
        } else if (fileUrl.endsWith(".png")) {
            return "PNG";
        } else {
            return "Unknown";
        }
    }

    class MaterialViewHolder extends RecyclerView.ViewHolder {
        // Define views for your item layout (materials_cover.xml)
        TextView materialName;
        TextView format;

        public MaterialViewHolder(@NonNull View itemView) {
            super(itemView);
            materialName = itemView.findViewById(R.id.materialType);
            format = itemView.findViewById(R.id.format);
        }
    }
}
