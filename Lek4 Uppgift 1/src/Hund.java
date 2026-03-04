public class Hund extends Djur{

    public Hund(double vikt, int energi){
        super(vikt, energi);
        this.vikt = 5;
        this.energi = 20000;
    }

    public void Spåra(String bytet){
        IO.print("Spårar " + bytet); 
    }

    @Override
    public String Läte(){
        return "Vooffff";
    }
}
