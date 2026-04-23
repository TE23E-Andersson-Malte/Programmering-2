package lek7uppgift1;

public class ForumPost {
private int id;
    private int userId;
    private  String title;
    private String body;

    public ForumPost() {
        IO.println("Post()");
    }
    
    public ForumPost(int id, int userId, String title, String body) {
        IO.println("Post(MED ID)");
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.body = body;
    }

    public ForumPost( int userId, String title, String body) {
        IO.println("Post( INGET ID)");
        this.userId = userId;
        this.title = title;
        this.body = body;
    }

    public void setId(int id) {this.id=id;}

    public int getId() {return id;}

    public int getUserId() {return userId;}

    public String getTitle() {return title;}

    public String getBody() {return body;}

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }
    @Override
    public String toString() {
        return "Post id=" + id + ", userId=" + userId + ", title=" + title + ", body=" + body ;
    }
}
