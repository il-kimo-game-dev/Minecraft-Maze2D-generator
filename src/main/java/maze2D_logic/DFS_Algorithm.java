package maze2D_logic;

import utility.Utility;
import java.util.*;

public class DFS_Algorithm implements GenerationAlgorithm {
    private ArrayList<MazeCell> find_neighbours(MazeCell starting_position,
                                                ArrayList<MazeCell> remaining_positions) {
        ArrayList<MazeCell> neighbours = new ArrayList<>();
        int x, y;

        x = starting_position.getX();
        y = starting_position.getY();

        if(remaining_positions != null) {
            for(MazeCell cell : remaining_positions) {
                if((cell.getX() == (x+1)) && (cell.getY() == y)) {
                    neighbours.add(cell);
                } else if((cell.getX() == (x-1)) && (cell.getY() == y)) {
                    neighbours.add(cell);
                } else if((cell.getX() == x) && (cell.getY() == (y+1))) {
                    neighbours.add(cell);
                } else if((cell.getX() == x) && (cell.getY() == (y-1))) {
                    neighbours.add(cell);
                }
            }
        }

        return neighbours;
    }

    public MazeCell[][] generate(int size_x, int size_y) {
        int starting_x = 0, starting_y = 0, coord_x, coord_y;
        MazeCell[][] generated_maze = new MazeCell[size_x][size_y];
        MazeCell peek, placeholder;
        Stack<MazeCell> generation_stack = new Stack<>();
        ArrayList<MazeCell> not_visited_positions = new ArrayList<>();
        ArrayList<MazeCell> visited_positions = new ArrayList<>();

        for(int x = 0; x < size_x; ++x) {
            for(int y = 0; y < size_y; ++y) {
                placeholder = new MazeCell(x, y);
                if((x == starting_x) && (y == starting_y)) {
                    generation_stack.push(placeholder);
                    visited_positions.add(placeholder);
                } else {
                    not_visited_positions.add(placeholder);
                }
            }
        }

        while(!generation_stack.empty()) {
            peek = generation_stack.peek();
            if(has_not_visited_neighbours(peek, not_visited_positions)) {
                placeholder = visit_random_neighbour(peek, not_visited_positions);
                visited_positions.add(placeholder);
                generation_stack.push(placeholder);
            } else {
                generation_stack.pop();
            }
        }

        for(MazeCell cell : visited_positions) {
            coord_x = cell.getX();
            coord_y = cell.getY();

            // add entrance and exit
            if(coord_x == 0 && coord_y == 0) {
                cell.set_bidirectional_passage_to(Directions.DIRECTION.NORTH);
            } else if(coord_x == size_x-1 && coord_y == size_y-1) {
               cell.set_bidirectional_passage_to(Directions.DIRECTION.SOUTH);
            }

            generated_maze[coord_x][coord_y] = cell;
        }

        return generated_maze;
    }

    private MazeCell select_random_neighbour(MazeCell starting_position,
                                             ArrayList<MazeCell> remaining_positions) {
        MazeCell selected_neighbour = null;
        ArrayList<MazeCell> neighbours = find_neighbours(starting_position, remaining_positions);

        if(neighbours.size() > 0) {
            selected_neighbour = (MazeCell) Utility.get_random(Arrays.asList(neighbours.toArray()));
        } else {
            throw new Error("This should not happen, ");
        }

        return selected_neighbour;
    }

    private MazeCell visit_random_neighbour(MazeCell starting_position,
                                                ArrayList<MazeCell> remaining_positions) {
        MazeCell next_position = select_random_neighbour(starting_position, remaining_positions);

        // remove neighbour from remaining_positions
        remaining_positions.remove(next_position);

        // select visits for starting and next positions
        starting_position.set_bidirectional_passage_to(next_position);

        return next_position;
    }

    /**
     * Checks if the actual_position still has at least one not visited neighbour
     */
    private boolean has_not_visited_neighbours(MazeCell actual_position,
                                               ArrayList<MazeCell> remaining_positions) {
        boolean res = false;
        int x, y;

        x = actual_position.getX();
        y = actual_position.getY();

        if(remaining_positions.contains(new MazeCell(x+1, y)) ||
                remaining_positions.contains(new MazeCell(x-1, y)) ||
                remaining_positions.contains(new MazeCell(x, y+1)) ||
                remaining_positions.contains(new MazeCell(x, y-1))) {
            res = true;
        }

        return res;
    }
}
