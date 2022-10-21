import maze2D_logic.Directions;
import maze_building.Cell;
import maze_building.Cell_CompactMaze;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Test_Cell_CompactMaze {
    @Test
    public void test_CellDigging() throws Exception {
        Cell cell_01;
        String str, expected;

        cell_01 = new Cell_CompactMaze(5, 4);
        str = cell_01.layer_toString(1);
        expected = "WWWWW\n" +
                   "WWWWW\n" +
                   "WWWWW\n" +
                   "WWWWW\n" +
                   "WWWWW\n";

        assertEquals(expected, str);

        cell_01.dig_from(Directions.DIRECTION.NORTH);
        str = cell_01.layer_toString(1);
        expected = "WAAAA\n" +
                   "WAAAA\n" +
                   "WAAAA\n" +
                   "WAAAA\n" +
                   "WWWWW\n";

        assertEquals(expected, str);

        cell_01 = new Cell_CompactMaze(5, 4);
        cell_01.dig_from(Directions.DIRECTION.WEST);
        str = cell_01.layer_toString(1);
        expected = "WAAAA\n" +
                   "WAAAA\n" +
                   "WAAAA\n" +
                   "WAAAA\n" +
                   "WWWWW\n";
        assertEquals(expected, str);

        cell_01 = new Cell_CompactMaze(5, 4);
        cell_01.dig_from(Directions.DIRECTION.SOUTH);
        str = cell_01.layer_toString(1);
        expected = "WAAAA\n" +
                   "WAAAA\n" +
                   "WAAAA\n" +
                   "WAAAA\n" +
                   "WAAAA\n";
        assertEquals(expected, str);

        cell_01 = new Cell_CompactMaze(5, 4);
        cell_01.dig_from(Directions.DIRECTION.EAST);
        str = cell_01.layer_toString(1);
        expected = "AAAAA\n" +
                   "AAAAA\n" +
                   "AAAAA\n" +
                   "AAAAA\n" +
                   "WWWWW\n";
        assertEquals(expected, str);
    }
}
