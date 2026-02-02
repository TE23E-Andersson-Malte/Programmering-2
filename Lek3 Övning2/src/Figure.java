public class Figure {
    private String color;

    public Figure(String color){
        this.color = color;
    }

    public String getColor(){
        return color;
    }

    public void draw(){
        IO.println("""
                 ____________
                |    FIGUR   |
                |____________|
                """);
    }
}
