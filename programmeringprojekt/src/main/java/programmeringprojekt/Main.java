package programmeringprojekt;

public class Main {
    public static void main(String[] args) {

        LibrarySystem bibliotek = new LibrarySystem();

        //Meny
        boolean loop = true;
        while (loop) {
            IO.println("""
                    \n==== BIBLIOTEK ====
                    1. Hämta böcker
                    2. Hämta tidningar
                    3. Skriv ut böcker
                    4. Skriv ut tidningar
                    5. Lägg till bok
                    6. Lägg till tidning
                    7. Avsluta
                    """);

            int val = Integer.parseInt(IO.readln("Ange alternativ: "));
            IO.println("\n----------------------------------------------------\n");
            switch (val) {
                case 1:
                    bibliotek.getBooksFromServer();
                    break;
                case 2:
                    bibliotek.getMagazinesFromServer();
                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:

                    break;
                case 6:

                    break;
                case 7:
                    IO.println("Avslutar...");
                    loop = false;
                    break;
                default:
                    break;
            }
        }
    }
}