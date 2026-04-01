package lek6upp1;

public class Person {
    private String namn;

    public Person(String namn){
        this.namn = namn;
    }

    public String getNamn(){
        return namn;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) {
            return true;
        }
        if (!(o instanceof Person)) {
            return false;
        }
        Person p = (Person) o;
        return namn.equals(p.namn);
    }

    @Override
    public int hashCode(){
        return namn.hashCode();
    }

    @Override
    public String toString(){
        return namn;
    }
}
