public class Fiende {
    protected String namn;
    protected int hälsa;

    public Fiende(String namn, int hälsa){
        this.namn = namn;
        this.hälsa = hälsa;
    }

    public void förflyttning(){
        IO.println(namn + " förflyttar sig");
    } 

    public void attack(){
        IO.println(namn + " attackerar");
    }
}
