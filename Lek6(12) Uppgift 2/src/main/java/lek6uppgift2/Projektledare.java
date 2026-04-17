package lek6uppgift2;
public class Projektledare extends Personal{
    private int antalProjekt;

    public Projektledare(String typ, String namn, long personnummer, int lön, String avdelning, int antalprojekt) {
        super(typ, namn, personnummer, lön, avdelning);
        this.antalProjekt = antalProjekt;
    }

    public int getAntalProjekt() {
        return antalProjekt;
    }

   @Override
    public String toString() {
        return namn + " - Typ: " + typ + 
         ", Personnummer: " + personnummer + ", Lön: " + lön + ", Avdelning: " + avdelning + ", Antal projekt: " + antalProjekt;
    }
}
