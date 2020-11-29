package com.example.complaints.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import com.example.complaints.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth.AuthStateListener listener;
    private CallbackManager callbackManager;
    private EditText txt_email, txt_password;
    private FirebaseAuth assistant;
    private RelativeLayout view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        view = findViewById(R.id.login_view);

        assistant = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();
        final FirebaseUser user = assistant.getCurrentUser();

        final LoginButton loginButton = findViewById(R.id.login_facebook);
        loginButton.setReadPermissions("email");

        if(user == null) {
            txt_email = findViewById(R.id.login_txt_email);
            txt_password = findViewById(R.id.login_txt_password);
        } else {
            goToMainMenu();
        }

        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(user != null) {
                    goToMainMenu();
                }
            }
        };

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loginFacebook(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                loadMessage("Operación Cancelada");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d("LoginActivity: ", exception.toString());
            }
        });
    }

    public void loadMessage(String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
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
                            if(task.isSuccessful()) {
                                loadMessage("Iniciando Sesión...");

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        goToMainMenu();
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

    private void loginFacebook(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        assistant.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    goToMainMenu();
                } else {
                    loadMessage("Ocurrió un Error");
                }
            }
        });
    }

    public void goToMainMenu() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToRegister(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        assistant.addAuthStateListener(listener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        assistant.removeAuthStateListener(listener);
    }
}