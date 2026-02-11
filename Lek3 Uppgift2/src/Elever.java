public class Elever extends SkolPersoner{
    protected String klass;
    protected String betyg;

    //Konstruktor
    public Elever(String namn, int ålder, String personnummer, String klass, String betyg){
        super(namn, ålder, personnummer);

        if (klass == null || klass.trim().isEmpty()) {
            throw new IllegalArgumentException("Klass får inte vara tom");
        } else {
            this.klass = klass;
        }

        if (betyg.matches("[A-Fa-f]{1}")) {
            this.betyg = betyg;
        } else {
            throw new IllegalArgumentException("Ogiltigt betyg");
        }
    }

    //Setters
    public void setKlass(String klass){
        if (klass == null || klass.trim().isEmpty()) {
            throw new IllegalArgumentException("Klass får inte vara tom");
        } else {
            this.klass = klass;
        }
    }

    public void setBetyg(String betyg){
        if (betyg.matches("[A-Fa-f]{1}")) {
            this.betyg = betyg;
        } else {
            throw new IllegalArgumentException("Ogiltigt betyg");
        }
    }
    //Getters
    public String getKlass(){
        return klass;
    }

    public String getBetyg(){
        return betyg;
    }

    //toString
    public String toString(){
        return "Elev: " + super.toString()
        + "| Klass: " + klass + "| Betyg: " + betyg;
    }
}
