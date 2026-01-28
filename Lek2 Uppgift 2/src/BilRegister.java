import java.util.ArrayList;

public class BilRegister {

    private ArrayList<Bil> bilar;

    // Konstruktor för tom lista
    public BilRegister() {
        bilar = new ArrayList<>();
    }

    //Hitta bil
    public Bil hittaBil(String registreringsnummer) {
        for (Bil bil : bilar) {
            if (bil.getRegistreringsnummer().equals(registreringsnummer)) {
                return bil;
            }
        }
        return null;
    }

    //Lägg till bil
    public void läggTillBil(Bil bil){
        if (hittaBil(bil.getRegistreringsnummer()) != null) {
            throw new IllegalArgumentException("En bil med dett registreringsnummer finns redan");
        }
        bilar.add(bil); 
    }

    //Ta bort bil
    public void taBortBil(String registreringsnummer){
        Bil bil = hittaBil(registreringsnummer);

        if (bil == null) {
            throw new IllegalArgumentException("Ingen bil med registreringsnumret finns");
        }
        bilar.remove(bil);
    }

    //toString metod
    @Override
    public String toString(){

        String resultat = "";

        for (Bil bil : bilar) {
            resultat += bil + "\n";
        }

        return resultat;
    }
}
