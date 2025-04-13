import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final AuthService authService = new AuthService();
    private static final AppointmentService appointmentService = new AppointmentService();
    private static final FeedbackService feedbackService = new FeedbackService();
    private static final FAQService faqService = new FAQService();
    private static final AnalyticsService analyticsService = new AnalyticsService(appointmentService, feedbackService);

    public static void main(String[] args) {
        System.out.println("\n=== Welcome to CampusAssist ===");
        showLoginMenu();
    }

    /**
     * Displays the login menu and redirects based on user role (Student/Admin).
     */
    public static void showLoginMenu() {
        while (true) {
            System.out.println("\n--- Login ---");
            System.out.println("\n1. Login as Student");
            System.out.println("2. Login as Admin");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            String input = scanner.nextLine();

            try {
                int choice = Integer.parseInt(input);

                switch (choice) {
                    case 1:
                        User student = authService.loginAsStudent();
                        if (student != null) {
                            showStudentMenu(student);
                        }
                        break;
                    case 2:
                        User admin = authService.loginAsAdmin();
                        if (admin != null) {
                            showAdminMenu(admin);
                        }
                        break;
                    case 3:
                        System.out.println("Exiting CampusAssist. Goodbye!");
                        System.exit(0);
                    default:
                        System.out.println("Invalid option. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    /**
     * Student menu with options to book appointments, view feedback, etc.
     */
    private static void showStudentMenu(User student) {
        while (true) {
            
            System.out.println("\n--- Welcome " + student.getName() + " ---");
            // In showStudentMenu(), after successful login
            appointmentService.checkReminders(student.getId());

            System.out.println("\n1. Request Support Session");
            System.out.println("2. View My Appointments");
            System.out.println("3. Submit Feedback");
            System.out.println("4. View FAQs");
            System.out.println("5. Logout");

            System.out.print("\nChoose an option: ");

            String input = scanner.nextLine();

            try {
                int choice = Integer.parseInt(input);

                switch (choice) {
                    case 1 -> appointmentService.bookAppointment(student.getId());
                    case 2 -> appointmentService.viewStudentAppointments(student.getId());
                    case 3 -> feedbackService.submitFeedback(student.getId());
                    case 4 -> faqService.viewFAQs();
                    case 5 -> {
                        System.out.println("Logging out...");
                        return; // Return to login menu
                    }
                    default -> System.out.println("Invalid option. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    /**
     * Admin menu with options to manage appointments and feedback.
     */
    private static void showAdminMenu(User admin) {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. View All Appointments");
            System.out.println("2. Approve/Reschedule/Cancel Appointments");
            System.out.println("3. View Feedback");
            System.out.println("4. Manage FAQs");
            System.out.println("5. Show Analytics"); 
            System.out.println("6. Logout");
            System.out.print("Choose an option: ");

            String input = scanner.nextLine();

            try {
                int choice = Integer.parseInt(input);

                switch (choice) {
                    case 1 -> appointmentService.viewAllAppointments();
                    case 2 -> appointmentService.manageAppointments();
                    case 3 -> feedbackService.viewAllFeedback();
                    case 4 -> faqService.manageFAQs();
                    case 5 -> analyticsService.showAnalyticsMenu();
                    case 6 -> {
                        System.out.println("Logging out...");
                        return; // Return to login menu
                    }
                    default -> System.out.println("Invalid option. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}