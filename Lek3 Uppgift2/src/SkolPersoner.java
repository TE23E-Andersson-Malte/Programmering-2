public class SkolPersoner {
    protected String namn;
    protected int ålder;
    protected String personnummer;

    // konstruktor
    public SkolPersoner(String namn, int ålder, String personnummer) {
        if (namn == null || namn.trim().isEmpty()) {
            throw new IllegalArgumentException("Namnet får inte vara tomt");
        } else {
            this.namn = namn;
        }

        if (ålder < 0) {
            throw new IllegalArgumentException("Ålder får inte vara negativ");
        } else {
            this.ålder = ålder;
        }

        if (personnummer == null || personnummer.length() != 10) {
            throw new IllegalArgumentException("Personnummer måste vara 10 siffror");
        } else {
            this.personnummer = personnummer;
        }
    }

    // Setters
    public void setNamn(String namn) {
        if (namn == null || namn.trim().isEmpty()) {
            throw new IllegalArgumentException("Namnet får inte vara tomt");
        } else {
            this.namn = namn;
        }
    }

    public void setÅlder(int ålder) {
        if (ålder < 0) {
            throw new IllegalArgumentException("Ålder får inte vara negativ");
        } else {
            this.ålder = ålder;
        }
    }

    public void setPersonnummer(String personnummer) {
        if (personnummer == null || personnummer.length() != 10) {
            throw new IllegalArgumentException("Personnummer måste vara 10 siffror");
        } else {
            this.personnummer = personnummer;
        }
    }

    // Getters
    public String getNamn() {
        return namn;
    }

    public int getÅlder() {
        return ålder;
    }

    public String getPersonnummer() {
        return personnummer;
    }

    // toString
    @Override
    public String toString() {
        return "Person --- Namn: " + namn
                + "| Ålder: " + ålder +
                "| Personnummer: " + personnummer;
    }

}
