import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AppointmentService {
    private final List<Appointment> appointments;
    private final Scanner scanner;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public AppointmentService() {
        this.appointments = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        initializeSampleAppointments();
    }

    private void initializeSampleAppointments() {
        // Add some sample appointments
        appointments.add(new Appointment(1, 1, "Mental Health", 
            LocalDateTime.now().plusHours(2), "Approved"));
        appointments.add(new Appointment(2, 2, "Academic Support", 
            LocalDateTime.now().plusDays(1), "Pending"));
        appointments.add(new Appointment(3, 1, "Financial Aid", 
            LocalDateTime.now().minusDays(1), "Completed"));
    }
    
    public List<Appointment> getAllAppointments() {
        return this.appointments;
    }

    // Feature A: Book new appointment
    public void bookAppointment(int studentId) {
        System.out.println("\n--- Request Support Session ---");
        System.out.println("1. Mental Health");
        System.out.println("2. Academic Support");
        System.out.println("3. Financial Aid");
        System.out.print("Select category (1-3): ");
        
        int categoryChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        String category;
        switch (categoryChoice) {
            case 1 -> category = "Mental Health";
            case 2 -> category = "Academic Support";
            case 3 -> category = "Financial Aid";
            default -> {
                System.out.println("Invalid choice. Defaulting to Academic Support.");
                category = "Academic Support";
            }
        }

        System.out.print("Enter date and time (yyyy-MM-dd HH:mm): ");
        String dateInput = scanner.nextLine();
        LocalDateTime dateTime = LocalDateTime.parse(dateInput, formatter);

        int newId = appointments.size() + 1;
        Appointment newAppointment = new Appointment(newId, studentId, category, dateTime, "Pending");
        appointments.add(newAppointment);

        System.out.println("\nAppointment requested successfully! ID: " + newId);
    }

    public List<Appointment> getCompletedAppointments(int studentId) {
        return appointments.stream()
            .filter(a -> a.getStudentId() == studentId)
            .filter(a -> a.getStatus().equals("Completed"))
            .collect(Collectors.toList());
    }
    
    // Feature B: View student's appointments
    public void viewStudentAppointments(int studentId) {
        System.out.println("\n--- Your Appointments ---");
        
        List<Appointment> studentAppointments = appointments.stream()
            .filter(a -> a.getStudentId() == studentId)
            .collect(Collectors.toList());

        if (studentAppointments.isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }

        System.out.println("ID | Category | Date/Time | Status");
        System.out.println("---------------------------------");
        for (Appointment app : studentAppointments) {
            System.out.printf("%d | %s | %s | %s%n",
                app.getId(),
                app.getCategory(),
                app.getDateTime().format(formatter),
                app.getStatus());
        }
    }

    // Feature D: Check for upcoming appointments (reminders)
    public void checkReminders(int studentId) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tomorrow = now.plusDays(1);
        
        List<Appointment> upcoming = appointments.stream()
            .filter(a -> a.getStudentId() == studentId)
            .filter(a -> a.getDateTime().isAfter(now))
            .filter(a -> a.getDateTime().isBefore(tomorrow))
            .filter(a -> !a.getStatus().equals("Cancelled"))
            .collect(Collectors.toList());

        if (!upcoming.isEmpty()) {
            System.out.println("\n!!!! REMINDER !!!!");
            System.out.println("You have upcoming appointments:");
            upcoming.forEach(app -> System.out.printf(
                "- %s at %s%n", 
                app.getCategory(), 
                app.getDateTime().format(formatter)));
        }
    }

    // Admin feature: View all appointments
    public void viewAllAppointments() {
        System.out.println("\n--- All Appointments ---");
        System.out.println("ID | Student ID | Category | Date/Time | Status");
        System.out.println("------------------------------------------------");
        for (Appointment app : appointments) {
            System.out.printf("%d | %d | %s | %s | %s%n",
                app.getId(),
                app.getStudentId(),
                app.getCategory(),
                app.getDateTime().format(formatter),
                app.getStatus());
        }
    }

    // Admin feature: Manage appointments
    public void manageAppointments() {
        viewAllAppointments();
        System.out.print("\nEnter appointment ID to manage: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Appointment appointment = appointments.stream()
            .filter(a -> a.getId() == id)
            .findFirst()
            .orElse(null);

        if (appointment == null) {
            System.out.println("Appointment not found.");
            return;
        }

        System.out.println("\n1. Approve");
        System.out.println("2. Reschedule");
        System.out.println("3. Cancel");
        System.out.print("Select action: ");
        int action = scanner.nextInt();
        scanner.nextLine();

        switch (action) {
            case 1 -> {
                // Approve
                System.out.print("Enter admin ID: ");
                int adminId = scanner.nextInt();
                scanner.nextLine();
                appointment.setAdminId(adminId);
                appointment.setStatus("Approved");
                System.out.println("Appointment approved.");
            }
                
            case 2 -> {
                // Reschedule
                System.out.print("Enter new date/time (yyyy-MM-dd HH:mm): ");
                String newDate = scanner.nextLine();
                appointment.setDateTime(LocalDateTime.parse(newDate, formatter));
                System.out.println("Appointment rescheduled.");
            }
                
            case 3 -> {
                // Cancel
                appointment.setStatus("Cancelled");
                System.out.println("Appointment cancelled.");
            }
                
            default -> System.out.println("Invalid action.");
        }
    }
}