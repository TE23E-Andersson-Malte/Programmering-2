package programmeringprojekt;

/*
 * Malte Andersson
 * LibrarySystem är kärnan i bibliotekssystemet och ansvarar för all logik som rör hantering av böcker, tidningar, användare och avstängda
 * Klassen kommunicerar med servern via HTTP-anrop (GET, POST, DELETE)
 * och använder Gson för att översätta JSON-data till Java-objekt och tvärtom
 * LibrarySystem använder sig av övriga klasser (Book, Magazine, User, SuspendedUser)
 * och innehåller funktioner för att skapa, hämta, söka, sortera och ta bort objekt, 
 * samt avgöra om användare får låna eller ej
 * Klassen används av Main för att utföra alla menyval 
 * och i grund och botten det som håller ihop hela applikationens funktionalitet  
 */

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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

    // TODO städa och kommentera

    // Metod för att skapa ny bok och lägg till i listan
    public void addBook() {
        // Be användaren mata in information om boken
        String title = checkEmpty("Ange titel: ");
        String author = checkEmpty("Ange författare: ");
        String genre = checkEmpty("Ange genre: ");
        int pages = checkEmptyInt("Ange antal sidor: ");

        // Skapa ny bok med informationen
        Book newBook = new Book(null, title, true, author, genre, pages);

        /***********
         ** C-NIVÅ **
         ***********/

        //Översätt informationen till JSON-format
        jsonBody = gson.toJson(newBook);
        //Försök lägga till boken på servern, annars skicka undantag
        try {
            response = Unirest.post(baseURL + "books")
                    .header("Content-Type", "application/json")
                    .body(jsonBody)
                    .asString(); // Returnerar ett HTTPResponse<String>

        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return;
        }

        //Hämta statuskod och kolla om den är OK, annars ge felmeddelande och avbryt
        status = response.getStatus();
        if (status != 200 && status != 201 && status != 500) {
            IO.println("Fel från server: " + status);
            return;
        }
        //Servern bör inte ge statuskod 500, men har haft problem med att den ger det även fast boken laddas upp
        if (status == 500) {
            IO.println("Status: 500");
            IO.println(response.getBody());
        }

        //Försök läsa svaret från servern och skapa ett nytt objekt av boken för att se vilket ID den fick
        try {
            body = response.getBody();
            Book responseBook = gson.fromJson(body, Book.class);
            IO.println("\nBoken sparades till servern: " + responseBook.getInfo());
        } catch (Exception e) {
            IO.println("Fel: " + e.getLocalizedMessage());
        }

        //Fråga om användaren vill synkronisera informationen på server till listan lokalt i programmet, då de inte gör det automatiskt
        boolean val = askYesNo("\nSynka böcker från server? (j/n): ");
        if (val) {
            IO.println("Hämtar böcker från server...");
            getBooksFromServer();
        } else {
            IO.println("Hämtar inte böcker från server.");
        }

    }

    // Metod för att skapa en ny tidning och ladda upp på servern
    public void addMagazine() {
        // Be användaren mata in info om tidningen
        String title = checkEmpty("Ange titel: ");
        int issueNumber = checkEmptyInt("Ange utgåvonummer: "); 
        String category = checkEmpty("Ange kategori: ");
        int publishedYear = checkEmptyInt("Ange publicerat år: ");

        // Skapa en ny tidning
        Magazine newMagazine = new Magazine(null, title, true, issueNumber, category, publishedYear);
        
        /***********
         ** C-NIVÅ **
         ***********/

        //Omvandla tidningen till JSON-format
        jsonBody = gson.toJson(newMagazine);
        //Försök lägg till tidningen på servern, annars skicka undantag
        try {
            response = Unirest.post(baseURL + "magazines")
                    .header("Content-Type", "application/json")
                    .body(jsonBody)
                    .asString(); // Returnerar ett HTTPResponse<String>
        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return;
        }

        //Hämta statuskod och kolla så att den är OK, annars skicka felmeddelande och avbryt
        status = response.getStatus();
        if (status != 200 && status != 201 && status != 500) {
            IO.println("Fel från server: " + status);
            return;
        }
        //Servern bör inte ge statuskod 500, men har haft problem med att den ger det även fast tidningen laddas upp
        if (status == 500) {
            IO.println("Status: 500");
            IO.println(response.getBody());
        }

        //Försök läsa svaret från servern och skriv ut tidningen (med ID som servern skapade), skicka felmeddelande om det misslyckas
        try {
            body = response.getBody();
            Magazine responseMagazine = gson.fromJson(body, Magazine.class);
            IO.println("Tidningen sparades till servern: " + responseMagazine.getInfo());
        } catch (Exception e) {
            IO.println("Fel: " + e.getLocalizedMessage());
        }

        //Fråga om användaren vill synkronisera informationen på server till listan lokalt i programmet, då de inte gör det automatiskt
        boolean val = askYesNo("\nSynka tidningar från server? (j/n): ");
        if (val) {
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
        Type bookType = new TypeToken<ArrayList<Book>>() {}.getType();
        // Lägg till i lista av böcker
        ArrayList<Book> jsonBooks = gson.fromJson(body, bookType);

        //Uppdatera den lokala listan genom att ta bort nuvarande objekt och fyll på med hämtade objekt
        books.clear();
        books.addAll(jsonBooks);

        /* loopa igenom listan för att lägga till i samlingen
         * for (Book book : jsonBooks) {
         * books.add(book);
         * }
         */

        IO.println("Hämtning av böcker lyckad! Antal böcker hämtade: " + jsonBooks.size());
        return true;
    }

    //Metod för att hämta alla tidningar från servern
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

        //Uppdatera den lokala listan genom att ta bort nuvarande objekt och fyll på med hämtade objekt
        magazines.clear();
        magazines.addAll(jsonMagazines);

        /* sedan loopa igenom listan för att lägga till i samlingen
         * for (Magazine magazine : jsonMagazines) {
         * magazines.add(magazine);
         * }
         */

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

    //Metod för att skriva ut tidningar
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

    // Hämta alla användare från servern
    public boolean getUsersFromServer() {
        // Försök hämta användre
        try {
            response = Unirest.get(baseURL + "users").asString();
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

        // Hämta själva informationen för användarna
        body = response.getBody();

        // Översätt JSON-texten till en ArrayList av Användar-objektet
        Type userType = new TypeToken<ArrayList<User>>() {
        }.getType();
        // Lägg till i lista, sedan loopa igenom listan för att lägga till i samlingen
        // av användare
        ArrayList<User> jsonUsers = gson.fromJson(body, userType);

        users.clear();
        users.addAll(jsonUsers);

        /*
         * for (User user : jsonUsers) {
         * users.add(user);
         * }
         */

        IO.println("Hämtning av användare lyckad! Antal användare hämtade: " + jsonUsers.size());
        return true;
    }

    // Hämta alla avstängda användare från servern
    public boolean getSuspendedUsersFromServer() {
        // Försök hämta avstängda användare
        try {
            response = Unirest.get(baseURL + "suspended").asString();
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

        // Hämta själva informationen för de avstända
        body = response.getBody();

        // Översätt JSON-texten till en ArrayList av Användar-objektet
        Type suspendedUserType = new TypeToken<ArrayList<SuspendedUser>>() {
        }.getType();
        // Lägg till i lista, sedan loopa igenom listan för att lägga till i samlingen
        // av användare
        ArrayList<SuspendedUser> jsonSuspendedUsers = gson.fromJson(body, suspendedUserType);

        suspendedUsers.clear();
        suspendedUsers.addAll(jsonSuspendedUsers);

        /*
         * for (SuspendedUser suspendedUser : jsonSuspendedUsers) {
         * suspendedUsers.add(suspendedUser);
         * }
         */

        IO.println("Hämtning av avstängda användare lyckad! Antal avstängda användare hämtade: "
                + jsonSuspendedUsers.size());
        return true;
    }

    // Hämta en bok från servern
    public boolean getOneBookFromServer() {
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
        books.add(jsonBook); // TODO ????

        IO.println("Hämtning av bok (ID: " + id + ") lyckad!");
        return true;
    }

    // Hämta en tidning från Servern
    public boolean getOneMagazineFromServer() {
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

        // Översätt JSON-texten till ett Magazine-objekt av tidningen och lägg till i
        // samlingen
        Magazine jsonMagazine = gson.fromJson(body, Magazine.class);
        magazines.add(jsonMagazine); // TODO ????

        IO.println("Hämtning av tidning (ID: " + id + ") lyckad!");
        return true;
    }

    // Hämta en användare från servern
    public boolean getOneUserFromServer() {
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

        // Översätt JSON-texten till ett user-objekt av användaren och lägg till i
        // samlingen
        User jsonUser = gson.fromJson(body, User.class);
        users.add(jsonUser); // TODO ????

        IO.println("Hämtning av användare (ID: " + id + ") lyckad!");
        return true;
    }

    // Hämta en avstängd användare från servern
    public boolean getOneSuspendedUserFromServer() {
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

        // Översätt JSON-texten till ett SuspendedUser-objekt av den avstängda och lägg
        // till i samlingen
        SuspendedUser jsonSuspendedUser = gson.fromJson(body, SuspendedUser.class);
        suspendedUsers.add(jsonSuspendedUser); // TODO ????

        IO.println("Hämtning av avständ användare (ID: " + id + ") lyckad!");
        return true;
    }

    // TODO
    // Skapa ny bok/tidning/användare/avstängd och lägga upp på server

    // Skapa ny användare och lägg till i listan och på server
    public void addUser() {
        // Be användaren mata in info om user
        String name = checkEmpty("Ange namn: ");
        String email = checkEmpty("Ange email: ");

        // Skapa ny användare med informationen och lägg till i listan
        User newUser = new User(null, name, email);

        // TODO Ladda upp på server

        jsonBody = gson.toJson(newUser);
        try {
            response = Unirest.post(baseURL + "users")
                    .header("Content-Type", "application/json")
                    .body(jsonBody)
                    .asString(); // Returnerar ett HTTPResponse<String>

        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return;
        }

        status = response.getStatus();
        if (status != 200 && status != 201 && status != 500) {
            IO.println("Fel från server: " + status);
            return;
        }
        if (status == 500) {
            IO.println("Status: 500");
            IO.println(response.getBody());
        }

        try {
            body = response.getBody();
            User responseUser = gson.fromJson(body, User.class);
            IO.println("Användaren sparades till servern: " + responseUser);
        } catch (Exception e) {
            IO.println("Fel: " + e.getLocalizedMessage());
        }

        boolean val = askYesNo("\nSynka användare från server? (j/n): ");
        if (val) {
            IO.println("Hämtar användare från server...");
            getUsersFromServer();
        } else {
            IO.println("Hämtar inte användare från server.");
        }

    }

    // Skapa ny avstängd använadre och lägg till i listan och på server
    public void addSuspendedUser() {
        String userId = checkEmpty("Ange användar-ID: ");

        // Skapa ny användare med informationen och lägg till i listan
        SuspendedUser newSuspendedUser = new SuspendedUser(null, userId);

        // TODO Ladda upp på server

        jsonBody = gson.toJson(newSuspendedUser);
        try {
            response = Unirest.post(baseURL + "suspended")
                    .header("Content-Type", "application/json")
                    .body(jsonBody)
                    .asString(); // Returnerar ett HTTPResponse<String>

        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return;
        }

        status = response.getStatus();
        if (status != 200 && status != 201 && status != 500) {
            IO.println("Fel från server: " + status);
            return;
        }
        if (status == 500) {
            IO.println("Status: 500");
            IO.println(response.getBody());
        }

        try {
            body = response.getBody();
            SuspendedUser responseSuspendedUser = gson.fromJson(body, SuspendedUser.class);
            IO.println("Avstängd användare sparades till servern: " + responseSuspendedUser);
        } catch (Exception e) {
            IO.println("Fel: " + e.getLocalizedMessage());
        }

        boolean val = askYesNo("\nSynka avstängda användare från server? (j/n): ");
        if (val) {
            IO.println("Hämtar avstängda användare från server...");
            getSuspendedUsersFromServer();
        } else {
            IO.println("Hämtar inte avstängda användare från server.");
        }

    }

    // Hitta en kund med hjälp av email-adress
    public User findUser() {
        String email = checkEmpty("Ange användarens email: ");

        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                IO.println("Kund hittad!");
                IO.println(u.toString());
                return u;
            }
        }

        IO.println("Ingen kund med emailen hittades...");
        return null;
    }

    // Hitta en bok med hjälp av titel
    public Book findBook() {
        String title = checkEmpty("Ange bokens titel: ");

        for (Book b : books) {
            if (b.getTitle().equalsIgnoreCase(title)) {
                IO.println("Bok hittad!");
                IO.println(b.getInfo());
                return b;
            }
        }

        IO.println("Ingen bok med titeln hittades...");
        return null;
    }

    // Hitta en tidning med hjälp av titel
    public Magazine findMagazine() {
        String title = checkEmpty("Ange tidningens titel: ");

        for (Magazine m : magazines) {
            if (m.getTitle().equalsIgnoreCase(title)) {
                IO.println("Tidning hittad!");
                IO.println(m.getInfo());
                return m;
            }
        }

        IO.println("Ingen tidning med titeln hittades...");
        return null;
    }

    // Ta bort bok från server
    public void removeBook() {
        Book foundBook = findBook();
        if (foundBook == null) {
            return;
        }
          
        String id = foundBook.getId();

        try {
            status = Unirest.delete(baseURL + "books/" + id)
                    .asEmpty() // Skickar ingen body
                    .getStatus();
        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return;
        }

        if (status == 200) {
            IO.println("Boken med titeln: " + foundBook.getTitle() + " togs bort!\n");
        } else if (status == 204) {
            IO.println("Boken finns inte kvar / inget innehåll med titeln: " + foundBook.getTitle() + "\n");
        } else {
            IO.println("Något gick snett. Status: " + status + "\n");
        }
    }

    // Ta bort tidning från server
    public void removeMagazine() {
        Magazine foundMagazine = findMagazine();
        if (foundMagazine == null) {
            return;
        }
          
        String id = foundMagazine.getId();

        try {
            status = Unirest.delete(baseURL + "magazines/" + id)
                    .asEmpty() // Skickar ingen body
                    .getStatus();
        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return;
        }

        if (status == 200) {
            IO.println("Tidningen med titeln: " + foundMagazine.getTitle() + " togs bort!\n");
        } else if (status == 204) {
            IO.println("Tidningen finns inte kvar / inget innehåll med titeln: " + foundMagazine.getTitle() + "\n");
        } else {
            IO.println("Något gick snett. Status: " + status + "\n");
        }
    }

    // TA bort användare från server
    public void removeUser() {
        User foundUser = findUser();
        if (foundUser == null) {
            return;
        }
          
        String id = foundUser.getId();

        try {
            status = Unirest.delete(baseURL + "users/" + id)
                    .asEmpty() // Skickar ingen body
                    .getStatus();
        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return;
        }

        if (status == 200) {
            IO.println("Användaren med emailen: " + foundUser.getEmail() + " togs bort!\n");
        } else if (status == 204) {
            IO.println("Användaren finns inte kvar / inget innehåll med emailen: " + foundUser.getEmail() + "\n");
        } else {
            IO.println("Något gick snett. Status: " + status + "\n");
        }
    }

    // Ta bort användare från server
    public void removeSuspendedUser() {
        String id = checkEmpty("Ange ID på avstängda användaren som ska tas bort: ");

        try {
            status = Unirest.delete(baseURL + "suspended/" + id)
                    .asEmpty() // Skickar ingen body
                    .getStatus();
        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return;
        }

        if (status == 200) {
            IO.println("Avstängda användaren med ID: " + id + " togs bort!\n");
        } else if (status == 204) {
            IO.println("Avstängda användaren finns inte kvar / inget innehåll med ID: " + id + "\n");
        } else {
            IO.println("Något gick snett. Status: " + status + "\n");
        }
    }

    // Skriv ut böcker sorterat
    public void printBooksSorted() {
        books.sort(null);
        IO.println("***** Sorterade böcker *****");
        for (Book b : books) {
            IO.println(b.getInfo());
        }
    }

    // Skriv ut tidningar sorterat
    public void printMagazinesSorted() {
        magazines.sort(null);
        IO.println("***** Sorterade tidningar *****");
        for (Magazine m : magazines) {
            IO.println(m.getInfo());
        }
    }

    // Skriv ut användare sorterat
    public void printUsersSorted() {
        users.sort(null);
        IO.println("***** Sorterade användare *****");
        for (User u : users) {
            IO.println(u.toString());
        }
    }

    // Skriv ut användare
    public void printUsers() {
        IO.println("***** Alla användare *****");
        for (User user : users) {
            IO.println(user.toString());
        }
    }

    // Skriv ut avstängda
    public void printSuspendedUser() {
        IO.println("***** Alla avstängda användare *****");
        for (SuspendedUser s : suspendedUsers) {
            IO.println(s.toString());
        }
    }

    // Kolla om användaren får låna
    public boolean canUserBorrow() {
        String id = checkEmpty("Ange ID:t på användaren: ");
        User foundUser = null;
        // Loopa igenom alla användare
        for (User u : users) {
            if (u.getId().equals(id)) {
                foundUser = u;
                break;
            }
        }

        // Om användaren inte finns
        if (foundUser == null) {
            IO.println("Ingen användare med ID:t finns...");
            return false;
        }

        // Kolla om det finns en suspended user med userId = id
        for (SuspendedUser s : suspendedUsers) {
            if (s.getCustomerId().equals(id)) {
                IO.println("Användaren är avstängd och får inte låna!");
                return false;
            }
        }

        IO.print("Användaren får låna!");
        return true;
    }



    /***************************************
     * ================ A-nivå ================
     ****************************************/



    // Börja här



    /****************************************
     *=============== METODER ===============
     ****************************************/



    // Metod för att kolla val i meny
    public int checkChoice() {
        int val = 0;
        try {
            val = Integer.parseInt(IO.readln("Ange alternativ: "));
        } catch (Exception e) {
            return val;
        }
        return val;
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

    //Metod för att kolla att int-inputs inte är tomma, och så att de är siffror 
    public int checkEmptyInt(String prompt){
        String input =  IO.readln(prompt).trim();
        
        //Loopa tills ett giltigt värde anges
        while (true) {
            if (input.isEmpty()) {
                IO.println("Fältet får inte vara tomt!");
            } else {
                try {
                    return Integer.parseInt(input);
                } catch (Exception e) {
                    IO.println("Du måste ange en siffra!");
                }
            }
            input = IO.readln(prompt).trim();
        }
    }

    // Metod för att kolla svar på ja/nej frågor
    public boolean askYesNo(String prompt) {
        String val = IO.readln(prompt).trim().toLowerCase();

        while (!val.equals("j") && !val.equals("n")) {
            IO.println("Ogiltigt val. Skriv j eller n");
            val = IO.readln(prompt).trim().toLowerCase();
        }

        if (val.equals("j")) {
            return true;
        } else {
            return false;
        }
    }

}
