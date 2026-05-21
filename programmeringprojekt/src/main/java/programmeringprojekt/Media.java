package programmeringprojekt;

public abstract class Media implements Comparable<Media>{
    protected String id;
    protected String title;
    protected boolean isAvailable;

    /***KONSTRUKTOR***/
    public Media(String id, String title, boolean isAvailable){
        this.id = id.trim();
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Titel får inte vara tomt");
        }
        this.title = title.trim();
        this.isAvailable = isAvailable;
    }

    /***GETTERS***/
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean getIsAvailable(){
        return isAvailable;
    }

    /***SETTERS***/
    public void setIsAvailable(boolean isAvailable){
        this.isAvailable = isAvailable;
    }

    //COMPARE TO metod
    public int compareTo(Media other){
        return this.title.compareToIgnoreCase(other.title);
    };

    //GET INFO
    public abstract String getInfo();
}
