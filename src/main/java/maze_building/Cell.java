package maze_building;

import kimo.com.SizesException;
import maze2D_logic.Directions;
import org.bukkit.Material;

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

public class Cell {
    public enum MATERIAL_DESCRIPTION {WALL, AIR, FLOOR, LIGHT, CEILING}

    protected int base_size, height;
    protected MATERIAL_DESCRIPTION[][][] blocks;
    protected Material wall, air, floor, light_floor, ceiling;
    protected int walls_thickness = 1;
    protected boolean visited = false;

    public Cell(int base_size, int height) throws SizesException {
        if(base_size < 3 || height < 1) {
            throw new SizesException("This are not valid sizes for Cells in a minecraft labyrinth!");
        } else {
            this.base_size = base_size;
            this.height = height;
            this.wall = Material.BLACK_WOOL;
            this.floor = Material.RED_WOOL;
            this.air = Material.AIR;
            this.light_floor = Material.SEA_LANTERN;
            this.ceiling = Material.GLASS;
            blocks = new MATERIAL_DESCRIPTION[base_size][base_size][height];

            init();
        }
    }

    public Cell(int base_size, int height, int wall_thickness,
                Material wall, Material air, Material floor,
                Material light_floor, Material ceiling) throws SizesException {
        if(base_size < 3 || height < 1) {
            throw new SizesException("This are not valid sizes for Cells in a minecraft labyrinth!");
        } else {
            this.base_size = base_size;
            this.height = height;
            this.walls_thickness = wall_thickness;
            this.wall = wall;
            this.air = air;
            this.floor = floor;
            this.light_floor = light_floor;
            this.ceiling = ceiling;
            blocks = new MATERIAL_DESCRIPTION[base_size][base_size][height];

            init();
        }
    }

//    public Cell(int base_size, int height, Material wall, Material floor, Material air, Material light_floor) throws SizesException {
//        if(base_size < 3 || height < 1) {
//            throw new SizesException("This are not valid sizes for Cells in a minecraft labyrinth!");
//        } else {
//            this.base_size = base_size;
//            this.height = height;
//            this.wall = (wall != null) ? wall : Material.BLACK_WOOL;
//            this.floor = (floor != null) ? floor : Material.RED_WOOL;
//            this.air = (air != null) ? air : Material.AIR;
//            this.light_floor = (light_floor != null) ? light_floor : Material.SEA_LANTERN;
//            blocks = new MATERIAL_DESCRIPTION[base_size][base_size][height];
//
//            init();
//        }
//    }

    public int getHeight() { return height; }
    public Material getCeiling() { return ceiling; }

    private void init() {
        for(int x = 0; x < blocks.length; ++x) {
            for(int y = 0; y < blocks[x].length; ++y) {
                for(int z = 0; z < blocks[x][y].length; ++z) {
                    if(z == 0) {
                        blocks[x][y][z] = MATERIAL_DESCRIPTION.FLOOR;
                    } else if(z == height-1) {
                        blocks[x][y][z] = MATERIAL_DESCRIPTION.CEILING;
                    } else {
                        blocks[x][y][z] = MATERIAL_DESCRIPTION.WALL;
                    }
                }
            }
        }

        if(base_size % 2 == 0) {
            for(int x = base_size/2-1; x < base_size/2+1; ++x) {
                for(int y = base_size/2-1; y < base_size/2+1; ++y) {
                    blocks[x][y][0] = MATERIAL_DESCRIPTION.LIGHT;
                }
            }
        } else {
            blocks[base_size / 2][base_size / 2][0] = MATERIAL_DESCRIPTION.LIGHT;
        }
    }

    // SET & GET -------------------------------------------------------------------------------------------------------
    protected void setVisited(boolean state) {
        visited = state;
    }

    public boolean isVisited() { return visited; }

    public int getBase_size() { return base_size; }
    // OTHER METHODS ---------------------------------------------------------------------------------------------------
    public Material[][][] getComposition() {
        Material[][][] composition = new Material[base_size][base_size][height];

        for(int x = 0; x < base_size; ++x) {
            for(int y = 0; y < base_size; ++y) {
                for(int z = 0; z < height; ++z) {
                    composition[x][y][z] = getMaterialFromDescription(blocks[x][y][z]);
                }
            }
        }

        return composition;
    }

    protected Material getMaterialFromDescription(MATERIAL_DESCRIPTION description) {
        switch(description) {
            case WALL:
                return wall;
            case FLOOR:
                return floor;
            case AIR:
                return air;
            case LIGHT:
                return light_floor;
            case CEILING:
                return ceiling;
            default:
                throw new Error("This should not happen!");
        }
    }

    public void dig_from(Directions.DIRECTION d) {
        int x_start, x_end;
        int y_start, y_end;
        int z_start = 1, z_end = height-1; // starting from 1 because we never want to remove the floor

        switch(d) {
            case NORTH:
                x_start = 0;
                x_end = base_size-walls_thickness;
                y_start = walls_thickness;
                y_end = base_size-walls_thickness;
                break;
            case WEST:
                x_start = walls_thickness;
                x_end = base_size-walls_thickness;
                y_start = walls_thickness;
                y_end = base_size;
                break;
            case SOUTH:
                x_start = walls_thickness;
                x_end = base_size;
                y_start = walls_thickness;
                y_end = base_size-walls_thickness;
                break;
            default: // EAST
                x_start = walls_thickness;
                x_end = base_size-walls_thickness;
                y_start = 0;
                y_end = base_size-walls_thickness;
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

    public String layer_toString(int vertical_layer) throws Exception {
        String str = "";

        for(int x = 0; x < base_size; ++x) {
            for(int y = 0; y < base_size; ++y) {
                switch(blocks[x][y][vertical_layer]) {
                    case WALL:
                        str += "W";
                        break;
                    case AIR:
                        str += "A";
                        break;
                    case FLOOR:
                        str += "F";
                        break;
                    default:
                        str += "X";
                        throw new Exception("Exception: some string descriptions are missing!!");
                }
            }
            str += "\n";
        }

        return str;
    }
}
