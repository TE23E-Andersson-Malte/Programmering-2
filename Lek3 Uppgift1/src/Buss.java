public class Buss extends Vagfordon{

    protected int antalPassagerare;

    public Buss(String reg_nr, String ägare, String färg, double vikt, int antalPassagerare){
        super(reg_nr, ägare, färg, vikt);

        if (antalPassagerare < 0) {
            throw new IllegalArgumentException("Antal passagerare får inte vara negativ");
        } else {
            this.antalPassagerare = antalPassagerare;
        }
    }

    public int getAntalPassagerare(){
        return antalPassagerare;
    }

    public void setAntalPassagerare(int antalPassagerare){
        if (antalPassagerare > 0) {
            this.antalPassagerare = antalPassagerare;
        } else {
            throw new IllegalArgumentException("Antal passagerare kan inte vara negativ");
        }
    }

    @Override
    public String toString(){
        return "Buss: " + super.toString() +
                "| Antal passagerare: " + antalPassagerare + "\n";
    }
}


