public class Mus extends Djur{

    public Mus(double vikt, int energi){
        super(vikt, energi);
        this.vikt = 1;
        this.energi = 5000;
    }

    public void Klättra(){
        IO.print("Musen klättrar "); 
    }

    @Override
    public String Läte(){
        return "Piiiiip";
    }
}
