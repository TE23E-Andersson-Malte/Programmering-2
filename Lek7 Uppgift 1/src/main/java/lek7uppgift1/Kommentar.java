package lek7uppgift1;

public class Kommentar {
    private int postid;
    private int id;
    private String email;
    private String body;

    public Kommentar(){}

    public Kommentar(int postid, int id, String email, String body){
        this.postid = postid;
        this.id = id;
        this.email = email;
        this.body = body;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPostid(int postid) {
        this.postid = postid;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getPostid() {
        return postid;
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
        return "Kommentar [postid=" + postid + ", id=" + id + ", email=" + email + ", body=" + body + "]";
    }
}
