import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws Exception {

        ArrayList<Person> personer = new ArrayList<>();

        //Lägg till personer
        personer.add(new Person("Bert",  19740302));
        personer.add(new Person("Greg", 19850504));
        IO.println(personer);

        for(Person p: personer){
            if (p.getNamn().equals("Greg")) {
                IO.println("Träff");
            } else {
                IO.println("Miss");
            }
        }
    }
}
