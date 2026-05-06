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
        
        IO.println("Hämtning av böcker lyckad! Antal böcker hämtade: " + books.size());
        return true;
    }

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
        
        IO.println("Hämtning av tidningar lyckad! Antal tidningar hämtade: " + magazines.size());
        return true;
    }

    public void printBooks(){
        //Loopa igenom listan med böcker och skriv ut varje bok på en rad
        for (Book book : books) {
            IO.println(book.getInfo());
        }
    }

    public void printMagazines(){
        //Loopa igenom listan med tidningar och skriv ut 
        for (Magazine magazine : magazines) {
            IO.println(magazine.getInfo());
        }
    }

    /*************
    === C-nivå ===
    *************/

    /*************
    === A-nivå ===
    *************/

    public void printBooksSorted(){

    }

    public void printMagazinesSorted(){

    }
}
