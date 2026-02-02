public class Djur {
    private String namn;

    public Djur(String namn){
        this.namn = namn;
    }

    public String getNamn(){
        return namn;
    }

    public void ät(){
        IO.println(namn + " äter sin mat.");
    }
}
