import java.awt.*;
import java.util.ArrayList;

/**
 * Created by michael on 5/9/2015.
 */
public class Piece {
    private int owner;
    private Color color;

    public Piece(int owner){
        this.owner = owner;
        if (owner ==0 ){
            color = Color.RED;
        }
        else if (owner == 1){
            this.color = Color.blue;
        }
        else {
            color=Color.gray;
        }
    }

    public int getOwner() {
        return owner;
    }

    public Color getColor() {
        return this.color;
    }
}
