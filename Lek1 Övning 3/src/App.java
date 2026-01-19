import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        /*** String svar = IO.readln("Ange temperatur i celsius: ");
        double c2 = Double.parseDouble(svar);
        double f2 = celsiusTillFahrenheit(c2);
        IO.println(c2 + " celsius = " + f2 + " fahrenheit"); ***/

        /*** String svar = IO.readln("Ange längd i centimeter: ");
        double cm2 = Double.parseDouble(svar);
        double inch2 = cmTillInches(cm2);
        IO.println(cm2 + " cm = " + inch2 + " inches"); ***/

        String svar = IO.readln("Ange längd in kilometer: ");
        double km2 = Double.parseDouble(svar);
        double miles2 = kmTillMiles(km2);
        IO.println(km2 + " km = " + miles2 + " miles");
    }

    public static double celsiusTillFahrenheit(double celsius){
        return (celsius * 9.0 / 5.0) + 32;
    }

    public static double cmTillInches(double cm) {
        return cm / 2.54;
    }

    public static double kmTillMiles(double km){
        return km / 1.60934;
    }
}
