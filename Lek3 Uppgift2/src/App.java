public class App {
    public static void main(String[] args) throws Exception {
        
        //Skapa rektor
        Rektor rektorn = new Rektor("Greger", 19, "5604093023", "T17");
        
        //Skapa skola
        Skola skolan = new Skola("Gregers gymnaise", "Gregervägen 4", rektorn);

        //Skapa klass
        SkolKlasser TE23E = new SkolKlasser("TE23E", "En massa matte 08:00 - 17:30");

        //Skapa elever
        Elever elev1 = new  Elever("Lars", 180, "0384539438", "TE23E", "A");
        Elever elev2 = new Elever("Stina", 50, "4095949334", "TE23E", "F");

        //Skapa lärare
        Larare lärare1 = new Larare("Åsa", 77, "8765432435", "Syslöjd");
        Larare lärare2 = new Larare("Kent", 54, "9083657467", "Teknik");

        //Lägg till elever i klassen
        TE23E.laggTillElev(elev1);
        TE23E.laggTillElev(elev2);

        //Lägg till klassen i skolan
        skolan.laggTillKlass(TE23E);
        
        //Lägg till lärare i skolan
        skolan.laggTillLärare(lärare1);
        skolan.laggTillLärare(lärare2);

        IO.println(skolan);
        IO.println(TE23E);
    }
}
