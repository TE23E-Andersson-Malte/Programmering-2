public class User {
    private String name;
    private String user_name;
    private String pass_word;

    public User(String name, String user_name, String pass_word) {
        this.name = name;
        this.user_name = user_name;
        this.pass_word = pass_word;
    }

    // Getters
    public String getName() { return name; }
    public String getUser_name() { return user_name; }
    public String getPass_word() { return pass_word; }

    // En setter för att kunna byta lösenord
    public void setPass_word(String pass_word) { this.pass_word = pass_word; }

    @Override
    public String toString() {
        return "Användare: " + name + " (@" + user_name + ")";
    }
}