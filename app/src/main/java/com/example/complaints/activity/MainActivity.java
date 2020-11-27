package com.example.complaints.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.complaints.R;
import com.example.complaints.model.User;
import com.facebook.login.LoginManager;
import com.google.android.material.tabs.TabLayout;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.example.complaints.ui.main.SectionsPagerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    TextView title;

    @Override
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAuth assistant = FirebaseAuth.getInstance();
        FirebaseUser user = assistant.getCurrentUser();

        if(user == null) {
            goToLogin();
        } else {
            title = findViewById(R.id.main_title);

            if(user.getDisplayName() == null || user.getDisplayName().isEmpty()) {
                loadName(Objects.requireNonNull(user).getUid());
            } else {
                title.setText(user.getDisplayName());
            }

            SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(
                    this, getSupportFragmentManager()
            );

            ViewPager viewPager = findViewById(R.id.main_view_pager);
            viewPager.setAdapter(sectionsPagerAdapter);

            TabLayout tabs = findViewById(R.id.main_tabs);
            tabs.setupWithViewPager(viewPager);
        }
    }

    public void loadName(final String uid) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("user");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot result : dataSnapshot.getChildren()) {
                    if (dataSnapshot.exists()) {
                        User user = result.getValue(User.class);

                        if(uid.equals(Objects.requireNonNull(user).getId())) {
                            title.setText(user.getName());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("MainActivity: ", error.toString());
            }
        });
    }

    private  void goToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void signOff(View view) {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        goToLogin();
    }

    public void goToCreator(View view) {
        Intent intent = new Intent(MainActivity.this, CreatorActivity.class);
        startActivity(intent);
        finish();
    }
}