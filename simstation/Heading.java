package simstation;
import java.util.Random;
public enum Heading {
    NORTH, SOUTH, EAST, WEST,
    NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST;

    public static Heading random(){
        Random rand = new Random();
        // Random integers between 0 - 7
        int choose = rand.nextInt(8);
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
    }
}
