public class Vara {
    private String namn;
    private double pris;
    private int antal;

    public Vara(String namn, double pris, int antal) {
        this.namn = namn;
        this.pris = pris;
        this.antal = antal;
    }

    public String getNamn() { return namn; }
    public double getPris() { return pris; }
    public int getAntal() { return antal; }

    // Metod för att sälja en vara
    public boolean sälj() {
        if (antal > 0) {
            antal--;
            return true;
        }
        return false;
    }

    public void köp_in(int antal)
    {
        this.antal +=antal;
    }

    @Override
    public String toString() {
        return namn + " - Pris: " + pris + " kr (Lager: " + antal + " st)";
    }
}
