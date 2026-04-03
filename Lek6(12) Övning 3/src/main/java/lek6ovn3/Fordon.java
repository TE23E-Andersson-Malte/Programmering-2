package lek6ovn3;

public abstract class Fordon {
    protected String marke;
    protected String modell;
    protected String reg;
    protected String typ;

    public Fordon(String marke, String modell, String reg, String typ) {
        this.marke = marke;
        this.modell = modell;
        this.reg = reg;
        this.typ=typ;
    }
    
    public String getMarke() {
        return marke;
    }

    public String getModell() {
        return modell;
    }

    public String getReg() {
        return reg;
    }

    public void setReg(String reg)
    {
        this.reg=reg;
    }

    @Override
    public String toString() {
        return "marke=" + marke + ", modell=" + modell + ", reg=" + reg + "n";
    }
}
