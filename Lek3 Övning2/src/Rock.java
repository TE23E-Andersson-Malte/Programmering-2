public class Rock extends Figure{
    private double weight;

    public Rock(String color, double weight){
        super(color);
        this.weight = weight;
    }

    @Override
    public void draw(){
        IO.println("""
                      ------
                    |  ROCK   |
                      ------
                """);
    }

    public void roll(){
        IO.println(getColor() + " rock (weight: " + weight + "kg) is rolling.");
    }
}
