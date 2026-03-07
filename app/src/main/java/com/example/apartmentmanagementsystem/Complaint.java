package com.example.apartmentmanagementsystem;

public class Complaint {
    private String id;
    private String title;
    private String description;
    private String category;
    private String priority;
    private String status;
    private String time;
    private String unit;

    public Complaint(String id, String title, String description, String category, String priority, String status, String time, String unit) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.priority = priority;
        this.status = status;
        this.time = time;
        this.unit = unit;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public String getPriority() { return priority; }
    public String getStatus() { return status; }
    public String getTime() { return time; }
    public String getUnit() { return unit; }
}