package com.example.appguitarhub.ui.edit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.appguitarhub.R;
import com.example.appguitarhub.model.Equipment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditFragment extends Fragment {

    private TextInputEditText edtName;
    private TextInputEditText edtDescription;
    private TextInputEditText edtType;
    private TextInputEditText edtContact;
    private Button btnEdit;
    private String key;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit, container, false);

        Bundle bundle = getArguments();
        edtName = root.findViewById(R.id.edtName);
        edtName.setText(bundle.getString("NAME"));
        edtDescription = root.findViewById(R.id.edtDescription);
        edtDescription.setText(bundle.getString("DESCRIPTION"));
        edtType = root.findViewById(R.id.edtType);
        edtType.setText(bundle.getString("TYPE"));
        edtContact = root.findViewById(R.id.edtContact);
        edtContact.setText(bundle.getString("CONTACT"));
        btnEdit = root.findViewById(R.id.btnEdit);
        key = bundle.getString("KEY");

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editItem();
            }
        });

        return root;
    }

    private void editItem() {
        new AlertDialog.Builder(getContext())
            .setTitle("Editando equipamento")
            .setMessage("Tem certeza que deseja editar esse equipamento?")
            .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("equipments").child(key);

                    Equipment e = new Equipment(edtName.getText().toString(), edtDescription.getText().toString(), edtType.getText().toString(), edtContact.getText().toString());
                    reference.setValue(e);

                    Snackbar.make(getView(), "Item editado", Snackbar.LENGTH_LONG).show();

                    Navigation.findNavController(getView()).navigate(R.id.action_nav_edit_to_nav_listing);
                }
            })
            .setNegativeButton("Não", null).show();
    }
}