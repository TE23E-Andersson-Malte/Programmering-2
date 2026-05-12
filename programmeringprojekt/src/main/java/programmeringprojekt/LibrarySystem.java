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

    Gson gson = new Gson(); //Gson för att översätta data
    String baseURL = "http://10.151.168.5:3140/"; //URL till server

    //Tomma variabler för hämtning av data från servern
    HttpResponse<String> response;
    int status;
    String body;
    String jsonBody;

    public int checkChoice(){
        int val = 0;
       try {
            val = Integer.parseInt(IO.readln("Ange alternativ: "));
       } catch (Exception e) {
            return val;
       }
        return val;
    }



    /***************************************
    ================ E-nivå ================
    ***************************************/



    //Skapa ny bok och lägg till i listan
    public void addBook(){ 
        //Be användaren mata in info om boken
        String title = IO.readln("Ange titel: ");

        String author = IO.readln("Ange författare: ");
        String genre = IO.readln("Ange genre: ");
        int pages = Integer.parseInt(IO.readln("Ange antal sidor: "));

        //Skapa ny bok med informationen och lägg till i listan
        Book newBook = new Book(null, title, true, author, genre, pages);
        books.add(newBook);
        IO.println("\nBoken har lagts till i listan: \n" + newBook.getInfo());
        /***********
        ** C-NIVÅ **
        ***********/

        //TODO Ladda upp på server

        jsonBody = gson.toJson(newBook);
        try {
            response = Unirest.post(baseURL + "books")
                .header("Content-Type", "application/json")
                .body(jsonBody)
                .asString(); //Returnerar ett HTTPResponse<String>
            
        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return;
        }

        status = response.getStatus();
        if (status != 200 && status != 201) {
            IO.println("Fel från server: " + status);
            return;
        }

        try {
            body = response.getBody();
            Book responseBook = gson.fromJson(body, Book.class);
            IO.println("Sparat till server: " + responseBook);
            //users.add(responseUser);
            IO.println("\nAnvändaren har lagts till i listan: \n" + responseBook.toString());
        } catch (Exception e) {
            IO.println("Knas");
        }

        //TODO skriv ut riktiga föremålet och lägg till i listan (ta bort tidigare)
    }

    

    //Skapa ny tidning och lägg till i listan
    public void addMagazine(){ 
        //Be användaren mata in info om tidningen
        String title = IO.readln("Ange titel: ");

        int issueNumber = Integer.parseInt(IO.readln("Ange utgåvonummer: "));
        String category = IO.readln("Ange kategori: ");
        int publishedYear = Integer.parseInt(IO.readln("Ange publicerat år: "));

        //Skapa och lägg till tidningen i listan
        Magazine newMagazine = new Magazine(null, title, true, issueNumber, category, publishedYear);
        magazines.add(newMagazine);
        IO.println("\nTidningen har lagts till i listan: \n" + newMagazine.getInfo());
        /***********
        ** C-NIVÅ **
        ***********/

        //TODO Ladda upp på server

        //TODO skriv ut riktiga föremålet och lägg till i listan (ta bort tidigare)
    }
        

    //Hämta alla böcker från servern
    public boolean getBooksFromServer(){ ///hade inte med i planering!!!!
        //Försök hämta böckerna
        try {
            response = Unirest.get(baseURL + "books").asString();
        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return false;
        }

        //Kolla status
        status = response.getStatus();
        if (status != 200) {
            IO.println("Fel från server! Statuskod: " + status);
            return false;
        }

        //Hämta själva informationen för böckerna
        body = response.getBody();

        //Översätt JSON-texten till en ArrayList av Book-objektet
        Type bookType = new TypeToken<ArrayList<Book>>(){}.getType();
        //Lägg till i lista, sedan loopa igenom listan för att lägga till i samlingen av böcker
        ArrayList<Book> jsonBooks = gson.fromJson(body, bookType);
        for (Book book : jsonBooks) {
            books.add(book);
        }
        
        IO.println("Hämtning av böcker lyckad! Antal böcker hämtade: " + jsonBooks.size());
        return true;
    }

    //Hämta alla tidningar från servern
    public boolean getMagazinesFromServer(){ ///hade inte med i planering!!!!
        //Försök hämta tidningar
        try {
            response = Unirest.get(baseURL + "magazines").asString();
        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return false;
        }

        //Kolla status
        status = response.getStatus();
        if (status != 200) {
            IO.println("Fel från server! Statuskod: " + status);
            return false;
        }

        //Hämta själva informationen för böckerna
        body = response.getBody();

        //Översätt JSON-texten till en ArrayList av Book-objektet
        Type magazineType = new TypeToken<ArrayList<Magazine>>(){}.getType();
        //Lägg till i lista, sedan loopa igenom listan för att lägga till i samlingen av tidningar
        ArrayList<Magazine> jsonMagazines = gson.fromJson(body, magazineType);
        for (Magazine magazine : jsonMagazines) {
            magazines.add(magazine);
        }
        
        IO.println("Hämtning av tidningar lyckad! Antal tidningar hämtade: " + jsonMagazines.size());
        return true;
    }

    //Skriv ut böcker
    public void printBooks(){
        //Loopa igenom listan med böcker och skriv ut varje bok på en rad
        for (Book book : books) {
            IO.println(book.getInfo());
        }
    }

    //Skriv ut tidningar
    public void printMagazines(){
        //Loopa igenom listan med tidningar och skriv ut 
        for (Magazine magazine : magazines) {
            IO.println(magazine.getInfo());
        }
    }



    /***************************************
    ================ C-nivå ================
    ****************************************/



    //Hämta alla användare från servern
    public boolean getUsersFromServer(){
        //Försök hämta användre
        try {
            response = Unirest.get(baseURL + "users").asString();
        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return false;
        }

        //Kolla status
        status = response.getStatus();
        if (status != 200) {
            IO.println("Fel från server! Statuskod: " + status);
            return false;
        }

        //Hämta själva informationen för användarna
        body = response.getBody();

        //Översätt JSON-texten till en ArrayList av Användar-objektet
        Type userType = new TypeToken<ArrayList<User>>(){}.getType();
        //Lägg till i lista, sedan loopa igenom listan för att lägga till i samlingen av användare
        ArrayList<User> jsonUsers = gson.fromJson(body, userType);
        for (User user : jsonUsers) {
            users.add(user);
        }
        
        IO.println("Hämtning av användare lyckad! Antal användare hämtade: " + jsonUsers.size());
        return true;
    }

    //Hämta alla avstängda användare från servern
    public boolean getSuspendedUsersFromServer(){
        //Försök hämta avstängda användare
        try {
            response = Unirest.get(baseURL + "suspended").asString();
        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return false;
        }

        //Kolla status
        status = response.getStatus();
        if (status != 200) {
            IO.println("Fel från server! Statuskod: " + status);
            return false;
        }

        //Hämta själva informationen för de avstända 
        body = response.getBody();

        //Översätt JSON-texten till en ArrayList av Användar-objektet
        Type suspendedUserType = new TypeToken<ArrayList<SuspendedUser>>(){}.getType();
        //Lägg till i lista, sedan loopa igenom listan för att lägga till i samlingen av användare
        ArrayList<SuspendedUser> jsonSuspendedUsers = gson.fromJson(body, suspendedUserType);
        for (SuspendedUser suspendedUser : jsonSuspendedUsers) {
            suspendedUsers.add(suspendedUser);
        }
        
        IO.println("Hämtning av avstängda användare lyckad! Antal avstängda användare hämtade: " + jsonSuspendedUsers.size());
        return true;
    }

    //Hämta en bok från servern
    public boolean getOneBookFromServer(){
        String id = IO.readln("Ange ID på boken: ");
        
        //Försök hämta boken
        try {
            response = Unirest.get(baseURL + "books/" + id).asString();
        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return false;
        }

        //Kolla status
        status = response.getStatus();
        if (status != 200) {
            IO.println("Fel från server! Statuskod: " + status);
            return false;
        }

        //Hämta själva informationen för boken
        body = response.getBody();

        //Översätt JSON-texten till ett Book-objekt av boken och lägg till i samlingen       
        Book jsonBook = gson.fromJson(body, Book.class);
        books.add(jsonBook);
        
        
        IO.println("Hämtning av bok (ID: " + id + ") lyckad!");
        return true;
    }

    //Hämta en tidning från Servern
    public boolean getOneMagazineFromServer(){
        String id = IO.readln("Ange ID på tidningen: ");
        
        //Försök hämta tidningen
        try {
            response = Unirest.get(baseURL + "magazines/" + id).asString();
        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return false;
        }

        //Kolla status
        status = response.getStatus();
        if (status != 200) {
            IO.println("Fel från server! Statuskod: " + status);
            return false;
        }

        //Hämta själva informationen för tidningen
        body = response.getBody();

        //Översätt JSON-texten till ett Magazine-objekt av tidningen och lägg till i samlingen       
        Magazine jsonMagazine = gson.fromJson(body, Magazine.class);
        magazines.add(jsonMagazine);
        
        IO.println("Hämtning av tidning (ID: " + id + ") lyckad!");
        return true;
    }

    //Hämta en användare från servern
    public boolean getOneUserFromServer(){
        String id = IO.readln("Ange ID på användaren: ");
        
        //Försök hämta användare
        try {
            response = Unirest.get(baseURL + "users/" + id).asString();
        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return false;
        }

        //Kolla status
        status = response.getStatus();
        if (status != 200) {
            IO.println("Fel från server! Statuskod: " + status);
            return false;
        }

        //Hämta själva informationen för användare
        body = response.getBody();

        //Översätt JSON-texten till ett user-objekt av användaren och lägg till i samlingen       
        User jsonUser = gson.fromJson(body, User.class);
        users.add(jsonUser);
        
        IO.println("Hämtning av användare (ID: " + id + ") lyckad!");
        return true;
    }

    //Hämta en avstängd användare från servern
    public boolean getOneSuspendedUserFromServer(){
        String id = IO.readln("Ange ID på avstängd användare: ");
        
        //Försök hämta avstängda
        try {
            response = Unirest.get(baseURL + "suspended/" + id).asString();
        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return false;
        }

        //Kolla status
        status = response.getStatus();
        if (status != 200) {
            IO.println("Fel från server! Statuskod: " + status);
            return false;
        }

        //Hämta själva informationen för avstängda
        body = response.getBody();

        //Översätt JSON-texten till ett SuspendedUser-objekt av den avstängda och lägg till i samlingen       
        SuspendedUser jsonSuspendedUser = gson.fromJson(body, SuspendedUser.class);
        suspendedUsers.add(jsonSuspendedUser);
        
        IO.println("Hämtning av avständ användare (ID: " + id + ") lyckad!");
        return true;
    }

    //TODO
    //Skapa ny bok/tidning/användare/avstängd och lägga upp på server

    //Skapa ny användare och lägg till i listan och på server
    public void addUser(){ 
        //Be användaren mata in info om user
        String name = IO.readln("Ange namn: ");

        String email = IO.readln("Ange email: ");

        //Skapa ny användare med informationen och lägg till i listan
        User newUser = new User(null, name, email);
        

        //TODO Ladda upp på server

        HttpResponse<String> postResponse;
        String postBody;
        jsonBody = gson.toJson(newUser);
        try {
            postResponse = Unirest.post(baseURL + "users")
                .header("Content-Type", "application/json")
                .body(jsonBody)
                .asString(); //Returnerar ett HTTPResponse<String>
            
        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return;
        }

        IO.println(postBody = postResponse.getBody());

        status = postResponse.getStatus();
        if (status != 200 && status != 201 && status != 500) {
            IO.println("Fel från server: " + status);
            return;
        }
        if (status == 500) {
            IO.println("Status: 500");
        }

        ///TODO idk man
        try {
             postBody = postResponse.getBody();
        User responseUser = gson.fromJson(postBody, User.class);
        IO.println("Sparat till server: " + responseUser);
        users.add(responseUser);
        IO.println("\nAnvändaren har lagts till i listan: \n" + responseUser.toString());
        } catch (Exception e) {
            IO.println("Knas");
        }
       
    }

    //Skapa ny avstängd använadre och lägg till i listan och på server
    public void addSuspendedUser(){ 
        String userId = IO.readln("Ange användar-ID: ");

        //Skapa ny användare med informationen och lägg till i listan
        SuspendedUser newSuspendedUser = new SuspendedUser(null, userId);
        suspendedUsers.add(newSuspendedUser);
        IO.println("\nAvstängd användare har lagts till i listan: \n" + newSuspendedUser.toString());

        //TODO Ladda upp på server

        //TODO skriv ut riktiga föremålet och lägg till i listan (ta bort tidigare)

    }

    //Hitta en kund med hjälp av email-adress
    public void findUser(){

    }

    //Hitta en bok med hjälp av titel
    public void findBook(){

    }

    //Hitta en tidning med hjälp av titel
    public void findMagazine(){

    }

    //TODO
    //Ta bort böcker/tidningar/kund/avstängd på server med hjälp av title och ta bort på server.
    //Ta bort kund med hjälp av email och avstängd med id på server.


    public void printBooksSorted(){

    }

    public void printMagazinesSorted(){

    }

    public void printUsersSorted(){

    }

    public boolean canUserBorrow(){
        return true;
    }



    /***************************************
    ================ A-nivå ================
    ****************************************/



    //Börja här
    
}
