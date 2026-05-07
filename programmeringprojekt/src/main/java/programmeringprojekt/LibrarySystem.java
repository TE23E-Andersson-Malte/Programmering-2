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

    Gson gson = new Gson(); //Gson för att översätta data
    String baseURL = "http://10.151.168.5:3140/"; //URL till server

    //Tomma variabler för hämtning av data från servern
    HttpResponse<String> response;
    int status;
    String body;

    //Variabler för ID för nya böcker och tidningar
    int iBook = 1;
    int iMagazine = 1;

    /*************
    === E-nivå ===
    *************/

    //Skapa ny bok och lägg till i listan
    public void addBookToArrayList(){ 
        //Be användaren mata in info om boken
        String title = IO.readln("Ange titel: ");
        String id = String.valueOf(100 + iBook);

        String author = IO.readln("Ange författare: ");
        String genre = IO.readln("Ange genre: ");
        int pages = Integer.parseInt(IO.readln("Ange antal sidor: "));

        //Skapa ny bok med informationen och lägg till i listan
        Book newBook = new Book(id, title, true, author, genre, pages);
        books.add(newBook);
        IO.println("\nBoken har lagts till i listan: \n" + newBook.getInfo());
        iBook++;
    }

    //Skapa ny tidning och lägg till i listan
    public void addMagazineToArrayList(){ 
        //Be användaren mata in info om tidningen
        String title = IO.readln("Ange titel: ");
        String id = String.valueOf(10 + iMagazine);

        int issueNumber = Integer.parseInt(IO.readln("Ange utgåvonummer: "));
        String category = IO.readln("Ange kategori: ");
        int publishedYear = Integer.parseInt(IO.readln("Ange publicerat år: "));

        //Skapa och lägg till tidningen i listan
        Magazine newMagazine = new Magazine(id, title, true, issueNumber, category, publishedYear);
        magazines.add(newMagazine);
        IO.println("\nTidningen har lagts till i listan: \n" + newMagazine.getInfo());
        iMagazine++;
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

    /*************
    === C-nivå ===
    *************/

    //Hämta alla användare från servern
    public boolean getUsersFromServer(){
        return true;
    }

    //Hämta alla avstängda användare från servern
    public boolean getSuspendedUsersFromServer(){
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
        
        //Försök hämta boken
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

        //Hämta själva informationen för boken
        body = response.getBody();

        //Översätt JSON-texten till ett Book-objekt av boken och lägg till i samlingen       
        Magazine jsonMagazine = gson.fromJson(body, Magazine.class);
        magazines.add(jsonMagazine);
        
        IO.println("Hämtning av tidning (ID: " + id + ") lyckad!");
        return true;
    }

    //Hämta en användare från servern
    public boolean getOneUserFromServer(){
        return true;
    }

    //Hämta en avstängd användare från servern
    public boolean getOneSuspendedUserFromServer(){
        return true;
    }

    //TODO
    //Skapa ny bok/tidning/användare/avstängd och lägga upp på server

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
    /************
    === A-nivå ===
    *************/

    
}
