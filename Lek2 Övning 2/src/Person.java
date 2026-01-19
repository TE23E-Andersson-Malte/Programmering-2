public class Person {
    private final int birth_date;
    private String name;

    public Person(int birth_date, String name){
        if(birth_date < 0)
            throw new IllegalArgumentException("Födelsedatum måste vara > 0");
        if (name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Namnet får inte vara tomt");
        }
        this.birth_date = birth_date;
        this.name = name;
    }

    public void setName(String name)
    {
        if(name.trim().isEmpty())
            throw new IllegalArgumentException("Tomt namn");
        else
            this.name = name;
    }
}
