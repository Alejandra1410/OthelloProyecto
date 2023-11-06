
package Model;
public class Ficha {
    private String color; 

    public Ficha(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void cambiarColor() {
        if (color.equals("B")) {
            color = "P";
        } else if (color.equals("P")) {
            color = "B";
        }
    }
      
}