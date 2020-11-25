package com.example.complaints.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.example.complaints.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText txt_email, txt_password;
    private FirebaseAuth assistant;
    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txt_email = findViewById(R.id.login_txt_email);
        txt_password = findViewById(R.id.login_txt_password);
        message = findViewById(R.id.login_message);
        assistant = FirebaseAuth.getInstance();
    }

    public void login(View view) {
        String email = txt_email.getText().toString();
        String password = txt_password.getText().toString();
        String error = "";

        if(email.isEmpty() && password.isEmpty()) {
            error = "Verifica los Campos";
        } else {
            if(email.isEmpty()) {
                error = "Verifica el Correo";
            }

            if(password.isEmpty()) {
                error = "Verifica la Contraseña";
            }
        }

        if(error.isEmpty()) {
            assistant.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
//                                Intent intent = new Intent(
//                                        LoginActivity.this, MainActivity.class
//                                );
//                                startActivity(intent);
//                                finish();
                            } else {
//                                Toast.makeText(
//                                        LoginActivity.this,
//                                        "Hubo un Error al Iniciar Sesión"+ task.getException(),
//                                        Toast.LENGTH_SHORT
//                                ).show();
                            }
                        }
                    });
        } else {
            message.setText(error);
            message.setTextColor(Color.parseColor("#D81F1F"));
        }
    }

    public void goToRegister(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}