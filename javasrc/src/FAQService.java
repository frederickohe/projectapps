import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FAQService {
    private final List<FAQ> faqList;
    private final Scanner scanner;

    public FAQService() {
        this.faqList = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        initializeSampleFAQs();
    }

    private void initializeSampleFAQs() {
        faqList.add(new FAQ(1, 
            "How do I access mental health support?", 
            "You can book appointments through CampusAssist or visit the wellness center.", 
            "Mental Health"));
        
        faqList.add(new FAQ(2,
            "What financial aid options are available?",
            "Scholarships, grants, and student loans are available. Visit the financial office.",
            "Financial Aid"));
    }

    // Feature H: View FAQs (for students)
    public void viewFAQs() {
        System.out.println("\n--- Student Welfare FAQs ---");
        
        System.out.println("1. View all FAQs");
        System.out.println("2. View by category");
        System.out.print("Choose option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        switch (choice) {
            case 1 -> displayFAQs(faqList);
            case 2 -> viewByCategory();
            default -> {
                System.out.println("Invalid choice. Showing all FAQs.");
                displayFAQs(faqList);
            }
        }
    }

    private void viewByCategory() {
        List<String> categories = faqList.stream()
            .map(FAQ::getCategory)
            .distinct()
            .collect(Collectors.toList());

        System.out.println("\nAvailable categories:");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i+1) + ". " + categories.get(i));
        }
        
        System.out.print("Select category: ");
        int catChoice = scanner.nextInt();
        scanner.nextLine();
        
        if (catChoice > 0 && catChoice <= categories.size()) {
            String selectedCategory = categories.get(catChoice-1);
            List<FAQ> filtered = faqList.stream()
                .filter(f -> f.getCategory().equals(selectedCategory))
                .collect(Collectors.toList());
            displayFAQs(filtered);
        } else {
            System.out.println("Invalid choice. Showing all FAQs.");
            displayFAQs(faqList);
        }
    }

    private void displayFAQs(List<FAQ> faqs) {
        if (faqs.isEmpty()) {
            System.out.println("No FAQs available in this category.");
            return;
        }

        System.out.println("\nID | Category | Question | Answer");
        System.out.println("----------------------------------------");
        faqs.forEach(f -> 
            System.out.printf("%d | %s | %s | %s%n",
                f.getId(),
                f.getCategory(),
                f.getQuestion(),
                f.getAnswer()));
    }

    // Feature I: Manage FAQs (for admins)
    public void manageFAQs() {
        while (true) {
            System.out.println("\n--- FAQ Management ---");
            System.out.println("1. View all FAQs");
            System.out.println("2. Add new FAQ");
            System.out.println("3. Edit FAQ");
            System.out.println("4. Delete FAQ");
            System.out.println("5. Back to main menu");
            System.out.print("Choose option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> displayFAQs(faqList);
                case 2 -> addFAQ();
                case 3 -> editFAQ();
                case 4 -> deleteFAQ();
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void addFAQ() {
        System.out.println("\n--- Add New FAQ ---");
        System.out.print("Enter question: ");
        String question = scanner.nextLine();
        
        System.out.print("Enter answer: ");
        String answer = scanner.nextLine();
        
        System.out.print("Enter category: ");
        String category = scanner.nextLine();
        
        int newId = faqList.isEmpty() ? 1 : faqList.get(faqList.size()-1).getId() + 1;
        faqList.add(new FAQ(newId, question, answer, category));
        System.out.println("FAQ added successfully!");
    }

    private void editFAQ() {
        displayFAQs(faqList);
        System.out.print("\nEnter FAQ ID to edit: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        FAQ faq = faqList.stream()
            .filter(f -> f.getId() == id)
            .findFirst()
            .orElse(null);
            
        if (faq == null) {
            System.out.println("FAQ not found.");
            return;
        }
        
        System.out.println("Editing FAQ #" + id);
        System.out.println("1. Edit question");
        System.out.println("2. Edit answer");
        System.out.println("3. Edit category");
        System.out.print("Choose field to edit: ");
        int field = scanner.nextInt();
        scanner.nextLine();
        
        switch (field) {
            case 1 -> {
                System.out.print("Enter new question: ");
                faq.setQuestion(scanner.nextLine());
            }
            case 2 -> {
                System.out.print("Enter new answer: ");
                faq.setAnswer(scanner.nextLine());
            }
            case 3 -> {
                System.out.print("Enter new category: ");
                faq.setCategory(scanner.nextLine());
            }
            default -> {
                System.out.println("Invalid choice.");
                return;
            }
        }
        System.out.println("FAQ updated successfully!");
    }

    private void deleteFAQ() {
        displayFAQs(faqList);
        System.out.print("\nEnter FAQ ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        FAQ faq = faqList.stream()
            .filter(f -> f.getId() == id)
            .findFirst()
            .orElse(null);
            
        if (faq != null) {
            faqList.remove(faq);
            System.out.println("FAQ deleted successfully!");
        } else {
            System.out.println("FAQ not found.");
        }
    }
}