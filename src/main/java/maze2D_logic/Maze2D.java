package maze2D_logic;

public class Maze2D {
    private int size_x, size_y;
    private boolean maze_generated;
    private MazeCell[][] maze_cells;
    private GenerationAlgorithm algorithm;
    // Constructor -----------------------------------------------------------------------------------------------------
    public Maze2D(int size_x, int size_y, GenerationAlgorithm algorithm) throws Exception {
        if(size_x < 1 || size_y < 1) {
            throw new Exception("Dimensions of the maze not valid");
        }

        this.size_x = size_x;
        this.size_y = size_y;
        maze_generated = false;
        this.algorithm = algorithm;
    }
    // SET & GET -------------------------------------------------------------------------------------------------------
    public int getSize_x() { return size_x; }
    public int getSize_y() { return size_y; }
    // Methods ---------------------------------------------------------------------------------------------------------
    /**
     * This function creates a passage in this MazeCell in the direction of the next_cell
     * and vice-versa without checking for adjacency!
     */
    public int count_crossroads() {
        int result = 0;

        for(MazeCell[] row : maze_cells) {
            for(MazeCell cell: row) {
                result += cell.count_crossroads();
            }
        }

        return result;
    }

    public MazeCell getMazeCell(int x, int y) {
        if((maze_cells != null) && (maze_cells.length > x) &&
           (maze_cells[x] != null) && (maze_cells[x].length > y)) {
            return maze_cells[x][y];
        } else {
            return null;
        }
    }

    public boolean isGenerated() {
        return maze_generated;
    }

    public boolean generate() {
        boolean result = false;

        if(algorithm != null) {
            maze_cells = algorithm.generate(size_x, size_y);

            if(maze_cells != null) {
                maze_generated = true;
                result = true;
            }
        }

        return result;
    }
}
