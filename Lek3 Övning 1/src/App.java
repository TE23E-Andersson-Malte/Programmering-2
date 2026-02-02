public class App {
    public static void main(String[] args) throws Exception {
        
        Hund minHund = new Hund( "GrgerHund",  "bög");
        minHund.voffa();
        minHund.ät();

        Hast minHäst = new Hast("gregerhäst", 100);
        minHäst.galoppera();
        minHäst.ät();
    }
}
