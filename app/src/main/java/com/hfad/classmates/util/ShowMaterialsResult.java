package com.hfad.classmates.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hfad.classmates.R;
import com.hfad.classmates.objectClasses.Materials;

import java.util.List;

public class ShowMaterialsResult extends RecyclerView.Adapter<ShowMaterialsResult.MaterialViewHolder> {
    private final Context context;
    private List<Materials> materialsList;
    ImageButton deleteMaterialBtn;


    public ShowMaterialsResult(Context context, List<Materials> materialsList) {
        this.context = context;
        this.materialsList = materialsList;
    }

    private TextView emptyView;

    public void setEmptyView(TextView view) {
        this.emptyView = view;
        checkEmpty();
    }

    public void setMaterialsList(List<Materials> materialsList) {
        this.materialsList = materialsList;
        checkEmpty();
        notifyDataSetChanged();
    }

    private void checkEmpty() {
        if (emptyView != null) {
            if (getItemCount() == 0) {
                emptyView.setVisibility(View.VISIBLE);
            } else {
                emptyView.setVisibility(View.GONE);
            }
        }
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

            // set the material name and format text
            holder.materialName.setText(material.getMaterialName());
            holder.fileType.setText(material.getFileType());

            // open the material
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openFileFromFirebase(material);
                }
            });

            //delete the material
            deleteMaterialBtn = holder.itemView.findViewById(R.id.deleteMaterialBtn);
            deleteMaterialBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteFileFromFirebase(material);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return materialsList == null ? 0 : materialsList.size();
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

    // open/download the file
    private void openFileFromFirebase(Materials material) {
        StorageReference fileReference = FirebaseStorage.getInstance().getReference().child(material.getFilePath());

        fileReference.getDownloadUrl()
            // opened/downloaded file
            .addOnSuccessListener(uri -> {
                String downloadUrl = uri.toString();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(downloadUrl));

                try {
                    context.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(context, "No app found to handle this action", Toast.LENGTH_SHORT).show();
                }
            })
            // cannot open/download file
            .addOnFailureListener(e -> {
                Toast.makeText(context, "Failed to retrieve download URL: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
    }

    // delete the file from storage
    private void deleteFileFromFirebase(Materials material) {
        StorageReference fileReference = FirebaseStorage.getInstance().getReference().child(material.getFilePath());

        fileReference.delete()
            // successfully deleted the file from storage
            .addOnSuccessListener(aVoid -> {
                materialsList.remove(material);
                notifyDataSetChanged();
            })
            // failed to delete the file from storage
            .addOnFailureListener(exception -> {
                Toast.makeText(context, "Failed to delete file: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
            });
    }

}
