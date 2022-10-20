package maze2D_logic;

public interface GenerationAlgorithm {
    public abstract MazeCell[][] generate(int size_x, int size_y);
}
