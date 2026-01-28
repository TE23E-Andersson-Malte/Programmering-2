
public class Bil {
    private String märke;
    private String modell;
    private String registreringsnummer;
    private int årsmodell;
    private int hastighet = 0;

    // Default kontruktor
    Bil() {
        this.märke = "Okänd";
        this.modell = "Okänd";
        this.registreringsnummer = "--- ---";
        this.årsmodell = 0000;
        this.hastighet = 0;
    }

    // Kontruktor
    Bil(String märke, String modell, String registreringsnummer, int årsmodell) {
        if (märke == null || märke.trim().isEmpty())
            throw new IllegalArgumentException("Märket får inte vara tomt");
        if (modell == null || modell.trim().isEmpty())
            throw new IllegalArgumentException("Modell får inte vara tomt");
        if (registreringsnummer == null || registreringsnummer.trim().isEmpty())
            throw new IllegalArgumentException("Registreringsnummer får inte vara tomt");
        if (årsmodell < 1860 || årsmodell > 2026)
            throw new IllegalArgumentException("Ogiltig årsmodell");

        this.märke = märke;
        this.modell = modell;
        this.registreringsnummer = registreringsnummer;
        this.årsmodell = årsmodell;
    }

    // Getters
    public String getRegistreringsnummer() {
        return registreringsnummer;
    }

    public String getModell() {
        return modell;
    }

    public int getHastighet() {
        return hastighet;
    }

    public int getÅrsmodell() {
        return årsmodell;
    }

    // Ska man ha denna?
    public String getMärke() {
        return märke;
    }

    // Setters
    public void setModell(String modell) {
        if (modell == null || modell.trim().isEmpty()) {
            throw new IllegalArgumentException("Modell får inte vara tomt");
        }
        this.modell = modell;
    }

    // Metoder
    public void ökaHastighet(int ökning) {
        if (ökning <= 0) {
            throw new IllegalArgumentException("Ökningen ska vara > 0");
        }
        hastighet = hastighet + ökning;
    }

    public void bromsa(int minskning) {
        if (minskning <= 0) {
            throw new IllegalArgumentException("Minskning ska vara > 0");
        }
        if (minskning > hastighet) {
            hastighet = 0;
        } else {
            hastighet = hastighet - minskning;
        }
    }

    // toString metod
    @Override
    public String toString() {
        return "Bilen - Märke: " + märke +
                ", Modell: " + modell +
                ", Registreringsnummer: " + registreringsnummer +
                ", Årsmodell: " + årsmodell;
    }
}
