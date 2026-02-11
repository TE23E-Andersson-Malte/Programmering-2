public class Larare extends SkolPersoner{
    protected String kurs;

    //Konstruktor
    public Larare(String namn, int ålder, String personnummer, String kurs){
        super(namn, ålder, personnummer);

        if (kurs == null || kurs.trim().isEmpty()) {
            throw new IllegalArgumentException("Kurs får inte vara tom");
        } else {
            this.kurs = kurs;
        }
    }

    //Setter
    public void setKurs(String kurs){
        if (kurs == null || kurs.trim().isEmpty()) {
            throw new IllegalArgumentException("Kurs får inte vara tom");
        } else {
            this.kurs = kurs;
        }
    }
    //Getter
    public String getKurs(){
        return kurs;
    }
    //toString
    public String toString(){
        return "Lärare: " + super.toString() +
        "| Kurs: " + kurs;
    }
}
