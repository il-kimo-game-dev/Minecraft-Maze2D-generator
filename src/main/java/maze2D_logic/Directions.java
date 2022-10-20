package maze2D_logic;

public class Directions {
    public enum DIRECTION {
        NORTH,
        WEST,
        SOUTH,
        EAST,
        NORTH_WEST,
        SOUTH_WEST,
        SOUTH_EAST,
        NORTH_EAST,
        UP,
        DOWN
    }

    public static int get_encoding(DIRECTION direction) {
        switch(direction) {
            case NORTH:
                return 0b00000001;
            case EAST:
                return (0b00000001<<1);
            case SOUTH:
                return (0b00000001<<2);
            default: // WEST
                return (0b00000001<<3);
        }
    }

    public static boolean contains_encoded_direction(int data, DIRECTION d) {
        switch(d) {
            case NORTH:
                return (data & (0b00000001)) == 0b00000001;
            case EAST:
                return (data & (0b00000001<<1)) == 0b00000001<<1;
            case SOUTH:
                return (data & (0b00000001<<2)) == 0b00000001<<2;
            default: // WEST
                return (data & (0b00000001<<3)) == 0b00000001<<3;
        }
    }
}
