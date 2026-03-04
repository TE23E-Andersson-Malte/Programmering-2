public class Magiker extends Fiende{
    private int mana;

    public Magiker(String namn, int hälsa, int mana){
        super(namn, hälsa);
        this.mana = mana;
    }

    public void förflyttning(){
        super.förflyttning();
    }

    @Override
    public void attack(){
        IO.println("Magikern " + namn + " skjuter mana-blixtar");
    }
}
