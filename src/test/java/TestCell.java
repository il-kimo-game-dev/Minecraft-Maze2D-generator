
import maze_building.Cell;
import maze2D_logic.Directions;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestCell {
    @Test
    public void test_CellDigging() throws Exception {
        Cell cell_01;
        String str, expected;

        cell_01 = new Cell(5, 4);
        str = cell_01.layer_toString(1);
        expected = "WWWWW\n" +
                   "WWWWW\n" +
                   "WWWWW\n" +
                   "WWWWW\n" +
                   "WWWWW\n";

        assertEquals(expected, str);

        cell_01.dig_from(Directions.DIRECTION.NORTH);
        str = cell_01.layer_toString(1);
        expected = "WAAAW\n" +
                   "WAAAW\n" +
                   "WAAAW\n" +
                   "WAAAW\n" +
                   "WWWWW\n";

        assertEquals(expected, str);

        cell_01 = new Cell(5, 4);
        cell_01.dig_from(Directions.DIRECTION.WEST);
        str = cell_01.layer_toString(1);
        expected = "WWWWW\n" +
                   "WAAAA\n" +
                   "WAAAA\n" +
                   "WAAAA\n" +
                   "WWWWW\n";
        assertEquals(expected, str);

        cell_01 = new Cell(5, 4);
        cell_01.dig_from(Directions.DIRECTION.SOUTH);
        str = cell_01.layer_toString(1);
        expected = "WWWWW\n" +
                   "WAAAW\n" +
                   "WAAAW\n" +
                   "WAAAW\n" +
                   "WAAAW\n";
        assertEquals(expected, str);

        cell_01 = new Cell(5, 4);
        cell_01.dig_from(Directions.DIRECTION.EAST);
        str = cell_01.layer_toString(1);
        expected = "WWWWW\n" +
                   "AAAAW\n" +
                   "AAAAW\n" +
                   "AAAAW\n" +
                   "WWWWW\n";
        assertEquals(expected, str);
    }
}
