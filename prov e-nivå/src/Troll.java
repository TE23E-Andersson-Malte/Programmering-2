public class Troll extends Fiende{
    private int styrka;

    public Troll(String namn, int hälsa, int styrka){
        super(namn, hälsa);
        this.styrka = styrka;
    }

    public void förflyttning(){
        super.förflyttning();
    }

    @Override
    public void attack(){
        IO.println("Trollet " + namn + " slår");
    }
}
