import java.util.*;

public class App {
    public static void main(String[] args) throws Exception {
        User u1 = new User("Anna And", "anna90", "hemlig");
        User u2 = new User("Erik Bak","ebak", "abc123");
        User u3 = new User("Tom To", "toto", "ssshhh");
        User u4 = new User("Maria", "M99", "vetej");

        Map<String, User> users = new HashMap<>();

        users.put(u1.getUser_name(), u1);
        users.put(u2.getUser_name(), u2);
        users.put(u3.getUser_name(), u3);
        users.put(u4.getUser_name(), u4);

        String username = IO.readln("Ange user_name: ");
        String pass_wd = IO.readln("Ange password: ");

        User user = users.get(username);
        if (user != null && user.getPass_word().equals(pass_wd)) {
            IO.println("Du är inloggad, välkommen " + users.get(username));
        } else {
            IO.println("Fel user eller passwd");
        }
    }
}
