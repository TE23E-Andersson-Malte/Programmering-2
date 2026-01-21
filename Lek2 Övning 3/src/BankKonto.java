public class BankKonto {
    private final String kontonummer;
    private String ägare;
    private double saldo;
    private static int antal_konton = 0;

    //Kontruktor (deafault)
    public BankKonto() {
        this.kontonummer = "0000-0000";
        this.ägare = "Okänd";
        this.saldo = 0.0;
        BankKonto.antal_konton++;
    }

    //Kontruktor
    public BankKonto(String kontonummer, String ägare, double startSaldo) {
        if (kontonummer == null || kontonummer.isEmpty())
            throw new IllegalArgumentException("Kontonummer får inte vara tomt");
        if (ägare == null || ägare.trim().isEmpty())
            throw new IllegalArgumentException("Ägare får inte vara tomt");
        if (startSaldo < 0)
            throw new IllegalArgumentException("Startsaldo kan inte vara negativt");
        this.kontonummer = kontonummer;
        this.ägare = ägare;
        this.saldo = startSaldo;
        BankKonto.antal_konton++;
    }

    //Hämta kontonummer
    public String get_kontonummer(){
        return kontonummer;
    }

    //Hämta saldo
    public double get_saldo(){
        return saldo;
    }

    //Ändra ägare
    public void set_ägare(String ägare){
        if (ägare == null || ägare.trim().isEmpty()){
            throw new IllegalArgumentException("Ägare får inte vara tomt");
        }
        this.ägare = ägare;
    }

    //Ta ut pengar
    public void ta_ut(double belopp){
        if (belopp <= 0)
            throw new IllegalArgumentException("Beloppet måste vara > 0");
        if (belopp > saldo) {
            throw new IllegalArgumentException("Uttaget är större än saldot");
        }
        saldo -= belopp;
    }

    //Sätt in pengar
    public void sätt_in(double belopp){
        if(belopp <= 0)
            throw new IllegalArgumentException("Beloppet måste vara > 0");
        saldo += belopp;
    }

    //Överför pengar
    public void överför(BankKonto mottagare, double belopp){
        if(mottagare == null)
            throw new IllegalArgumentException("Mottagaren får ej vara null");
        
        if (belopp <= 0)
            throw new IllegalArgumentException("Beloppet måste vara > 0");
        this.ta_ut(belopp);
        mottagare.sätt_in(belopp);
    }

    //toString
        @Override
        public String toString(){
            return "Bankkonto: kontonummer = " + kontonummer +
                    ", ägare = " + ägare +
                    ", saldo = " + saldo;
        }
    
    //Antal konton
    public static int antal_konton_skapta(){
        return BankKonto.antal_konton;
    }
}