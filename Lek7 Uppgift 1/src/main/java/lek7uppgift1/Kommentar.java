package lek7uppgift1;

public class Kommentar {
    private int postId;
    private int id;
    private String email;
    private String body;

    public Kommentar(){}

    public Kommentar(int postId, int id, String email, String body){
        this.postId = postId;
        this.id = id;
        this.email = email;
        this.body = body;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPostUd(int postId) {
        this.postId = postId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getPostId() {
        return postId;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Kommentar [postid=" + postId + ", id=" + id + ", email=" + email + ", body=" + body + "]";
    }
}
