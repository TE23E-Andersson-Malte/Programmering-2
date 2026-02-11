public class Rektor extends SkolPersoner{
    protected String arbetsrum;

    //Konstruktor
    public Rektor(String namn, int ålder, String personnummer, String arbetsrum){
        super(namn, ålder, personnummer);
        
        if (arbetsrum == null || arbetsrum.trim().isEmpty()) {
            throw new IllegalArgumentException("Arbetsrum får inte vara tomt");
        } else {
            this.arbetsrum = arbetsrum;
        }
    }

    //Setters
    public void setArbetsrum(String arbetsrum){
        if (arbetsrum == null || arbetsrum.trim().isEmpty()) {
            throw new IllegalArgumentException("Arbetsrum får inte vara tomt");
        } else {
            this.arbetsrum = arbetsrum;
        }
    }

    //getters
    public String getArbetsrum(){
        return arbetsrum;
    }

    @Override
    public String toString(){
        return "Rektor: " + super.toString() + "| Arbetsrum: " + arbetsrum;
    }
}
