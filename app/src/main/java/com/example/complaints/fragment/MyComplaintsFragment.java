package com.example.complaints.fragment;

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.complaints.R;
import com.example.complaints.adapter.MyComplaintsAdapter;
import com.example.complaints.model.Complaint;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyComplaintsFragment extends Fragment {
    private List<Complaint> complaintList;
    private DatabaseReference reference;
    private RecyclerView recycler;

    @Override
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_complaints, container, false);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth assistant = FirebaseAuth.getInstance();
        String uid = Objects.requireNonNull(assistant.getCurrentUser()).getUid();

        recycler = view.findViewById(R.id.my_complaints_recycler);
        reference = database.getReference("complaint").child(uid);
        complaintList = new ArrayList<>();
        loadComplaints();

        return view;
    }

    private void loadComplaints() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                complaintList.clear();

                if(dataSnapshot.exists()) {
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Complaint complaint = snapshot.getValue(Complaint.class);

                        if (complaint != null) {
                            complaint.setId(snapshot.getKey());
                        }

                        complaintList.add(complaint);
                    }
                } else {
                    complaintList.add(new Complaint(
                            "Empty", "No Hay Nada Registrado",
                            "Crea una Nueva Denuncia!", false
                    ));
                }

                MyComplaintsAdapter adapter = new MyComplaintsAdapter(getActivity(), complaintList, R.layout.item_my_complaints);
                LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                manager.setOrientation(RecyclerView.VERTICAL);
                recycler.setLayoutManager(manager);
                recycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("MyComplaintsFragment: ", error.toString());
            }
        });
    }
}