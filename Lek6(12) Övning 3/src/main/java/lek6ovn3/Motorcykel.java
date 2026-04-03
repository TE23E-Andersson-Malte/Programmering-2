package lek6ovn3;

public class Motorcykel extends Fordon {
    private String mcKlass; // t.ex. "Lätt" eller "Tung"

    public Motorcykel(String marke, String modell, String reg, String mcKlass) {
        super(marke, modell, reg, "Motorcyckel");
        this.mcKlass = mcKlass;
    }

    public String getMcKlass() {
        return mcKlass;
    }

    @Override
    public String toString() {
        return "Motorcykel mcKlass=" + mcKlass + super.toString();
    } 
}
