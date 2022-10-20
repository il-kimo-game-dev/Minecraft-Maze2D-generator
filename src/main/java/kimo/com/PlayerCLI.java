package kimo.com;

import maze2D_logic.DFS_Algorithm;
import maze2D_logic.GenerationAlgorithm;
import maze2D_logic.Maze2D;
import maze_building.Maze2D_Builder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerCLI implements Listener {
    private MazeGeneratorPluginEntry plugin;

    public PlayerCLI(MazeGeneratorPluginEntry plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void AsyncChatEvent(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        MinecraftPlayerLogger playerLogger = new MinecraftPlayerLogger(plugin.getName(), player);

        if(sendCommand(player, e.getMessage())) {
            playerLogger.log("Command executed");
        } else {
            playerLogger.log("Command NOT executed successfully");
        }
    }

    public boolean sendCommand(Player sender, String commandStr) {
        boolean result = false;
        MinecraftPlayerLogger playerLogger = new MinecraftPlayerLogger(plugin.getName(), sender);
        Maze_settings settings = new Maze_settings(new DFS_Algorithm());
        Maze2D maze;

        if(settings.parse_settings(commandStr)) {
            playerLogger.log("Building maze...");

            try {
                maze = new Maze2D(settings.maze_size_x, settings.maze_size_y, settings.algorithm);
                maze.generate();
                playerLogger.log("number of maze crossroads:" + maze.count_crossroads());

                (new Maze2D_Builder(plugin, sender))
                        .build(
                                maze, settings.cell_base_size,
                                settings.cell_height, settings.cell_walls_thickness,
                                settings.wall, settings.air, settings.floor,
                                settings.light_floor, settings.ceiling
                        );
                result = true;
            } catch(Exception e) {
                playerLogger.log(e.getMessage());
            }
        } else if(commandStr.contains("Labyrinth(")) {
            playerLogger.log("Command not accepted");
        } else {
            // nothing
        }

        return result;
    }

    public class Maze_settings {
        int maze_size_x, maze_size_y, cell_base_size, cell_height, cell_walls_thickness;
        Material wall, air, floor, light_floor, ceiling;

        GenerationAlgorithm algorithm;

        public Maze_settings() {
            maze_size_x = 5;
            maze_size_y = 5;
            cell_base_size = 5;
            cell_height = 5;
            cell_walls_thickness = 1;
            wall = Material.BLACK_CONCRETE;
            air = Material.AIR;
            floor = Material.WHITE_CONCRETE;
            light_floor = Material.SEA_LANTERN;
            ceiling = Material.AIR;

            algorithm = new DFS_Algorithm();
        }

        public Maze_settings(GenerationAlgorithm algorithm) {
            this();

            this.algorithm = algorithm;
        }

        private Material parse_material(String material_name) {
            Material res = null;

            for (Material material : Material.values()) {
                if (material.name().equalsIgnoreCase(material_name)) {
                    res = material;
                }
            }

            return res;
        }

        /**
         * Accepts a string containing specifics for the
         * labyrinth creation like Labyrinth2D(5, 5, 5, 4), or Maze2D(5, 5).
         * You can also specify materials for walls, floor, air, lights and ceiling!
         * @param settings
         */
        private boolean parse_settings(String settings) {
            boolean parsing_successful = false;
            String[] args;
            Pattern pattern1 = Pattern.compile("^Labyrinth2D\\((([0-9]+, )|([0-9]+, ){4})[0-9]+(, [a-zA-Z._]+){0,5}\\)$");
            Pattern pattern2 = Pattern.compile("^Maze2D\\((([0-9]+, )|([0-9]+, ){4})[0-9]+(, [a-zA-Z._]+){0,5}\\)$");
            Matcher matcher1 = pattern1.matcher(settings);
            Matcher matcher2 = pattern2.matcher(settings);
            boolean match = false;
            boolean string_found = false;
            int strings_assigned = 0;

            if(matcher1.matches()) {
                settings = settings.replace("Labyrinth2D(", "");
                match = true;
            } else if(matcher2.matches()) {
                settings = settings.replace("Maze2D(", "");
                match = true;
            }

            if(match) {
                settings = settings.replace(")", "");
                args = settings.split(", ");

                if(args.length >= 2) {
                    maze_size_x = Integer.parseInt(args[0]);
                    maze_size_y = Integer.parseInt(args[1]);
                }
                if(args.length > 2) {
                    for(int i = 2; i < args.length; ++i) {
                        if(!string_found && is_number(args[i])) {
                            switch(i) {
                                case 2:
                                    cell_base_size = Integer.parseInt(args[i]);
                                    break;
                                case 3:
                                    cell_height = Integer.parseInt(args[i]);
                                    break;
                                case 4:
                                    cell_walls_thickness = Integer.parseInt(args[i]);
                                    break;
                                default:
                                    throw new Error("Not possible to parse the settings, probably the pattern has changed or is wrong");
                            }
                        } else {
                            string_found = true;

                            switch(strings_assigned++) {
                                case 0:
                                    wall = parse_material(args[i]);
                                    break;
                                case 1:
                                    floor = parse_material(args[i]);
                                    break;
                                case 2:
                                    air = parse_material(args[i]);
                                    break;
                                case 3:
                                    light_floor = parse_material(args[i]);
                                    break;
                                case 4:
                                    ceiling = parse_material(args[i]);
                                    break;
                                default:
                                    throw new Error("Not possible to parse the settings, probably the pattern has changed or is wrong");
                            }
                        }
                    }
                }

                parsing_successful = true;
            }

            return parsing_successful;
        }

        public boolean is_number(String str) {
            boolean res = false;

            try {
                Integer.parseInt(str);
                res = true;
            } catch(NumberFormatException ignored) {}

            return res;
        }
    }
}



















