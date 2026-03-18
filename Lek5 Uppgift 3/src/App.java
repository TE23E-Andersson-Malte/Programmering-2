
import java.util.*;

public class App {
    public static void main(String[] args) throws Exception {
        
        Map<String, Vara> sortiment = new HashMap<>();

        sortiment.put("kaffe", new Vara("Kaffe", 25.0, 5));
        sortiment.put("bulle", new Vara("Bulle", 20.0, 5));
        sortiment.put("läsk", new Vara("Läsk", 15.0, 3));
        sortiment.put("glass", new Vara("Glass", 10.0, 3));
        sortiment.put("godispåse", new Vara("Godispåse", 10.0, 3));

        double vinst = 0;

        while (true) {
            IO.print("""
                    \n---KIOSK---
                    1. Visa varor
                    2. Sälj vara
                    3. Avsluta 
                    """);
            IO.print("Val: ");
            int val = Integer.parseInt(IO.readln());
            switch (val) {
                case 1:
                    for (Vara v : sortiment.values()) {
                        IO.println(v);
                    }
                    break;
                case 2:
                    IO.print("\nAnge varans namn: ");
                    String varanamn = IO.readln().toLowerCase();

                    Vara vara = sortiment.get(varanamn);

                    if (vara != null) {
                        if (vara.sälj()) {
                            IO.println("\nSålde: " + sortiment.get(varanamn));
                            vinst += vara.getPris();
                            IO.println("Vinst efter försäljning: " + vinst + "kr");
                        } else {
                            IO.print("\nSlut i lager. Vill du köpa in fler? (ja/nej): ");
                            String svar = IO.readln();
                            if (svar.equals("ja")) {
                                IO.print("Hur många?");
                                int antal = Integer.parseInt(IO.readln());
                                vara.köp_in(antal);
                                IO.println("\nNytt lager: " + vara.getAntal());

                                vinst -= antal * vara.getPris() / 2;
                                IO.println("Vinst efter inköp: " + vinst + "kr");
                            }
                        }
                    } else{
                        IO.println("\nVaran finns inte");
                    }
                    break;
                case 3:
                    IO.println("\nLämnar kiosken...");
                    return;
                default:
                    break;
            }
        }
    }
}
