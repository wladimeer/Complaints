package com.example.complaints.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.complaints.R;
import com.example.complaints.model.Complaint;
import java.util.List;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.ComplaintHolder> {
    private List<Complaint> complaintList;
    private int layout;

    public ComplaintAdapter(Activity activity, List<Complaint> complaintList, int layout) {
        this.complaintList = complaintList;
        this.layout = layout;
    }

    @NonNull
    @Override
    public ComplaintHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);

        return new ComplaintHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComplaintHolder holder, int position) {
        Complaint complaint = complaintList.get(position);
        holder.name.setText(complaint.getName());
        holder.address.setText(complaint.getAddress());
    }

    @Override
    public int getItemCount() {
        return complaintList.size();
    }

    public static class ComplaintHolder extends RecyclerView.ViewHolder {
        TextView name, address;

        public ComplaintHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_complaint_name);
            address = itemView.findViewById(R.id.item_complaint_address);
        }
    }
}