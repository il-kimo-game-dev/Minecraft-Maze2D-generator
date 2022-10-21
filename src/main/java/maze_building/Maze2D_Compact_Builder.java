package maze_building;

import kimo.com.MazeGeneratorPluginEntry;
import kimo.com.SizesException;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Maze2D_Compact_Builder extends Maze2D_Builder {
    public Maze2D_Compact_Builder(MazeGeneratorPluginEntry plugin, Player player) {
        super(plugin, player);
    }

    @Override
    protected Cell get_building_cell(int cell_base_size, int cell_height,
                                     int cell_walls_thickness, Material wall, Material air,
                                     Material floor, Material floor_light, Material ceiling) throws SizesException {
        return new Cell_CompactMaze(cell_base_size, cell_height, cell_walls_thickness,
                wall, air, floor, floor_light, ceiling);
    }

    @Override
    public void build_maze_cell(int maze_size_x, int maze_size_y, Cell cell_to_build, Location sender_location, int cell_base_size, int x, int y) {
        super.build_maze_cell(maze_size_x, maze_size_y, cell_to_build, sender_location, cell_base_size, x, y);

        // build perimeter for external raw
        if(x == 0) {
            for(int j = 0; j < cell_base_size; ++j) {
                for(int k = 0; k < cell_to_build.getHeight(); ++k) {
                    for(int i = 1; i < cell_to_build.walls_thickness+1; ++i) {
                        Block block = (
                                new Location(player.getWorld(),
                                        sender_location.getBlockX()+cell_base_size-i,
                                        sender_location.getBlockY()+k,
                                        sender_location.getBlockZ()+cell_base_size*y+j)
                        ).getBlock();

                        if(k == cell_to_build.getHeight()-1) {
                            block.setType(cell_to_build.ceiling);
                        } else {
                            block.setType(cell_to_build.wall);
                        }
                    }
                }
            }

            // create entrance
            if(y == 0) {
                for(int j = cell_to_build.walls_thickness; j < cell_base_size; ++j) {
                    for(int k = 0; k < cell_to_build.getHeight(); ++k) {
                        for(int i = 1; i < cell_to_build.walls_thickness+1; ++i) {
                            Block block = (
                                    new Location(player.getWorld(),
                                            sender_location.getBlockX()+cell_base_size-i,
                                            sender_location.getBlockY()+k,
                                            sender_location.getBlockZ()+cell_base_size*y+j)
                            ).getBlock();

                            if(k == cell_to_build.getHeight()-1) {
                                block.setType(cell_to_build.ceiling);
                            } else if(k == 0) {
                                block.setType(cell_to_build.floor);
                            } else {
                                block.setType(cell_to_build.air);
                            }
                        }
                    }
                }
            }
        }

        // build perimeter for external raw
        if(y == maze_size_y-1) {
            for(int i = 0; i < cell_base_size; ++i) {
                for(int k = 0; k < cell_to_build.getHeight(); ++k) {
                    for(int j = 0; j < cell_to_build.walls_thickness; ++j) {
                        Block block = (
                                new Location(player.getWorld(),
                                        sender_location.getBlockX()+cell_base_size+cell_base_size*x+i,
                                        sender_location.getBlockY()+k,
                                        sender_location.getBlockZ()+cell_base_size*(y+1)+j)
                        ).getBlock();

                        if(k == cell_to_build.getHeight()-1) {
                            block.setType(cell_to_build.ceiling);
                        } else {
                            block.setType(cell_to_build.wall);
                        }
                    }
                }
            }
        }

        // build perimeter x, y angle
        if(x == 0 && y == maze_size_y-1) {
            for(int i = 1; i < cell_to_build.walls_thickness+1; ++i) {
                for(int k = 0; k < cell_to_build.getHeight(); ++k) {
                    for(int j = 0; j < cell_to_build.walls_thickness; ++j) {
                        Block block = (
                                new Location(player.getWorld(),
                                        sender_location.getBlockX()-i+cell_base_size,
                                        sender_location.getBlockY()+k,
                                        sender_location.getBlockZ()+cell_base_size*(y+1)+j)
                        ).getBlock();

                        if(k == cell_to_build.getHeight()-1) {
                            block.setType(cell_to_build.ceiling);
                        } else {
                            block.setType(cell_to_build.wall);
                        }
                    }
                }
            }
        }
    }

    @Override
    protected boolean valid_parameters(int cell_base_size, int cell_height, int cell_walls_thickness) {
        boolean res = true;

        if(cell_base_size < 3) {
            res = false;
        }

        if(cell_height < 3) {
            res = false;
        }

        if(cell_walls_thickness < 0 || cell_walls_thickness > cell_base_size) {
            res = false;
        }

        return res;
    }
}
