public class App {
    public static void main(String[] args) throws Exception {
        boolean kör = true;
        while (kör) {
            // Visa meny
            IO.println("\n---MENY---");
            IO.println("1. Skriv ut 'Hej världen!'");
            IO.println("2. Räkna ut kvadraten av ett tal");
            IO.println("3. Avsluta");
            IO.print("Välj ett alternativ (1-3): ");
            int val = Integer.parseInt(IO.readln());

            switch (val) {
                case 1:
                    IO.println("Hej världen!");
                    break;
                case 2:
                    int tal = Integer.parseInt(IO.readln("Ange ett heltal: "));
                    int kvadrat = tal * tal;
                    IO.println("Kvadraten av " + tal + " är " + kvadrat);
                    break;
                case 3:
                    IO.println("Programmet avslutas...");
                    kör = false;
                    break;
                default:
                    IO.println("Ogiltigt tal, försök igen.");
                    break;

            }
        }
    }
}
