public class Hast extends Djur{
private double höjd;

public Hast(String namn, double höjd){
    super(namn);
    this.höjd = höjd;
}

public void galoppera(){
    IO.println(getNamn() + " galopperar!");
}
}
