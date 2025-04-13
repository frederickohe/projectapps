import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AuthService {
    private List<Student> students;  // Hardcoded student data
    private List<Admin> admins;      // Hardcoded admin data
    private final Scanner scanner = new Scanner(System.in);

    public AuthService() {
        // Initialize hardcoded data
        initializeUsers();
    }

    /**
     * Preloads sample students and admins for testing.
     */
    private void initializeUsers() {
        students = new ArrayList<>();
        admins = new ArrayList<>();

        // Add sample students
        students.add(new Student(1, "student1", "password1", "Alice", "alice@uni.edu"));
        students.add(new Student(2, "student2", "password2", "Bob", "bob@uni.edu"));

        // Add sample admins
        admins.add(new Admin(101, "admin1", "admin123", "Dr. Smith", "smith@uni.edu"));
        admins.add(new Admin(102, "admin2", "admin456", "Dr. Jones", "jones@uni.edu"));
    }

    /**
     * Logs in a student by validating credentials.
     * @return Authenticated Student object, or null if failed.
     */
    public User loginAsStudent() {
        System.out.println("\n--- Student Login ---");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        for (Student student : students) {
            if (student.getUsername().equals(username) && student.getPassword().equals(password)) {
                System.out.println("Login successful! Welcome, " + student.getName() + ".");
                return student;
            }
        }
        System.out.println("\nInvalid username or password.");
        return null;
    }

    /**
     * Logs in an admin by validating credentials.
     * @return Authenticated Admin object, or null if failed.
     */
    public User loginAsAdmin() {
        System.out.println("\n--- Admin Login ---");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        for (Admin admin : admins) {
            if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
                System.out.println("Login successful! Welcome, " + admin.getName() + ".");
                return admin;
            }
        }
        System.out.println("\nInvalid username or password.");
        return null;
    }
}