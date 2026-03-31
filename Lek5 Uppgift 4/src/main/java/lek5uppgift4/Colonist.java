package lek5uppgift4;

public class Colonist {
    private String name;
    private int age;
    private String profession; // T.ex. "Engineer", "Scientist", "Farmer"
    private double oxygenConsumption; // Liter per timme
    private int credits; // Mars-valuta

    public Colonist(String name, int age, String profession, double oxygenConsumption, int credits) {
        this.name = name;
        this.age = age;
        this.profession = profession;
        this.oxygenConsumption = oxygenConsumption;
        this.credits = credits;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public String getProfession() { return profession; }
    public double getOxygenConsumption() { return oxygenConsumption; }
    public int getCredits() { return credits; }

    @Override
    public String toString() {
        return String.format("%s (%d år) - %s", name, age, profession);
    }
}
