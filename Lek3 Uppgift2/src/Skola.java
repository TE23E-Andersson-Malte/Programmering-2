import java.util.ArrayList;
import java.util.List;

public class Skola {
    private String namn;
    private String adress;
    private Rektor rektor;
    private List<SkolKlasser> klasser;
    private List<Larare> lärare;

    public Skola(String namn, String adress, Rektor rektor) {
        if (namn == null || namn.trim().isEmpty()) {
            throw new IllegalArgumentException("Namn på skola får inte vara tomt");
        } else {
            this.namn = namn;
        }
        if (adress == null || adress.trim().isEmpty()) {
            throw new IllegalArgumentException("Namn på adress får inte vara tomt");
        } else {
            this.adress = adress;
        }
        if (rektor == null) {
            throw new IllegalArgumentException("Namn på rektor får inte vara tomt");
        } else {
            this.rektor = rektor;
        }
        this.klasser = new ArrayList<>();
        this.lärare = new ArrayList<>();
    }

    public void laggTillKlass(SkolKlasser klass){
        klasser.add(klass);
    }

     public void laggTillLärare(Larare larare){
        lärare.add(larare);
    }

    // toString
    @Override
    public String toString() {
        return "\nSkola: " + namn
                + "| Adress: " + adress +
                "| Rektor: " + rektor.getNamn() +
                " | Lärare: " + lärare +
                "| Antal klasser: "+ klasser.size() + "\n";
    }
}
