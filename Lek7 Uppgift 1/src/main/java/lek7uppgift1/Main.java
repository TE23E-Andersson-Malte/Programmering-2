package lek7uppgift1;

import java.nio.file.*;

import com.google.gson.Gson;

import kong.unirest.Unirest;
import kong.unirest.HttpResponse;
import kong.unirest.UnirestException;

public class Main {
    public static void main(String[] args) {
        String baseURL = "http://10.151.168.5:32768/";
        Gson gson = new Gson();

        //Hämta post
        HttpResponse<String> getResponse;
        try {
            getResponse = Unirest.get(baseURL + "posts/2").asString();
            IO.println("Hämtar post 2...");
        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return;
        }

        int status = getResponse.getStatus();
        IO.println("Statuskod: " + status);
        if (status != 200) {
            IO.println("Fel från server: " + status);
            return;
        }

        String getBody = getResponse.getBody();
        ForumPost savedPost = gson.fromJson(getBody, ForumPost.class);
        IO.println("Hämtat inlägg: " + savedPost);
        int inläggID = savedPost.getId();

        //Hämta kommentarer
        HttpResponse<String> commentResponse;
        try {
            commentResponse = Unirest.get(baseURL + "comments/" + inläggID).asString();
        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return;
        }

        String commentsBody = commentResponse.getBody();
        try {
            Files.writeString(Paths.get("comments.json"), commentsBody);
            IO.println("Kommentater sparad till comments.json");
        } catch (Exception e) {
            IO.println("Filfel: " + e.getMessage());
        }

    }
}