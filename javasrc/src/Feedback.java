public class Feedback {
    private final int id;
    private final int appointmentId;
    private final int studentId;
    private final int rating; // 1-5
    private String comments;
    private boolean isReviewed;

    public Feedback(int id, int appointmentId, int studentId, int rating, String comments) {
        this.id = id;
        this.appointmentId = appointmentId;
        this.studentId = studentId;
        this.rating = rating;
        this.comments = comments;
        this.isReviewed = false;
    }

    // Getters and Setters
    public int getId() { return id; }
    public int getAppointmentId() { return appointmentId; }
    public int getStudentId() { return studentId; }
    public int getRating() { return rating; }
    public String getComments() { return comments; }
    public boolean isReviewed() { return isReviewed; }

    public void setReviewed(boolean reviewed) { isReviewed = reviewed; }
    public void setComments(String comments) { this.comments = comments; }
}