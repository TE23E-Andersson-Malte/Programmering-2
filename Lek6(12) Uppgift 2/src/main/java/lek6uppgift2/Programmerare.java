package lek6uppgift2;
public class Programmerare extends Personal{
    private String programSprak;

    public Programmerare(String typ, String namn, long personnummer, int lön, String avdelning, String programSprak) {
        super(typ, namn, personnummer, lön, avdelning);
        this.programSprak = programSprak;
    }

    public String getProgramSprak() {
        return programSprak;
    }

   @Override
    public String toString() {
        return "Personal [typ=" + typ + ", namn=" + namn + ", personnummer=" + personnummer + ", lön=" + lön
                + ", avdelning=" + avdelning + ", programSprak: " + programSprak + "]";
    }
}
