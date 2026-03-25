package trafikverket;


public abstract class Fordon implements Comparable<Fordon> {
    private String märke;
    private String modell;
    private String regNr;

    public Fordon(String märke, String modell, String regNr) {
        this.märke = märke;
        this.modell = modell;
        this.regNr = regNr;
    }

    public String getRegNr() { return regNr; }
    public String getMärke() { return märke; }

    // En metod som alla barnklasser ska dela
    public void skrivUtBasInfo() {
        IO.println("Fordon: " + märke + " " + modell + " [" + regNr + "]");
    }

    @Override
    public int compareTo(Fordon annat) {
        // Sorterar alfabetiskt efter registreringsnummer
        return this.regNr.compareTo(annat.regNr);
    }

    @Override
    public String toString() {
        return regNr + " (" + märke + " " + modell + ")";
    }
}