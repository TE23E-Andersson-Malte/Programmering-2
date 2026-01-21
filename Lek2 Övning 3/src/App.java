public class App {
    public static void main(String[] args) throws Exception {
        BankKonto mitt_konto = new BankKonto("4854-2983", "Greger", 9822);
        System.out.println(mitt_konto);

        //Sätt in
        mitt_konto.sätt_in(500);
        IO.println("Saldo efter insättning: " + mitt_konto.get_saldo());

        //Ogilitg insättning
        try {
            mitt_konto.sätt_in(-200);
        } catch (Exception e) {
            IO.println("Fel: " + e.getMessage());
        }

        //Korrekt uttag
        mitt_konto.ta_ut(2000);
        IO.println("Saldo efter uttag: " + mitt_konto.get_saldo());

        //Övertrassering
        try {
            mitt_konto.ta_ut(100000);
        } catch (Exception e) {
            IO.println("Fel: " + e.getMessage());
        }

        //Antal konton
        IO.println("Antal konton: " + BankKonto.antal_konton_skapta());
    }
}
