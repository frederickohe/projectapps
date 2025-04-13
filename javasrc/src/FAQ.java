
public class FAQ {
    private final int id;
    private String question;
    private String answer;
    private String category; // e.g., "Mental Health", "Financial Aid", "General"

    public FAQ(int id, String question, String answer, String category) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.category = category;
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getQuestion() { return question; }
    public String getAnswer() { return answer; }
    public String getCategory() { return category; }

    public void setQuestion(String question) { this.question = question; }
    public void setAnswer(String answer) { this.answer = answer; }
    public void setCategory(String category) { this.category = category; }
}