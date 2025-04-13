
public abstract class User {
    private final int id;
    private final String username;
    private final String password;
    private final String name;
    private final String email;

    // Constructor, getters, and setters
    public User(int id, String username, String password, String name, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    // Getters (omitted setters for brevity)
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public String getEmail() { return email; }
}