package com.example.complaints.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import com.example.complaints.R;
import com.example.complaints.model.Complaint;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.List;
import java.util.Objects;

public class MyComplaintsAdapter extends RecyclerView.Adapter<MyComplaintsAdapter.MyComplaintsHolder> {
    private List<Complaint> complaintList;
    private int layout;

    public MyComplaintsAdapter(Activity activity, List<Complaint> complaintList, int layout) {
        this.complaintList = complaintList;
        this.layout = layout;
    }

    public static class MyComplaintsHolder extends RecyclerView.ViewHolder {
        TextView id, name, address;
        DatabaseReference reference;
        ImageView icon, delete;

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public MyComplaintsHolder(@NonNull final View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.item_my_complaints_id);
            name = itemView.findViewById(R.id.item_my_complaints_name);
            address = itemView.findViewById(R.id.item_my_complaints_address);
            icon = itemView.findViewById(R.id.item_my_complaints_icon);
            delete = itemView.findViewById(R.id.item_my_complaints_delete);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = Objects.requireNonNull(Objects.requireNonNull(user).getUid());
            reference = database.getReference("complaint").child(uid);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delete(id.getText().toString());
                }
            });
        }

        private void delete(String id) {
            reference.child(id).removeValue();

            Snackbar snackbar = Snackbar.make(itemView, "Denuncia Eliminada", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
    }

    @NonNull
    @Override
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public MyComplaintsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new MyComplaintsHolder(view);
    }

    @Override
    @SuppressLint("SetTextI18n")
    public void onBindViewHolder(@NonNull MyComplaintsHolder holder, int position) {
        Complaint complaint = complaintList.get(position);

        if(complaint.getId().equals("Empty")) {
            holder.icon.setImageResource(R.drawable.ic_alert);
            holder.icon.setColorFilter(Color.parseColor("#D1D12B"));
        } else {
            if(complaint.isState()) {
                holder.icon.setImageResource(R.drawable.ic_checking);
                holder.icon.setColorFilter(Color.parseColor("#39D119"));
            } else {
                holder.icon.setImageResource(R.drawable.ic_withoutchecking);
                holder.icon.setColorFilter(Color.parseColor("#D12919"));
            }

            holder.delete.setImageResource(R.drawable.ic_delete);
            holder.delete.setColorFilter(Color.parseColor("#000000"));
        }

        holder.id.setText(complaint.getId());
        holder.name.setText(complaint.getName());
        holder.address.setText(complaint.getAddress());
    }

    @Override
    public int getItemCount() {
        return complaintList.size();
    }
}