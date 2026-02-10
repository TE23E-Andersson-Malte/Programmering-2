import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws Exception {

        ArrayList<Vagfordon> fordon = new ArrayList<Vagfordon>();
        while (true) {
            IO.println("""
                    1. Skapa bil
                    2. Skapa lastbil
                    3. Skapa buss
                    4. Lisa skapade vägfordon
                    5. Avsluta
                    """);
            int val = Integer.parseInt(IO.readln());
            IO.println("");
            switch (val) {
                case 1:
                    Vagfordon ny_bil = new Bil("BIL123", "Bilägare", "Gul", 1000, 5);
                    fordon.add(ny_bil);
                    break;
                case 2:
                    Vagfordon ny_lastbil = new Lastbil("LAS123", "Lastbilägare", "Grön", 10000, 500);
                    fordon.add(ny_lastbil);
                    break;
                case 3:
                    Vagfordon ny_buss = new Buss("BUS123", "Bussägare", "Blå", 15000, 18);
                    fordon.add(ny_buss);
                    break;
                case 4:
                    IO.println("------------Lista över vägfordon--------------\n");
                for (Vagfordon vagfordon : fordon) {
                        IO.println(vagfordon + "\n");
                    }
                    break;
                case 5:
                    IO.println("Programmet avslutas...");
                    return;
                default:
                    IO.println("Ange ett val\n");

            }
        }

        /* 
        Vagfordon min_buss = new Buss("ADC193", "jag", "grön och skön", 10010, 18);
        Vagfordon min_lastbil = new Lastbil("HUG869", "din morsa", "blå", 9, 777000);
        Vagfordon min_bil = new Bil("ERY685", "Elis (patrick)", "inte svart i alla fall", 0.1, 5);

        IO.println(min_buss);
        IO.println(min_lastbil);
        IO.println(min_bil);
        */
    }
}
