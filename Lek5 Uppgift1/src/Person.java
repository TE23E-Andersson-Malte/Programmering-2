public class Person implements Comparable{
    private String namn;
    private int fodelsear;

    public Person(String namn, int fodelsear){
        if (namn == null || namn.trim().isEmpty()) {
            throw new IllegalArgumentException("Namn får inte vara tomt");
        } else {
            this.namn = namn;
        }

        if (fodelsear < 0) {
            throw new IllegalArgumentException("Födelseår får inte vara negativt");
        } else {
            this.fodelsear = fodelsear;
        }
    }

    public String getNamn(){
        return namn;
    }

    public int getFodelsear(){
        return fodelsear;
    }

    @Override
    public String toString(){
        return "Person - Namn: " + namn +
        " Födelseår: " + fodelsear;
    }

    @Override
    public int compareTo(Object o){
        Person annan = (Person)o;

        return this.namn.compareTo(annan.getNamn());
    }
}
