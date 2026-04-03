package lek6ovn3;

public class Bil extends Fordon {
    private int sittplatser;

    public Bil(String marke, String modell, String reg, int sittplatser) {
        super(marke, modell, reg, "Bil"); // Anropar Fordons konstruktor
        this.sittplatser = sittplatser;
    }

    public int getSittplatser() {
        return sittplatser;
    }

    @Override
    public String toString() {
        return "Bil , sittplatser=" + sittplatser + super.toString();
    }
}
