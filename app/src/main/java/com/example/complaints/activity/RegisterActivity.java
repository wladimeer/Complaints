package com.example.complaints.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import com.example.complaints.R;
import com.example.complaints.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private EditText txt_email, txt_name, txt_cellphone, txt_password;
    private FirebaseAuth assistant;
    private RelativeLayout view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        view = findViewById(R.id.register_view);
        txt_email = findViewById(R.id.register_txt_email);
        txt_name = findViewById(R.id.register_txt_name);
        txt_cellphone = findViewById(R.id.register_txt_cellphone);
        txt_password = findViewById(R.id.register_txt_password);
        assistant = FirebaseAuth.getInstance();
    }

    public void loadMessage(String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    public void register(View view) {
        final String email = txt_email.getText().toString();
        final String name = txt_name.getText().toString();
        final String cellphone = txt_cellphone.getText().toString();
        final String password = txt_password.getText().toString();
        String error = "";

        if(email.isEmpty() && name.isEmpty() && cellphone.isEmpty() && password.isEmpty()) {
            error = "Verifica los Campos";
        } else {
            if(email.isEmpty()) {
                error = "Verifica el Correo" + "\n";
            }

            if(name.isEmpty()) {
                error += "Verifica el Nombre" + "\n";
            }

            if(cellphone.isEmpty()) {
                error += "Verifica el Celular" + "\n";
            }

            if(password.isEmpty()) {
                error += "Verifica la Contraseña";
            }
        }

        if(error.isEmpty()) {
            assistant.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {

                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference reference = database.getReference("user");

                                User user = new User(
                                        Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getUser()).getUid(),
                                        email, name, cellphone, password
                                );

                                reference.push().setValue(user);

                                txt_email.setText("");
                                txt_name.setText("");
                                txt_cellphone.setText("");
                                txt_password.setText("");

                                loadMessage("Cuenta Creada, Iniciando Sesión...");

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }, 3000);
                            } else {
                                loadMessage("Verifica los Datos Ingresados");
                            }
                        }
                    });
        } else {
            loadMessage(error);
        }
    }

    public void goToLogin(View view) {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}