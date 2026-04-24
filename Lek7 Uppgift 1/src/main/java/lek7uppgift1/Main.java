package lek7uppgift1;

import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import com.google.gson.Gson;

import kong.unirest.Unirest;
import kong.unirest.HttpResponse;
import kong.unirest.UnirestException;

public class Main {
    public static void main(String[] args) {
        String baseURL = "http://10.151.168.5:32768/";
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Kommentar>>(){}.getType();

        //Hämta post
        HttpResponse<String> getResponse;
        try {
            getResponse = Unirest.get(baseURL + "posts/6").asString();
            IO.println("Hämtar post 6...");
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

        IO.println("\n=== ForumTråd ===");
        String getBody = getResponse.getBody();
        ForumPost savedPost = gson.fromJson(getBody, ForumPost.class);
        IO.println("Hämtat inlägg: " + savedPost);
        int inläggID = savedPost.getId();

        IO.println("\n=== Kommentarer ===");

        //Hämta kommentarer
        HttpResponse<String> commentResponse;
        try {
            commentResponse = Unirest.get(baseURL + "comments/").asString();
        } catch (UnirestException e) {
            IO.println("Undantag uppkoppling: " + e.getLocalizedMessage());
            return;
        }

        String commentsBody = commentResponse.getBody();
        ArrayList<Kommentar> commentsList = gson.fromJson(commentsBody, listType);

        ArrayList<Kommentar> filteredComments = new ArrayList<>();

        for (Kommentar comment : commentsList) {
            if (comment.getPostId() == inläggID) {
                filteredComments.add(comment);
                IO.println("\n" + comment);
            }}

        String jsonComments = gson.toJson(filteredComments);

        try {
            Files.writeString(Paths.get("comments.json"), jsonComments);
            IO.println("Kommentater sparad till comments.json");
        } catch (Exception e) {
            IO.println("Filfel: " + e.getMessage());
        }

    }
}