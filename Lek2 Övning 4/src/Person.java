public class Person {
    private String namn;
    private int personnummer;

    public Person(String namn, int personnummer) {
        this.namn = namn;
        this.personnummer = personnummer;
    }

    public String getNamn() {
        return namn;
    }

    public void setNamn(String namn) {
        this.namn = namn;
    }

    public int getPersonnummer() {
        return personnummer;
    }

    public void setPnr(int personnummer) {
        this.personnummer = personnummer;
    }

    @Override
    public String toString(){
        return "Personens namn: " + namn +
         " | Personnummer: " + personnummer
         + "\n";
    }
}
