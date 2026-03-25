package trafikverket;

public class LastBil extends Fordon {
    private int maxLast;    // Maxgräns i kg
    private int lastadVikt; // Nuvarande vikt i kg

    public LastBil(String märke, String modell, String regNr, int maxLast) {
        super(märke, modell, regNr);
        this.maxLast = maxLast;
        this.lastadVikt = 0; // Startar alltid tom
    }

    // Returnerar hur många kg som lastats på LastBilen
    public int getLastadVikt() {
        return lastadVikt;
    }

    // Försöker lasta på gods
    public boolean lasta_på(int vikt) {
        // Enligt instruktion: om den nya vikten är större än maxLast så lastas inget
        // (Logiskt sett kollar vi oftast nuvarande + ny, men vi följer kravet här)
        if (vikt > maxLast || (lastadVikt + vikt) > maxLast) {
            IO.println("Lastning misslyckades: Vikten överstiger maxgränsen (" + maxLast + " kg).");
            return false;
        } else {
            lastadVikt += vikt;
            IO.println("Lastning lyckades: " + vikt + " kg pålastat. Totalt: " + lastadVikt + " kg.");
            return true;
        }
    }

    // Lastar av gods
    public void lasta_av(int vikt) {
        if (vikt > lastadVikt) {
            // Om vi försöker lasta av mer än vad som finns, nollställs vikten
            lastadVikt = 0;
            IO.println("Varning: Försökte lasta av mer än vad som fanns. Lasten är nu 0 kg.");
        } else {
            lastadVikt -= vikt;
            IO.println("Lastade av " + vikt + " kg. Kvar i bilen: " + lastadVikt + " kg.");
        }
    }


    @Override
    public String toString() {
        return super.toString() + " [Maxlast: " + maxLast + " kg, Lastat: " + lastadVikt + " kg]";
    }
}