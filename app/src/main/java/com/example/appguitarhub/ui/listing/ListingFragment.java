package com.example.appguitarhub.ui.listing;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appguitarhub.R;
import com.example.appguitarhub.model.Equipment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListingFragment extends Fragment {

    List<Equipment> listEquipments = new ArrayList<>();
    MyAdapter myAdapter;
    RecyclerView recyclerView;
    View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_listing, container, false);
        recyclerView = root.findViewById(R.id.recyclerView);

        loadEquipaments();

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        return root;
    }

    private void loadEquipaments() {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("equipments");
        listEquipments = new ArrayList<>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Equipment equipment = ds.getValue(Equipment.class);
                    equipment.setId(ds.getKey());
                    listEquipments.add(equipment);
                }

                myAdapter = new MyAdapter(root.getContext(), listEquipments);

                recyclerView.setAdapter(myAdapter);
                recyclerView.setHasFixedSize(true);
                reference.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}