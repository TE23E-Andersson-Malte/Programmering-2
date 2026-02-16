import java.util.ArrayList;
import java.util.List;

public class SkolKlasser {
    private String klassnamn;
    private List<Elever> elever;
    private String schema;

    public SkolKlasser(String klassnamn, String schema){
        if (klassnamn == null || klassnamn.trim().isEmpty()) {
            throw new IllegalArgumentException("Klassnamn får inte vara tom");
        } else {
            this.klassnamn = klassnamn;
        }

        if (schema == null || schema.trim().isEmpty()) {
            throw new IllegalArgumentException("Schema får inte vara tom");
        } else {
            this.schema = schema;
        }    

        this.elever = new ArrayList<>();
    }

    public void laggTillElev(Elever elev) {
        elever.add(elev);
    }

    public List<Elever> getElever() {
        return elever;
    }

    public String getKlassNamn() {
        return klassnamn;
    }

    public String getSchema() {
        return schema;
    }

    public String toString(){
        return "\nKlassen - " + "Namn: " + klassnamn +
                " | Elever: " + elever + " | Schema: " + schema + "\n";
    }
}

