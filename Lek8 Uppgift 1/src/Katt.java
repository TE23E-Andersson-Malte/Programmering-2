public class Katt extends Djur{

    public Katt(double vikt, int energi){
        super(vikt, energi);
        this.vikt = 3;
        this.energi = 10000;
    }

    public void Leka(String sak){
        IO.print("Leker med " + sak); 
    }

    @Override
    public String Läte(){
        return "Miiuuaooo";
    }
}
