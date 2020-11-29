package com.example.complaints.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.complaints.R;

public class CreatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creator);
    }

    public void goToMainMenu(View view) {
        Intent intent = new Intent(CreatorActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}