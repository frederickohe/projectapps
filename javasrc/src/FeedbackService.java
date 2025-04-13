import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FeedbackService {
    private final List<Feedback> feedbackList;
    private final Scanner scanner;
    private AppointmentService appointmentService;

    public FeedbackService() {
        this.feedbackList = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        initializeSampleFeedback();
    }

    // Connect to AppointmentService for validation
    public void setAppointmentService(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    private void initializeSampleFeedback() {
        feedbackList.add(new Feedback(1, 3, 1, 5, "Very helpful session!"));
        feedbackList.add(new Feedback(2, 1, 2, 4, "Good advice"));
    }

    

    // Feature C: Submit feedback (for students)
    public void submitFeedback(int studentId) {
        System.out.println("\n--- Submit Feedback ---");
        
        // Get completed appointments for this student
        List<Appointment> completedAppointments = appointmentService.getCompletedAppointments(studentId);
        
        if (completedAppointments.isEmpty()) {
            System.out.println("No completed appointments available for feedback.");
            return;
        }

        System.out.println("Select an appointment to provide feedback:");
        completedAppointments.forEach(app -> 
            System.out.printf("%d - %s (on %s)%n", 
                app.getId(), 
                app.getCategory(), 
                app.getDateTime().toString()));

        System.out.print("Enter appointment ID: ");
        int appointmentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Check if feedback already exists
        if (feedbackList.stream().anyMatch(f -> f.getAppointmentId() == appointmentId)) {
            System.out.println("Feedback already submitted for this appointment.");
            return;
        }

        System.out.print("Rating (1-5 stars): ");
        int rating = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (rating < 1 || rating > 5) {
            System.out.println("Invalid rating. Must be between 1-5.");
            return;
        }

        System.out.print("Comments (optional): ");
        String comments = scanner.nextLine();

        int newId = feedbackList.size() + 1;
        Feedback newFeedback = new Feedback(newId, appointmentId, studentId, rating, comments);
        feedbackList.add(newFeedback);

        System.out.println("Thank you for your feedback!");
    }

    // Feature G: View all feedback (for admins)
    public void viewAllFeedback() {
        System.out.println("\n--- All Feedback ---");
        if (feedbackList.isEmpty()) {
            System.out.println("No feedback submitted yet.");
            return;
        }

        System.out.println("ID | Appointment | Student | Rating | Comments | Reviewed");
        System.out.println("--------------------------------------------------------");
        feedbackList.forEach(f -> 
            System.out.printf("%d | %d | %d | %d stars | %s | %s%n",
                f.getId(),
                f.getAppointmentId(),
                f.getStudentId(),
                f.getRating(),
                (f.getComments() != null ? f.getComments() : "-"),
                (f.isReviewed() ? "Yes" : "No")));
    }

    // Feature G: Manage feedback (for admins)
    public void manageFeedback() {
        viewAllFeedback();
        System.out.print("\nEnter feedback ID to manage: ");
        int feedbackId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Feedback feedback = feedbackList.stream()
            .filter(f -> f.getId() == feedbackId)
            .findFirst()
            .orElse(null);

        if (feedback == null) {
            System.out.println("Feedback not found.");
            return;
        }

        System.out.println("\n1. Mark as reviewed");
        System.out.println("2. Update comments");
        System.out.println("3. Delete feedback");
        System.out.print("Select action: ");
        int action = scanner.nextInt();
        scanner.nextLine();

        switch (action) {
            case 1 -> {
                feedback.setReviewed(true);
                System.out.println("Marked as reviewed.");
            }
            case 2 -> {
                System.out.print("Enter new comments: ");
                feedback.setComments(scanner.nextLine());
                System.out.println("Comments updated.");
            }
            case 3 -> {
                feedbackList.remove(feedback);
                System.out.println("Feedback deleted.");
            }
            default -> System.out.println("Invalid action.");
        }
    }

    // Helper method for analytics
    public List<Feedback> getAllFeedback() {
        return this.feedbackList;
    }
}