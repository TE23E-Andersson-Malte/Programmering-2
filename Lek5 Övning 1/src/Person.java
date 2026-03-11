public class Person implements Comparable{
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
    public int compareTo(Object o){
        Person annan = (Person)o;

        return this.namn.compareTo(annan.getNamn());
    }

}
