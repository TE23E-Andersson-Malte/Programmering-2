package programmeringprojekt;

/*
 * Malte Andersson
 * LibrarySystem är kärnan i bibliotekssystemet och ansvarar för all logik som rör hantering av böcker, tidningar, användare och avstängda
 * Klassen kommunicerar med servern via HTTP-anrop (GET, POST, DELETE)
 * och använder Gson för att översätta JSON-data till Java-objekt och tvärtom
 * LibrarySystem använder sig av övriga klasser (Book, Magazine, User, SuspendedUser)
 * och innehåller funktioner för att skapa, hämta, söka, sortera och ta bort objekt, 
 * samt avgöra om användare får låna eller ej
 * Klassen används av Main för att utföra alla meny val 
 * och i grund och botten det som håller ihop hela applikationens funktionalitet  
 */

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import kong.unirest.Unirest;
import kong.unirest.HttpResponse;
import kong.unirest.UnirestException;

public class LibrarySystem {
    // Listor för att lagra böcker, tidningar, användare, avstängda för sig
    private List<Book> books = new ArrayList<>();
    private List<Magazine> magazines = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private List<SuspendedUser> suspendedUsers = new ArrayList<>();

    // Mappar för att snabbt hitta hitta objekt
    private Map<String, Book> bookMap = new HashMap<>();
    private Map<String, Magazine> magazineMap = new HashMap<>();
    private Map<String, User> userMap = new HashMap<>();

    // Set för att snabbt kontrollera om en användare är avstängd
    private Set<String> suspendedIdSet = new HashSet<>();

    Gson gson = new Gson(); // Gson för att översätta data
    // String baseURL = "http://10.151.168.5:3140/"; // URL till server
    String baseURL = "http://localhost:3000/"; // URL för server lokalt

    // Tomma variabler för hämtning av data från servern
    HttpResponse<String> response;
    int status;
    String body;
    String jsonBody;

    /***************************************
     * ================ E-nivå ================
     ***************************************/

    // TODO generella metoder?

    // Metod för att skapa ny bok och lägg till i listan
    public void addBook() {
        IO.println("=== SKAPA NY BOK ===");
        // Be användaren mata in information om boken
        String title = checkEmpty("Ange titel: ");
        String author = checkEmpty("Ange författare: ");
        String genre = checkEmpty("Ange genre: ");
        int pages = checkEmptyInt("Ange antal sidor: ");

        // Skapa ny bok med informationen
        Book newBook = new Book(null, title, true, author, genre, pages);

        //Kalla på metoden som laddar upp objekt på servern (med inparametrar för vilket objekt, vilken del av servern den ska ligga på och vilken typ av objekt)
        Book responseBook = postToServer(newBook, "books", Book.class);
        //Om metoden returnerar null har något gått snett, avbryt skapandet av boken
        if (responseBook == null) {
            return;
        }
        
        //Lägg till boken i listan
        books.add(responseBook);
        IO.println("\nBoken sparades till servern och lokalt i listan: " + responseBook.getInfo());
        
        // Fråga om användaren vill synka users från servern (eller låta den lokala
        // listan vara som den är)
        boolean choice = askYesNo("\nSynka böcker från server? (j/n): ");
        if (choice) {
            IO.println("Hämtar böcker från server...");
            getBooksFromServer();
        } else {
            IO.println("Hämtar inte böcker från server.");
        }
    }

    // Metod för att skapa en ny tidning och ladda upp på servern
    public void addMagazine() {
        IO.println("=== SKAPA NY TIDNING ===");
        // Be användaren mata in info om tidningen
        String title = checkEmpty("Ange titel: ");
        int issueNumber = checkEmptyInt("Ange utgåvonummer: ");
        String category = checkEmpty("Ange kategori: ");
        int publishedYear = checkEmptyInt("Ange publicerat år: ");

        // Skapa en ny tidning
        Magazine newMagazine = new Magazine(null, title, true, issueNumber, category, publishedYear);

        //Kalla på metoden som laddar upp objekt på servern (med inparametrar för vilket objekt, vilken del av servern den ska ligga på och vilken typ av objekt)
        Magazine responseMagazine = postToServer(newMagazine, "magazines", Magazine.class);
        //Om metoden returnerar null har något gått snett, avbryt skapandet av tidningen
        if (responseMagazine == null) {
            return;
        }
        
        //Lägg till tidningen i listan
        magazines.add(responseMagazine);
        IO.println("\nTidningen sparades till servern och lokalt i listan: " + responseMagazine.getInfo());

        // Fråga om användaren vill synka users från servern (eller låta den lokala
        // listan vara som den är)
        boolean choice = askYesNo("\nSynka tidningar från server? (j/n): ");
        if (choice) {
            IO.println("Hämtar tidningar från server...");
            getMagazinesFromServer();
        } else {
            IO.println("Hämtar inte tidningar från server.");
        }
    }

    // Metod för att hämta alla böcker från servern
    public boolean getBooksFromServer() {
        // Försök hämta böckerna
        try {
            response = Unirest.get(baseURL + "books").asString();
        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return false;
        }

        // Kolla status och verifiera att den är OK, annars felmeddelande
        status = response.getStatus();
        if (status != 200) {
            IO.println("Fel från server! Statuskod: " + status);
            return false;
        }

        // Hämta själva informationen för böckerna
        body = response.getBody();

        // Översätt JSON-texten till en ArrayList av Book-objektet
        Type bookType = new TypeToken<ArrayList<Book>>() {
        }.getType();
        // Lägg till i lista av böcker
        ArrayList<Book> jsonBooks = gson.fromJson(body, bookType);

        // Uppdatera den lokala listan genom att ta bort nuvarande objekt och fyll på
        // med hämtade objekt
        books.clear();
        books.addAll(jsonBooks);

        // Lägg till objekten i mapen med en nyckel för att kunna hitta effektivare
        // senare
        bookMap.clear();
        for (Book b : jsonBooks) {
            bookMap.put(b.getTitle(), b);
        }

        IO.println("Hämtning av böcker lyckad! Antal böcker hämtade: " + jsonBooks.size());
        return true;
    }

    // Metod för att hämta alla tidningar från servern
    public boolean getMagazinesFromServer() {
        // Försök hämta tidningar
        try {
            response = Unirest.get(baseURL + "magazines").asString();
        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return false;
        }

        // Kolla status om den är OK
        status = response.getStatus();
        if (status != 200) {
            IO.println("Fel från server! Statuskod: " + status);
            return false;
        }

        // Hämta själva informationen för böckerna
        body = response.getBody();

        // Översätt JSON-texten till en ArrayList av Book-objektet
        Type magazineType = new TypeToken<ArrayList<Magazine>>() {
        }.getType();
        // Lägg till i lista av tidningar
        ArrayList<Magazine> jsonMagazines = gson.fromJson(body, magazineType);

        // Uppdatera den lokala listan genom att ta bort nuvarande objekt och fyll på
        // med hämtade objekt
        magazines.clear();
        magazines.addAll(jsonMagazines);

        // Lägg till objekten i mapen med en nyckel för att kunna hitta effektivare
        // senare
        magazineMap.clear();
        for (Magazine m : jsonMagazines) {
            magazineMap.put(m.getTitle(), m);
        }

        IO.println("Hämtning av tidningar lyckad! Antal tidningar hämtade: " + jsonMagazines.size());
        return true;
    }

    // Metod för att skriva ut böcker
    public void printBooks() {
        IO.println("***** Alla böcker *****");
        // Loopa igenom listan med böcker och skriv ut varje bok på en rad
        for (Book book : books) {
            IO.println(book.getInfo());
        }
    }

    // Metod för att skriva ut tidningar
    public void printMagazines() {
        IO.println("***** Alla tidningar *****");
        // Loopa igenom listan med tidningar och skriv ut
        for (Magazine magazine : magazines) {
            IO.println(magazine.getInfo());
        }
    }

    /***************************************
     * ================ C-nivå ================
     ****************************************/

    // Metod för att hämta alla användare från servern
    public boolean getUsersFromServer() {
        // Försök hämta användre
        try {
            response = Unirest.get(baseURL + "users").asString();
        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return false;
        }

        // Kolla status om den är OK, annars felmeddelande och avbryt
        status = response.getStatus();
        if (status != 200) {
            IO.println("Fel från server! Statuskod: " + status);
            return false;
        }

        // Hämta själva informationen för användarna
        body = response.getBody();

        // Översätt JSON-texten till en ArrayList av Användar-objektet
        Type userType = new TypeToken<ArrayList<User>>() {
        }.getType();
        ArrayList<User> jsonUsers = gson.fromJson(body, userType);

        // Ta bort nuvarande användare i lokala listan och synka istället till hämtade
        // användarna från servern
        users.clear();
        users.addAll(jsonUsers);

        // Lägg till objekten i mapen med en nyckel för att kunna hitta effektivare
        // senare
        userMap.clear();
        for (User u : jsonUsers) {
            userMap.put(u.getEmail(), u);
        }

        IO.println("Hämtning av användare lyckad! Antal användare hämtade: " + jsonUsers.size());
        return true;
    }

    // Metod för att hämta alla avstängda användare från servern
    public boolean getSuspendedUsersFromServer() {
        // Försök hämta avstängda användare
        try {
            response = Unirest.get(baseURL + "suspended").asString();
        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return false;
        }

        // Kolla status om den är OK, annars felmeddelande och avbryt
        status = response.getStatus();
        if (status != 200) {
            IO.println("Fel från server! Statuskod: " + status);
            return false;
        }

        // Hämta själva informationen för de avstända
        body = response.getBody();

        // Översätt JSON-texten till en ArrayList av Användar-objektet
        Type suspendedUserType = new TypeToken<ArrayList<SuspendedUser>>() {
        }.getType();
        ArrayList<SuspendedUser> jsonSuspendedUsers = gson.fromJson(body, suspendedUserType);

        // Ta bort nuvarande objekt och lägg till hämtade serverobjekt
        suspendedUsers.clear();
        suspendedUsers.addAll(jsonSuspendedUsers);

        suspendedIdSet.clear();
        for (SuspendedUser s : jsonSuspendedUsers) {
            suspendedIdSet.add(s.getCustomerId());
        }

        IO.println("Hämtning av avstängda användare lyckad! Antal avstängda användare hämtade: "
                + jsonSuspendedUsers.size());
        return true;
    }

    // Hämta en bok från servern
    public boolean getOneBookFromServer() {
        // Be användaren ange ID på boken som ska hämtas
        String id = checkEmpty("Ange ID på boken: ");

        // Försök hämta boken
        try {
            response = Unirest.get(baseURL + "books/" + id).asString();
        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return false;
        }

        // Kolla status
        status = response.getStatus();
        if (status != 200) {
            IO.println("Fel från server! Statuskod: " + status);
            return false;
        }

        // Hämta själva informationen för boken
        body = response.getBody();

        // Översätt JSON-texten till ett Book-objekt av boken och lägg till i samlingen
        Book jsonBook = gson.fromJson(body, Book.class);

        // Om boken redan finns lagrad lokalt, ta bort innan den hämtade läggs till
        for (Book b : books) {
            if (b.getId().equals(jsonBook.getId())) {
                books.remove(b);
                break;
            }
        }
        // Lägg till hämtade boken i lokala listan
        books.add(jsonBook);

        bookMap.remove(jsonBook.getTitle());
        bookMap.put(jsonBook.getTitle(), jsonBook);

        IO.println("Hämtning av bok (ID: " + id + ") lyckad!");
        return true;
    }

    // Metod för att hämta en tidning från Servern
    public boolean getOneMagazineFromServer() {
        // Be användaren ange ID på det som ska hämtas
        String id = checkEmpty("Ange ID på tidningen: ");

        // Försök hämta tidningen
        try {
            response = Unirest.get(baseURL + "magazines/" + id).asString();
        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return false;
        }

        // Kolla status
        status = response.getStatus();
        if (status != 200) {
            IO.println("Fel från server! Statuskod: " + status);
            return false;
        }

        // Hämta själva informationen för tidningen
        body = response.getBody();

        // Översätt JSON-texten till ett Magazine-objekt av tidningen
        Magazine jsonMagazine = gson.fromJson(body, Magazine.class);
        // Om tidningen redan finns lagrad lokalt, ta bort innan den hämtade läggs till
        for (Magazine m : magazines) {
            if (m.getId().equals(jsonMagazine.getId())) {
                magazines.remove(m);
                break;
            }
        }
        // Lägg till hämtade tidningen i lokala samlingen
        magazines.add(jsonMagazine);

        magazineMap.remove(jsonMagazine.getTitle());
        magazineMap.put(jsonMagazine.getTitle(), jsonMagazine);

        IO.println("Hämtning av tidning (ID: " + id + ") lyckad!");
        return true;
    }

    // Metod för att hämta en användare från servern
    public boolean getOneUserFromServer() {
        // Be användaren ange ID på det som ska hämtas
        String id = checkEmpty("Ange ID på användaren: ");

        // Försök hämta användare
        try {
            response = Unirest.get(baseURL + "users/" + id).asString();
        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return false;
        }

        // Kolla status
        status = response.getStatus();
        if (status != 200) {
            IO.println("Fel från server! Statuskod: " + status);
            return false;
        }

        // Hämta själva informationen för användare
        body = response.getBody();

        // Översätt JSON-texten till ett user-objekt av användaren
        User jsonUser = gson.fromJson(body, User.class);
        // Om användaren redan finns lagrad lokalt, ta bort innan den hämtade läggs till
        for (User u : users) {
            if (u.getId().equals(jsonUser.getId())) {
                users.remove(u);
                break;
            }
        }
        // Lägg till i den lokala listan
        users.add(jsonUser);

        userMap.remove(jsonUser.getEmail());
        userMap.put(jsonUser.getEmail(), jsonUser);

        IO.println("Hämtning av användare (ID: " + id + ") lyckad!");
        return true;
    }

    // Metod för att hämta en avstängd användare från servern
    public boolean getOneSuspendedUserFromServer() {
        // Be användaren ange ID på det som ska hämtas
        String id = checkEmpty("Ange ID på avstängd användare: ");

        // Försök hämta avstängda
        try {
            response = Unirest.get(baseURL + "suspended/" + id).asString();
        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return false;
        }

        // Kolla status
        status = response.getStatus();
        if (status != 200) {
            IO.println("Fel från server! Statuskod: " + status);
            return false;
        }

        // Hämta själva informationen för avstängda
        body = response.getBody();

        // Översätt JSON-texten till ett SuspendedUser-objekt av den avstängda
        SuspendedUser jsonSuspendedUser = gson.fromJson(body, SuspendedUser.class);
        // Om avstängda redan finns lagrad lokalt, ta bort innan den hämtade läggs till
        for (SuspendedUser s : suspendedUsers) {
            if (s.getId().equals(jsonSuspendedUser.getId())) {
                suspendedUsers.remove(s);
                break;
            }
        }
        // Lägg till i lokala listan
        suspendedUsers.add(jsonSuspendedUser);

        suspendedIdSet.remove(jsonSuspendedUser.getCustomerId());
        suspendedIdSet.add(jsonSuspendedUser.getCustomerId());

        IO.println("Hämtning av avständ användare (ID: " + id + ") lyckad!");
        return true;
    }

    // Metod för att skapa ny användare och lägg till i listan och på server
    public void addUser() {
        IO.println("=== SKAPA NY ANVÄNDARE ===");
        // Be användaren mata in info om ny user
        String name = checkEmpty("Ange namn: ");
        String email = checkEmpty("Ange email: ");

        // Skapa ny användare med informationen och lägg till i listan
        User newUser = new User(null, name, email.toLowerCase());

        //Kalla på metoden som laddar upp objekt på servern (med inparametrar för vilket objekt, vilken del av servern den ska ligga på och vilken typ av objekt)
        User responseUser = postToServer(newUser, "users", User.class);
        //Om metoden returnerar null har något gått snett, avbryt skapandet av objektet
        if (responseUser == null) {
            return;
        }
        
        //Lägg till användaren i listan
        users.add(responseUser);
        IO.println("\nAnvändaren sparades till servern och lokalt i listan: " + responseUser.toString());

        // Fråga om användaren vill synka users från servern (eller låta den lokala
        // listan vara som den är)
        boolean choice = askYesNo("\nSynka användare från server? (j/n): ");
        if (choice) {
            IO.println("Hämtar användare från server...");
            getUsersFromServer();
        } else {
            IO.println("Hämtar inte användare från server.");
        }
    }

    // Metod för att skapa ny avstängd använadre och lägg till i listan och på
    // server
    public void addSuspendedUser() {
        IO.println("=== SKAPA NY AVSTÄNGA ANVÄNDARE ===");
        // Fråga om info
        String userId = checkEmpty("Ange användar-ID: "); // TODO Ska man kolla så att det finns en user att para ihop
                                                          // till denna???

        // Skapa ny användare med informationen och lägg till i listan
        SuspendedUser newSuspendedUser = new SuspendedUser(null, userId);

        //Kalla på metoden som laddar upp objekt på servern (med inparametrar för vilket objekt, vilken del av servern den ska ligga på och vilken typ av objekt)
        SuspendedUser responseSuspendedUser = postToServer(newSuspendedUser, "suspended", SuspendedUser.class);
        //Om metoden returnerar null har något gått snett, avbryt skapandet av avstängd
        if (responseSuspendedUser == null) {
            return;
        }
        
        //Lägg till boken i listan
        suspendedUsers.add(responseSuspendedUser);
        IO.println("\nAvstängda användaren sparades till servern och lokalt i listan: " + responseSuspendedUser.toString());

        // Fråga om användaren vill synka users från servern (eller låta den lokala
        // listan vara som den är)
        boolean choice = askYesNo("\nSynka avstängda användare från server? (j/n): ");
        if (choice) {
            IO.println("Hämtar avstängda användare från server...");
            getSuspendedUsersFromServer();
        } else {
            IO.println("Hämtar inte avstängda användare från server.");
        }
    }

    // Metod för att hitta en kund med hjälp av email-adress
    public User findUser() {
        // Fråga om email
        String email = checkEmpty("Ange användarens email: ");
        User foundUser;
        try {
            foundUser = userMap.get(email.toLowerCase());
            IO.println(foundUser.toString());
            IO.println("Hitta användare: LYCKAD!");
            return foundUser;
        } catch (Exception e) {
            IO.println("Ingen användare med emailen hittades...");
            return null;
        }
    }

    // Metod för att hitta en bok med hjälp av titel
    public Book findBook() {
        // Fråga om titel
        String title = checkEmpty("Ange bokens titel: ");

        Book foundBook;
        try {
            foundBook = bookMap.get(title);
            IO.println(foundBook.getInfo());
            IO.println("Hitta bok: LYCKAD!");
            return foundBook;
        } catch (Exception e) {
            IO.println("Ingen bok med titeln hittades...");
            return null;
        }
    }

    // Metod för att hitta en tidning med hjälp av titel
    public Magazine findMagazine() {
        // fråga om titel
        String title = checkEmpty("Ange tidningens titel: ");
        Magazine foundMagazine;
        try {
            foundMagazine = magazineMap.get(title);
            IO.println(foundMagazine.getInfo());
            IO.println("Hitta tidning: LYCKAD!");
            return foundMagazine;
        } catch (Exception e) {
            IO.println("Ingen tidning med titlen hittades...");
            return null;
        }
    }

    // Metod för att ta bort bok från server
    public void removeBook() {
        IO.println("=== TA BORT BOK ==="); // Kolla om boken finns, avbryt om den inte finns
        Book foundBook = findBook();
        if (foundBook == null) {
            return;
        }

        // Om den finns, hämta dess ID
        String id = foundBook.getId();

        // Försök ta bort boken
        try {
            status = Unirest.delete(baseURL + "books/" + id)
                    .asEmpty() // Skickar ingen body
                    .getStatus();
        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return;
        }

        // Kolla status och meddela om borttagning lyckades eller inte
        if (status == 200) {
            IO.println("Boken med titeln: " + foundBook.getTitle() + " togs bort!\n");
        } else if (status == 204) {
            IO.println("Boken finns inte kvar / inget innehåll med titeln: " + foundBook.getTitle() + "\n");
        } else {
            IO.println("Något gick snett. Status: " + status + "\n");
        }
    }

    // Metod för att ta bort tidning från server
    public void removeMagazine() {
        IO.println("=== TA BORT TIDNING ===;");
        // Kolla om tidningen finns
        Magazine foundMagazine = findMagazine();
        if (foundMagazine == null) {
            return;
        }

        // Hämta dess ID
        String id = foundMagazine.getId();

        // Försök ta bort
        try {
            status = Unirest.delete(baseURL + "magazines/" + id)
                    .asEmpty() // Skickar ingen body
                    .getStatus();
        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return;
        }

        // Kolla status för att se om borttagning lyckades
        if (status == 200) {
            IO.println("Tidningen med titeln: " + foundMagazine.getTitle() + " togs bort!\n");
        } else if (status == 204) {
            IO.println("Tidningen finns inte kvar / inget innehåll med titeln: " + foundMagazine.getTitle() + "\n");
        } else {
            IO.println("Något gick snett. Status: " + status + "\n");
        }
    }

    // Metod för att ta bort användare från server
    public void removeUser() {
        IO.println("=== TA BORT ANVÄNDARE ===");
        // Kolla om den finns
        User foundUser = findUser();
        if (foundUser == null) {
            return;
        }

        // Hämta dess ID
        String id = foundUser.getId();

        // Försök ta bort
        try {
            status = Unirest.delete(baseURL + "users/" + id)
                    .asEmpty() // Skickar ingen body
                    .getStatus();
        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return;
        }

        // Kolla status om borttagning lycakdes
        if (status == 200) {
            IO.println("Användaren med emailen: " + foundUser.getEmail() + " togs bort!\n");
        } else if (status == 204) {
            IO.println("Användaren finns inte kvar / inget innehåll med emailen: " + foundUser.getEmail() + "\n");
        } else {
            IO.println("Något gick snett. Status: " + status + "\n");
        }
    }

    // Metod för att ta bort användare från server
    public void removeSuspendedUser() {
        IO.println("=== TA BORT AVSTÄNGD ANVÄNDARE ===");
        // Fråga om ID
        String id = checkEmpty("Ange ID på avstängda användaren som ska tas bort: ");

        // Försök ta bort
        try {
            status = Unirest.delete(baseURL + "suspended/" + id)
                    .asEmpty() // Skickar ingen body
                    .getStatus();
        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return;
        }

        // Kolla status och ge feedback
        if (status == 200) {
            IO.println("Avstängda användaren med ID: " + id + " togs bort!\n");
        } else if (status == 204) {
            IO.println("Avstängda användaren finns inte kvar: " + id + "\n");
        } else if (status == 404) {
            IO.println("Inget avstängd användare med ID: " + id + " finns...\n");
        } else {
            IO.println("Något gick snett. Status: " + status + "\n");
        }
    }

    // Metod för att skriva ut böcker sorterat efter titel
    public void printBooksSorted() {
        IO.println("***** Sorterade böcker *****");
        // Strömma igenom listan, sortera efter titel och skriv ut varje element
        books.stream()
                .sorted()
                .forEach(b -> IO.println(b.getInfo()));
    }

    // Metod för att skriva ut tidningar sorterat efter titel
    public void printMagazinesSorted() {
        IO.println("***** Sorterade tidningar *****");
        // Strömma igenom listan, sortera efter titel och skriv ut varje element
        magazines.stream()
                .sorted()
                .forEach(m -> IO.println(m.getInfo()));
    }

    // Metod för att skriva ut användare sorterat efter namn
    public void printUsersSorted() {
        IO.println("***** Sorterade användare *****");
        // Strömma igenom listan, sortera och skriv ut
        users.stream()
                .sorted()
                .forEach(u -> IO.println(u.toString()));
    }

    // Metod för att skriva ut användare
    public void printUsers() {
        IO.println("***** Alla användare *****");
        // Loopa igenom listan och skriv ut varje element
        for (User user : users) {
            IO.println(user.toString());
        }
    }

    // Metod för att skriva ut avstängda
    public void printSuspendedUser() {
        IO.println("***** Alla avstängda användare *****");
        // Loopa igenom listan och skriv ut varje element
        for (SuspendedUser s : suspendedUsers) {
            IO.println(s.toString());
        }
    }

    // Metod för att kolla om användaren får låna
    public boolean canUserBorrow() {
        IO.println("=== KAN ANVÄNDAREN LÅNA? ===\n");

        User u = findUser();
        if (u == null) {
            return false;
        }

        // Kolla om användaren är avstängd via Set
        if (suspendedIdSet.contains(u.getId())) {
            IO.println("Användaren är avstängd och får inte låna.");
            return false;
        }

        IO.println("Användaren får låna!");
        return true;
    }

    /***************************************
     * ================ A-nivå ================
     ****************************************/

    // Börja här

    /****************************************
     * =============== METODER ===============
     ****************************************/

    // Metod för att kolla val i meny
    public int checkChoice() {
        int choice = 0;
        try {
            choice = Integer.parseInt(IO.readln("Ange alternativ: "));
        } catch (Exception e) {
            return choice;
        }
        return choice;
    }

    // Metod för att kolla om inmatningsfält är tomma
    public String checkEmpty(String prompt) {
        // Läs in inmatning
        String input = IO.readln(prompt).trim();

        // Loopa tills fältet inte är tomt
        while (input.isEmpty()) {
            IO.println("Fältet får inte vara tomt!");
            input = IO.readln(prompt).trim();
        }

        // Returnera inmatningen
        return input;
    }

    // Metod för att kolla att int-inputs inte är tomma, och så att de är siffror
    public int checkEmptyInt(String prompt) {
        // Läs in inmatning
        String input = IO.readln(prompt).trim();

        // Loopa tills ett giltigt värde anges
        while (true) {
            if (input.isEmpty()) {
                IO.println("Fältet får inte vara tomt!");
            } else {
                try {
                    int val = Integer.parseInt(input);
                    if (val <= 0) {
                        IO.println("Du måste ange ett positivt tal!");
                    } else {
                        return val;
                    }
                } catch (Exception e) {
                    IO.println("Du måste ange en siffra!");
                }
            }
            input = IO.readln(prompt).trim();
        }
    }

    // Metod för att kolla svar på ja/nej frågor
    public boolean askYesNo(String prompt) {
        // Läs in inmatning
        String choice = IO.readln(prompt).trim().toLowerCase();

        // Loopa tills användaren anger antingen j eller n
        while (!choice.equals("j") && !choice.equals("n")) {
            IO.println("Ogiltigt choice. Skriv j eller n");
            choice = IO.readln(prompt).trim().toLowerCase();
        }

        if (choice.equals("j")) {
            return true;
        } else {
            return false;
        }
    }

    // Generell metod för att ladda upp ett objekt på servern
    public <Type> Type postToServer(Object newObject, String endpoint, Class<Type> responseType){
        //Översätt objektet till JSON-format
        jsonBody = gson.toJson(newObject);
        // Försök lägga till objektet på servern, annars skicka undantag
        try {
            response = Unirest.post(baseURL + endpoint)
                .header("Content-Type", "application/json")
                .body(jsonBody)
                .asString(); // Returnerar ett HTTPResponse<String>
        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return null;
        }

        // Hämta statuskod och kolla om den är OK, annars ge felmeddelande
        status = response.getStatus();
        if (status != 200 && status != 201) {
            IO.println("\nFel från server: " + status);
            return null;
        }
            //Skicka tillbaka ett objektet som laddas upp på sidan
            return gson.fromJson(response.getBody(), responseType);
        }

}
