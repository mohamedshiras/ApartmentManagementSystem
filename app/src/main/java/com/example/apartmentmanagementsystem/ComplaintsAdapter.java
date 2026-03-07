package com.example.apartmentmanagementsystem;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ComplaintsAdapter extends RecyclerView.Adapter<ComplaintsAdapter.ComplaintViewHolder> {

    private List<Complaint> complaintsList;
    private List<Complaint> filteredList;

    public ComplaintsAdapter(List<Complaint> complaintsList) {
        this.complaintsList = complaintsList;
        this.filteredList = new ArrayList<>(complaintsList);
    }

    @NonNull
    @Override
    public ComplaintViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_complaint, parent, false);
        return new ComplaintViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComplaintViewHolder holder, int position) {
        Complaint complaint = filteredList.get(position);
        holder.title.setText(complaint.getTitle());
        holder.details.setText("Unit " + complaint.getUnit() + " · " + complaint.getTime());
        holder.category.setText(complaint.getCategory());
        holder.priority.setText(complaint.getPriority() + " Priority");
        holder.status.setText(complaint.getStatus());

        // Color coding for Status
        if (complaint.getStatus().equals("PENDING")) {
            holder.status.setTextColor(Color.parseColor("#F59E0B"));
        } else {
            holder.status.setTextColor(Color.parseColor("#10B981"));
        }

        // Color coding for Priority
        if (complaint.getPriority().equals("Emergency") || complaint.getPriority().equals("High")) {
            holder.priority.setTextColor(Color.parseColor("#EF4444"));
        } else {
            holder.priority.setTextColor(Color.parseColor("#64748B"));
        }
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public void filter(String query, String statusFilter) {
        filteredList.clear();
        for (Complaint complaint : complaintsList) {
            boolean matchesQuery = complaint.getTitle().toLowerCase().contains(query.toLowerCase());
            boolean matchesStatus = statusFilter.equals("All") || complaint.getStatus().equalsIgnoreCase(statusFilter);

            if (matchesQuery && matchesStatus) {
                filteredList.add(complaint);
            }
        }
        notifyDataSetChanged();
    }

    public static class ComplaintViewHolder extends RecyclerView.ViewHolder {
        TextView title, details, category, priority, status;

        public ComplaintViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.complaintTitle);
            details = itemView.findViewById(R.id.complaintDetails);
            category = itemView.findViewById(R.id.complaintCategory);
            priority = itemView.findViewById(R.id.complaintPriority);
            status = itemView.findViewById(R.id.complaintStatus);
        }
    }
}