package com.example.appguitarhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextInputEditText email;
    private TextInputEditText password;
    private Button login;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.edtEmail);
        password = findViewById(R.id.edtPassword);
        login = findViewById(R.id.btnLogin);
        register = findViewById(R.id.btnRegister);

        if ( isLogged() ) {
            openMainWindow();
        }
        else {
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ( !email.getText().equals("") && !password.getText().toString().equals("") )
                        valiidateLogin(view, email.getText().toString(), password.getText().toString());
                    else
                        Snackbar.make(view, "Informe email e senha", Snackbar.LENGTH_LONG).show();
                }
            });
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterUserActivity.class);
                startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();
    }

    private Boolean isLogged() {
        Log.d("Login", "instance" + FirebaseAuth.getInstance());

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser == null)
            return false;

        return true;
    }

    private void openMainWindow() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void valiidateLogin(View view, String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if ( task.isSuccessful() ) {
                    openMainWindow();
                    Snackbar.make(view, "Sucesso", Snackbar.LENGTH_LONG).show();
                }
                else {
                    Snackbar.make(view, "Dados inválidos", Snackbar.LENGTH_LONG).show();
                    Log.d("LOGIN", "dados inválidos!");
                }
            }
        });
    }
}