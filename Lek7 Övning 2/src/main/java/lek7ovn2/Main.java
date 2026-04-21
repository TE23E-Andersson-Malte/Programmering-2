package lek7ovn2;

//GSON objekt som vi behöver
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
//Importera Type för att hjälpa json att omvandla data
import java.lang.reflect.Type;
//UniREST objekt som vi behöver
import kong.unirest.Unirest;
import kong.unirest.HttpResponse;
import kong.unirest.UnirestException;
//Importera filhantering
import java.nio.file.*;
import java.io.IOException;
//ArrayList för att lagra objekt
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        //URL till vår server-tjänst
        String baseUrl = "http://10.151.168.5:32768/posts";
        Gson gson = new Gson(); //att översätta data

        IO.println("STARTAR JSON KLIENT");

        IO.println("\n--- 1. GET (Ett objekt) ---");
        HttpResponse<String> one_response;
        try {
            one_response = Unirest.get(baseUrl + "/2").asString();
        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return;
        }

        //Hämta status
        int status = one_response.getStatus();
        IO.println("Statuskod: " + status);
        if (status != 200) {
            IO.println("Fel från server, statuskod: " + status);
            return;
        }

        //Hämta texten från svaret
        String get_one_body = one_response.getBody();
        //Låt gson omvandla json-texten till ett forumpost-objekt
        ForumPost savedPost = gson.fromJson(get_one_body, ForumPost.class);
        IO.println("Hämtade inlägg: " + savedPost);

        IO.println("\n--- 2. GET (Alla objekt) ---");
        HttpResponse<String> get_all_response;
        try {
            get_all_response = Unirest.get(baseUrl).asString();
        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return;
        }
        status = get_all_response.getStatus();
        if (status != 200) {
            IO.println("Fel från server, statuskod: " + status);
            return;
        }

        String get_all_body = get_all_response.getBody();
        IO.println("Storlek på data: " + get_all_body.length());

        //Spara all JSON-data till en lokal fil
        try {
            Files.writeString(Paths.get("data.json"), get_all_body);
            IO.println("Data sparad till data.json");
        } catch (IOException e) {
            IO.println("Filfel: " + e.getMessage());
        }

        //Översätt JSON-texten till en ArrayList av ForumPost-objekt
        Type postListType = new TypeToken<ArrayList<ForumPost>>(){}.getType();
        ArrayList<ForumPost> posts = gson.fromJson(get_all_body, postListType);
        IO.println("Antal inlägg i listan: " + posts.size());

        IO.println("\n--- 3. POST ---");
        //Skapa inlägget (id=0 låter servern bestämma id)
        ForumPost newPost = new ForumPost(0, 102, "Welcome to the Forum", "This is the first post");

        //Gör om Java-objekt till Json
        String jsonBody = gson.toJson(newPost);
        HttpResponse<String> postResponse;

        try {
            postResponse = Unirest.post(baseUrl)
                .header("Content-Type", "application/json")
                .body(jsonBody)
                .asString(); //Returnerar ett HTTPResponse<String>
        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return;
        }

        status = postResponse.getStatus();
        if (status != 200 && status != 201) {
            IO.println("Fel från server: " + status);
            return;
        }

        //Hämta tillbaka svaret från serven för att se vilket id inlägget fick
        String postBody = postResponse.getBody();
        ForumPost responsePost = gson.fromJson(postBody, ForumPost.class);
        IO.println("Sparat på servern: " + responsePost);

        IO.println("\n--- 4. PUT (Uppdatera inlägg) ---");

        //Skapa ett objekt med den uppdaterade informationen
        ForumPost putPost = new ForumPost();
        putPost.setTitle("Välkommen till Forumet!");
        putPost.setBody("Texten är nu ändrad via en PUT-request.");

        //Gör om till JSON
        String putPost_json = gson.toJson(putPost);
        HttpResponse<String> putResponse;

        try {
            //Vi använder id från inlägget vi hämtade i del 1
            putResponse = Unirest.put(baseUrl + "/" + savedPost.getId())
                .header("Content-Type", "application/json")
                .body(putPost_json)
                .asString();
        } catch (UnirestException e) {
            IO.println("Undantasg uppkoppling: " + e.getLocalizedMessage());
            return;
        }

        status = putResponse.getStatus();
        if (status != 200 && status != 204) {
            IO.println("Fel från server: " + status);
            return;
        }

        //Hämta svaret (det uppdaterade objektet) från put-anropet
        String putBody = putResponse.getBody();
        ForumPost updatedPost = gson.fromJson(putBody, ForumPost.class);
        IO.println("Uppdaterat på servern: " + updatedPost);
    }
}