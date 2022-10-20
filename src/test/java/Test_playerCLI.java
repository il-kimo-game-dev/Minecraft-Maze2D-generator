import kimo.com.PlayerCLI;
import org.bukkit.Material;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class Test_playerCLI {
    @Test
    public void test_Maze2D_input_regex() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        PlayerCLI cli = new PlayerCLI(null);
        boolean method_found = false;
        Method parse_settings_method = null;
        Constructor<PlayerCLI.Maze_settings> constructor;
        PlayerCLI.Maze_settings innerClass;

        try {
            constructor = PlayerCLI.Maze_settings.class.getDeclaredConstructor(PlayerCLI.class);
            constructor.setAccessible(true);
            innerClass = constructor.newInstance(cli);

            Class<?>[] c = PlayerCLI.class.getClasses();
            for(int i = 0; i < c.length; ++i) {
                parse_settings_method = c[i].getDeclaredMethod("parse_settings", String.class);

                if(parse_settings_method.getName().equals("parse_settings")) {
                    method_found = true;
                    break;
                }
            }
            if(method_found) {
                parse_settings_method.setAccessible(true);
                assertEquals(false, parse_settings_method.invoke(innerClass, ""));
                assertEquals(false, parse_settings_method.invoke(innerClass, "sus"));
                assertEquals(false, parse_settings_method.invoke(innerClass, "Labyrinth2D()"));
                assertEquals(false, parse_settings_method.invoke(innerClass, "5, 5"));
                assertEquals(true, parse_settings_method.invoke(innerClass, "Labyrinth2D(5, 4)"));
                assertEquals(false, parse_settings_method.invoke(innerClass, "Labyrinth2D( , )"));
                assertEquals(true, parse_settings_method.invoke(innerClass, "Labyrinth2D(5, 5, 5, 4, 1)"));
                assertEquals(false, parse_settings_method.invoke(innerClass, "Labyrinth2D(5, 5, 5, 4, 1, )"));
                assertEquals(false, parse_settings_method.invoke(innerClass, "Labyrinth2D(5, 5, 5, 4, n))"));
                assertEquals(true, parse_settings_method.invoke(innerClass, "Labyrinth2D(5, 5, COBBLESTONE)"));
                assertEquals(true, parse_settings_method.invoke(innerClass, "Labyrinth2D(5, 5, COBBLESTONE, STONE, AIR, GLOWSTONE)"));
                assertEquals(false, parse_settings_method.invoke(innerClass, "Labyrinth2D(5, 5, 3, STONE, AIR, GLOWSTONE)"));
                assertEquals(true, parse_settings_method.invoke(innerClass, "Labyrinth2D(5, 5, 5, 4, 2, COBBLESTONE, STONE, AIR, GLOWSTONE)"));
                assertEquals(true, parse_settings_method.invoke(innerClass, "Labyrinth2D(5, 5, 5, 4, 2, COBBLESTONE, STONE, AIR, GLOWSTONE, AIR)"));
                assertEquals(true, parse_settings_method.invoke(innerClass, "Labyrinth2D(5, 5, 5, 4, 2, COBBLESTONE, STONE)"));
                assertEquals(false, parse_settings_method.invoke(innerClass, "Labyrinth(5, 5, 5, 4, 2, COBBLESTONE, STONE)"));
                assertEquals(true, parse_settings_method.invoke(innerClass, "Maze2D(5, 5, 5, 4, 2, COBBLESTONE, STONE)"));
                assertEquals(false, parse_settings_method.invoke(innerClass, "Maze(5, 5, 5, 4, 2, COBBLESTONE, STONE)"));
            } else {
                assertEquals(false, true); // wanting to generate a Test error to make people check
            }
        } catch(Exception e) {
            throw e;
        }
    }

    @Test
    public void test_parse_material() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        PlayerCLI cli = new PlayerCLI(null);
        boolean method_found = false;
        Method parse_settings_method = null;
        Constructor<PlayerCLI.Maze_settings> constructor;
        PlayerCLI.Maze_settings innerClass;

        try {
            constructor = PlayerCLI.Maze_settings.class.getDeclaredConstructor(PlayerCLI.class);
            constructor.setAccessible(true);
            innerClass = constructor.newInstance(cli);

            Class<?>[] c = PlayerCLI.class.getClasses();
            for(int i = 0; i < c.length; ++i) {
                parse_settings_method = c[i].getDeclaredMethod("parse_material", String.class);

                if(parse_settings_method.getName().equals("parse_material")) {
                    method_found = true;
                    break;
                }
            }
            if(method_found) {
                parse_settings_method.setAccessible(true);
                assertEquals(Material.COBBLESTONE,  parse_settings_method.invoke(innerClass, "COBBLESTONE"));
                assertNotEquals(Material.COBBLESTONE,  parse_settings_method.invoke(innerClass, "STONE"));
                assertNull(parse_settings_method.invoke(innerClass, "COSO"));
            } else {
                assertEquals(false, true); // wanting to generate a Test error to make people check
            }
        } catch(Exception e) {
            throw e;
        }
    }
}
