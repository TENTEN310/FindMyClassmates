package com.hfad.classmates.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
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

            // set the material name and format text
            holder.materialName.setText(material.getMaterialName());
            holder.fileType.setText(material.getFileType());
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
}
