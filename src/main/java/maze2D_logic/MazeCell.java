package maze2D_logic;

/**
 * Unit block of a labyrinth.
 * It has a 2D position (x, y) and
 * information regarding
 */
public class MazeCell {
    private final int x, y;

    /**
     * This variable holds the encoding for knowing which
     * parts of the cell have passages (labyrinth roads to other cells).
     * To see the specifics of the encoding, look at Directions.get_encoding(direction)
     */
    private int cell_communication_mappings;

    public MazeCell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // SET & GET ---------------------------------------------------------------------------------------------------
    public int getX() { return x; }
    public int getY() { return y; }
    public int getCell_communication_mappings() { return cell_communication_mappings; }

    // METHODS -----------------------------------------------------------------------------------------------------

    /**
     * A crossroad is defined as a choice of direction to take when arrived in this MazeCell and wanting to step
     * forward.
     * @return
     */
    public int count_crossroads() {
        int res = -2; // we don't want to count the first 2 passages, because having two does not provide choices to make.
        int encoded_passages = cell_communication_mappings;

        while(encoded_passages != 0) {
            if((encoded_passages & 0b1) == 0b1) {
                ++res;
            }

            encoded_passages = encoded_passages >> 1;
        }

        return res < 0 ? 0 : res;
    }

    private void encode_direction(Directions.DIRECTION direction) {
        cell_communication_mappings = cell_communication_mappings | Directions.get_encoding(direction);
    }

    public boolean has_passage_to(Directions.DIRECTION direction) {
        return Directions.contains_encoded_direction(cell_communication_mappings, direction);
    }

    /**
     * This function creates a passage in this MazeCell in the direction of the next_cell
     * and vice-versa without checking for adjacency!
     */
    public void set_bidirectional_passage_to(MazeCell next_cell) {
        Directions.DIRECTION this_direction, next_direction;

        if(x > next_cell.getX()) { // going NORTH
            this_direction = Directions.DIRECTION.NORTH;
            next_direction = Directions.DIRECTION.SOUTH;
        } else if(x < next_cell.getX()) { // going SOUTH
            this_direction = Directions.DIRECTION.SOUTH;
            next_direction = Directions.DIRECTION.NORTH;
        } else if(y < next_cell.getY()) { // going WEST
            this_direction = Directions.DIRECTION.WEST;
            next_direction = Directions.DIRECTION.EAST;
        } else { // going EAST
            this_direction = Directions.DIRECTION.EAST;
            next_direction = Directions.DIRECTION.WEST;
        }

        encode_direction(this_direction);
        next_cell.encode_direction(next_direction);
    }

    protected void set_bidirectional_passage_to(Directions.DIRECTION direction) {
        encode_direction(direction);
    }
    // OVERRIDES ---------------------------------------------------------------------------------------------------
    @Override
    public boolean equals(Object obj) {
        boolean result = false;

        if(obj instanceof MazeCell) {
            if(x == ((MazeCell)obj).getX() &&
               y == ((MazeCell)obj).getY() &&
               cell_communication_mappings == ((MazeCell)obj).getCell_communication_mappings()) {
                result = true;
            }
        }

        return result;
    }
}
