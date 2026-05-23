package programmeringprojekt.system;

import kong.unirest.Unirest;

/*
* Malte Andersson
* Klassen main ansvarar för visa huvudmenyn och dess undermenyer 
samt ansvarar för användarens val.
Main skapar ett LibrarySystem-objekt och anropar dess metoder beroende på användarens val i menyn
Klassen hanterar programloopen och ser till att användaren för tydlig feedback vid varje val
Main innehåller ingen logik utan fungerar endast som vägen mellan användaren och Librarysystem
*/

public class Main {
    public static void main(String[] args) {

        // Skapa ett nytt bibliotekssystem
        LibrarySystem bibliotek = new LibrarySystem();

        // Meny för programmet
        boolean loop = true;
        int val;
        while (loop) {
            val = 0;
            IO.println("""
                    \n==== BIBLIOTEK ====
                    1. Hämta
                    2. Skriv ut
                    3. Skapa nytt
                    4. Kan användaren låna?
                    5. Hitta
                    6. Ta bort
                    7. Streams
                    8. Låna
                    9. Avsluta
                    """);
            val = bibliotek.checkChoice();
            IO.println("\n-------------------------------------------------");
            switch (val) {
                case 1:
                    IO.println("""
                            === HÄMTA ===
                            1. Hämta böcker
                            2. Hämta tidningar
                            3. Hämta användare
                            4. Hämta media
                            5. Gå tillbaka
                            """);
                    val = bibliotek.checkChoice();
                    IO.println("\n-------------------------------------------------");
                    switch (val) {
                        case 1:
                            IO.println("""
                                    === HÄMTA BÖCKER ===
                                    1. Hämta alla böcker
                                    2. Hämta en bok
                                    3. Gå tillbaka
                                    """);
                            val = bibliotek.checkChoice();
                            IO.println("\n-------------------------------------------------");
                            switch (val) {
                                case 1 -> bibliotek.getBooksFromServer();
                                case 2 -> bibliotek.getOneBookFromServer();
                                case 3 -> {
                                    break;
                                }
                                default -> {
                                    IO.println("\nOgiltigt val! Försök igen...");
                                    break;
                                }
                            }
                            break;
                        case 2:
                            IO.println("""
                                    === HÄMTA TIDNINGAR ===
                                    1. Hämta alla tidningar
                                    2. Hämta en tidning
                                    3. Gå tillbaka
                                    """);
                            val = bibliotek.checkChoice();
                            IO.println("\n-------------------------------------------------");
                            switch (val) {
                                case 1 -> bibliotek.getMagazinesFromServer();
                                case 2 -> bibliotek.getOneMagazineFromServer();
                                case 3 -> {
                                    break;
                                }
                                default -> {
                                    IO.println("\nOgiltigt val! Försök igen...");
                                    break;
                                }
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
                            val = bibliotek.checkChoice();
                            IO.println("\n-------------------------------------------------");
                            switch (val) {
                                case 1 -> bibliotek.getUsersFromServer();
                                case 2 -> bibliotek.getOneUserFromServer();
                                case 3 -> bibliotek.getSuspendedUsersFromServer();
                                case 4 -> bibliotek.getOneSuspendedUserFromServer();
                                case 5 -> {
                                    break;
                                }
                                default -> {
                                    IO.println("\nOgiltigt val! Försök igen...");
                                    break;
                                }
                            }
                            break;
                        case 4: 
                            IO.println("""
                                    === HÄMTA MEDIA ===
                                    1. Hämta all media
                                    2. Hämta en media
                                    3. Gå tillbaka
                                    """);
                            val = bibliotek.checkChoice();
                            IO.println("\n-------------------------------------------------");
                            switch (val) {
                                case 1 -> bibliotek.getMediaFromServer();
                                case 2 -> bibliotek.getOneMediaFromServer();
                                case 3 -> {
                                    break;
                                }
                                default -> {
                                    IO.println("\nOgiltigt val! Försök igen...");
                                    break;
                                }
                            }
                            break;
                        case 5:
                            break;
                        default:
                            IO.println("\nOgiltigt val! Försök igen...");
                            break;
                    }
                    break;
                case 2:
                    IO.println("""
                            === SKRIV UT ===
                            1. Skriv ut böcker
                            2. Skriv ut tidningar
                            3. Skriv ut användare
                            4. Skriv ut media
                            5. Gå tillbaka
                            """);
                    val = bibliotek.checkChoice();
                    IO.println("\n-------------------------------------------------");
                    switch (val) {
                        case 1:
                            IO.println("""
                                    === SKRIV UT BÖCKER ===
                                    1. Skriv ut böcker
                                    2. Skriv ut böcker sorterat (efter title)
                                    3. Gå tillbaka
                                    """);
                            val = bibliotek.checkChoice();
                            IO.println("\n-------------------------------------------------");
                            switch (val) {
                                case 1 -> bibliotek.printBooks();
                                case 2 -> bibliotek.printBooksSorted();
                                case 3 -> {
                                    break;
                                }
                                default -> {
                                    IO.println("\nOgiltigt val! Försök igen...");
                                    break;
                                }
                            }
                            break;
                        case 2:
                            IO.println("""
                                    === SKRIV UT TIDNINGAR ===
                                    1. Skriv ut tidningar
                                    2. Skriv ut tidningar sorterat (efter title)
                                    3. Gå tillbaka
                                    """);
                            val = bibliotek.checkChoice();
                            IO.println("\n-------------------------------------------------");
                            switch (val) {
                                case 1 -> bibliotek.printMagazines();
                                case 2 -> bibliotek.printMagazinesSorted();
                                case 3 -> {
                                    break;
                                }
                                default -> {
                                    IO.println("\nOgiltigt val! Försök igen...");
                                    break;
                                }
                            }
                            break;
                        case 3:
                            IO.println("""
                                    === SKRIV UT ANVÄNDARE ===
                                    1. Skriv ut användare
                                    2. Skriv ut användare sorterat (efter namn)
                                    3. Skriv ut avstängda användare
                                    4. Gå tillbaka
                                    """);
                            val = bibliotek.checkChoice();
                            IO.println("\n-------------------------------------------------");
                            switch (val) {
                                case 1 -> bibliotek.printUsers();
                                case 2 -> bibliotek.printUsersSorted();
                                case 3 -> bibliotek.printSuspendedUser();
                                case 4 -> {
                                    break;
                                }
                                default -> {
                                    IO.println("\nOgiltigt val! Försök igen...");
                                    break;
                                }
                            }
                            break;
                        case 4:
                            IO.println("""
                                    === SKRIV UT MEDIA ===
                                    1. Skriv ut media
                                    2. Skriv ut media sorterat (efter title)
                                    3. Gå tillbaka
                                    """);
                            val = bibliotek.checkChoice();
                            IO.println("\n-------------------------------------------------");
                            switch (val) {
                                case 1 -> bibliotek.printMedia();
                                case 2 -> bibliotek.printMediaSorted();
                                case 3 -> {
                                    break;
                                }
                                default -> {
                                    IO.println("\nOgiltigt val! Försök igen...");
                                    break;
                                }
                            }
                            break;
                        case 5:
                            break;
                        default:
                            IO.println("\nOgiltigt val! Försök igen...");
                            break;
                    }
                    break;
                case 3:
                    IO.println("""
                            === SKAPA NYTT ===
                            1. Skapa ny bok
                            2. Skapa ny tidning
                            3. Skapa ny användare
                            4. Skapa ny avständ användare
                            5. Skapa ny media
                            6. Gå tillbaka
                            """);
                    val = bibliotek.checkChoice();
                    IO.println("\n-------------------------------------------------");
                    switch (val) {
                        case 1 -> bibliotek.addBook();
                        case 2 -> bibliotek.addMagazine();
                        case 3 -> bibliotek.addUser();
                        case 4 -> bibliotek.addSuspendedUser();
                        case 5 -> bibliotek.addMedia();
                        case 6 -> {
                            break;
                        }
                        default -> {
                            IO.println("\nOgiltigt val! Försök igen...");
                            break;
                        }
                    }
                    break;
                case 4:
                    bibliotek.canUserBorrow();
                    break;
                case 5:
                    IO.println("""
                            === HITTA ===
                            1. Hitta bok
                            2. Hitta tidning
                            3. Hitta användare
                            4. Hitta media
                            5. Gå tillbaka
                            """);
                    val = bibliotek.checkChoice();
                    IO.println("\n-------------------------------------------------");
                    switch (val) {
                        case 1 -> bibliotek.findBook();
                        case 2 -> bibliotek.findMagazine();
                        case 3 -> bibliotek.findUser();
                        case 4 -> bibliotek.findMedia();
                        case 5 -> {
                            break;
                        }
                        default -> {
                            IO.println("\nOgiltigt val! Försök igen...");
                            break;
                        }
                    }
                    break;
                case 6:
                    IO.println("""
                            === TA BORT ===
                            1. Ta bort bok
                            2. Ta bort tidning
                            3. Ta bort användare
                            4. Ta bort avstängd användare
                            5. Ta bort media
                            6. Gå tillbaka
                            """);
                    val = bibliotek.checkChoice();
                    IO.println("\n-------------------------------------------------");
                    switch (val) {
                        case 1 -> bibliotek.removeBook();
                        case 2 -> bibliotek.removeMagazine();
                        case 3 -> bibliotek.removeUser();
                        case 4 -> bibliotek.removeSuspendedUser();
                        case 5 -> bibliotek.removeMedia();
                        case 6 -> {
                            break;
                        }
                        default -> {
                            IO.println("\nOgiltigt val! Försök igen...");
                            break;
                        }
                    }
                    break;
                case 7: 
                    IO.println("""
                            === STREAMS ===
                            1. Filtrering
                            2. Sortering
                            3. Visa data
                            4. Gå tillbaka
                            """);
                            val = bibliotek.checkChoice();
                    IO.println("\n-------------------------------------------------");
                    switch (val) {
                        case 1:
                            IO.println("""
                                    === FILTRERING ===
                                    1. Filtrera böcker efter författare
                                    2. Filtrera böcker efter genre
                                    3. Gå tillbaka
                                    """);
                            val = bibliotek.checkChoice();
                            IO.println("\n-------------------------------------------------");
                            switch (val) {
                                case 1 -> bibliotek.printBooksFilteredByAuthor();
                                case 2 -> bibliotek.printBooksFilteredByGenre();
                                case 3 -> {
                                    break;
                                }
                                default -> {
                                    IO.println("\nOgiltigt val! Försök igen...");
                                    break;
                                }
                            }
                            break;
                        case 2:
                            IO.println("""
                                    === SORTERING ===
                                    1. Sortera böcker efter författare
                                    2. Sortera böcker efter genre
                                    3. Gå tillbaka
                                    """);
                            val = bibliotek.checkChoice();
                            IO.println("\n-------------------------------------------------");
                            switch (val) {
                                case 1 -> bibliotek.printBooksSortedByAuthor();
                                case 2 -> bibliotek.printBooksSortedByGenre();
                                case 3 -> {
                                    break;
                                }
                                default -> {
                                    IO.println("\nOgiltigt val! Försök igen...");
                                    break;
                                }
                            }
                            break;
                        case 3:
                            IO.println("""
                                    === VISA DATA ===
                                    1. Visa antal böcker av en författare
                                    2. Visa alla bokförfattare
                                    3. Visas alla bokgenrer
                                    4. Gå tillbaka
                                    """);
                            val = bibliotek.checkChoice();
                            IO.println("\n-------------------------------------------------");
                            switch (val) {
                                case 1 -> bibliotek.countBooksByAuthor();
                                case 2 -> bibliotek.printBookAuthors();
                                case 3 -> bibliotek.printBookGenres();
                                case 4 -> {
                                    break;
                                }
                                default -> {
                                    IO.println("\nOgiltigt val! Försök igen...");
                                    break;
                                }
                            }
                            break;
                        case 4:
                            break;
                        default:
                            IO.println("\nOgiltigt val! Försök igen...");
                            break;
                    }
                    break;
                case 8:
                    IO.println("""
                            1. Låna
                            2. Returnera
                            3. Gå tillbaka
                            """);
                            val = bibliotek.checkChoice();
                            IO.println("\n-------------------------------------------------");
                            switch (val) {
                                case 1 -> bibliotek.loanItem();
                                case 2 -> bibliotek.returnItem();
                                case 3 -> {
                                    break;
                                }
                                default -> {
                                    IO.println("\nOgiltigt val! Försök igen...");
                                    break;
                                }
                            }
                        break;
                case 9:
                    IO.println("Avslutar...\n");
                    loop = false;
                    Unirest.shutDown();
                    break;
                default:
                    IO.println("\nOgiltigt val! Försök igen...");
                    break;
            }
        }
    }
}
