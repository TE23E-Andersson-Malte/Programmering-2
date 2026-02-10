public class Lastbil extends Vagfordon {

    protected double lastkapacitet;

    public Lastbil(String reg_nr, String ägare, String färg, double vikt, double lastkapacitet){
        super(reg_nr, ägare, färg, vikt);

        if (lastkapacitet < 0) {
            throw new IllegalArgumentException("Lastkapacitet får inte vara negativ");
        } else {
            this.lastkapacitet = lastkapacitet;
        }
    }

    public double getLastkapacitet(){
        return lastkapacitet;
    }

    public void setLastkapacitet(double lastkapacitet){
        if (lastkapacitet > 0) {
            this.lastkapacitet = lastkapacitet;
        } else {
            throw new IllegalArgumentException("Lastkapacitet kan inte vara negativ");
        }
    }

    @Override
    public String toString(){
        return "Lastbil: " + super.toString() +
                "| Lastkapacitet: " + lastkapacitet + "\n";
    }
}
