package programmeringprojekt;

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

    public void addBook(Book book){ //boolean????

    }

    public void addMagazine(Magazine magazine){ //boolean????

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
        books = gson.fromJson(body, bookType);
        
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
        magazines = gson.fromJson(body, magazineType);
        
        IO.println("Hämtning av tidningar lyckad! Antal tidningar hämtade: " + magazines.size());
        return true;
    }

    public void printBooksSorted(){

    }

    public void printMagazinesSorted(){

    }
}
