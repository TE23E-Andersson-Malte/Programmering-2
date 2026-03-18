import java.util.HashMap;

public class App {
    public static void main(String[] args) throws Exception {
        
        HashMap<String, Vara> sortiment = new HashMap<>();

        sortiment.put("kaffe", new Vara("Kaffe", 25.0, 5));
        sortiment.put("bulle", new Vara("Bulle", 20.0, 5));
        sortiment.put("läsk", new Vara("Läsk", 15.0, 3));
        sortiment.put("glass", new Vara("Glass", 10.0, 3));
        sortiment.put("godispåse", new Vara("Godispåse", 10.0, 3));

        while (true) {
            IO.println("""
                    \n---KIOSK---
                    1. Visa varor
                    2. Sälj vara
                    3. Avsluta
                    Val: 
                    """);
            int val = Integer.parseInt(IO.readln());
            switch (val) {
                case 1:
                    IO.println(sortiment);
                    break;
                case 2:
                    IO.println("Ange varans namn: ");
                    String namn = IO.readln().toLowerCase();
                    break;
                case 3:
                    break;
                default:
                    break;
            }
        }
    }
}
