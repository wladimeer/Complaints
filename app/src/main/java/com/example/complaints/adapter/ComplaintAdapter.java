package com.example.complaints.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

    public static class ComplaintHolder extends RecyclerView.ViewHolder {
        TextView name, address;
        ImageView icon;

        public ComplaintHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_complaint_name);
            address = itemView.findViewById(R.id.item_complaint_address);
            icon = itemView.findViewById(R.id.item_complaint_icon);
        }
    }

    @NonNull
    @Override
    public ComplaintHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ComplaintHolder(view);
    }

    @Override
    @SuppressLint("SetTextI18n")
    public void onBindViewHolder(@NonNull ComplaintHolder holder, int position) {
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
        }

        holder.name.setText(complaint.getName());
        holder.address.setText(complaint.getAddress());
    }

    @Override
    public int getItemCount() {
        return complaintList.size();
    }
}