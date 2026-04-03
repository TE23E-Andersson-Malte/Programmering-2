package lek6ovn3;

public class Lastbil extends Fordon {
    private double lastKapacitet;

    public Lastbil(String marke, String modell, String reg, double lastKapacitet) {
        super(marke, modell, reg,"Lastbil");
        this.lastKapacitet = lastKapacitet;
    }

    public double getLastKapacitet() {
        return lastKapacitet;
    }

    @Override
    public String toString() {
        return "Lastbil , [lastKapacitet=" + lastKapacitet + super.toString();
    }
}
