public class Tree extends Figure{
    private int height;

    public Tree(String color, int height){
        super(color);
        this.height = height;
    }

    @Override
    public void draw(){
        IO.println("""
                  /\
                 /  \
                /____\
                  ||
                """);
    }

    public void sway(){
        IO.println("The " + getColor() + " and " + height + "m tree is swaying.");
    }
}
