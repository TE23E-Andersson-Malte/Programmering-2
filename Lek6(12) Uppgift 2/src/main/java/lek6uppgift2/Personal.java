package lek6uppgift2;

public abstract class Personal {
    protected String typ;
    protected String namn;
    protected long personnummer;
    protected int lön;
    protected String avdelning;

    public Personal(String typ, String namn, long personnummer, int lön, String avdelning){
        this.typ = typ;
        this.namn = namn;
        this.personnummer = personnummer;
        this.lön = lön;
        this.avdelning = avdelning;
    }

    public String getTyp() {
        return typ;
    }

    public String getNamn() {
        return namn;
    }

    public long getPersonnummer() {
        return personnummer;
    }

    public int getLön() {
        return lön;
    }

    public String getAvdelning() {
        return avdelning;
    }

    @Override
    public String toString(){
        return namn + " - Typ: " + typ + 
         ", Personnummer: " + personnummer + ", Lön: " + lön + ", Avdelning: " + avdelning;
    }

}
