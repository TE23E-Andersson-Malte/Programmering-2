import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Iterator;

public class App {
    public static void main(String[] args) throws Exception {
        List<Person> register = new ArrayList<>();

        register.add(new Person("Anna", 1990));
        register.add(new Person("Berit", 1955));
        register.add(new Person("Carl", 2005));
        register.add(new Person("David", 1982));

        boolean loop = true;
        while (loop) {
            IO.println("""
                        1. Lägg till person
                        2. Sök efter person
                        3. Ta bort person
                        4. Visa register
                        5. Sortera lista
                        6. Avsluta
                    """);
            int val;
            try {
                val = Integer.parseInt(IO.readln("Ange val: "));
            } catch (Exception e) {
                IO.println("Felaktigt inmatning\n");
                continue;
            }
            IO.println("");
            switch (val) {
                case 1:
                    String namn = IO.readln("Ange namn: ");
                    int födelseår = Integer.parseInt(IO.readln("Ange födelseår: "));
                    register.add(new Person(namn, födelseår));
                    IO.println("Person (" + namn + ", " + födelseår + ") lades till\n");
                    break;
                case 2:
                    String namnSök = IO.readln("Ange namn: ");
                    boolean hittad = false;
                    for (Person p : register) {
                        if (p.getNamn().equals(namnSök)) {
                            hittad = true;
                            break;
                        }
                    }
                    if (hittad) {
                        IO.println(namnSök + " finns i registret\n");
                    } else {
                        IO.println(namnSök + " hittades inte\n");
                    }
                    break;
                case 3:
                    Iterator<Person> it = register.iterator();
                    String namnTabort = IO.readln("Ange namn: ");
                    boolean hittas = false;
                    while (it.hasNext()) {
                        Person p = it.next(); // Hämta nästa person

                        if (p.getNamn().equals(namnTabort)) {
                            it.remove();
                            hittas = true;
                        }
                    }
                    if (hittas) {
                        IO.println("Tog bort " + namnTabort + " ur registret\n");
                    } else {
                        IO.println(namnTabort + " finns inte i registret");
                    }
                    break;
                case 4:
                    IO.println(register + "\n");
                    break;
                case 5:
                    Collections.sort(register);
                    for (Person p : register) {
                        IO.println(p);
                    }
                    IO.println("");
                    break;
                case 6:
                    loop = false;
                default:
                    break;
            }
        }
    }
}
