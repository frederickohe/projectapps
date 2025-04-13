
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AnalyticsService {
    private final AppointmentService appointmentService;
    private final FeedbackService feedbackService;
    private final Scanner scanner;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public AnalyticsService(AppointmentService appointmentService, FeedbackService feedbackService) {
        this.appointmentService = appointmentService;
        this.feedbackService = feedbackService;
        this.scanner = new Scanner(System.in);
    }

    public void showAnalyticsMenu() {
        while (true) {
            System.out.println("\n--- Analytics Dashboard ---");
            System.out.println("1. Appointment Trends");
            System.out.println("2. Feedback Analysis");
            System.out.println("3. Return to Main Menu");
            System.out.print("Choose option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> showAppointmentTrends();
                case 2 -> showFeedbackAnalysis();
                case 3 -> {
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    // j.1) Appointment Trends Analysis
    private void showAppointmentTrends() {
        System.out.println("\n=== Appointment Trends ===");
        
        List<Appointment> allAppointments = appointmentService.getAllAppointments();
        if (allAppointments.isEmpty()) {
            System.out.println("No appointment data available.");
            return;
        }

        // 1. Total appointments
        System.out.println("\n1. Total Appointments: " + allAppointments.size());

        // 2. Most requested categories
        System.out.println("\n2. Most Requested Categories:");
        Map<String, Long> categoryCounts = allAppointments.stream()
            .collect(Collectors.groupingBy(
                Appointment::getCategory,
                Collectors.counting()
            ));
        
        categoryCounts.forEach((category, count) -> 
            System.out.printf("- %s: %d bookings%n", category, count));

        // 3. Peak appointment times
        System.out.println("\n3. Peak Appointment Times:");
        Map<Integer, Long> hourCounts = allAppointments.stream()
            .collect(Collectors.groupingBy(
                app -> app.getDateTime().getHour(),
                Collectors.counting()
            ));
        
        hourCounts.entrySet().stream()
            .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
            .limit(3)
            .forEach(entry -> 
                System.out.printf("- %02d:00-%02d:00: %d appointments%n", 
                    entry.getKey(), entry.getKey()+1, entry.getValue()));

        // 4. Weekly/Monthly trends
        System.out.println("\n4. Booking Trends Over Time:");
        Map<String, Long> weeklyTrends = allAppointments.stream()
            .collect(Collectors.groupingBy(
                app -> dateFormatter.format(app.getDateTime()),
                Collectors.counting()
            ));
        
        System.out.println("Daily Appointments:");
        weeklyTrends.forEach((date, count) -> 
            System.out.printf("- %s: %d appointments%n", date, count));
    }

    // j.2) Feedback Scores Analysis
    private void showFeedbackAnalysis() {
        System.out.println("\n=== Feedback Analysis ===");
        
        List<Feedback> allFeedback = feedbackService.getAllFeedback();
        if (allFeedback.isEmpty()) {
            System.out.println("No feedback data available.");
            return;
        }

        // 1. Average rating
        double average = allFeedback.stream()
            .mapToInt(Feedback::getRating)
            .average()
            .orElse(0.0);
        System.out.printf("\n1. Average Rating: %.1f/5 stars%n", average);

        // 2. Positive vs Negative reviews
        long positive = allFeedback.stream()
            .filter(f -> f.getRating() >= 4)
            .count();
        long negative = allFeedback.stream()
            .filter(f -> f.getRating() <= 2)
            .count();
        System.out.printf("2. Feedback Sentiment:\n- Positive (4-5 stars): %d\n- Negative (1-2 stars): %d%n", 
            positive, negative);

        // 3. Common concerns from comments
        System.out.println("\n3. Common Concerns from Comments:");
        List<String> concerns = allFeedback.stream()
            .filter(f -> f.getRating() <= 3)
            .filter(f -> f.getComments() != null && !f.getComments().isEmpty())
            .map(Feedback::getComments)
            .collect(Collectors.toList());
        
        if (concerns.isEmpty()) {
            System.out.println("No concerning comments found.");
        } else {
            System.out.println("Recent concerning feedback:");
            concerns.stream().limit(5).forEach(c -> System.out.println("- " + c));
        }
    }
}