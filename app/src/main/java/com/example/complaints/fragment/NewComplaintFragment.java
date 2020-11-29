package com.example.complaints.fragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.complaints.R;
import com.example.complaints.model.Complaint;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tapadoo.alerter.Alerter;
import java.util.Objects;

public class NewComplaintFragment extends Fragment {
    private EditText txt_name, txt_address;
    private FirebaseAuth assistant;

    @Override
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_complaint, container, false);

        txt_name = view.findViewById(R.id.new_complaint_txt_name);
        txt_address = view.findViewById(R.id.new_complaint_txt_address);
        assistant = FirebaseAuth.getInstance();

        Button new_complaint = view.findViewById(R.id.new_complaint_new_complaint);
        new_complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create();
            }
        });

        return view;
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void create() {
        String name = txt_name.getText().toString();
        String address = txt_address.getText().toString();
        String uid = Objects.requireNonNull(assistant.getCurrentUser()).getUid();
        String error = "";

        if(name.isEmpty() && address.isEmpty()) {
            error = "Verifica los Campos";
        } else {
            if(name.isEmpty()) {
                error = "Verifica el Nombre";
            }

            if(address.isEmpty()) {
                error = "Verifica la Dirección";
            }
        }

        if(error.isEmpty()) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference("complaint").child(uid);

            Complaint complaint = new Complaint();
            complaint.setName(name);
            complaint.setAddress(address);
            complaint.setState(false);

            reference.push().setValue(complaint);

            txt_name.setText("");
            txt_address.setText("");

            Alerter.create(Objects.requireNonNull(getActivity())).setTitle("Atención").setText("Denuncia Creada con Exito")
                    .setIcon(R.drawable.ic_checking).setBackgroundColorRes(R.color.colorPrimaryDark)
                    .setDuration(3000).show();
        } else {
            Alerter.create(Objects.requireNonNull(getActivity())).setTitle("Atención").setText(error)
                    .setIcon(R.drawable.ic_withoutchecking).setBackgroundColorRes(R.color.colorPrimaryDark)
                    .setDuration(3000).show();
        }
    }
}