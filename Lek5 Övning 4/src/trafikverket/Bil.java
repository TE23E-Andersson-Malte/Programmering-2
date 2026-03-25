package trafikverket;

public class Bil extends Fordon {
    private int sittPlatser;
    private int ledigaPlatser;

    public Bil(String märke, String modell, String regNr, int sittPlatser) {
        super(märke, modell, regNr);
        this.sittPlatser = sittPlatser;
        this.ledigaPlatser = sittPlatser; // Från början är alla platser lediga
    }

    public int getLedigaPlatser() {
        return ledigaPlatser;
    }

    public boolean addPassagerare(int passagerare) {
        // Om antalet nya passagerare är fler än vad som finns ledigt:
        if (passagerare > ledigaPlatser) {
            IO.println("Fel: Kan inte lasta " + passagerare + " personer. Endast " + ledigaPlatser + " platser kvar.");
            return false;
        } else {
            // Minska antalet lediga platser
            ledigaPlatser -= passagerare;
            IO.println("Lyckades: Lastade " + passagerare + " personer. Lediga platser nu: " + ledigaPlatser);
            return true;
        }
    }

    public void removePassagerare(int passagerare){
        this.ledigaPlatser+=passagerare;
        if(this.ledigaPlatser>this.sittPlatser)
            this.ledigaPlatser=sittPlatser;
            
    }


    @Override
    public String toString() {
        return super.toString() + " [Sittplatser: " + sittPlatser + ", Lediga: " + ledigaPlatser + "]";
    }
}