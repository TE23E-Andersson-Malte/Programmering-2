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
                    3. Hämta användare
                    4. Skriv ut böcker
                    5. Skriv ut tidningar
                    6. Skapa nytt
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
                            break;
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
                            break;
                        case 3:
                            IO.println("""
                                    === HÄMTA ANVÄNDARE ===
                                    1. Hämta alla användare
                                    2. Hämta en användare
                                    3. Hämta alla avstängda användare
                                    4. Hämta en avstängd användare
                                    5. Gå tillbaka
                                    """);
                            val = Integer.parseInt(IO.readln("Ange alternativ: "));
                            if (val >= 1 && val <= 5) {
                                IO.println("\n----------------------------------------------------\n");
                                switch (val) {
                                    case 1:
                                        bibliotek.getUsersFromServer();
                                        break;
                                    case 2:
                                        bibliotek.getOneUserFromServer();
                                        break;
                                    case 3:
                                        bibliotek.getSuspendedUsersFromServer();
                                        break;
                                    case 4:
                                        bibliotek.getOneSuspendedUserFromServer();
                                    case 5:
                                        break;
                                    default:
                                        break;
                                }
                            } else {
                                IO.println("\nOgiltigt val! Försök igen...");
                            }
                            break;
                        case 4:
                            bibliotek.printBooks();
                            break;
                        case 5:
                            bibliotek.printMagazines();
                            break;
                        case 6:
                            IO.println("""
                                    === SKAPA NYTT OCH LÄGG TILL===
                                    1. Skapa och lägg till bok 
                                    2. Skapa och lägg till tidning
                                    3. Skapa och lägg till användare
                                    4. Skapa och lägg till avständ användare
                                    5. Gå tillbaka
                                    """);
                            val = Integer.parseInt(IO.readln("Ange alternativ: "));
                            if (val >= 1 && val <= 5) {
                                IO.println("\n----------------------------------------------------\n");
                                switch (val) {
                                    case 1:
                                        bibliotek.addBook();
                                        break;
                                    case 2:
                                        bibliotek.addMagazine();
                                        break;
                                    case 3:

                                        break;
                                    case 4: 

                                        break;
                                    case 5:
                                        break;
                                    default:
                                        break;
                                }
                            } else {
                                IO.println("\nOgiltigt val! Försök igen...");
                            }
                            break;
                        case 7:
                            IO.println("Avslutar...\n");
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