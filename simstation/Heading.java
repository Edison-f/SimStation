package simstation;
import java.util.Random;
public enum Heading {
    NORTH, SOUTH, EAST, WEST;

    public static Heading random(){
        Random rand = new Random();
        int choose = rand.nextInt(3);
        if(choose == 0)
            return NORTH;
        else if (choose == 1)
            return SOUTH;
        else if (choose == 2)
            return EAST;
        else
            return WEST;
    }
}
