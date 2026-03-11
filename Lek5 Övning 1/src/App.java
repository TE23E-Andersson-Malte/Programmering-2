import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Collections;

public class App {
    public static void main(String[] args) throws Exception {
        List<Person> personLista = new ArrayList<>();

        personLista.add(new Person("Bob", 1982));
        personLista.add(new Person("Alice", 1995));
        personLista.add(new Person("Charlie", 2005));

        IO.println("Lista efter tillägg: " + personLista);

        for (Person p : personLista) {
            if (p.getNamn().equals("Bob")) {
                p.setNamn("Bobby");
                break;
            }
        }

        IO.println("Efter ändring: " + personLista);

        Iterator<Person> it = personLista.iterator();

        while (it.hasNext()) {
            Person p = it.next(); // Hämta nästa person

            if (p.getNamn().equals("Charlie")) {
                it.remove();
                System.out.println("Tog bort Charlie ur registret");
            }
        }

        IO.println("Efter borttagning: " + personLista);

        Collections.sort(personLista);
        IO.println("----Slutgiltigt register----");
        for (Person p : personLista) {
            IO.println("- " + p);
        }

        Person p = personLista.get(1);
        personLista.remove(p);
        IO.println("Efter ännu en borttagning: " + personLista);

        personLista.remove(0);
        IO.println("Efter sista borttagning: " + personLista);

    }
}
