import java.time.LocalDateTime;

public class Appointment {
    private final int id;
    private final int studentId;
    private Integer adminId; // Nullable (unassigned appointments)
    private final String category; // "Mental Health", "Academic Support", "Financial Aid"
    private LocalDateTime dateTime;
    private String status; // "Pending", "Approved", "Cancelled", "Completed"
    private String notes;

    public Appointment(int id, int studentId, String category, LocalDateTime dateTime, String status) {
        this.id = id;
        this.studentId = studentId;
        this.category = category;
        this.dateTime = dateTime;
        this.status = status;
    }

    // Getters and Setters
    public int getId() { return id; }
    public int getStudentId() { return studentId; }
    public Integer getAdminId() { return adminId; }
    public String getCategory() { return category; }
    public LocalDateTime getDateTime() { return dateTime; }
    public String getStatus() { return status; }
    public String getNotes() { return notes; }

    public void setAdminId(Integer adminId) { this.adminId = adminId; }
    public void setStatus(String status) { this.status = status; }
    public void setNotes(String notes) { this.notes = notes; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
}