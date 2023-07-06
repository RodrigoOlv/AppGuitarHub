package com.example.appguitarhub.ui.register;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.appguitarhub.R;
import com.example.appguitarhub.model.Equipment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterFragment extends Fragment {

    private Button btnRegister;

    View root;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_register, container, false);

        btnRegister = root.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register(view);
            }
        });

        return root;
    }

    public void register(View view) {
        String name = ((EditText) root.findViewById(R.id.edtName)).getText().toString();
        String description = ((EditText) root.findViewById(R.id.edtDescription)).getText().toString();
        String type = ((EditText) root.findViewById(R.id.edtType)).getText().toString();
        String contact = ((EditText) root.findViewById(R.id.edtContact)).getText().toString();

        Equipment equipment = new Equipment(name, description, type, contact);
        DatabaseReference equipments = FirebaseDatabase.getInstance().getReference().child("equipments");

        equipments.push().setValue(equipment)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Navigation.findNavController(view).navigate(R.id.action_nav_register_to_nav_listing);
                        Snackbar.make(view, "Equipamento cadastrado", Snackbar.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(view, "Erro ao cadastrar equipamento", Snackbar.LENGTH_LONG)
                                .setTextColor(Color.RED).show();
                    }
                });
    }
}