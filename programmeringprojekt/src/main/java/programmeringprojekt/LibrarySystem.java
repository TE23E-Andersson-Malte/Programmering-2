package programmeringprojekt;

/*
 * Malte Andersson
 * Library system hanterar alla funktioner inom biblioteket
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
    private List<Book> books = new ArrayList<>();
    private List<Magazine> magazines = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private List<SuspendedUser> suspendedUsers = new ArrayList<>();

    Gson gson = new Gson(); // Gson för att översätta data
    String baseURL = "http://10.151.168.5:3140/"; // URL till server

    // Tomma variabler för hämtning av data från servern
    HttpResponse<String> response;
    int status;
    String body;
    String jsonBody;

    //Metod för att kolla val i meny
    public int checkChoice() {
        int val = 0;
        try {
            val = Integer.parseInt(IO.readln("Ange alternativ: "));
        } catch (Exception e) {
            return val;
        }
        return val;
    }

    /***************************************
     * ================ E-nivå ================
     ***************************************/

     //TODO städa och kommentera

    // Skapa ny bok och lägg till i listan
    public void addBook() {
        // Be användaren mata in info om boken
        String title = IO.readln("Ange titel: ");

        String author = IO.readln("Ange författare: ");
        String genre = IO.readln("Ange genre: ");
        int pages = Integer.parseInt(IO.readln("Ange antal sidor: "));

        // Skapa ny bok med informationen och lägg till i listan
        Book newBook = new Book(null, title, true, author, genre, pages);

        /***********
         ** C-NIVÅ **
         ***********/

        // TODO Ladda upp på server

        jsonBody = gson.toJson(newBook);
        try {
            response = Unirest.post(baseURL + "books")
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

        //Funkar inte pga status 500
        try {
            body = response.getBody();
            Book responseBook = gson.fromJson(body, Book.class);
            IO.println("Sparat till server: " + responseBook);
            books.add(responseBook);
            IO.println("\nBoken har lagts till i listan: \n" + responseBook.toString());
        } catch (Exception e) {
            IO.println("Knas: " + e.getLocalizedMessage());
        }
        
        getBooksFromServer();
        IO.println("Boken lades till");
    }

    // Skapa ny tidning och lägg till i listan
    public void addMagazine() {
        // Be användaren mata in info om tidningen
        String title = IO.readln("Ange titel: ");

        int issueNumber = Integer.parseInt(IO.readln("Ange utgåvonummer: "));
        String category = IO.readln("Ange kategori: ");
        int publishedYear = Integer.parseInt(IO.readln("Ange publicerat år: "));

        // Skapa och lägg till tidningen i listan
        Magazine newMagazine = new Magazine(null, title, true, issueNumber, category, publishedYear);
        magazines.add(newMagazine);
        IO.println("\nTidningen har lagts till i listan: \n" + newMagazine.getInfo());
        /***********
         ** C-NIVÅ **
         ***********/

        // TODO Ladda upp på server

        jsonBody = gson.toJson(newMagazine);
        try {
            response = Unirest.post(baseURL + "magazines")
                    .header("Content-Type", "application/json")
                    .body(jsonBody)
                    .asString(); // Returnerar ett HTTPResponse<String>

        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return;
        }

        body = response.getBody();

        IO.println(body);

        status = response.getStatus();
        if (status != 200 && status != 201 && status != 500) {
            IO.println("Fel från server: " + status);
            return;
        }
        if (status == 500) {
            IO.println("Status: 500");
            IO.println(response.getBody());
        }

        //Funkar inte pga status 500
        try {
            body = response.getBody();
            Magazine responseMagazine = gson.fromJson(body, Magazine.class);
            IO.println("Sparat till server: " + responseMagazine);
            magazines.add(responseMagazine);
            IO.println("\nTidningen har lagts till i listan: \n" + responseMagazine.toString());
        } catch (Exception e) {
            IO.println("Knas");
        }
        
        getMagazinesFromServer();
        IO.println("Tidningen lades till");
    }

    // Hämta alla böcker från servern
    public boolean getBooksFromServer() { /// hade inte med i planering!!!!
        // Försök hämta böckerna
        try {
            response = Unirest.get(baseURL + "books").asString();
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

        // Hämta själva informationen för böckerna
        body = response.getBody();

        // Översätt JSON-texten till en ArrayList av Book-objektet
        Type bookType = new TypeToken<ArrayList<Book>>() {}.getType();
        // Lägg till i lista, sedan loopa igenom listan för att lägga till i samlingen
        // av böcker
        ArrayList<Book> jsonBooks = gson.fromJson(body, bookType);

        books.clear();
        books.addAll(jsonBooks);

        /*
        for (Book book : jsonBooks) {
            books.add(book);
        }*/

        IO.println("Hämtning av böcker lyckad! Antal böcker hämtade: " + jsonBooks.size());
        return true;
    }

    // Hämta alla tidningar från servern
    public boolean getMagazinesFromServer() { /// hade inte med i planering!!!!
        // Försök hämta tidningar
        try {
            response = Unirest.get(baseURL + "magazines").asString();
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

        // Hämta själva informationen för böckerna
        body = response.getBody();

        // Översätt JSON-texten till en ArrayList av Book-objektet
        Type magazineType = new TypeToken<ArrayList<Magazine>>() {
        }.getType();
        // Lägg till i lista, sedan loopa igenom listan för att lägga till i samlingen
        // av tidningar
        ArrayList<Magazine> jsonMagazines = gson.fromJson(body, magazineType);

        magazines.clear();
        magazines.addAll(jsonMagazines);

        /* 
        for (Magazine magazine : jsonMagazines) {
            magazines.add(magazine);
        }*/

        IO.println("Hämtning av tidningar lyckad! Antal tidningar hämtade: " + jsonMagazines.size());
        return true;
    }

    // Skriv ut böcker
    public void printBooks() {
        // Loopa igenom listan med böcker och skriv ut varje bok på en rad
        for (Book book : books) {
            IO.println(book.getInfo());
        }
    }

    // Skriv ut tidningar
    public void printMagazines() {
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
        for (User user : jsonUsers) {
            users.add(user);
        }*/

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
        for (SuspendedUser suspendedUser : jsonSuspendedUsers) {
            suspendedUsers.add(suspendedUser);
        }*/

        IO.println("Hämtning av avstängda användare lyckad! Antal avstängda användare hämtade: "
                + jsonSuspendedUsers.size());
        return true;
    }

    // Hämta en bok från servern
    public boolean getOneBookFromServer() {
        String id = IO.readln("Ange ID på boken: ");

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
        books.add(jsonBook); //TODO ????

        IO.println("Hämtning av bok (ID: " + id + ") lyckad!");
        return true;
    }

    // Hämta en tidning från Servern
    public boolean getOneMagazineFromServer() {
        String id = IO.readln("Ange ID på tidningen: ");

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
        magazines.add(jsonMagazine); //TODO ????

        IO.println("Hämtning av tidning (ID: " + id + ") lyckad!");
        return true;
    }

    // Hämta en användare från servern
    public boolean getOneUserFromServer() {
        String id = IO.readln("Ange ID på användaren: ");

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
        users.add(jsonUser); //TODO ????

        IO.println("Hämtning av användare (ID: " + id + ") lyckad!");
        return true;
    }

    // Hämta en avstängd användare från servern
    public boolean getOneSuspendedUserFromServer() {
        String id = IO.readln("Ange ID på avstängd användare: ");

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
        suspendedUsers.add(jsonSuspendedUser); //TODO ????

        IO.println("Hämtning av avständ användare (ID: " + id + ") lyckad!");
        return true;
    }

    // TODO
    // Skapa ny bok/tidning/användare/avstängd och lägga upp på server

    // Skapa ny användare och lägg till i listan och på server
    public void addUser() {
        // Be användaren mata in info om user
        String name = IO.readln("Ange namn: ");

        String email = IO.readln("Ange email: ");

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
            IO.println("Sparat till server: " + responseUser);
            users.add(responseUser);
            IO.println("\nAnvändaren har lagts till i listan: \n" + responseUser.toString());
        } catch (Exception e) {
            IO.println("Knas");
        }

        getUsersFromServer();
        IO.println("Användaren lades till");

    }

    // Skapa ny avstängd använadre och lägg till i listan och på server
    public void addSuspendedUser() {
        String userId = IO.readln("Ange användar-ID: ");

        // Skapa ny användare med informationen och lägg till i listan
        SuspendedUser newSuspendedUser = new SuspendedUser(null, userId);
        suspendedUsers.add(newSuspendedUser);
        IO.println("\nAvstängd användare har lagts till i listan: \n" + newSuspendedUser.toString());

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
            IO.println("Sparat till server: " + responseSuspendedUser);
            suspendedUsers.add(responseSuspendedUser);
            IO.println("\nAvstängda användaren har lagts till i listan: \n" + responseSuspendedUser.toString());
        } catch (Exception e) {
            IO.println("Knas");
        }

        getSuspendedUsersFromServer();
        IO.println("Avstängda användaren lades till");

    }

    // Hitta en kund med hjälp av email-adress
    public void findUser() {
        String email = IO.readln("Ange användarens email: ");

        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                IO.println("Kund hittad!");
                IO.println(u.toString());
                return;
            }
        }

        IO.println("Ingen kund med emailen hittades...");
    }

    // Hitta en bok med hjälp av titel
    public void findBook() {
        String title = IO.readln("Ange bokens titel: ");

        for (Book b : books) {
            if (b.getTitle().equalsIgnoreCase(title)) {
                IO.println("Bok hittad!");
                IO.println(b.getInfo());
                return;
            }
        }

        IO.println("Ingen bok med titeln hittades...");
    }

    // Hitta en tidning med hjälp av titel
    public void findMagazine() {
        String title = IO.readln("Ange tidningens titel: ");

        for (Magazine m : magazines) {
            if (m.getTitle().equalsIgnoreCase(title)) {
                IO.println("Tidning hittad!");
                IO.println(m.getInfo());
                return;
            }
        }

        IO.println("Ingen tidning med titeln hittades...");
    }

    //Ta bort bok från server
    public void removeBook(){

    }

    //Ta bort tidning från server
    public void removeMagazine(){

    }

    //TA bort användare från server
    public void removeUser(){

    }

    //Ta bort användare från server
    public void removeSuspendedUser(){

    }

    //Skriv ut böcker sorterat
    public void printBooksSorted() {
        books.sort(null);
        IO.println("***** Sorterade böcker *****");
        for (Book b : books) {
            IO.println(b.getInfo());
        }
    }

    //Skriv ut tidningar sorterat
    public void printMagazinesSorted() {
        magazines.sort(null);
        IO.println("***** Sorterade tidningar *****");
        for (Magazine m : magazines) {
            IO.println(m.getInfo());
        }
    }

    //Skriv ut användare sorterat
    public void printUsersSorted() {
        users.sort(null);
        IO.println("***** Sorterade användare *****");
        for (User u : users) {
            IO.println(u.toString());
        }
    }

    // Skriv ut användare
    public void printUsers() {
        for (User user : users) {
            IO.println(user.toString());
        }
    }

    // Skriv ut avstängda
    public void printSuspendedUser() {
        for (SuspendedUser s : suspendedUsers) {
            IO.println(s.toString());
        }
    }

    //Kolla om användaren får låna
    public boolean canUserBorrow() {
        String id = IO.readln("Ange ID:t på användaren: ");
        User foundUser = null;
        //Loopa igenom alla användare
        for (User u : users) {
            if (u.getId().equals(id)) {
                foundUser = u;
                break;
            }
        }

        //Om användaren inte finns
        if (foundUser == null) {
            IO.println("Ingen användare med ID:t finns...");
            return false;
        }

        //Kolla om det finns en suspended user med userId = id
        for (SuspendedUser s : suspendedUsers) {
            if (s.getUserId().equals(id)) {
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

}
