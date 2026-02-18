public class Djur {
    protected double vikt;
    protected int energi;
    protected boolean lever;
    private int antal_djur = 0;

    public Djur(double vikt, int energi){
        this.vikt = vikt;
        this.energi = energi;
        this.lever = true;
        antal_djur++;
    }

    public void äta(int energi){
        this.energi += energi;
    }

    public void spring(int meter){
        this.energi -= meter*vikt;
        if (energi < 0) {
            lever = false;
        }
    }

    public String Läte(){
        return "Djuret låter";
    }
}
