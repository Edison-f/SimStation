package simstation;
<<<<<<< HEAD

public class Heading {
    public static Heading random() {
        return null;
=======
import mvc.Utilities;

import static mvc.Utilities.rng;

//import java.util.Random;
public enum Heading {
    NORTH, SOUTH, EAST, WEST,
    NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST;

    public static Heading random(){
        int choose = rng.nextInt(8);
        if(choose == 0)
            return NORTH;
        else if (choose == 1)
            return SOUTH;
        else if (choose == 2)
            return EAST;
        else if (choose == 3)
            return WEST;
        else if (choose == 4)
            return NORTHWEST;
        else if (choose == 5)
            return NORTHEAST;
        else if (choose == 6)
            return SOUTHWEST;
        else
            return SOUTHEAST;
>>>>>>> master
    }
}
