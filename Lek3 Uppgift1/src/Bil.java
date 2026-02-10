public class Bil extends Vagfordon {

    protected int sittplatser;

    public Bil(String reg_nr, String ägare, String färg, double vikt, int sittplatser){
        super(reg_nr, ägare, färg, vikt);

        if (sittplatser < 0) {
            throw new IllegalArgumentException("Sittplatser får inte vara negativ");
        } else {
        this.sittplatser = sittplatser;
        }
    }

    public int getAntalSittplatser() {
        return sittplatser;
    }

    public void setAntalSittplatser(int sittplatser) {
        if (sittplatser > 0) {
            this.sittplatser = sittplatser;
        } else {
            throw new IllegalArgumentException("Antal sittplaster kan inte vara negativ");
        }
    }

    @Override
    public String toString() {
        return "Bil: " + super.toString() +
                "| Antal sittplatser: " + sittplatser + "\n";
    }

}
