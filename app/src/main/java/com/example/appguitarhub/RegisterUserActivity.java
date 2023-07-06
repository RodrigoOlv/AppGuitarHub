package com.example.appguitarhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterUserActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextInputEditText email;
    private TextInputEditText password;
    private TextInputEditText confPass;
    private Button register;
    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        email = findViewById(R.id.edtEmail);
        password = findViewById(R.id.edtPassword);
        confPass = findViewById(R.id.edtPassword);
        register = findViewById(R.id.btnRegister);
        cancel = findViewById(R.id.btnCancel);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (
                    !email.getText().toString().equals("") &&
                    !password.getText().toString().equals("") &&
                    !confPass.getText().toString().equals("")
                ) {
                    if ( password.getText().toString().equals(confPass.getText().toString()) ) {
                        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if ( task.isSuccessful() ) {
                                            Intent intent = new Intent(RegisterUserActivity.this, LoginActivity.class);
                                            startActivity(intent);

                                            Snackbar.make(view, "Sucesso", Snackbar.LENGTH_LONG).show();
                                        }
                                        else
                                            Snackbar.make(view, "Erro ao cadastrar usuário", Snackbar.LENGTH_LONG).show();
                                    }
                                });
                    }
                    else {
                        Snackbar.make(view, "Senha e onfirmação de senha devem ser iguais", Snackbar.LENGTH_LONG).show();
                    }
                }
                else {
                    Snackbar.make(view, "Informe os dados para o cadastro", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();

                Intent intent = new Intent(RegisterUserActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();
    }
}