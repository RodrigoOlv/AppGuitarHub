package com.example.appguitarhub.ui.listing;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appguitarhub.R;
import com.example.appguitarhub.model.Equipment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    List<Equipment> listEquipments = new ArrayList<>();
    Context context;

    public MyAdapter(Context context, List<Equipment> equipments) {
        this.context = context;
        this.listEquipments = equipments;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemList = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_card_icons, viewGroup, false);
        return new MyViewHolder(itemList);
    }

    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, @SuppressLint("RecyclerView") int position) {
        Equipment e = listEquipments.get(position);

        myViewHolder.name.setText(e.getName());
        myViewHolder.description.setText(e.getDescription());
        myViewHolder.type.setText(e.getType());
        myViewHolder.contact.setText(e.getContact());

        myViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem(position);
            }
        });

        Bundle bundle = new Bundle();
        bundle.putString("NAME", listEquipments.get(position).getName());
        bundle.putString("DESCRIPTION", listEquipments.get(position).getDescription());
        bundle.putString("TYPE", listEquipments.get(position).getType());
        bundle.putString("CONTACT", listEquipments.get(position).getContact());
        bundle.putString("KEY", listEquipments.get(position).getId());

    }

    @Override
    public int getItemCount() {
        return listEquipments.size();
    }

    public void deleteItem(final int position) {
        new AlertDialog.Builder(context)
            .setTitle("Excluindo equipamento")
            .setMessage("Tem certeza que deseja excluir esse equipamento?")
            .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("equipments");
                    reference.child(listEquipments.get(position).getId()).removeValue();
                    listEquipments.remove(position);
                    notifyItemRemoved(position);
                }
            })
            .setNegativeButton("Não", null).show();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView description;
        TextView type;
        TextView contact;
        ImageButton btnDelete;
        ImageButton btnEdit;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textViewName);
            description = itemView.findViewById(R.id.textViewDescription);
            type = itemView.findViewById(R.id.textViewType);
            contact = itemView.findViewById(R.id.textViewContact);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }
}
