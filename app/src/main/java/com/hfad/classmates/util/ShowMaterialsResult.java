package com.hfad.classmates.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hfad.classmates.R;
import com.hfad.classmates.objectClasses.Materials;

import java.util.List;

public class ShowMaterialsResult extends RecyclerView.Adapter<ShowMaterialsResult.MaterialViewHolder> {
    private Context context;
    private List<Materials> materialsList;
    private String storagePath;

    public ShowMaterialsResult(Context context, String storagePath, List<Materials> materialsList) {
        this.context = context;
        this.storagePath = storagePath;
        this.materialsList = materialsList;
    }

    public void setMaterialsList(List<Materials> materialsList) {
        this.materialsList = materialsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MaterialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.materials_cover, parent, false);
        return new MaterialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MaterialViewHolder holder, int position) {
        if (materialsList != null) {
            Materials material = materialsList.get(position);

            // retrieve the materials from storage
            StorageReference storageRef = FirebaseStorage.getInstance().getReference()
                    .child(storagePath)
                    .child(material.getMaterialName());

            // set the material name and format text
            holder.materialName.setText(material.getMaterialName());
            storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                String materialUrl = uri.toString();
                holder.fileType.setText(getFileFormat(materialUrl));
            });
        }
    }

    @Override
    public int getItemCount() {
        return materialsList == null ? 0 : materialsList.size();
    }

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

    static class MaterialViewHolder extends RecyclerView.ViewHolder {
        TextView materialName;
        TextView fileType;

        MaterialViewHolder(@NonNull View itemView) {
            super(itemView);
            materialName = itemView.findViewById(R.id.materialName);
            fileType = itemView.findViewById(R.id.fileType);
        }
    }
}
