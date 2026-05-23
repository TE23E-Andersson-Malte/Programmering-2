package programmeringprojekt.system;

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
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import kong.unirest.Unirest;
import kong.unirest.HttpResponse;
import kong.unirest.UnirestException;
import programmeringprojekt.items.Book;
import programmeringprojekt.items.Game;
import programmeringprojekt.items.Magazine;
import programmeringprojekt.items.Media;
import programmeringprojekt.items.Movie;
import programmeringprojekt.items.MusicAlbum;
import programmeringprojekt.loans.Borrowable;
import programmeringprojekt.loans.Loan;
import programmeringprojekt.loans.LoanManager;
import programmeringprojekt.users.SuspendedUser;
import programmeringprojekt.users.User;

public class LibrarySystem {
    // Listor för att lagra böcker, tidningar, användare, avstängda för sig
    private List<Book> books = new ArrayList<>();
    private List<Magazine> magazines = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private List<SuspendedUser> suspendedUsers = new ArrayList<>();
    private List<Media> media = new ArrayList<>();

    // Mappar för att snabbt hitta hitta objekt
    private Map<String, Book> bookMap = new HashMap<>();
    private Map<String, Magazine> magazineMap = new HashMap<>();
    private Map<String, User> userMap = new HashMap<>();
    private Map<String, Media> mediaMap = new HashMap<>();

    // Set för att snabbt kontrollera om en användare är avstängd
    private Set<String> suspendedIdSet = new HashSet<>();

    LoanManager loanManager = new LoanManager(); // för att kunna låna
    Gson gson = new Gson(); // Gson för att översätta data
    // String baseURL = "http://10.151.168.5:3140/"; // URL till server
    String baseURL = "http://localhost:3000/"; // URL för server lokalt

    // Tomma variabler för hämtning av data från servern
    HttpResponse<String> response;
    int status;
    String body;

    /***************************************
     * ================ E-nivå ================
     ***************************************/

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

        // Kalla på metoden som laddar upp objekt på servern (med inparametrar för
        // vilket objekt, vilken del av servern den ska ligga på och vilken typ av
        // objekt)
        Book responseBook = postToServer(newBook, "books", Book.class);
        // Om metoden returnerar null har något gått snett, avbryt skapandet av boken
        if (responseBook == null) {
            return;
        }

        // Lägg till boken i listan
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

        // Kalla på metoden som laddar upp objekt på servern (med inparametrar för
        // vilket objekt, vilken del av servern den ska ligga på och vilken typ av
        // objekt)
        Magazine responseMagazine = postToServer(newMagazine, "magazines", Magazine.class);
        // Om metoden returnerar null har något gått snett, avbryt skapandet av
        // tidningen
        if (responseMagazine == null) {
            return;
        }

        // Lägg till tidningen i listan
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
    public boolean getBooksFromServer() { // TODO IF null
        // Försök hämta böckerna
        String bookBody = getFromServer("books");

        // Översätt JSON-texten till en ArrayList av Book-objektet
        Type bookType = new TypeToken<ArrayList<Book>>() {
        }.getType();
        // Lägg till i lista av böcker
        ArrayList<Book> jsonBooks = gson.fromJson(bookBody, bookType);

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
        String magazineBody = getFromServer("magazines");

        // Översätt JSON-texten till en ArrayList av Book-objektet
        Type magazineType = new TypeToken<ArrayList<Magazine>>() {
        }.getType();
        // Lägg till i lista av tidningar
        ArrayList<Magazine> jsonMagazines = gson.fromJson(magazineBody, magazineType);

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
        String userBody = getFromServer("users");

        // Översätt JSON-texten till en ArrayList av Användar-objektet
        Type userType = new TypeToken<ArrayList<User>>() {
        }.getType();
        ArrayList<User> jsonUsers = gson.fromJson(userBody, userType);

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
        String suspendedUserBody = getFromServer("suspended");

        // Översätt JSON-texten till en ArrayList av avstägnd Användar-objektet
        Type suspendedUserType = new TypeToken<ArrayList<SuspendedUser>>() {
        }.getType();
        ArrayList<SuspendedUser> jsonSuspendedUsers = gson.fromJson(suspendedUserBody, suspendedUserType);

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

        // Anropa metod för att hämta en bok med id
        String bookBody = getOneFromServer("books", id);

        // Översätt JSON-texten till ett Book-objekt av boken och lägg till i samlingen
        Book jsonBook = gson.fromJson(bookBody, Book.class);

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

        // Anropa metod för att hämta en tidning med id
        String magazineBody = getOneFromServer("magazines", id);

        // Översätt JSON-texten till ett Magazine-objekt av tidningen
        Magazine jsonMagazine = gson.fromJson(magazineBody, Magazine.class);
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

        // Anropa metod för att hämta en user med id
        String userBody = getOneFromServer("users", id);

        // Översätt JSON-texten till ett user-objekt av användaren
        User jsonUser = gson.fromJson(userBody, User.class);
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

        // Anropa metod för att hämta en suspended med id
        String suspendedUserBody = getOneFromServer("suspended", id);

        // Översätt JSON-texten till ett SuspendedUser-objekt av den avstängda
        SuspendedUser jsonSuspendedUser = gson.fromJson(suspendedUserBody, SuspendedUser.class);
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

        // Kalla på metoden som laddar upp objekt på servern (med inparametrar för
        // vilket objekt, vilken del av servern den ska ligga på och vilken typ av
        // objekt)
        User responseUser = postToServer(newUser, "users", User.class);
        // Om metoden returnerar null har något gått snett, avbryt skapandet av objektet
        if (responseUser == null) {
            return;
        }

        // Lägg till användaren i listan
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
        String userId = checkEmpty("Ange användar-ID: ");

        // Skapa ny användare med informationen och lägg till i listan
        SuspendedUser newSuspendedUser = new SuspendedUser(null, userId);

        // Kalla på metoden som laddar upp objekt på servern (med inparametrar för
        // vilket objekt, vilken del av servern den ska ligga på och vilken typ av
        // objekt)
        SuspendedUser responseSuspendedUser = postToServer(newSuspendedUser, "suspended", SuspendedUser.class);
        // Om metoden returnerar null har något gått snett, avbryt skapandet av avstängd
        if (responseSuspendedUser == null) {
            return;
        }

        // Lägg till boken i listan
        suspendedUsers.add(responseSuspendedUser);
        IO.println("\nAvstängda användaren sparades till servern och lokalt i listan: "
                + responseSuspendedUser.toString());

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
        boolean ok = deleteFromServer("books", id);
        if (ok) {
            books.remove(foundBook);
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
        boolean ok = deleteFromServer("magazines", id);
        if (ok) {
            magazines.remove(foundMagazine);
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
        boolean ok = deleteFromServer("users", id);
        if (ok) {
            try {
                users.remove(foundUser);
            } catch (Exception e) {
                IO.println("Användaren togs inte bort från den lokala listan då den inte är hämtad från servern");
            }
        }
    }

    // Metod för att ta bort användare från server
    public void removeSuspendedUser() {
        IO.println("=== TA BORT AVSTÄNGD ANVÄNDARE ===");
        // Fråga om ID
        String id = checkEmpty("Ange ID på avstängda användaren som ska tas bort: ");

        // Försök ta bort
        deleteFromServer("suspended", id);
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

    public void addMedia() {
        IO.println("=== SKAPA NY MEDIA ===");
        String type = checkEmpty("Ange typ av media (game/music_album/movie): ");
        String title = checkEmpty("Ange titel: ");
        Media newMedia = null;
        switch (type) {
            case "game" -> {
                String genre = checkEmpty("Ange genre: ");
                int age = checkEmptyInt("Ange åldersgräns: ");
                newMedia = new Game("game", null, title, true, genre, age);
            }
            case "music_album" -> {
                String artist = checkEmpty("Artist: ");
                newMedia = new MusicAlbum("music_album", null, title, true, artist);
            }
            case "movie" -> {
                String genre = checkEmpty("Genre: ");
                int minutes = checkEmptyInt("Längd (minuter): ");
                newMedia = new Movie("movie", null, title, true, genre, minutes);
            }
            default -> {
                IO.println("Ogiltig typ av media.");
                return;
            }
        }
        // Kalla på metoden som laddar upp objekt på servern (med inparametrar för
        // vilket objekt, vilken del av servern den ska ligga på och vilken typ av
        // objekt)
        Media response = postToServer(newMedia, "media", newMedia.getClass());
        if (response == null) {
            return;
        }
        // Lägg till media i listan
        media.add(response);
        IO.println("Media sparad: " + response.getInfo());

        // Fråga om användaren vill synka users från servern (eller låta den lokala
        // listan vara som den är)
        boolean choice = askYesNo("\nSynka media från server? (j/n): ");
        if (choice) {
            IO.println("Hämtar media från server...");
            getMediaFromServer();
        } else {
            IO.println("Hämtar inte media från server.");
        }
    }

    public boolean getMediaFromServer() {
        // Försök hämta media
        String mediaBody = getFromServer("media");
        if (mediaBody == null) {
            return false;
        }

        // gör om JSOn-texten till en JsonArray för att kunna loopa igenom objekten
        JsonArray jsonArray = JsonParser.parseString(mediaBody).getAsJsonArray();

        // Rensa liston för att fylla på med uppdaterad info
        media.clear();
        mediaMap.clear();

        // Loopa igenom varje objekt
        for (JsonElement element : jsonArray) {
            // Hämta själva objektet och läs ut viklen typ av media det är
            JsonObject object = element.getAsJsonObject();

            // Kontrollera att type finns
            if (!object.has("type")) {
                IO.println("⚠️  VARNING: Media-objekt saknar 'type'. ID: " + object.get("id"));
                IO.println("Detta objekt måste tas bort manuellt.");
                continue; // hoppa över så programmet inte kraschar
            }

            String type = object.get("type").getAsString();

            Media mediaObject = null;

            // Skapa rätt typ av media beroende på typen i json objektet
            if (type.equals("game")) {
                mediaObject = gson.fromJson(object, Game.class);
            } else if (type.equals("music_album")) {
                mediaObject = gson.fromJson(object, MusicAlbum.class);
            } else if (type.equals("movie")) {
                mediaObject = gson.fromJson(object, Movie.class);
            }

            // Lägg till i listor
            if (mediaObject != null) {
                media.add(mediaObject);
                mediaMap.put(mediaObject.getTitle(), mediaObject);
            }
        }

        IO.println("Hämtning av media lyckad! Antal: " + media.size());
        return true;
    }

    // Hämta en media från servern
    public boolean getOneMediaFromServer() {
        // Be användaren ange ID på media som ska hämtas
        String id = checkEmpty("Ange ID på media: ");

        // Anropa metod för att hämta en media med id
        String mediaBody = getOneFromServer("media", id);

        // Gör om JSON-texten till ett JsonObject
        JsonObject mediaObject = JsonParser.parseString(mediaBody).getAsJsonObject();

        // Läs ut vilken typ av media det är
        String type = mediaObject.get("type").getAsString();

        Media m = null;

        // Skapa rätt objekt beroende på typ
        if (type.equals("game")) {
            m = gson.fromJson(mediaObject, Game.class);
        } else if (type.equals("music_album")) {
            m = gson.fromJson(mediaObject, MusicAlbum.class);
        } else if (type.equals("movie")) {
            m = gson.fromJson(mediaObject, Movie.class);
        }

        // Om något gick fel och media objektet fortfarande är null
        if (m == null) {
            IO.println("Kunde inte tolka media-objektet.");
            return false;
        }

        // Ta bort eventuell gammal version av objektet i listan
        for (Media duplicate : media) {
            if (duplicate.getId().equals(m.getId())) {
                media.remove(duplicate);
                break;
            }
        }
        mediaMap.remove(m.getTitle());

        // Lägg till hämtat objekt i lokala listan
        media.add(m);

        // Lägg till i mapen
        mediaMap.put(m.getTitle(), m);

        IO.println("Hämtning av media (ID: " + id + ") lyckad!");
        return true;
    }

    public Media findMedia() {
        String title = checkEmpty("Ange titel: ");

        Media foundMedia = mediaMap.get(title);

        if (foundMedia == null) {
            IO.println("Ingen media med titeln hittades...");
            return null;
        }

        IO.println("Hitta media: LYCKAD!");

        if (foundMedia instanceof Game g) {
            IO.println("Typ: Game");
            IO.println(g.getInfo());
        } else if (foundMedia instanceof Movie mov) {
            IO.println("Typ: Movie");
            IO.println(mov.getInfo());
        } else if (foundMedia instanceof MusicAlbum ma) {
            IO.println("Typ: Music Album");
            IO.println(ma.getInfo());
        } else {
            IO.println("Något gick snett");
        }
        return foundMedia;
    }

    public void printMedia() {
        IO.println("***** Alla media *****");
        for (Media m : media) {
            IO.println(m.getInfo());
        }
    }

    public void printMediaSorted() {
        IO.println("***** Sorterad media *****");
        // Strömma igenom listan, sortera efter titel och skriv ut varje element
        media.stream()
                .sorted()
                .forEach(b -> IO.println(b.getInfo()));
    }

    public void removeMedia() {
        IO.println("=== TA BORT MEDIA ==="); // Kolla om media finns, avbryt om den inte finns
        Media foundMedia = findMedia();
        if (foundMedia == null) {
            return;
        }

        // Om den finns, hämta dess ID
        String id = foundMedia.getId();
        IO.println(id);

        // Försök ta bort boken
        boolean ok = deleteFromServer("media", id);
        if (ok) {
            media.remove(foundMedia);
        }
    }

    // Metod för att skriva ut alla böcker av en författare
    public void printBooksFilteredByAuthor() {
        String author = checkEmpty("Ange författare: ");

        // Strömma igenom böcker, filtrera genom att jämföra varje boks författare med
        // den angivna författaren, skriv ut de böcker med den författaren
        books.stream()
                .filter(b -> b.getAuthor().equalsIgnoreCase(author))
                .forEach(b -> IO.println(b.getInfo()));
    }

    // Metod för att skriva ut alla böcker av en genre
    public void printBooksFilteredByGenre() {
        String genre = checkEmpty("Ange genre: ");

        // Strömma igenom böcker, filtrera genom att jämföra varje boks genre med den
        // angivna genre, skriv ut de böcker med den genre
        books.stream()
                .filter(b -> b.getGenre().equalsIgnoreCase(genre))
                .forEach(b -> IO.println(b.getInfo()));
    }

    // Metod för att skriva ut böcker sorterat efter författare
    public void printBooksSortedByAuthor() {
        // Strömma igenom böcker, sortera genom att jämföra en boks författare med en
        // annan boks författare, skriv ut böckerna sorterat efter författarnamn
        books.stream()
                .sorted((a, b) -> a.getAuthor().compareToIgnoreCase(b.getAuthor()))
                .forEach(b -> IO.println(b.getInfo()));
    }

    // Metod för att skriva ut böcker sorterat efter genre
    public void printBooksSortedByGenre() {
        // Strömma igenom böcker, sortera genom att jämföra en boks genre med en annan
        // boks genre, skriv ut böckerna sorterat efter genre
        books.stream()
                .sorted((a, b) -> a.getGenre().compareToIgnoreCase(b.getGenre()))
                .forEach(b -> IO.println(b.getInfo()));
    }

    // Metod för att räkna antal böcker av en författare
    public void countBooksByAuthor() {
        String auhtor = checkEmpty("Ange författare: ");

        // Strömma igenom böcker, filtrera genom att jämföra varje boks författare med
        // den angivna författaren, räkna antalet författare som matchar
        long count = books.stream()
                .filter(b -> b.getAuthor().equalsIgnoreCase(auhtor))
                .count();

        IO.println("Antal böcker av " + auhtor + ": " + count);
    }

    // Metod för att skriva ut alla bokförfattare
    public void printBookAuthors() {
        // Strömma igenom böckerna, plocka ut varje boks författare, ta bort dubletter,
        // skriv ut varje författare
        books.stream()
                .map(b -> b.getAuthor())
                .distinct()
                .forEach(b -> IO.println(b));
    }

    // Metod för att skriva ut alla bokgenrer
    public void printBookGenres() {
        // Strömma igenom böckerna, plocka ut varje boks genre, ta bort dubletter, skriv
        // ut varje genre
        books.stream()
                .map(b -> b.getGenre())
                .distinct()
                .forEach(g -> IO.println(g));
    }

    // Metod för att låna föremål
    public void loanItem() {
        IO.println("=== LÅNA OBJEKT (book) ===");

        // vem ska låna
        User loanUser = findUser();
        if (loanUser == null) {
            return;
        }

        // vad ska lånas
        Borrowable item = findAnyItem();
        if (item == null) {
            return;
        }

        // går det att låna? (är det tillgängligt?)
        if (!item.borrowItem()) {
            IO.println("Föremålet är redan utlånad");
            return;
        }

        // Hämta IDt för objektet
        String itemId = getIdOfBorrowable(item);

        // Lägg till lånet
        loanManager.addLoan(new Loan(loanUser.getId(), itemId));

        // Uppdatera informationen om tillgängligthet på servern
        updateItemOnServer(item);

        IO.println("Utlåning registrerad");
    }

    // Metod för att returnera föremål
    public void returnItem() {
        IO.println("=== LÄMNA TILLBAKA ===");

        // vad ska returneras
        Borrowable item = findAnyItem();
        if (item == null) {
            return;
        }

        // returnera
        item.returnItem();

        // hämta idt för föremålet
        String itemId = getIdOfBorrowable(item);

        // ta bort lånet
        loanManager.removeLoan(itemId);

        // uppdatera server
        updateItemOnServer(item);

        IO.println("Objektet är nu återlämnat");
    }

    public Borrowable findAnyItem() {
        IO.println("Sök efter objekt (bok/tidning/media)");

        String title = checkEmpty("Ange titel: ");

        // Sök bland listorna för att hitta rätta typ av föremål
        for (Book b : books) {
            if (b.getTitle().equals(title)) {
                return b;
            }
        }
        for (Magazine m : magazines) {
            if (m.getTitle().equals(title)) {
                return m;
            }
        }
        for (Media media : media) {
            if (media.getTitle().equals(title)) {
                return media;
            }
        }

        IO.println("Inget objekt hittades...");
        return null;
    }

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
    public <Type> Type postToServer(Object newObject, String endpoint, Class<Type> responseType) {
        // Översätt objektet till JSON-format
        String jsonBody = gson.toJson(newObject);
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
        // Skicka tillbaka ett objektet som laddas upp på sidan
        return gson.fromJson(response.getBody(), responseType);
    }

    // Generell metod som använder sig av olika inparametrar för att hämta olika
    // saker från server
    private String getFromServer(String endpoint) {
        // Försök hämta alla utav en typ av objekt från servern
        try {
            response = Unirest.get(baseURL + endpoint).asString();
        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return null;
        }

        // Kolla status och verifiera att den är OK, annars felmeddelande
        status = response.getStatus();
        if (status != 200) {
            IO.println("Fel från server! Statuskod: " + status);
            return null;
        }

        // Hämta själva informationen för böckerna
        body = response.getBody();
        return body;
    }

    // Generell metod för att hämta ett objekt från server, använder imparametrar
    // för att få rätt slutpunkt på servern, samt id på objektet
    private String getOneFromServer(String endpoint, String id) {
        // Försök hämta alla utav en typ av objekt från servern
        try {
            response = Unirest.get(baseURL + endpoint + "/" + id).asString();
        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return null;
        }

        // Kolla status och verifiera att den är OK, annars felmeddelande
        status = response.getStatus();
        if (status != 200) {
            IO.println("Fel från server! Statuskod: " + status);
            return null;
        }

        // Hämta själva informationen för böckerna
        body = response.getBody();
        return body;
    }

    // Generell metod för att ta bort ett objekt från server, använder imparametrar
    // för att få rätt slutpunkt på servern, samt id på objektet
    public boolean deleteFromServer(String endpoint, String id) {
        // Försök ta bort
        try {
            status = Unirest.delete(baseURL + endpoint + "/" + id)
                    .asEmpty() // Skickar ingen body
                    .getStatus();
        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return false;
        }

        // Kolla status och ge feedback
        if (status == 200) {
            IO.println("Objektet bland /" + endpoint + "/ med ID: " + id + " togs bort!\n");
            return true;
        } else if (status == 204) {
            IO.println("Objektet bland /" + endpoint + "/ med ID: " + id + " finns inte kvar...\n");
            return false;
        } else if (status == 404) {
            IO.println("Inget objekt bland /" + endpoint + "/ med ID: " + id + " finns...\n");
            return false;
        } else {
            IO.println("Något gick snett. Status: " + status + "\n");
            return false;
        }
    }

    // Metod för att uppdatera objekt på servern
    public void updateItemOnServer(Borrowable item) {
        String endpoint = "";
        String id = "";

        // Om det är en bok
        if (item instanceof Book b) {
            endpoint = "books";
            id = b.getId();
        }
        // Om det är en tidning
        else if (item instanceof Magazine m) {
            endpoint = "magazines";
            id = m.getId();
        }
        // Om det är media (game/movie/music)
        else if (item instanceof Media media) {
            endpoint = "media";
            id = media.getId();
        } else {
            IO.println("Okänd typ av objekt...");
            return;
        }

        // Försök ändra på objektet på servern genom put-request
        try {
            String json = gson.toJson(item);
            response = Unirest.put(baseURL + endpoint + "/" + id)
                    .header("Content-Type", "application/json")
                    .body(json)
                    .asString();
        } catch (Exception e) {
            IO.println("Fel: " + e.getLocalizedMessage());
            return;
        }

        // Kolla status om OK
        if (response.getStatus() != 200) {
            IO.println("Fel: " + response.getStatus());
        } else {
            IO.println("Server uppdaterad!");
        }
    }

    public String getIdOfBorrowable(Borrowable item) {

        //kolla vilken typ det är och returnera idt
        if (item instanceof Book b) {
            return b.getId();
        } else if (item instanceof Magazine m) {
            return m.getId();
        } else if (item instanceof Media media) {
            return media.getId();
        }

        return null;
    }

}
