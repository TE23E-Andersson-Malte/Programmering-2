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
            books.add(responseBook);
            IO.println("\nBoken sparades till servern och lokalt i listan: " + responseBook.getInfo());
        } catch (Exception e) {
            IO.println("Fel: " + e.getLocalizedMessage());
        }

        //Fråga om användaren vill synka users från servern (eller låta den lokala listan vara som den är)
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
        IO.println("=== SKAPA NY TIDNING ===");
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
            magazines.add(responseMagazine);
            IO.println("Tidningen sparades till servern och lokalt i listan: " + responseMagazine.getInfo());
        } catch (Exception e) {
            IO.println("Fel: " + e.getLocalizedMessage());
        }

        //Fråga om användaren vill synka users från servern (eller låta den lokala listan vara som den är)
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

    //Metod för att hämta alla användare från servern
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
        Type userType = new TypeToken<ArrayList<User>>() {}.getType();
        ArrayList<User> jsonUsers = gson.fromJson(body, userType);

        //Ta bort nuvarande användare i lokala listan och synka istället till hämtade användarna från servern
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

    //Metod för att hämta alla avstängda användare från servern
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
        Type suspendedUserType = new TypeToken<ArrayList<SuspendedUser>>() {}.getType();
        ArrayList<SuspendedUser> jsonSuspendedUsers = gson.fromJson(body, suspendedUserType);

        //Ta bort nuvarande objekt och lägg till hämtade serverobjekt
        suspendedUsers.clear();
        suspendedUsers.addAll(jsonSuspendedUsers);

        /*
         * for (SuspendedUser suspendedUser : jsonSuspendedUsers) {
         * suspendedUsers.add(suspendedUser);
         * }
         */

        IO.println("Hämtning av avstängda användare lyckad! Antal avstängda användare hämtade: " + jsonSuspendedUsers.size());
        return true;
    }

    // Hämta en bok från servern
    public boolean getOneBookFromServer() {
        //Be användaren ange ID på boken som ska hämtas
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

        //Om boken redan finns lagrad lokalt, ta bort innan den hämtade läggs till
        for (Book b : books) {
            if (b.getId().equals(jsonBook.getId())) {
                books.remove(b);
                break;
            }
        }
        //Lägg till hämtade boken i lokala listan
        books.add(jsonBook);

        IO.println("Hämtning av bok (ID: " + id + ") lyckad!");
        return true;
    }

    //Metod för att hämta en tidning från Servern
    public boolean getOneMagazineFromServer() {
        //Be användaren ange ID på det som ska hämtas
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
        //Om tidningen redan finns lagrad lokalt, ta bort innan den hämtade läggs till
        for (Magazine m : magazines) {
            if (m.getId().equals(jsonMagazine.getId())) {
                magazines.remove(m);
                break;
            }
        }
        //Lägg till hämtade tidningen i lokala samlingen
        magazines.add(jsonMagazine); 

        IO.println("Hämtning av tidning (ID: " + id + ") lyckad!");
        return true;
    }

    //Metod för att hämta en användare från servern
    public boolean getOneUserFromServer() {
        //Be användaren ange ID på det som ska hämtas
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
        //Om användaren redan finns lagrad lokalt, ta bort innan den hämtade läggs till
        for (User u : users) {
            if (u.getId().equals(jsonUser.getId())) {
                users.remove(u);
                break;
            }
        }
        //Lägg till i den lokala listan
        users.add(jsonUser);

        IO.println("Hämtning av användare (ID: " + id + ") lyckad!");
        return true;
    }

    //Metod för att hämta en avstängd användare från servern
    public boolean getOneSuspendedUserFromServer() {
        //Be användaren ange ID på det som ska hämtas
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
        //Om avstängda redan finns lagrad lokalt, ta bort innan den hämtade läggs till
        for (SuspendedUser s : suspendedUsers) {
            if (s.getId().equals(jsonSuspendedUser.getId())) {
                suspendedUsers.remove(s);
                break;
            }
        }
        //Lägg till i lokala listan
        suspendedUsers.add(jsonSuspendedUser);

        IO.println("Hämtning av avständ användare (ID: " + id + ") lyckad!");
        return true;
    }

    //Metod för att skapa ny användare och lägg till i listan och på server
    public void addUser() {
        IO.println("=== SKAPA NY ANVÄNDARE ===");
        // Be användaren mata in info om ny user
        String name = checkEmpty("Ange namn: ");
        String email = checkEmpty("Ange email: ");

        // Skapa ny användare med informationen och lägg till i listan
        User newUser = new User(null, name, email);

        //Översätt informationen till JSON-format
        jsonBody = gson.toJson(newUser);
        //Försök ladda upp nya usern på servern
        try {
            response = Unirest.post(baseURL + "users")
                    .header("Content-Type", "application/json")
                    .body(jsonBody)
                    .asString(); // Returnerar ett HTTPResponse<String>

        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return;
        }

        //Kolla status om det är OK
        status = response.getStatus();
        if (status != 200 && status != 201 && status != 500) {
            IO.println("Fel från server: " + status);
            return;
        }
        //I vissa fall laddas objektet upp men status 500 skickas //TODO Ta bort denna och alla andra????
        if (status == 500) {
            IO.println("Status: 500");
            IO.println(response.getBody());
        }

        //Försök hämta svaret från servern och skapa ett nytt objekt med IDt som servern gav det, lägg till i lista
        try {
            body = response.getBody();
            User responseUser = gson.fromJson(body, User.class);
            users.add(responseUser);
            IO.println("Användaren sparades till servern och lokalt i listan: " + responseUser);
        } catch (Exception e) {
            IO.println("Fel: " + e.getLocalizedMessage());
        }

        //Fråga om användaren vill synka users från servern (eller låta den lokala listan vara som den är)
        boolean val = askYesNo("\nSynka användare från server? (j/n): ");
        if (val) {
            IO.println("Hämtar användare från server...");
            getUsersFromServer();
        } else {
            IO.println("Hämtar inte användare från server.");
        }
    }

    //Metod för att skapa ny avstängd använadre och lägg till i listan och på server
    public void addSuspendedUser() {
        IO.println("=== SKAPA NY AVSTÄNGA ANVÄNDARE ===");
        //Fråga om info
        String userId = checkEmpty("Ange användar-ID: "); //TODO Ska man kolla så att det finns en user att para ihop till denna???

        // Skapa ny användare med informationen och lägg till i listan
        SuspendedUser newSuspendedUser = new SuspendedUser(null, userId);

        //Översätt till JSON-format
        jsonBody = gson.toJson(newSuspendedUser);
        //Försök ladda upp på servern
        try {
            response = Unirest.post(baseURL + "suspended")
                    .header("Content-Type", "application/json")
                    .body(jsonBody)
                    .asString(); // Returnerar ett HTTPResponse<String>
        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return;
        }

        //Kolla status
        status = response.getStatus();
        if (status != 200 && status != 201 && status != 500) {
            IO.println("Fel från server: " + status);
            return;
        }
        //Utifall objektet laddas upp, men servern skickar status 500
        if (status == 500) {
            IO.println("Status: 500");
            IO.println(response.getBody());
        }

        //Försök hämta svaret från servern för att få IDt på nya objektet
        try {
            body = response.getBody();
            SuspendedUser responseSuspendedUser = gson.fromJson(body, SuspendedUser.class);
            suspendedUsers.add(responseSuspendedUser);
            IO.println("Avstängd användare sparades till servern och lokalt i listan: " + responseSuspendedUser);
        } catch (Exception e) {
            IO.println("Fel: " + e.getLocalizedMessage());
        }

        //Fråga om användaren vill synka users från servern (eller låta den lokala listan vara som den är)
        boolean val = askYesNo("\nSynka avstängda användare från server? (j/n): ");
        if (val) {
            IO.println("Hämtar avstängda användare från server...");
            getSuspendedUsersFromServer();
        } else {
            IO.println("Hämtar inte avstängda användare från server.");
        }

    }

    //Metod för att hitta en kund med hjälp av email-adress
    public User findUser() {
        //Fråga om email
        String email = checkEmpty("Ange användarens email: ");

        //Sök igenom lokala listan med users och leta efter matchande email
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

    //Metod för att hitta en bok med hjälp av titel
    public Book findBook() {
        //Fråga om titel
        String title = checkEmpty("Ange bokens titel: ");

        //Loopa igenom listan med böcker och leta efter matchande titel
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

    //Metod för att hitta en tidning med hjälp av titel
    public Magazine findMagazine() {
        //fråga om titel
        String title = checkEmpty("Ange tidningens titel: ");

        //Loopa igenom listan med tidningar och leta efter matchande titel
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

    //Metod för att ta bort bok från server
    public void removeBook() {
        IO.println("=== TA BORT BOK ===");        //Kolla om boken finns, avbryt om den inte finns
        Book foundBook = findBook();
        if (foundBook == null) {
            return;
        }
        
        //Om den finns, hämta dess ID
        String id = foundBook.getId();

        //Försök ta bort boken
        try {
            status = Unirest.delete(baseURL + "books/" + id)
                    .asEmpty() // Skickar ingen body
                    .getStatus();
        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return;
        }

        //Kolla status och meddela om borttagning lyckades eller inte
        if (status == 200) {
            IO.println("Boken med titeln: " + foundBook.getTitle() + " togs bort!\n");
        } else if (status == 204) {
            IO.println("Boken finns inte kvar / inget innehåll med titeln: " + foundBook.getTitle() + "\n");
        } else {
            IO.println("Något gick snett. Status: " + status + "\n");
        }
    }

    //Metod för att ta bort tidning från server
    public void removeMagazine() {
        IO.println("=== TA BORT TIDNING ===;");
        //Kolla om tidningen finns
        Magazine foundMagazine = findMagazine();
        if (foundMagazine == null) {
            return;
        }
          
        //Hämta dess ID
        String id = foundMagazine.getId();

        //Försök ta bort
        try {
            status = Unirest.delete(baseURL + "magazines/" + id)
                    .asEmpty() // Skickar ingen body
                    .getStatus();
        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return;
        }

        //Kolla status för att se om borttagning lyckades
        if (status == 200) {
            IO.println("Tidningen med titeln: " + foundMagazine.getTitle() + " togs bort!\n");
        } else if (status == 204) {
            IO.println("Tidningen finns inte kvar / inget innehåll med titeln: " + foundMagazine.getTitle() + "\n");
        } else {
            IO.println("Något gick snett. Status: " + status + "\n");
        }
    }

    //Metod för att ta bort användare från server
    public void removeUser() {
        IO.println("=== TA BORT ANVÄNDARE ===");
        //Kolla om den finns
        User foundUser = findUser();
        if (foundUser == null) {
            return;
        }
          
        //Hämta dess ID
        String id = foundUser.getId();

        //Försök ta bort
        try {
            status = Unirest.delete(baseURL + "users/" + id)
                    .asEmpty() // Skickar ingen body
                    .getStatus();
        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return;
        }

        //Kolla status om borttagning lycakdes
        if (status == 200) {
            IO.println("Användaren med emailen: " + foundUser.getEmail() + " togs bort!\n");
        } else if (status == 204) {
            IO.println("Användaren finns inte kvar / inget innehåll med emailen: " + foundUser.getEmail() + "\n");
        } else {
            IO.println("Något gick snett. Status: " + status + "\n");
        }
    }

    //Metod för att ta bort användare från server
    public void removeSuspendedUser() {
        IO.println("=== TA BORT AVSTÄNGD ANVÄNDARE ===");
        //Fråga om ID
        String id = checkEmpty("Ange ID på avstängda användaren som ska tas bort: ");

        //Försök ta bort
        try {
            status = Unirest.delete(baseURL + "suspended/" + id)
                    .asEmpty() // Skickar ingen body
                    .getStatus();
        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return;
        }

        //Kolla status och ge feedback
        if (status == 200) {
            IO.println("Avstängda användaren med ID: " + id + " togs bort!\n");
        } else if (status == 204) {
            IO.println("Avstängda användaren finns inte kvar: " + id + "\n");
        } else if (status == 404){
            IO.println("Inget avstängd användare med ID: " + id + " finns...\n");
        } else {
            IO.println("Något gick snett. Status: " + status + "\n");
        }
    }

    //Metod för att skriva ut böcker sorterat efter titel
    public void printBooksSorted() {
        IO.println("***** Sorterade böcker *****");
        //Strömma igenom listan, sortera efter titel och skriv ut varje element
        books.stream()
            .sorted()
            .forEach(b -> IO.println(b.getInfo()));
    }

    //Metod för att skriva ut tidningar sorterat efter titel
    public void printMagazinesSorted() {
        IO.println("***** Sorterade tidningar *****");
        //Strömma igenom listan, sortera efter titel och skriv ut varje element
        magazines.stream()
            .sorted()
            .forEach(m -> IO.println(m.getInfo()));
    }

    //Metod för att skriva ut användare sorterat efter namn
    public void printUsersSorted() {
        IO.println("***** Sorterade användare *****");
        //Strömma igenom listan, sortera och skriv ut
        users.stream()
            .sorted()
            .forEach(u -> IO.println(u.toString()));
    }

    //Metod för att skriva ut användare
    public void printUsers() {
        IO.println("***** Alla användare *****");
        //Loopa igenom listan och skriv ut varje element
        for (User user : users) {
            IO.println(user.toString());
        }
    }

    //Metod för att skriva ut avstängda
    public void printSuspendedUser() {
        IO.println("***** Alla avstängda användare *****");
        //Loopa igenom listan och skriv ut varje element
        for (SuspendedUser s : suspendedUsers) {
            IO.println(s.toString());
        }
    }

    //Metod för att kolla om användaren får låna
    public boolean canUserBorrow() {
        IO.println("=== KAN ANVÄNDAREN LÅNA? ===\n");
        //Fråga om ID
        String id = checkEmpty("Ange ID:t på användaren: ");
        User foundUser = null;
        // Loopa igenom alla användare, kolla om någon med IDt finns (spara i foundUser om hen finns)
        for (User u : users) {
            if (u.getId().equals(id)) {
                foundUser = u;
                break;
            }
        }

        // Om användaren inte finns, ge felmeddelande och avbryt
        if (foundUser == null) {
            IO.println("Ingen användare med ID:t finns...");
            return false;
        }

        // Kolla om det finns en suspended user med userId = id genom att loopa igenom listan
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
        //Läs in inmatning
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
        //Läs in inmatning
        String val = IO.readln(prompt).trim().toLowerCase();

        //Loopa tills användaren anger antingen j eller n
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
