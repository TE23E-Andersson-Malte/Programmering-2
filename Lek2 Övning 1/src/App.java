public class App {
    public static void main(String[] args) throws Exception {
        Bil en_volvo = new Bil();

        en_volvo.märke = "Volvo";
        en_volvo.modell = "240 GL";
        en_volvo.reg_nr = "ABC123";

        IO.println("Bilens märke: " + en_volvo.märke);
        IO.println("Bilens modell: " + en_volvo.modell);
        IO.println("Bilens registreringsnummer: " + en_volvo.reg_nr);
        
        Bil ny_bil = new Bil("Saab", "900 Turbo", "ADG444");

        IO.println("Bilens märke: " + ny_bil.märke);
        IO.println("Bilens modell: " + ny_bil.modell);
        IO.println("Bilens registreringsnummer: " + ny_bil.reg_nr);

    }
}
