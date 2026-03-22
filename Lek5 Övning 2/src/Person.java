public class Person {
    private String namn;
    private int födelse_år;

    public Person(String namn, int födelse_år) {
        this.namn = namn;
        this.födelse_år = födelse_år;
    }

    // Getters och Setters (behövs för att ändra data senare)
    public String getNamn() { return namn; }
    public void setNamn(String namn) { this.namn = namn; }
    public int getFödelse_år() { return födelse_år; }
    public void setFödelse_år(int år) { this.födelse_år = år; }

    @Override
    public String toString() {
        return "Namn: " + namn + ", Född: " + födelse_år;
    }

    @Override
    public boolean equals(Object o){
        //Kontrollerar minnesadress
        if (this == o) {
            return true;
        }
        // Om null eller inte samma klass är det inte samma objekt
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person)o;
        //Om objektet har samma data så returnera true;
        return födelse_år == person.födelse_år && namn.equals(person.namn);
    }

    @Override
    public int hashCode(){
        return java.util.Objects.hash(namn, födelse_år);
    }
}