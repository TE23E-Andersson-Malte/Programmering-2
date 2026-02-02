public class Hund extends Djur{
    private String ras;

    public Hund(String namn, String ras){
        super(namn);
        this.ras = ras;
    }

    public void voffa(){
        IO.println(getNamn() + " voffar!");
    }
}
