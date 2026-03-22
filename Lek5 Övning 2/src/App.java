import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Collections;


public class App {
    public static void main(String[] args) throws Exception {
        /*
         * 
         * Person p1 = new Person("Anna", 1990);
         * Person p2 = new Person("Anna", 1990);
         * 
         * //Testa med ArrayList
         * List<Person> lista = new ArrayList<>();
         * lista.add(p1);
         * lista.add(p2);
         * IO.println("ArrayList innehåll: " + lista + ", storlek: " + lista.size());
         * 
         * //Testa med HashSet
         * Set<Person> mängd = new HashSet<>();
         * mängd.add(p1);
         * mängd.add(p2);
         * IO.println("HashSet innehåll: " + mängd + ", storlek: " + mängd.size());
         * 
         */

        List<String> epost_addr = new ArrayList<>();
        epost_addr.add("anna@mail.se");
        epost_addr.add("erik@web.com");
        epost_addr.add("anna@mail.se"); // Dubblett!
        epost_addr.add("boss@företaget.se");
        epost_addr.add("erik@web.com"); // Dubblett!
        epost_addr.add("clara@skola.se");
        epost_addr.add("anna@mail.se"); // Dubblett igen!
        // Skriv ut adresser och storleken på listan
        IO.println(epost_addr);
        IO.println("Antal adresser i ursprungslistan: " + epost_addr.size());

        //Städa
        Set<String> städade_eposter = new HashSet<>(epost_addr);
        IO.println("Antal unika adresser efter städning: " + städade_eposter.size());
        IO.println(städade_eposter);

        //Sortera
        List<String> sorteradLista = new ArrayList<>(städade_eposter);
        Collections.sort(sorteradLista);
        IO.println("Sorterad lista: " + sorteradLista);
    }
}
