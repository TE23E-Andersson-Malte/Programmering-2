public class Vagfordon {
    protected String reg_nr;
    protected String ägare;
    protected String färg;
    protected double vikt;

    // Konstruktor
    public Vagfordon(String reg_nr, String ägare, String färg, double vikt) {
        if (reg_nr == null || reg_nr.trim().isEmpty()) {
            throw new IllegalArgumentException("Registreringsnummer får inte vara tomt");
        } else {
            if (reg_nr.matches("[A-Za-z]{3}+[0-9]{3}+")) {
                this.reg_nr = reg_nr;
            } else {
                throw new IllegalArgumentException("Registreringsnumret är felskrivet");
            }
        }
        if (ägare == null || ägare.trim().isEmpty()) {
            throw new IllegalArgumentException("Ägare får inte vara tomt");
        } else {
            this.ägare = ägare;
        }

        if (färg == null || färg.trim().isEmpty()) {
            throw new IllegalArgumentException("Färg får inte vara tomt");
        } else {
            this.färg = färg;
        }

        if (vikt < 0) {
            throw new IllegalArgumentException("Vikt får inte vara negativ");
        } else {
            this.vikt = vikt;
        }
    }

    // Getters
    public String getReg_nr() {
        return reg_nr;
    }

    public String getÄgare() {
        return ägare;
    }

    public String getFärg() {
        return färg;
    }

    public double getVikt() {
        return vikt;
    }

    // Setters
    public void setReg_nr(String registreringsnummer) {
        if (registreringsnummer.matches("[A-Za-z]{3}+[0-9]{3}+")) {
            reg_nr = registreringsnummer;
        } else {
            throw new IllegalArgumentException("Registreringsnumret är felskrivet");
        }
    }

    @Override
    public String toString() {
        return "Fordonet -- Registreringsnummer: " + reg_nr
                + "| Ägare: " + ägare + "| Färg: " + färg + "| Vikt: "
                + vikt;
    }
}
