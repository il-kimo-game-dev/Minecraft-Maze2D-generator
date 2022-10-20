package maze_building;

import kimo.com.MazeGeneratorPluginEntry;
import maze2D_logic.Directions;
import maze2D_logic.Maze2D;
import maze2D_logic.MazeCell;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Maze2D_Builder {
    private Player player;
    private MazeGeneratorPluginEntry plugin;

    public Maze2D_Builder(MazeGeneratorPluginEntry plugin, Player player) {
        this.player = player;
        this.plugin = plugin;
    }

    public void build(Maze2D maze, int cell_base_size, int cell_height,
                      int cell_walls_thickness, Material wall, Material air,
                      Material floor, Material floor_light, Material ceiling) throws Exception {
        boolean res = false;
        final String[] exception_message = {"Unknown exception occurred during the generation of the maze"};

        if(!valid_parameters(cell_base_size, cell_height, cell_walls_thickness)) {
            throw new Exception("Maze dimensions are not valid for this builder");
        }

        if(maze == null) {
            exception_message[0] = "Maze is null";
        } else if(!maze.isGenerated()) {
            exception_message[0] = "Maze is not generated";
        } else {
            Runnable task = () -> {
                try {
                    place_maze_blocks(maze, cell_base_size, cell_height, cell_walls_thickness,
                                      wall, air, floor, floor_light, ceiling);
                } catch (Exception e) {
                    exception_message[0] = e.getMessage();
                }
            };

            try {
                Bukkit.getScheduler().runTask(plugin, task);
                res = true;
            } catch(Exception e) {
                exception_message[0] = e.getMessage();
            }
        }

        if(!res) {
            throw new Exception(exception_message[0]);
        }
    }

    private void place_maze_blocks(Maze2D maze, int cell_base_size, int cell_height,
                                   int cell_walls_thickness, Material wall, Material air,
                                   Material floor, Material floor_light, Material ceiling) throws Exception {
        Location sender_location;
        MazeCell maze_cell;
        Cell cell_to_build;

        try {
            sender_location = player.getLocation();
            for(int x = 0; x < maze.getSize_x(); ++x) {
                for(int y = 0; y < maze.getSize_y(); ++y) {
                    maze_cell = maze.getMazeCell(x, y);
                    cell_to_build = new Cell(cell_base_size, cell_height, cell_walls_thickness,
                                             wall, air, floor, floor_light, ceiling);

                    if(maze_cell != null) {
                        if(maze_cell.has_passage_to(Directions.DIRECTION.NORTH)) {
                            cell_to_build.dig_from(Directions.DIRECTION.NORTH);
                        }
                        if(maze_cell.has_passage_to(Directions.DIRECTION.WEST)) {
                            cell_to_build.dig_from(Directions.DIRECTION.WEST);
                        }
                        if(maze_cell.has_passage_to(Directions.DIRECTION.SOUTH)) {
                            cell_to_build.dig_from(Directions.DIRECTION.SOUTH);
                        }
                        if(maze_cell.has_passage_to(Directions.DIRECTION.EAST)) {
                            cell_to_build.dig_from(Directions.DIRECTION.EAST);
                        }

                        build_maze_cell(cell_to_build, sender_location, cell_base_size, x, y);
                    } else {
                        throw new Exception("Exception: maze cell for coords <x, y>=<" + x + ", " + y + "> is null");
                    }
                }
            }
        } catch(Exception e) {
            throw e;
        }
    }

    public void build_maze_cell(Cell cell_to_build, Location sender_location, int cell_base_size, int x, int y) {
        Material[][][] cell_composition = cell_to_build.getComposition();
        Material ceiling;
        int ceiling_height;

        if(cell_composition != null) {
            for(int i = 0; i < cell_composition.length; ++i) {
                for(int j = 0; j < cell_composition[i].length; ++j) {
                    for(int k = 0; k < cell_composition[i][j].length; ++k) {
                        Block block = (
                                new Location(player.getWorld(),
                                        sender_location.getBlockX()+cell_base_size+cell_base_size*x+i,
                                        sender_location.getBlockY()+k,
                                        sender_location.getBlockZ()+cell_base_size*y+j)
                        ).getBlock();
                        block.setType(cell_composition[i][j][k]);
                    }
                }
            }
        }
    }

    public boolean valid_parameters(int cell_base_size, int cell_height, int cell_walls_thickness) {
        boolean res = true;

        if(cell_base_size < 3) {
            res = false;
        }

        if(cell_height < 3) {
            res = false;
        }

        if(cell_walls_thickness < 1 || (2*cell_walls_thickness) > (cell_base_size-1)) {
            res = false;
        }

        return res;
    }
}
