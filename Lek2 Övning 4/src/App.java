import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws Exception {

        ArrayList<Person> personer = new ArrayList<>();

        //Lägg till personer
        personer.add(new Person("Bert",  19740302));
        personer.add(new Person("Greg", 19850504));
        personer.add(new Person("Roger", 19880808));
        personer.add(new Person("Lars", 20050405));
        personer.add(new Person("Larsa", 20070405));


        IO.println(personer);

        for(Person p: personer){
            if (p.getNamn().equals("Greg")) {
                IO.println("Träff");
            } else {
                IO.println("Miss");
            }
        }

        personer.remove(0);
        IO.println(personer + " är kvar");

        for(Person p: personer){
            if (p.getPersonnummer()==19880808) {
                IO.println(p + " tas bort");
                personer.remove(p);
            }
        }
        
        IO.println(personer.size() + " stycken kvar");
    }
}
