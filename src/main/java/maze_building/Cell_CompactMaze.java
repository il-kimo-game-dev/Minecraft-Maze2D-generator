package maze_building;

/*
      N
    ******
  E ****** W
    ******
      S

  This ASCII-art is not to scale, it has just the purpose to illustrate
  the orientation of the cardinal points to better understand how to
  bind North, West, South, East to the block[][][] matrix
  (actually just the first 2 dimensions, since the third one is height)
 */

import kimo.com.SizesException;
import maze2D_logic.Directions;
import org.bukkit.Material;

/**
 * This type of Cell has walls only on one side for each dimension. The other walls
 * will be created by the adjacent cell in order to achieve a more compact maze.
 *
 * Take care, using this type of Cells, it's necessary to build half of the external
 * perimeter of the maze, which walls are not provided by this type of Cells!!
 */
public class Cell_CompactMaze extends Cell {

    public Cell_CompactMaze(int base_size, int height) throws SizesException {
        super(base_size, height);
    }

    public Cell_CompactMaze(int base_size, int height, int wall_thickness, Material wall, Material air, Material floor, Material light_floor, Material ceiling) throws SizesException {
        super(base_size, height, wall_thickness, wall, air, floor, light_floor, ceiling);
    }

    @Override
    protected void init_lights() {
        if(walls_thickness < base_size) {
            if((base_size-walls_thickness) % 2 == 0) {
                for(int x = (base_size-walls_thickness)/2-1; x <= (base_size-walls_thickness)/2; ++x) {
                    for(int y = walls_thickness+(base_size-walls_thickness)/2-1; y <= walls_thickness+(base_size-walls_thickness)/2; ++y) {
                        blocks[x][y][0] = MATERIAL_DESCRIPTION.LIGHT;
                    }
                }
            } else {
                blocks[(base_size-walls_thickness)/2][walls_thickness+(base_size-walls_thickness)/2][0] = MATERIAL_DESCRIPTION.LIGHT;
            }
        }
    }

    @Override
    public void dig_from(Directions.DIRECTION d) {
        int x_start, x_end;
        int y_start, y_end;
        int z_start = 1, z_end = height-1; // starting from 1 because we never want to remove the floor

        switch(d) {
            case NORTH:
            case WEST:
                x_start = 0;
                x_end = base_size-walls_thickness;
                y_start = walls_thickness;
                y_end = base_size;
                break;
            case SOUTH:
                x_start = 0;
                x_end = base_size;
                y_start = walls_thickness;
                y_end = base_size;
                break;
            default: // EAST
                x_start = 0;
                x_end = base_size-walls_thickness;
                y_start = 0;
                y_end = base_size;
                break;
        }

        for(int x = x_start; x < x_end; ++x) {
            for(int y = y_start; y < y_end; ++y) {
                for(int z = z_start; z < z_end; ++z) {
                    blocks[x][y][z] = MATERIAL_DESCRIPTION.AIR;
                }
            }
        }

        setVisited(true);
    }
}
