public class Bil {
    private String märke;
    private String modell;
    private String registreringsnummer;
    private int årsmodell;
    private int hastighet = 0;

    Bil() {
        this.märke = "Okänd";
        this.modell = "Okänd";
        this.registreringsnummer = "ABC 132";
        this.årsmodell = 0000;
        this.hastighet = 0;
    }

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

    //Getters
    public String getRegistreringsnummer(){
        return registreringsnummer;
    }

    public String getModell(){
        return modell;
    }

    public int getHastighet(){
        return hastighet;
    }

    public int getÅrsmodell(){
        return årsmodell;
    }

    //Ska man ha denna?
    public String getMärke(){
        return märke;
    }

    public void setModell(String modell){
         if (modell == null || modell.trim().isEmpty()){
            throw new IllegalArgumentException("Modell får inte vara tomt");
        }
        this.modell = modell;
    }
}
