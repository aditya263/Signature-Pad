package com.aditya.signaturepad.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aditya.signaturepad.Model.ModelClass;
import com.aditya.signaturepad.R;

import java.util.ArrayList;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.RVViewHolderClass> {

    ArrayList<ModelClass> objectModelClass= new ArrayList<ModelClass>();

    public RVAdapter(ArrayList<ModelClass> objectModelClass) {
        this.objectModelClass = objectModelClass;
    }

    @NonNull
    @Override
    public RVViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RVViewHolderClass(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.single_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RVViewHolderClass holder, int position) {

        ModelClass objectModel =objectModelClass.get(position);
        Log.d("YourResponse","text-- "+objectModel.getImageName());
        holder.imageNameTV.setText(objectModel.getImageName());
        holder.objectImageView.setImageBitmap(objectModel.getImage());
    }

    @Override
    public int getItemCount() {

        return (objectModelClass == null) ? 0 : objectModelClass.size();
    }

    public static class RVViewHolderClass extends RecyclerView.ViewHolder{

        TextView imageNameTV;
        ImageView objectImageView;

        public RVViewHolderClass(@NonNull View itemView) {
            super(itemView);

            imageNameTV=itemView.findViewById(R.id.imageDetailsTV);
            objectImageView=itemView.findViewById(R.id.sr_imageTV);
        }
    }
}
