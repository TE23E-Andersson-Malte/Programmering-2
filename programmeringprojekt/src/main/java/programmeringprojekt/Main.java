package programmeringprojekt;

/*
* Malte Andersson
* Filen där menyn för programmet körs
*/

public class Main {
    public static void main(String[] args) {

        // Skapa ett nytt bibliotekssystem
        LibrarySystem bibliotek = new LibrarySystem();

        // Meny för programmet
        boolean loop = true;
        int val = 0;
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
            try {
                val = Integer.parseInt(IO.readln("Ange alternativ: "));
                if (val >= 1 && val <= 7) {
                    IO.println("\n----------------------------------------------------\n");
                    switch (val) {
                        // TODO Undermenyer
                        // Undermeny för hämta böcker
                        case 1:
                            IO.println("""
                                    === HÄMTA BÖCKER ===
                                    1. Hämta alla böcker
                                    2. Hämta en bok
                                    3. Gå tillbaka
                                    """);
                            val = Integer.parseInt(IO.readln("Ange alternativ: "));
                            if (val >= 1 && val <= 3) {
                                IO.println("\n----------------------------------------------------\n");
                                switch (val) {
                                    case 1:
                                        bibliotek.getBooksFromServer();
                                        break;
                                    case 2:
                                        bibliotek.getOneBookFromServer();
                                        break;
                                    case 3:
                                        break;
                                    default:
                                        break;
                                }
                            } else {
                                IO.println("\nOgiltigt val! Försök igen...");
                            }
                        case 2:
                            IO.println("""
                                    === HÄMTA TIDNINGAR ===
                                    1. Hämta alla tidningar
                                    2. Hämta en tidning
                                    3. Gå tillbaka
                                    """);
                            val = Integer.parseInt(IO.readln("Ange alternativ: "));
                            if (val >= 1 && val <= 3) {
                                IO.println("\n----------------------------------------------------\n");
                                switch (val) {
                                    case 1:
                                        bibliotek.getMagazinesFromServer();
                                        break;
                                    case 2:
                                        bibliotek.getOneMagazineFromServer();
                                        break;
                                    case 3:
                                        break;
                                    default:
                                        break;
                                }
                            } else {
                                IO.println("\nOgiltigt val! Försök igen...");
                            }
                        case 3:
                            bibliotek.printBooks();
                            break;
                        case 4:
                            bibliotek.printMagazines();
                            break;
                        case 5:
                            bibliotek.addBookToArrayList();
                            break;
                        case 6:
                            bibliotek.addMagazineToArrayList();
                            break;
                        case 7:
                            IO.println("Avslutar...");
                            loop = false;
                            break;
                        default:
                            break;
                    }
                } else {
                    IO.println("\nOgiltigt val! Försök igen...");
                }
            } catch (Exception e) {
                IO.println("\nOgiltigt val! Försök igen...");
            }

        }

    }
}